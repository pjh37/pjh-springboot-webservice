package com.pjh.share.controller;

import com.pjh.share.web.dto.ChatMessageDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessageDto sendMessage(@Payload ChatMessageDto chatMessageDto) {
        return chatMessageDto;
    }

    @MessageMapping("/newuser/{roomId}}")
    @SendTo("/topic/{roomId}")
    public ChatMessageDto newUser(@Payload ChatMessageDto chatMessageDto,
                                 SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessageDto.getSender());
        return chatMessageDto;
    }
}
