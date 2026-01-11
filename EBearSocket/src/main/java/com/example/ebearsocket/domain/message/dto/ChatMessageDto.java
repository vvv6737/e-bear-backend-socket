package com.example.ebearsocket.domain.message.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageDto {
    // 1. 어느 방에서 보낸 메시지인가?
    private Long roomId;

    // 2. 누가 보낸 메시지인가?
    private String senderId;

    // 3. 메시지 내용
    private String message;

    // 4. (선택) 보낸 시간 - 클라이언트에서 보여주기 용도
    // 서버에서 생성해서 넘겨주는 것이 정확합니다.
    private String sendTime;
}
