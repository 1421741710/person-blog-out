package com.itluo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itluo.constant.MessageConstant;
import com.itluo.constant.ProcessTypeConstant;
import com.itluo.constant.StatusConstant;
import com.itluo.context.BaseContext;
import com.itluo.dto.ProcessPageQueryDTO;
import com.itluo.dto.ProcessUpDTO;
import com.itluo.entity.Notifications;
import com.itluo.entity.Process;
import com.itluo.entity.UserInfo;
import com.itluo.enumeration.MessageType;
import com.itluo.exception.AccountNotFoundException;
import com.itluo.exception.DeletionNotAllowedException;
import com.itluo.exception.ProcessIDNotFoundException;
import com.itluo.exception.WebSocketProcessingException;
import com.itluo.json.JacksonObjectMapper;
import com.itluo.mapper.NotificationsMapper;
import com.itluo.mapper.ProcessMapper;
import com.itluo.mapper.UserInfoMapper;
import com.itluo.result.PageResult;
import com.itluo.result.WebSocketMessage;
import com.itluo.service.ProcessService;
import com.itluo.utils.LocalDateTimeUtils;
import com.itluo.vo.ProcessVO;
import com.itluo.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.LocaleUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;
import java.util.List;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessMapper processMapper;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private NotificationsMapper notificationsMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 分页查询
     * @param processPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(ProcessPageQueryDTO processPageQueryDTO) {
        PageHelper.startPage(processPageQueryDTO.getPage(),processPageQueryDTO.getPageSize());
        Page<ProcessVO> page = processMapper.pageQuery(processPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 修改审核状态
     * @param id
     */
    @Override
    public void updateStatus(Long id) {
        Long adminId = BaseContext.getCurrentId();
        Process process = processMapper.findByProcessId(id);
        if (process == null){
            throw new ProcessIDNotFoundException(MessageConstant.STUDENT);
        }
        if (process.getStatus().equals(StatusConstant.DISABLE)){
            throw new ProcessIDNotFoundException(MessageConstant.AUDITED);
        }
        boolean validate = validate(adminId, id);
        if (!validate){
            Process pro = Process.builder()
                    .id(id)
                    .adminId(adminId)
                    .status(StatusConstant.THREE)
                    .build();
            processMapper.update(pro);
            redisTemplate.opsForValue().set(String.valueOf(id),pro.getAdminId());
        }
    }

    /**
     * 验证
     * @param adminId
     * @param id
     */
    private boolean validate(Long adminId,Long id){
        Long pros = (Long) redisTemplate.opsForValue().get(String.valueOf(id));
        if (pros != null){
            if (!pros.equals(adminId)){
                throw new ProcessIDNotFoundException(MessageConstant.UNDER_REVIEW);
            }
            return true;
        }
        return false;
    }

    /**
     * 提交审核
     * @param processUpDTO
     */
    @Override
    @Transactional(rollbackFor = {ProcessIDNotFoundException.class,AccountNotFoundException.class,Exception.class})
    public void update(ProcessUpDTO processUpDTO) {
        Long adminId = BaseContext.getCurrentId();
        Long id = processUpDTO.getId();
        Process process = processMapper.findByProcessId(id);
        if (process == null){
            throw new ProcessIDNotFoundException(MessageConstant.STUDENT);
        }
        if (process.getStatus().equals(StatusConstant.DISABLE)){
            throw new ProcessIDNotFoundException(MessageConstant.AUDITED);
        }
        boolean validate = validate(adminId, id);
        if (validate){
            //提交审核单并删除缓存
            Process pros = Process.builder()
                    .adminId(adminId)
                    .status(StatusConstant.DISABLE)
                    .build();
            BeanUtils.copyProperties(processUpDTO,pros);
            processMapper.update(pros);
            redisTemplate.delete(String.valueOf(id));
            //更新用户审核状态
            updateUserInfo(process);
            //发送WebSocket通知并把通知存储在数据库中
            sendWebSocket(process,pros);
        }
    }

    /**
     * 更新用户审核状态
     * @param process
     */
    private void updateUserInfo(Process process){
        UserInfo byUserId = userInfoMapper.findByUserId(process.getUserId());
        if (byUserId == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        UserInfo userInfo = UserInfo.builder()
                .id(byUserId.getId())
                .status(StatusConstant.ENABLE)
                .build();
        if (process.getType().equals(StatusConstant.ENABLE)){
            userInfo.setName(process.getContent());
        }else{
            userInfo.setImg(process.getContent());
        }
        userInfoMapper.update(userInfo);
    }

    /**
     * 发送WebSocket通知并把通知存储在数据库中
     * @param process
     * @param pros
     */
    private void sendWebSocket(Process process,Process pros){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(process.getType().equals(StatusConstant.ENABLE) ? ProcessTypeConstant.TYPE_ONE : ProcessTypeConstant.TYPE_TWO);
        stringBuilder.append("审核");
        stringBuilder.append(pros.getResult().equals(StatusConstant.ENABLE) ? "未通过" : "通过");
        stringBuilder.append(",");
        stringBuilder.append(pros.getRemark());
        Notifications notifications = Notifications.builder()
                .userId(process.getUserId())
                .type(StatusConstant.ENABLE)
                .content(stringBuilder.toString())
                .status(StatusConstant.DISABLE)
                .build();
        notificationsMapper.insertNotifications(notifications);
        JacksonObjectMapper jacksonObjectMapper = new JacksonObjectMapper();
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setType(MessageType.SYSTEM_NOTICE);
        try {
            String str = jacksonObjectMapper.writeValueAsString(notifications);
            webSocketMessage.setMessage(str);
            String s = jacksonObjectMapper.writeValueAsString(webSocketMessage);
            Session session = webSocketServer.getSession(String.valueOf(process.getUserId()));
            webSocketServer.sendToOneClientIfOnline(session,s);
        } catch (JsonProcessingException e) {
            // 记录异常信息或抛出自定义异常
            log.error("Error processing JSON for WebSocket message", e);
            throw new WebSocketProcessingException("处理WebSocket消息的JSON时出错");
        }
    }

}
