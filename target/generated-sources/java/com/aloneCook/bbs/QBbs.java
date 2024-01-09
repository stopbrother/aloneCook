package com.aloneCook.bbs;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBbs is a Querydsl query type for Bbs
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBbs extends EntityPathBase<Bbs> {

    private static final long serialVersionUID = 410355200L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBbs bbs = new QBbs("bbs");

    public final EnumPath<Category> category = createEnum("category", Category.class);

    public final ListPath<com.aloneCook.community.Community, com.aloneCook.community.QCommunity> comments = this.<com.aloneCook.community.Community, com.aloneCook.community.QCommunity>createList("comments", com.aloneCook.community.Community.class, com.aloneCook.community.QCommunity.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdDateTime = createDateTime("createdDateTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final SetPath<com.aloneCook.user.Account, com.aloneCook.user.QAccount> managers = this.<com.aloneCook.user.Account, com.aloneCook.user.QAccount>createSet("managers", com.aloneCook.user.Account.class, com.aloneCook.user.QAccount.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public final com.aloneCook.user.QAccount writer;

    public QBbs(String variable) {
        this(Bbs.class, forVariable(variable), INITS);
    }

    public QBbs(Path<? extends Bbs> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBbs(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBbs(PathMetadata metadata, PathInits inits) {
        this(Bbs.class, metadata, inits);
    }

    public QBbs(Class<? extends Bbs> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.writer = inits.isInitialized("writer") ? new com.aloneCook.user.QAccount(forProperty("writer")) : null;
    }

}

