package com.dreamcup.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParticipant is a Querydsl query type for Participant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParticipant extends EntityPathBase<Participant> {

    private static final long serialVersionUID = -1653839850L;

    public static final QParticipant participant = new QParticipant("participant");

    public final com.dreamcup.common.entity.common.QBaseTimeEntity _super = new com.dreamcup.common.entity.common.QBaseTimeEntity(this);

    public final ListPath<com.dreamcup.chatroom.entity.ChatRoomParticipants, com.dreamcup.chatroom.entity.QChatRoomParticipants> chatRoomParticipants = this.<com.dreamcup.chatroom.entity.ChatRoomParticipants, com.dreamcup.chatroom.entity.QChatRoomParticipants>createList("chatRoomParticipants", com.dreamcup.chatroom.entity.ChatRoomParticipants.class, com.dreamcup.chatroom.entity.QChatRoomParticipants.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nameTag = createString("nameTag");

    public final StringPath nickName = createString("nickName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QParticipant(String variable) {
        super(Participant.class, forVariable(variable));
    }

    public QParticipant(Path<? extends Participant> path) {
        super(path.getType(), path.getMetadata());
    }

    public QParticipant(PathMetadata metadata) {
        super(Participant.class, metadata);
    }

}

