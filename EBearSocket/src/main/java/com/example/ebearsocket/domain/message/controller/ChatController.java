package com.example.ebearsocket.domain.message.controller;

import com.example.ebearsocket.domain.message.dto.ChatMessageDto;
import com.example.ebearsocket.domain.message.dto.ChatRoomRequest;
import com.example.ebearsocket.domain.message.entity.ChatMessage;
import com.example.ebearsocket.domain.message.entity.ChatRoom;
import com.example.ebearsocket.domain.message.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final SimpMessageSendingOperations messagingTemplate;

    // 방 접속 (없으면 생성)
    @PostMapping("/api/chat/room")
    public ChatRoom enterRoom(@RequestBody ChatRoomRequest req) {
        return chatService.getOrCreateRoom(req.userA(), req.userB());
    }

    // 과거 내역 조회
    @GetMapping("/api/chat/room/{roomId}/messages")
    public List<ChatMessage> getHistory(@PathVariable Long roomId) {
        return chatService.getChatHistory(roomId);
    }

    // 실시간 메시지 처리 (STOMP)
    @MessageMapping("/chat/message")
    public void sendMessage(ChatMessageDto dto) {
        chatService.saveMessage(dto); // DB 저장
        messagingTemplate.convertAndSend("/sub/chat/room/" + dto.getRoomId(), dto); // 전송
    }

    // 채팅방 목록 조회
    @GetMapping("/api/chat/rooms/{userId}")
    public List<ChatRoom> getRooms(@PathVariable String userId) {
        return chatService.getRoomList(userId);
    }
}
