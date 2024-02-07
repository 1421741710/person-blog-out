package com.itluo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itluo.constant.MessageConstant;
import com.itluo.constant.StatusConstant;
import com.itluo.context.BaseContext;
import com.itluo.dto.UserInfoPageQueryDTO;
import com.itluo.dto.UserInfoUpDTO;
import com.itluo.entity.Process;
import com.itluo.entity.User;
import com.itluo.entity.UserInfo;
import com.itluo.enumeration.MessageType;
import com.itluo.exception.AccountNotFoundException;
import com.itluo.exception.UserInfoProcessException;
import com.itluo.json.JacksonObjectMapper;
import com.itluo.mapper.ProcessMapper;
import com.itluo.mapper.UserInfoMapper;
import com.itluo.mapper.UserMapper;
import com.itluo.result.PageResult;
import com.itluo.result.WebSocketMessage;
import com.itluo.service.UserInfoService;
import com.itluo.vo.UserInfoNameVO;
import com.itluo.vo.UserInfoVO;
import com.itluo.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private WebSocketServer webSocketServer;

    @Autowired
    private UserMapper userMapper;


    /**
     * 分页查询
     * @param userInfoPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(UserInfoPageQueryDTO userInfoPageQueryDTO) {
        PageHelper.startPage(userInfoPageQueryDTO.getPage(),userInfoPageQueryDTO.getPageSize());
        Page<UserInfo> page = userInfoMapper.pageQuery(userInfoPageQueryDTO);
        Page<UserInfoNameVO> pages = new Page<>();
        List<Long> ids = new ArrayList<>();
        for (UserInfo userInfo : page){
            ids.add(userInfo.getUserId());
        }
        List<User> userList = userMapper.batchSelectUsers(ids);
        for (UserInfo userInfo : page){
            Long id = userInfo.getUserId();
            for (User user : userList){
                if (id.equals(user.getId())){
                    UserInfoNameVO userInfoNameVO = new UserInfoNameVO();
                    BeanUtils.copyProperties(userInfo,userInfoNameVO);
                    userInfoNameVO.setUsername(user.getUsername());
                    pages.add(userInfoNameVO);
                }
            }
        }
        return new PageResult(page.getTotal(),pages.getResult());
    }

    /**
     * 修改用户信息
     * @param userInfoUpDTO
     */
    @Override
    public void update(UserInfoUpDTO userInfoUpDTO) {
        Long id = userInfoUpDTO.getId();
        verify(id);
        UserInfo userInfos = new UserInfo();
        BeanUtils.copyProperties(userInfoUpDTO,userInfos);
        userInfoMapper.update(userInfos);
    }

    /**
     * 获取用户信息
     * @return
     */
    @Override
    public UserInfoVO findByUserInfo() {
        Long id = BaseContext.getCurrentId();
        UserInfoVO byUserInfoId = userInfoMapper.findByUserInfoId(id);
        if (byUserInfoId == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        return byUserInfoId;
    }

    /**
     * 验证
     * @param id
     */
    private UserInfo verify(Long id){
        UserInfo userInfo = userInfoMapper.findByUserInfoIds(id);
        if (userInfo == null){
            throw new AccountNotFoundException(MessageConstant.STUDENT);
        }
        return userInfo;
    }

    /**
     * 用户修改用户信息
     * @param userInfoUpDTO
     */
    @Override
    public void updateUserInfo(UserInfoUpDTO userInfoUpDTO) {
        Long userId = BaseContext.getCurrentId();
        Long id = userInfoUpDTO.getId();
        UserInfo verify = verify(id);
        UserInfoUpDTO determine = determine(verify, userInfoUpDTO);
        BeanUtils.copyProperties(determine,userInfoUpDTO);
        String name = userInfoUpDTO.getName();
        String img = userInfoUpDTO.getImg();
        if (name != null || img != null){
            //新增审核单
            if (verify.getStatus().equals(StatusConstant.DISABLE)){
                throw new UserInfoProcessException(MessageConstant.UNDER_REVIEW);
            }
            UserInfo userInfo = UserInfo.builder()
                    .userId(userId)
                    .name(name)
                    .img(img)
                    .build();
            Process process = addProcess(userInfo);
            //修改用户信息状态
            updateUserInfo(verify);
            //发送WebSocket消息
            sendWebSocket(process);
        }
        if (userInfoUpDTO.getPhone() != null || userInfoUpDTO.getAddress() != null){
            UserInfo userInfo = UserInfo.builder()
                    .id(userInfoUpDTO.getId())
                    .phone(userInfoUpDTO.getPhone())
                    .address(userInfoUpDTO.getAddress())
                    .build();
            userInfoMapper.update(userInfo);
        }

    }

    /**
     * 新增审核单
     * @param userInfo
     * @return
     */
    private Process addProcess(UserInfo userInfo){
        Long status = userInfo.getName() != null ? StatusConstant.ENABLE : StatusConstant.DISABLE;
        String str = status.equals(StatusConstant.ENABLE) ? userInfo.getName() : userInfo.getImg();
        Process process = Process.builder()
                .userId(userInfo.getUserId())
                .type(status)
                .content(str)
                .status(StatusConstant.ENABLE)
                .build();
        processMapper.insert(process);
        return process;
    }

    /**
     * 修改用户信息状态
     * @param verify
     */
    private void updateUserInfo(UserInfo verify){
        UserInfo userInfo = UserInfo.builder()
                .id(verify.getId())
                .status(StatusConstant.DISABLE)
                .build();
        userInfoMapper.update(userInfo);
    }

    /**
     * 发送WebSocket消息
     * @param process
     */
    private void sendWebSocket(Process process){
        JacksonObjectMapper objectMapper = new JacksonObjectMapper();
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setType(MessageType.SYSTEM_NOTICE);
        Map<String,Object> objectMap = new HashMap<>(2);
        objectMap.put("content","有新的审核单,请及时处理!");
        objectMap.put("createTime",process.getCreateTime());
        try {
            String data = objectMapper.writeValueAsString(objectMap);
            webSocketMessage.setMessage(data);
            String s = objectMapper.writeValueAsString(webSocketMessage);
            webSocketServer.sendToAllAdmin(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断昵称和图片是否有变化
     * @param userInfo
     * @param userInfoUpDTO
     * @return
     */
    private UserInfoUpDTO determine(UserInfo userInfo,UserInfoUpDTO userInfoUpDTO){
        if (userInfoUpDTO.getName() != null){
            if (userInfoUpDTO.getName().equals(userInfo.getName())){
                userInfoUpDTO.setName(null);
            }
        }
        if (userInfoUpDTO.getImg() != null){
            if (userInfoUpDTO.getImg().equals(userInfo.getImg())){
                userInfoUpDTO.setImg(null);
            }
        }
        return userInfoUpDTO;
    }

}
