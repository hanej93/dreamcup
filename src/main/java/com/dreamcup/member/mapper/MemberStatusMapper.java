package com.dreamcup.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.dreamcup.member.dto.response.MemberStatusDto;
import com.dreamcup.member.entity.MemberStatus;

@Mapper
public interface MemberStatusMapper {

	MemberStatusMapper INSTANCE = Mappers.getMapper(MemberStatusMapper.class);

	MemberStatusDto toMemberStatusDto(MemberStatus memberStatus);

}
