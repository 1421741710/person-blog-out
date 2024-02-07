package com.itluo.enumeration;

/**
 * @author Administrator
 */

public enum MessageType {
    /**
     * 系统通知
     */
    SYSTEM_NOTICE,
    /**
     * 私信
     */
    PRIVATE_MESSAGE,
    /**
     * 心跳包
     */
    HEARTBEAT,
    /**
     * 用户注销
     */
    USER_LOGGED_OUT,
    /**
     * 日志
     */
    LOG_ENTRY,
    /**
     * 令牌
     */
    VERIFY_TOKEN,
    /**
     * 实时统计
     */
    Real_Time_Statistics
}
