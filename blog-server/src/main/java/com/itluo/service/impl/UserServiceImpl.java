package com.itluo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itluo.constant.CaptchaConstants;
import com.itluo.constant.MessageConstant;
import com.itluo.constant.RoleConstant;
import com.itluo.constant.StatusConstant;
import com.itluo.context.BaseContext;
import com.itluo.dto.*;
import com.itluo.entity.User;
import com.itluo.entity.UserInfo;
import com.itluo.entity.UserIp;
import com.itluo.enumeration.MessageType;
import com.itluo.exception.*;
import com.itluo.json.JacksonObjectMapper;
import com.itluo.mapper.UserInfoMapper;
import com.itluo.mapper.UserIpMapper;
import com.itluo.mapper.UserMapper;
import com.itluo.result.PageResult;
import com.itluo.result.WebSocketMessage;
import com.itluo.service.UserService;
import com.itluo.utils.IpUtils;
import com.itluo.utils.LocalDateTimeUtils;
import com.itluo.vo.StatisticalCountVO;
import com.itluo.vo.UserSelectVO;
import com.itluo.vo.UserVO;
import com.itluo.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserIpMapper userIpMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 分页查询
     * @param userPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(UserPageQueryDTO userPageQueryDTO) {
        PageHelper.startPage(userPageQueryDTO.getPage(),userPageQueryDTO.getPageSize());
        Page<UserVO> page = userMapper.pageQuery(userPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 验证
     * @param id
     * @return
     */
    private User validate(Long id){
        User user = userMapper.findByUserId(id);
        if (user == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        return user;
    }

    /**
     * 修改信息
     * @param userUpDTO
     */
    @Override
    public void update(UserUpDTO userUpDTO) {
        Long id = userUpDTO.getId();
        User validate = validate(id);
        if (validate.getStatus().equals(StatusConstant.ENABLE)){
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }
        User users = new User();
        BeanUtils.copyProperties(userUpDTO,users);
        if (users != null){
            String password = DigestUtils.md5DigestAsHex(users.getPassword().getBytes());
            users.setPassword(password);
        }
        userMapper.update(users);
    }

    /**
     * 修改状态
     * @param id
     */
    @Override
    public void updateStatus(Long id) {
        User user = validate(id);
        Long status = user.getStatus().equals(StatusConstant.ENABLE) ? StatusConstant.DISABLE : StatusConstant.ENABLE;
        User users = User.builder()
                .id(user.getId())
                .status(status)
                .build();
        userMapper.update(users);
    }

    /**
     * 登录
     * @param userLoginDTO
     * @param request
     * @return
     */
    @Override
    public UserSelectVO login(UserLoginDTO userLoginDTO, HttpServletRequest request) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        String captcha = userLoginDTO.getCaptcha();
        HttpSession session = request.getSession();
        String ipAddr = IpUtils.getIpAddr(request);
        LocalDateTime localDateTime = LocalDateTimeUtils.getLocalDateTime();
        String captchaSession = (String) session.getAttribute(CaptchaConstants.CAPTCHA_SESSION_KEY);
        UserSelectVO user = userMapper.findByUserName(username);
        if (user == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        if (!captcha.equals(captchaSession)){
            session.removeAttribute(CaptchaConstants.CAPTCHA_SESSION_KEY);
            throw new CaptchaErrorException(MessageConstant.CODE_NAME);
        }
        session.removeAttribute(CaptchaConstants.CAPTCHA_SESSION_KEY);
        UserIp userIp = UserIp.builder()
                .userId(user.getId())
                .userIp(ipAddr)
                .build();
        userIpMapper.insert(userIp);
        User users = User.builder()
                .id(user.getId())
                .loginTime(localDateTime)
                .build();
        userMapper.update(users);
        return user;
    }

    /**
     * 修改密码
     * @param userUpPwdDTO
     */
    @Override
    public void updatePwd(UserUpPwdDTO userUpPwdDTO) {
        String mailbox = userUpPwdDTO.getMailbox();
        String password = userUpPwdDTO.getPassword();
        String captcha = userUpPwdDTO.getCaptcha();
        User user = userMapper.findByUserMailbox(mailbox);
        String code = (String) redisTemplate.opsForValue().get(mailbox);
        if (!captcha.equals(code)){
            throw new EmailAlreadyExistsException(MessageConstant.CODE_NAME);
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        User users = User.builder()
                .id(user.getId())
                .password(password)
                .build();
        userMapper.update(users);
    }

    /**
     * 注册
     * @param userRegisterDTO
     */
    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();
        String mailbox = userRegisterDTO.getMailbox();
        String verifyCode = userRegisterDTO.getVerifyCode();
        User user = userMapper.findByUserNameS(username);
        if (user != null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND_USER);
        }
        User us = userMapper.findByUserMailbox(mailbox);
        if (us != null){
            throw new EmailAlreadyExistsException(MessageConstant.EMAIL_ALREADY_EXISTS_EXCEPTION);
        }
        String code = (String) redisTemplate.opsForValue().get(mailbox);
        if (code == null){
            throw new BaseException(MessageConstant.CODE);
        }
        if (!verifyCode.equals(code)){
            throw new BaseException(MessageConstant.CODE_NAME);
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        User users = User.builder()
                .username(username)
                .password(password)
                .mailbox(mailbox)
                .role(RoleConstant.STUDENT)
                .status(StatusConstant.DISABLE)
                .build();
        userMapper.insert(users);
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 格式化年份和月份
        String yearMonth = currentDate.format(DateTimeFormatter.ofPattern("yyyyMM"));
        UserInfo userInfo = UserInfo.builder()
                .userId(users.getId())
                .name(yearMonth+users.getId())
                .img("https://music-1306786696.cos.ap-guangzhou.myqcloud.com/avatar20.png")
                .build();
        userInfoMapper.insert(userInfo);
        sendWebSocket();
        redisTemplate.delete(mailbox);
    }

    /**
     * 修改邮箱
     * @param userMailboxDTO
     */
    @Override
    public void updateMailbox(UserMailboxDTO userMailboxDTO) {
        String mailbox = userMailboxDTO.getMailbox();
        String captcha = userMailboxDTO.getCaptcha();
        Long id = BaseContext.getCurrentId();
        User byUserMailbox = userMapper.findByUserMailbox(mailbox);
        if (byUserMailbox != null){
            throw new EmailAlreadyExistsException(MessageConstant.EMAIL_ALREADY_EXISTS_EXCEPTION);
        }
        String code = (String) redisTemplate.opsForValue().get(mailbox);
        if (!captcha.equals(code)){
            throw new EmailAlreadyExistsException(MessageConstant.CODE_NAME);
        }
        User user = User.builder()
                .id(id)
                .mailbox(mailbox)
                .build();
        userMapper.update(user);
    }

    /**
     * 向管理端发送消息
     */
    private void sendWebSocket(){
        JacksonObjectMapper jacksonObjectMapper = new JacksonObjectMapper();
        Map<String,Object> map = new HashMap<>(2);
        Long userCount = userMapper.findByUserCount();
        List<StatisticalCountVO> userGrowth = userMapper.findByUserGrowth();
        map.put("userCount",userCount);
        map.put("userGrowth",userGrowth);
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setType(MessageType.Real_Time_Statistics);
        try {
            String data = jacksonObjectMapper.writeValueAsString(map);
            webSocketMessage.setMessage(data);
            String message = jacksonObjectMapper.writeValueAsString(webSocketMessage);
            webSocketServer.sendToAllAdmin(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
