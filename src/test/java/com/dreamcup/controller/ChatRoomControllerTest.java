package com.dreamcup.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dreamcup.entity.ChatRoom;
import com.dreamcup.dto.request.ChatRoomSaveRequestDto;
import com.dreamcup.dto.request.ChatRoomUpdateRequestDto;
import com.dreamcup.repository.ChatRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class ChatRoomControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@BeforeEach
	public void clean() throws Exception {
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
	@DisplayName("채팅방 생성")
	@WithMockUser
	void post() throws Exception {
		// given
		ChatRoomSaveRequestDto requestDto = ChatRoomSaveRequestDto.builder()
			.title("제목입니다.")
			.build();
		String request = objectMapper.writeValueAsString(requestDto);

		// expected
		mockMvc.perform(MockMvcRequestBuilders.post("/api/chatRoom")
			.contentType(APPLICATION_JSON)
			.content(request)
		)
		.andExpect(status().isCreated())
		.andDo(print());
	}

	@Test
	@DisplayName("채팅방 생성(제목 미포함)")
	@WithMockUser
	void postOmittedTitle() throws Exception {
		// given
		ChatRoomSaveRequestDto requestDto = ChatRoomSaveRequestDto.builder()
			.title("")
			.build();
		String request = objectMapper.writeValueAsString(requestDto);

		// expected
		mockMvc.perform(MockMvcRequestBuilders.post("/api/chatRoom")
				.contentType(APPLICATION_JSON)
				.content(request)
			)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.validation.title").exists())
			.andDo(print());
	}

	@Test
	@DisplayName("채팅방 아이디로 조회")
	@WithMockUser
	void get() throws Exception {
		// given
		saveChatRoomsForTest();
		ChatRoom chatRoom = ChatRoom.builder()
			.title("제목입니다.")
			.build();
		chatRoomRepository.save(chatRoom);

		// expected
		mockMvc.perform(MockMvcRequestBuilders.get("/api/chatRoom/{chatRoomId}", chatRoom.getChatRoomId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title").value(chatRoom.getTitle()))
			.andDo(print());
	}

	@Test
	@DisplayName("존재하지 않는 채팅방 아이디로 조회")
	@WithMockUser
	void getByNotExistsId() throws Exception {
		// given
		saveChatRoomsForTest();
		ChatRoom chatRoom = ChatRoom.builder()
			.title("제목입니다.")
			.build();
		chatRoomRepository.save(chatRoom);

		// expected
		mockMvc.perform(MockMvcRequestBuilders.get("/api/chatRoom/{chatRoomId}", chatRoom.getChatRoomId() + 1))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.code").value("404"))
			.andDo(print());
	}

	@Test
	@DisplayName("채팅방 조회(페이지, 제목)")
	@WithMockUser
	void getList() throws Exception {
		// given
		saveChatRoomsForTest();

		// expected
		mockMvc.perform(MockMvcRequestBuilders.get("/api/chatRoom")
				.param("page", "1")
				.param("size", "5")
				.param("schType", "title")
				.param("keyword", "제목1")
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("size()", is(5)))
			.andExpect(jsonPath("$[?(@.title =~ /.*제목1.*/)]").value(hasSize(5)))
			.andDo(print());
	}

	@Test
	@DisplayName("채팅방 수정")
	@WithMockUser
	void update() throws Exception {
		// given
		ChatRoom chatRoom = ChatRoom.builder()
			.title("제목입니다.")
			.build();
		chatRoomRepository.save(chatRoom);

		ChatRoomUpdateRequestDto requestDto = ChatRoomUpdateRequestDto.builder()
			.title("수정 제목")
			.build();

		String request = objectMapper.writeValueAsString(requestDto);

		// expected
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/chatRoom/{chatRoomId}", chatRoom.getChatRoomId())
				.contentType(APPLICATION_JSON)
				.content(request)
			)
			.andExpect(status().isOk())
			.andExpect(content().string(String.valueOf(chatRoom.getChatRoomId())))
			.andDo(print());
	}

	@Test
	@DisplayName("채팅방 삭제")
	@WithMockUser
	void delete() throws Exception {
		// given
		ChatRoom chatRoom = ChatRoom.builder()
			.title("제목입니다.")
			.build();
		chatRoomRepository.save(chatRoom);

		// expected
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/chatRoom/{chatRoomId}", chatRoom.getChatRoomId())
				.contentType(APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andExpect(content().string(String.valueOf(chatRoom.getChatRoomId())))
			.andDo(print());
	}
}