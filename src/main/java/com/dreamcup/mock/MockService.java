package com.dreamcup.mock;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.chatroom.code.MessageType;
import com.dreamcup.chatroom.dto.request.ChatRoomSaveRequestDto;
import com.dreamcup.chatroom.dto.request.PublicChatRoomJoinRequestDto;
import com.dreamcup.chatroom.service.ChatRoomService;
import com.dreamcup.chatroom.service.ChatService;
import com.dreamcup.chatroom.vo.ChatVo;
import com.dreamcup.member.dto.request.MemberSignupRequestDto;
import com.dreamcup.member.entity.Friendship;
import com.dreamcup.member.entity.Member;
import com.dreamcup.member.repository.FriendshipRepository;
import com.dreamcup.member.repository.MemberRepository;
import com.dreamcup.member.service.MemberService;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MockService {

	private final MemberService memberService;
	private final MemberRepository memberRepository;
	private final FriendshipRepository friendshipRepository;

	private final ChatRoomService chatRoomService;
	private final ChatService chatService;

	private final Faker faker = new Faker(Locale.KOREA);

	public void generateMembers() {
		for (int i = 1; i <= 100; i++) {
			MemberSignupRequestDto requestDto = MemberSignupRequestDto.builder()
				.username("user" + i)
				.password("1234")
				.nickname("user" + i)
				.build();
			memberService.signup(requestDto);
		}
	}

	public void generateFriends() {
		for (long i = 1; i <= 40; i++) {
			Member member = memberRepository.findById(i).orElseThrow();
			List<Member> members = memberRepository.findAll();
			members.remove(member);
			for (int j = 0; j < 5; j++) {
				Member friend = members.get(faker.random().nextInt(members.size()));
				members.remove(friend);

				Boolean accepted = faker.random().nextBoolean();
				Friendship friendship = Friendship.builder()
					.member(member)
					.friend(friend)
					.accepted(accepted)
					.build();

				Friendship reverseFriendShip = Friendship.builder()
					.member(friend)
					.friend(member)
					.accepted(accepted)
					.build();

				friendshipRepository.save(friendship);
				friendshipRepository.save(reverseFriendShip);
			}
		}
	}

	public void generateChatRooms() {
		for (int i = 1; i <= 100; i++) {
			ChatRoomSaveRequestDto chatRoomSaveRequestDto = ChatRoomSaveRequestDto.builder()
				.creatorId(faker.random().nextInt(1, 100).longValue())
				.privateRoom(true)
				.title("제목" + i)
				.userMaxCount((i % 8) + 1)
				.build();
			chatRoomService.createChatRoom(chatRoomSaveRequestDto);
			for (int j = 0; j < 5; j++) {
				long participantId = faker.random().nextInt(1, 100).longValue();
				PublicChatRoomJoinRequestDto joinRequestDto = PublicChatRoomJoinRequestDto.builder()
					.ChatRoomId((long)i)
					.participantId(participantId)
					.build();

				try {
					chatRoomService.joinPublicChatRoom(joinRequestDto);
				} catch (Exception e) {
					log.error("error: chatRoomId:{}, participantId{}", i, chatRoomSaveRequestDto.getCreatorId());
				}

				ChatVo chat = ChatVo.builder()
					.chatRoomId((long)i)
					.senderId(participantId)
					.message(faker.lorem().sentence())
					.messageType(MessageType.MEMBER)
					.build();
				chatService.save(chat);
			}
		}
	}
}
