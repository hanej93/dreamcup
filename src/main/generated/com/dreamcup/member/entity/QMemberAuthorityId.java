package com.dreamcup.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberAuthorityId is a Querydsl query type for MemberAuthorityId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMemberAuthorityId extends BeanPath<MemberAuthorityId> {

    private static final long serialVersionUID = 1855235943L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberAuthorityId memberAuthorityId = new QMemberAuthorityId("memberAuthorityId");

    public final EnumPath<com.dreamcup.member.code.AuthorityEnum> authority = createEnum("authority", com.dreamcup.member.code.AuthorityEnum.class);

    public final QMember member;

    public QMemberAuthorityId(String variable) {
        this(MemberAuthorityId.class, forVariable(variable), INITS);
    }

    public QMemberAuthorityId(Path<? extends MemberAuthorityId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberAuthorityId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberAuthorityId(PathMetadata metadata, PathInits inits) {
        this(MemberAuthorityId.class, metadata, inits);
    }

    public QMemberAuthorityId(Class<? extends MemberAuthorityId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

