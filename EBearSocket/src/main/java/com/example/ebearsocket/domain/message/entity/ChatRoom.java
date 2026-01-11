package com.example.ebearsocket.domain.message.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chat_rooms")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userA; // 참여자 1 ID

    @Column(nullable = false)
    private String userB; // 참여자 2 ID

    private LocalDateTime lastMessageTime; // 목록 정렬용 최신 시간

    // 이 방에 속한 메시지들과의 1:N 관계
    @JsonIgnore // 순환 참조 방지
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> messages = new ArrayList<>();

    public ChatRoom(String userA, String userB) {
        this.userA = userA;
        this.userB = userB;
        this.lastMessageTime = LocalDateTime.now();
    }

    // 새로운 메시지가 올 때마다 시간 업데이트
    public void updateLastMessageTime() {
        this.lastMessageTime = LocalDateTime.now();
    }
}
