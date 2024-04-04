package com.aloneCook.modules.bbs;

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

    private static final long serialVersionUID = 91725465L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBbs bbs = new QBbs("bbs");

    public final EnumPath<Category> category = createEnum("category", Category.class);

    public final ListPath<com.aloneCook.modules.community.Community, com.aloneCook.modules.community.QCommunity> comments = this.<com.aloneCook.modules.community.Community, com.aloneCook.modules.community.QCommunity>createList("comments", com.aloneCook.modules.community.Community.class, com.aloneCook.modules.community.QCommunity.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdDateTime = createDateTime("createdDateTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final SetPath<com.aloneCook.modules.account.Account, com.aloneCook.modules.account.QAccount> managers = this.<com.aloneCook.modules.account.Account, com.aloneCook.modules.account.QAccount>createSet("managers", com.aloneCook.modules.account.Account.class, com.aloneCook.modules.account.QAccount.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public final com.aloneCook.modules.account.QAccount writer;

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
        this.writer = inits.isInitialized("writer") ? new com.aloneCook.modules.account.QAccount(forProperty("writer")) : null;
    }

}

