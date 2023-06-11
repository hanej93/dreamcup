package com.dreamcup.chatroom.repository;

import com.dreamcup.config.QueryDslConfig;
import com.dreamcup.chatroom.entity.ChatRoom;
import com.dreamcup.chatroom.dto.request.ChatRoomSearchRequestDto;
import com.dreamcup.chatroom.dto.response.ChatRoomResponseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({QueryDslConfig.class})
class ChatRoomRepositoryTest {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @AfterEach
    public void cleanAll() {
        chatRoomRepository.deleteAll();
    }

    @Test
    @DisplayName("JpaRepository 기본 테스트")
    public void basicTest() {
        // given
        ChatRoom chatRoom = ChatRoom.builder()
                .title("제목입니다.")
                .build();

        // expected
        chatRoomRepository.save(chatRoom);

        ChatRoom findChatRoom = chatRoomRepository.findById(chatRoom.getId()).orElseThrow(() -> new IllegalArgumentException("조회할 수 없습니다."));
        assertThat(findChatRoom).isEqualTo(chatRoom);

        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        assertThat(chatRooms).containsExactly(findChatRoom);
    }

}