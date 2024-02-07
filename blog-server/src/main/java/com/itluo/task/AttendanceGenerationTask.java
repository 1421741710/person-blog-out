package com.itluo.task;

import com.itluo.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author Administrator
 */
@Component
public class AttendanceGenerationTask {

    @Autowired
    private WebSocketServer webSocketServer;


    /**
     * @Scheduled(cron = "0 0 8 * * ?")
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void anotherTask() {
    }
}
