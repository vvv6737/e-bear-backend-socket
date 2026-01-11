package com.example.ebearsocket.domain.message.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 권장
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom; // 연관 관계 (어느 방의 메시지인가)

    @Column(nullable = false)
    private String senderId; // 보낸 사람 ID

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message; // 메시지 내용

    private LocalDateTime sendTime; // 보낸 시간

    @Column(nullable = false)
    private Boolean isRead; // 읽음 여부

    @Builder
    public ChatMessage(ChatRoom chatRoom, String senderId, String message) {
        this.chatRoom = chatRoom;
        this.senderId = senderId;
        this.message = message;
        this.sendTime = LocalDateTime.now();
        this.isRead = false; // 생성 시점엔 항상 읽지 않음 상태
    }

    // 읽음 처리 메서드
    public void markAsRead() {
        this.isRead = true;
    }
}
