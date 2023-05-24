package com.dreamcup.repository;

import com.dreamcup.config.QueryDslConfig;
import com.dreamcup.entity.ChatRoom;
import com.dreamcup.dto.request.ChatRoomSearchRequestDto;
import com.dreamcup.dto.response.ChatRoomResponseDto;
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

        ChatRoom findChatRoom = chatRoomRepository.findById(chatRoom.getChatRoomId()).orElseThrow(() -> new IllegalArgumentException("조회할 수 없습니다."));
        assertThat(findChatRoom).isEqualTo(chatRoom);

        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        assertThat(chatRooms).containsExactly(findChatRoom);
    }

    @Test
    @DisplayName("제목으로 검색 페이지 확인")
    public void searchAndPageTest() {
        // given
        ChatRoom chatRoom1 = ChatRoom.builder()
                .title("제목1입니다.")
                .build();
        ChatRoom chatRoom2 = ChatRoom.builder()
                .title("제목2입니다.")
                .build();
        ChatRoom chatRoom3 = ChatRoom.builder()
                .title("제목11입니다.")
                .build();
        ChatRoom chatRoom4 = ChatRoom.builder()
                .title("제목12입니다.")
                .build();
        ChatRoom chatRoom5 = ChatRoom.builder()
                .title("제목21입니다.")
                .build();
        em.persist(chatRoom1);
        em.persist(chatRoom2);
        em.persist(chatRoom3);
        em.persist(chatRoom4);
        em.persist(chatRoom5);

        ChatRoomSearchRequestDto request = ChatRoomSearchRequestDto.builder()
                .page(1)
                .size(2)
                .schType("title")
                .keyword("제목1")
                .build();

        // when
        List<ChatRoomResponseDto> pagenatedList = chatRoomRepository.getPagenatedList(request);

        // then
        assertThat(pagenatedList).extracting("title").containsExactly("제목12입니다.", "제목11입니다.");
    }

}