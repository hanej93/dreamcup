package com.dreamcup.chatroom.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoomParticipants is a Querydsl query type for ChatRoomParticipants
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomParticipants extends EntityPathBase<ChatRoomParticipants> {

    private static final long serialVersionUID = -1611268439L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoomParticipants chatRoomParticipants = new QChatRoomParticipants("chatRoomParticipants");

    public final com.dreamcup.common.entity.common.QBaseTimeEntity _super = new com.dreamcup.common.entity.common.QBaseTimeEntity(this);

    public final QChatRoom chatRoom;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.dreamcup.member.entity.QParticipant participant;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QChatRoomParticipants(String variable) {
        this(ChatRoomParticipants.class, forVariable(variable), INITS);
    }

    public QChatRoomParticipants(Path<? extends ChatRoomParticipants> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoomParticipants(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoomParticipants(PathMetadata metadata, PathInits inits) {
        this(ChatRoomParticipants.class, metadata, inits);
    }

    public QChatRoomParticipants(Class<? extends ChatRoomParticipants> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new QChatRoom(forProperty("chatRoom"), inits.get("chatRoom")) : null;
        this.participant = inits.isInitialized("participant") ? new com.dreamcup.member.entity.QParticipant(forProperty("participant")) : null;
    }

}

