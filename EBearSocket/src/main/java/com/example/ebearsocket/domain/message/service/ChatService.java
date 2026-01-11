package com.example.ebearsocket.domain.message.service;

import com.example.ebearsocket.domain.message.dto.ChatMessageDto;
import com.example.ebearsocket.domain.message.entity.ChatMessage;
import com.example.ebearsocket.domain.message.entity.ChatRoom;
import com.example.ebearsocket.domain.message.repository.ChatMessageRepository;
import com.example.ebearsocket.domain.message.repository.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    // 채팅방 생성
    public ChatRoom getOrCreateRoom(String u1, String u2) {
        return chatRoomRepository.findChatRoomByUsers(u1, u2)
                .orElseGet(() -> chatRoomRepository.save(new ChatRoom(u1, u2)));
    }

    // 전송한 채팅 저장
    public void saveMessage(ChatMessageDto dto) {
        ChatRoom room = chatRoomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다."));

        ChatMessage message = ChatMessage.builder()
                .chatRoom(room)
                .senderId(dto.getSenderId())
                .message(dto.getMessage())
                .build();

        chatMessageRepository.save(message);
        room.updateLastMessageTime(); // 최신 대화 시간 갱신
    }

    // 각 채팅방에 대한 채팅 목록
    public List<ChatMessage> getChatHistory(Long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("방이 존재하지 않습니다."));
        return chatMessageRepository.findAllByChatRoomOrderBySendTimeAsc(room);
    }

    // 채팅방 목록
    public List<ChatRoom> getRoomList(String userId) {
        return chatRoomRepository.findAllByUserId(userId);
    }
}
