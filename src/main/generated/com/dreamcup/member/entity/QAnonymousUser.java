package com.dreamcup.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAnonymousUser is a Querydsl query type for AnonymousUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnonymousUser extends EntityPathBase<AnonymousUser> {

    private static final long serialVersionUID = 2144772603L;

    public static final QAnonymousUser anonymousUser = new QAnonymousUser("anonymousUser");

    public final QParticipant _super = new QParticipant(this);

    public final BooleanPath anonymous = createBoolean("anonymous");

    //inherited
    public final ListPath<com.dreamcup.chatroom.entity.ChatRoomParticipants, com.dreamcup.chatroom.entity.QChatRoomParticipants> chatRoomParticipants = _super.chatRoomParticipants;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath nameTag = _super.nameTag;

    //inherited
    public final StringPath nickName = _super.nickName;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QAnonymousUser(String variable) {
        super(AnonymousUser.class, forVariable(variable));
    }

    public QAnonymousUser(Path<? extends AnonymousUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAnonymousUser(PathMetadata metadata) {
        super(AnonymousUser.class, metadata);
    }

}

