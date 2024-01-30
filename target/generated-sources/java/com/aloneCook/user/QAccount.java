package com.aloneCook.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = 145444290L;

    public static final QAccount account = new QAccount("account");

    public final StringPath email = createString("email");

    public final ListPath<com.aloneCook.follow.Follow, com.aloneCook.follow.QFollow> followers = this.<com.aloneCook.follow.Follow, com.aloneCook.follow.QFollow>createList("followers", com.aloneCook.follow.Follow.class, com.aloneCook.follow.QFollow.class, PathInits.DIRECT2);

    public final ListPath<com.aloneCook.follow.Follow, com.aloneCook.follow.QFollow> following = this.<com.aloneCook.follow.Follow, com.aloneCook.follow.QFollow>createList("following", com.aloneCook.follow.Follow.class, com.aloneCook.follow.QFollow.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    public final DateTimePath<java.time.LocalDateTime> joinedAt = createDateTime("joinedAt", java.time.LocalDateTime.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath profileImg = createString("profileImg");

    public final StringPath url = createString("url");

    public QAccount(String variable) {
        super(Account.class, forVariable(variable));
    }

    public QAccount(Path<? extends Account> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccount(PathMetadata metadata) {
        super(Account.class, metadata);
    }

}

