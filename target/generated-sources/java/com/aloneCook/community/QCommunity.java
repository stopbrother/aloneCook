package com.aloneCook.community;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunity is a Querydsl query type for Community
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCommunity extends EntityPathBase<Community> {

    private static final long serialVersionUID = 458765868L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunity community = new QCommunity("community");

    public final com.aloneCook.user.QAccount account;

    public final com.aloneCook.bbs.QBbs bbs;

    public final StringPath comment = createString("comment");

    public final DateTimePath<java.time.LocalDateTime> createDateTime = createDateTime("createDateTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.aloneCook.recipe.QRecipe recipe;

    public QCommunity(String variable) {
        this(Community.class, forVariable(variable), INITS);
    }

    public QCommunity(Path<? extends Community> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunity(PathMetadata metadata, PathInits inits) {
        this(Community.class, metadata, inits);
    }

    public QCommunity(Class<? extends Community> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.aloneCook.user.QAccount(forProperty("account")) : null;
        this.bbs = inits.isInitialized("bbs") ? new com.aloneCook.bbs.QBbs(forProperty("bbs"), inits.get("bbs")) : null;
        this.recipe = inits.isInitialized("recipe") ? new com.aloneCook.recipe.QRecipe(forProperty("recipe"), inits.get("recipe")) : null;
    }

}

