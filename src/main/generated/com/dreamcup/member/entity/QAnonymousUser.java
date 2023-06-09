package com.dreamcup.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnonymousUser is a Querydsl query type for AnonymousUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnonymousUser extends EntityPathBase<AnonymousUser> {

    private static final long serialVersionUID = 2144772603L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnonymousUser anonymousUser = new QAnonymousUser("anonymousUser");

    public final QParticipant _super;

    public final BooleanPath anonymous = createBoolean("anonymous");

    // inherited
    public final com.dreamcup.chatroom.entity.QChatRoom chatRoom;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final StringPath nameTag;

    //inherited
    public final StringPath nickName;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate;

    public QAnonymousUser(String variable) {
        this(AnonymousUser.class, forVariable(variable), INITS);
    }

    public QAnonymousUser(Path<? extends AnonymousUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnonymousUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnonymousUser(PathMetadata metadata, PathInits inits) {
        this(AnonymousUser.class, metadata, inits);
    }

    public QAnonymousUser(Class<? extends AnonymousUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QParticipant(type, metadata, inits);
        this.chatRoom = _super.chatRoom;
        this.createdDate = _super.createdDate;
        this.id = _super.id;
        this.nameTag = _super.nameTag;
        this.nickName = _super.nickName;
        this.updatedDate = _super.updatedDate;
    }

}

