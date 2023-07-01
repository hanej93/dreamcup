package com.dreamcup.friend.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.dreamcup.friend.dto.respoonse.FriendChatResponseDto;
import com.dreamcup.friend.entity.FriendChat;

@Mapper
public interface FriendChatMapper {
	FriendChatMapper INSTANCE = Mappers.getMapper(FriendChatMapper.class);

	@Mapping(source = "sender.id", target = "senderId")
	@Mapping(source = "sender.nickname", target = "senderNickname")
	@Mapping(source = "sender.nameTag", target = "senderNameTag")
	@Mapping(source = "receiver.id", target = "receiverId")
	@Mapping(source = "receiver.nickname", target = "receiverNickname")
	@Mapping(source = "receiver.nameTag", target = "receiverNameTag")
	FriendChatResponseDto toFriendChatResponseDto(FriendChat friendChat);

	List<FriendChatResponseDto> toFriendChatResponseDtoList(List<FriendChat> friendChats);
}
