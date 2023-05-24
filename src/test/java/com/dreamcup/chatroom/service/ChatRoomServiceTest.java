package com.dreamcup.chatroom.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dreamcup.chatroom.dto.request.ChatRoomSaveRequestDto;
import com.dreamcup.chatroom.dto.request.ChatRoomSearchRequestDto;
import com.dreamcup.chatroom.dto.request.ChatRoomUpdateRequestDto;
import com.dreamcup.chatroom.dto.response.ChatRoomResponseDto;
import com.dreamcup.chatroom.entity.ChatRoom;
import com.dreamcup.chatroom.exception.ChatRoomNotFoundException;
import com.dreamcup.chatroom.repository.ChatRoomRepository;

@SpringBootTest
class ChatRoomServiceTest {

	@Autowired
	private ChatRoomService chatRoomService;

	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@BeforeEach
	void clean() {
		chatRoomRepository.deleteAll();
	}

	private void saveChatRoomsForTest() {
		List<ChatRoom> chatRooms = IntStream.range(1, 31)
			.mapToObj(i ->
				ChatRoom.builder()
					.title("테스트 제목" + i + "입니다.")
					.build()
			)
			.collect(Collectors.toList());

		chatRoomRepository.saveAll(chatRooms);
	}

	@Test
	@DisplayName("채팅방 생성 테스트")
	void save() {
		// given
		ChatRoomSaveRequestDto request = ChatRoomSaveRequestDto.builder()
			.title("제목입니다.")
			.build();

		// when
		Long id = chatRoomService.save(request);

		// then
		assertThat(chatRoomRepository.count()).isEqualTo(1L);

		ChatRoom findChatRoom = chatRoomRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
		assertThat(findChatRoom).extracting("title").isEqualTo("제목입니다.");
	}

	@Test
	@DisplayName("채팅방 제목 수정")
	void update() {
		// given
		ChatRoom chatRoom = ChatRoom.builder()
			.title("제목입니다.")
			.build();
		chatRoomRepository.save(chatRoom);

		ChatRoomUpdateRequestDto request = ChatRoomUpdateRequestDto.builder()
			.title("수정 제목")
			.build();

		// when
		Long id = chatRoomService.update(chatRoom.getChatRoomId(), request);

		// then
		ChatRoom findChatRoom = chatRoomRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
		assertThat(findChatRoom).extracting("title").isEqualTo("수정 제목");
	}

	@Test
	@DisplayName("채팅방 페이지 조회, 검색 테스트")
	void getPagenatedList() {
		// given
		saveChatRoomsForTest();

		ChatRoomSearchRequestDto request = ChatRoomSearchRequestDto.builder()
			.page(1)
			.size(5)
			.schType("title")
			.keyword("제목1")
			.build();

		// when
		List<ChatRoomResponseDto> pagenatedList = chatRoomService.getPagenatedList(request);

		// then
		assertThat(pagenatedList.size()).isEqualTo(5L);
		assertThat(pagenatedList).filteredOn(room -> room.getTitle().contains("제목1")).hasSize(5);
	}

	@Test
	@DisplayName("채팅방 페이지 조회, 검색 테스트(검색조건, 페이지 제외)")
	void getPagenatedList2() {
		// given
		saveChatRoomsForTest();
		ChatRoom chatRoom = ChatRoom.builder()
			.title("제목입니다.")
			.build();
		chatRoomRepository.save(chatRoom);

		ChatRoomSearchRequestDto request = ChatRoomSearchRequestDto.builder()
			.build();

		// when
		List<ChatRoomResponseDto> pagenatedList = chatRoomService.getPagenatedList(request);

		// then
		assertThat(pagenatedList.size()).isEqualTo(10L);
		assertThat(pagenatedList).extracting("title").contains("제목입니다.");
	}

	@Test
	@DisplayName("채팅방 id로 조회")
	void findById() {
		// given
		saveChatRoomsForTest();

		ChatRoom chatRoom = ChatRoom.builder()
			.title("제목입니다.")
			.build();
		chatRoomRepository.save(chatRoom);

		// when
		ChatRoomResponseDto findChatRoom = chatRoomService.findById(chatRoom.getChatRoomId());

		// then
		assertThat(findChatRoom).extracting("title").isEqualTo("제목입니다.");
	}

	@Test
	@DisplayName("존재하지 않는 채팅방 id로 조회")
	void findByNotExistsId() {
		// given
		saveChatRoomsForTest();
		ChatRoom chatRoom = ChatRoom.builder()
			.title("제목입니다.")
			.build();
		chatRoomRepository.save(chatRoom);

		// expected
		assertThatThrownBy(() -> chatRoomService.findById(chatRoom.getChatRoomId() + 1))
			.isInstanceOf(ChatRoomNotFoundException.class);
	}

	@Test
	@DisplayName("채팅방 삭제")
	void delete() {
		// given
		saveChatRoomsForTest();
		ChatRoom chatRoom = ChatRoom.builder()
			.title("제목입니다.")
			.build();
		chatRoomRepository.save(chatRoom);

		// when
		chatRoomService.delete(chatRoom.getChatRoomId());

		// then
		assertThatThrownBy(() -> chatRoomService.findById(chatRoom.getChatRoomId()))
			.isInstanceOf(ChatRoomNotFoundException.class);
	}

}