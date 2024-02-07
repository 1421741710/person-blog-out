package com.itluo.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itluo.entity.Process;
import com.itluo.enumeration.MessageType;
import com.itluo.json.JacksonObjectMapper;
import com.itluo.mapper.ProcessMapper;
import com.itluo.result.WebSocketMessage;
import com.itluo.service.ProcessService;
import com.itluo.utils.JwtUtil;
import com.itluo.utils.LocalDateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * WebSocket服务
 *
 * @author Administrator
 */
@Component
@ServerEndpoint("/ws/{sid}")
@Slf4j
public class WebSocketServer {


    /**
     * 存放会话对象
     */
    private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        log.info("连接成功!连接对象是:" + sid);
        //管理员登录操作内容
//        onAdminLogin(session);
        //用户登录操作内容
        onUserLogin(sid, session);
    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        log.info("收到用户:" + sid + "的信息:" + message);
        handleMessage(sid, message);
    }


    /**
     * 连接关闭调用的方法
     *
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        log.info("连接断开:" + sid);
        sessionMap.remove(sid);
        Session session = getSession(sid);
        log.info("是否获取到:{}",session != null);
    }

    /**
     * 向管理员群发
     *
     * @param message
     */
    public void sendToAllAdmin(String message) {
        for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
            String key = entry.getKey();
            // 或者使用其他字符串匹配方式，如contains、equals等
            if (key.startsWith("admin")) {
                Session adminSession = entry.getValue();
                if (adminSession.isOpen()) {
                    try {
                        adminSession.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 向用户群发
     *
     * @param message
     */
    public void sendToAllClient(String message) {
        for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
            String key = entry.getKey();
            // 或者使用其他字符串匹配方式，如contains、equals等
            if (!key.startsWith("admin")) {
                Session adminSession = entry.getValue();
                if (adminSession.isOpen()) {
                    sendToOneClientIfOnline(adminSession, message);
                }
            }
        }
//        Collection<Session> sessions = sessionMap.values();
//        for (Session session : sessions) {
//            try {
//                //服务器向客户端发送消息
//                sendToOneClientIfOnline(session, message);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * 获取key的session
     *
     * @param id
     * @return
     */
    public Session getSession(String id) {
        Session session = sessionMap.get(id);
        return session;
    }

    /**
     * 给指定用户发消息
     *
     * @param session
     * @param message
     */
    public void sendToOneClientIfOnline(Session session, String message) {
        //判断用户是否在线
        if (session != null) {
            //判断用户是否连接中
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 管理员登录操作内容
     *
     * @param session
     */
    public void onAdminLogin(Session session) {
        JacksonObjectMapper objectMapper = new JacksonObjectMapper();
        Map<String, Object> objectMap = new HashMap<>(2);
        objectMap.put("content", "请查看审核单是否有新的审核单,如果有请及时处理!");
        objectMap.put("createTime", LocalDateTimeUtils.getLocalDateTime());
        try {
            String s = objectMapper.writeValueAsString(objectMap);
            WebSocketMessage webSocketMessage = new WebSocketMessage();
            webSocketMessage.setType(MessageType.SYSTEM_NOTICE);
            webSocketMessage.setMessage(s);
            String str = objectMapper.writeValueAsString(webSocketMessage);
            session.getBasicRemote().sendText(str);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录逻辑，当用户登录时更新其WebSocket会话
     *
     * @param userId
     * @param session
     */
    public void onUserLogin(String userId, Session session) {
        // 创建一个ObjectMapper实例用于序列化对象
        ObjectMapper mapper = new ObjectMapper();

        // 从sessionMap中获取与该userId关联的旧的WebSocketSession对象，并用新的WebSocketSession替换它
        // 这里使用了ConcurrentHashMap的put方法，该方法返回的是之前与给定键关联的值（如果存在的话）
        Session session1 = sessionMap.get(userId);
        if (session1 != null) {
            // 关闭旧会话
            try {
                //创建WebSocket自定义消息格式
                WebSocketMessage webSocketMessage = new WebSocketMessage();
                //通知类型 用户注销
                webSocketMessage.setType(MessageType.USER_LOGGED_OUT);
                ////消息内容
                webSocketMessage.setMessage("检测到另一个登录");
                //发送消息
                session1.getBasicRemote().sendText(mapper.writeValueAsString(webSocketMessage));
                session1.close(); //关闭连接
                sessionMap.remove(userId);
                sessionMap.put(userId, session);
            } catch (JsonProcessingException e) {
                log.error("Failed to serialize logout notification", e);
            } catch (IOException e) {
                // 处理关闭异常
                log.error("Failed to send logout message or close the WebSocket session", e);
            }
        } else {
            sessionMap.put(userId, session);
        }
    }

    /**
     * 发送日志消息给所有在线客户端
     *
     * @param logMessage 需要发送的日志消息内容
     */
    public void sendLogToAllClient(String logMessage) {
        Session session = sessionMap.get("admin");
        if (session != null) {
            if (session.isOpen()) {
                WebSocketMessage webSocketMessage = new WebSocketMessage();
                webSocketMessage.setType(MessageType.LOG_ENTRY);
                webSocketMessage.setMessage(logMessage);

                ObjectMapper objectMapper = new ObjectMapper();

                try {
                    String messageJson = objectMapper.writeValueAsString(webSocketMessage);
                    sendToAllClient(messageJson);
                } catch (JsonProcessingException e) {
                    log.error("Failed to serialize log entry", e);
                }
            }
        }
    }


    /**
     * 用户登出
     *
     * @param id
     */
    public void onUserLogout(String id) {
        //删除其会话
        Session remove = sessionMap.remove(id);
        if (remove != null) {
            try {
                //关闭其会话
                remove.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleMessage(String id, String messageJson) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            WebSocketMessage webSocketMessage = mapper.readValue(messageJson, WebSocketMessage.class);

            // 根据消息类型做出不同的处理
            switch (webSocketMessage.getType()) {
                case SYSTEM_NOTICE:
                    handleSystemNotice(webSocketMessage.getMessage());
                    break;
                case PRIVATE_MESSAGE:
                    handlePrivateMessage(webSocketMessage.getId(), webSocketMessage.getMessage());
                    break;
                case HEARTBEAT:
                    handleHeartbeat(id, webSocketMessage.getMessage());
                    break;
                case USER_LOGGED_OUT:
                    onUserLogout(id);
                    break;
                default:
                    return;
            }
        } catch (Exception e) {
            log.error("Failed to deserialize WebSocket message: ", e);
        }
    }

    private void handleSystemNotice(String message) {
        // 处理系统通知
        log.info("系统通知:" + message);
    }

    private void handlePrivateMessage(Long id, String message) {
        log.info("id是:" + id + "消息内容:" + message);
        ObjectMapper objectMapper = new ObjectMapper();
        // 处理私信，例如找到对应的用户并保存或转发私信内容
        Session webSocketSession = sessionMap.get(String.valueOf(id));
        if (webSocketSession != null) {
            if (webSocketSession.isOpen()) {
                try {
                    WebSocketMessage webSocketMessage = new WebSocketMessage();
                    webSocketMessage.setType(MessageType.PRIVATE_MESSAGE);
                    webSocketMessage.setMessage(message);
                    String messageJson = objectMapper.writeValueAsString(webSocketMessage);
                    webSocketSession.getBasicRemote().sendText(messageJson);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 处理心跳包
     *
     * @param id
     * @param message
     */
    private void handleHeartbeat(String id, String message) {
        // 处理心跳包
        Session session = sessionMap.get(id);
        try {
            if (session != null) {
                if (session.isOpen()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    WebSocketMessage webSocketMessage = new WebSocketMessage();
                    webSocketMessage.setType(MessageType.VERIFY_TOKEN);
                    webSocketMessage.setMessage("token expire");
                    String s = objectMapper.writeValueAsString(webSocketMessage);
                    String subString = id.length() > 5 ? id.substring(0, 5) : id;
                    String admin = "admin";
                    if (admin.equals(subString)) {
                        try {
                            JwtUtil.parseJWT("luoqin", message);
                            session.getBasicRemote().sendPong(ByteBuffer.wrap("HEARTBEAT".getBytes()));
                        } catch (Exception ex) {
                            session.getBasicRemote().sendText(s);
                            session.close();
                        }
                    } else {
                        try {
                            JwtUtil.parseJWT("luoqinphone", message);
                            session.getBasicRemote().sendPong(ByteBuffer.wrap("HEARTBEAT".getBytes()));
                        } catch (Exception ex) {
                            session.getBasicRemote().sendText(s);
                            session.close();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
