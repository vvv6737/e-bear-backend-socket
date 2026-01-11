package com.example.ebearsocket.domain.message.repository;

import com.example.ebearsocket.domain.message.entity.ChatMessage;
import com.example.ebearsocket.domain.message.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // 특정 방의 모든 메시지를 시간순으로 조회
    List<ChatMessage> findAllByChatRoomOrderBySendTimeAsc(ChatRoom chatRoom);
}
