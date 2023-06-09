package com.dreamcup.chatroom.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = -306640791L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final com.dreamcup.common.entity.common.QBaseTimeEntity _super = new com.dreamcup.common.entity.common.QBaseTimeEntity(this);

    public final NumberPath<Long> chatRoomId = createNumber("chatRoomId", Long.class);

    public final ListPath<Chat, QChat> chats = this.<Chat, QChat>createList("chats", Chat.class, QChat.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final com.dreamcup.member.entity.QParticipant creator;

    public final NumberPath<Integer> maxUserCount = createNumber("maxUserCount", Integer.class);

    public final ListPath<com.dreamcup.member.entity.Participant, com.dreamcup.member.entity.QParticipant> participants = this.<com.dreamcup.member.entity.Participant, com.dreamcup.member.entity.QParticipant>createList("participants", com.dreamcup.member.entity.Participant.class, com.dreamcup.member.entity.QParticipant.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QChatRoom(String variable) {
        this(ChatRoom.class, forVariable(variable), INITS);
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoom(PathMetadata metadata, PathInits inits) {
        this(ChatRoom.class, metadata, inits);
    }

    public QChatRoom(Class<? extends ChatRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creator = inits.isInitialized("creator") ? new com.dreamcup.member.entity.QParticipant(forProperty("creator"), inits.get("creator")) : null;
    }

}

