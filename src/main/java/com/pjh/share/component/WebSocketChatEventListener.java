package com.pjh.share.component;

import com.pjh.share.web.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketChatEventListener {
    private static final Logger log= LoggerFactory.getLogger(WebSocketChatEventListener.class);

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event){

        log.info("===========");
        log.info("new web socket connection + "+event.getTimestamp());
        log.info("===========");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor=StompHeaderAccessor.wrap(event.getMessage());
        String username=(String)headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            log.info("===========");
            log.info("유저가 방을 나감 + "+username);
            log.info("===========");
            ChatMessageDto chatMessage = new ChatMessageDto("Leave",username,"나감");
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
