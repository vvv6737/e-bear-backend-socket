package com.example.ebearsocket.domain.message.repository;

import com.example.ebearsocket.domain.message.entity.ChatMessage;
import com.example.ebearsocket.domain.message.entity.ChatRoom;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // 두 유저가 포함된 방 찾기 (순서 무관)
    @Query("SELECT r FROM ChatRoom r WHERE (r.userA = :u1 AND r.userB = :u2) OR (r.userA = :u2 AND r.userB = :u1)")
    Optional<ChatRoom> findChatRoomByUsers(@Param("u1") String u1, @Param("u2") String u2);

    // 내가 참여한 모든 채팅방을 마지막 메시지 시간순으로 조회
    @Query("SELECT r FROM ChatRoom r WHERE r.userA = :userId OR r.userB = :userId ORDER BY r.lastMessageTime DESC")
    List<ChatRoom> findAllByUserId(@Param("userId") String userId);
}
