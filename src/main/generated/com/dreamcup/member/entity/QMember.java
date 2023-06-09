package com.dreamcup.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -2045849641L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final QParticipant _super;

    public final SetPath<MemberAuthority, QMemberAuthority> authorities = this.<MemberAuthority, QMemberAuthority>createSet("authorities", MemberAuthority.class, QMemberAuthority.class, PathInits.DIRECT2);

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

    public final StringPath password = createString("password");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate;

    public final StringPath username = createString("username");

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
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

