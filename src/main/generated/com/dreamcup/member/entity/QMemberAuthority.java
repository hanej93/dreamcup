package com.dreamcup.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberAuthority is a Querydsl query type for MemberAuthority
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberAuthority extends EntityPathBase<MemberAuthority> {

    private static final long serialVersionUID = -985777876L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberAuthority memberAuthority = new QMemberAuthority("memberAuthority");

    public final EnumPath<com.dreamcup.member.code.AuthorityEnum> authority = createEnum("authority", com.dreamcup.member.code.AuthorityEnum.class);

    public final QMember member;

    public QMemberAuthority(String variable) {
        this(MemberAuthority.class, forVariable(variable), INITS);
    }

    public QMemberAuthority(Path<? extends MemberAuthority> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberAuthority(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberAuthority(PathMetadata metadata, PathInits inits) {
        this(MemberAuthority.class, metadata, inits);
    }

    public QMemberAuthority(Class<? extends MemberAuthority> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

