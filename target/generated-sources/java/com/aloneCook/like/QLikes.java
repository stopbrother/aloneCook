package com.aloneCook.like;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikes is a Querydsl query type for Likes
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLikes extends EntityPathBase<Likes> {

    private static final long serialVersionUID = -400186531L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikes likes = new QLikes("likes");

    public final com.aloneCook.user.QAccount account;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath liked = createBoolean("liked");

    public final com.aloneCook.recipe.QRecipe recipe;

    public QLikes(String variable) {
        this(Likes.class, forVariable(variable), INITS);
    }

    public QLikes(Path<? extends Likes> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikes(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikes(PathMetadata metadata, PathInits inits) {
        this(Likes.class, metadata, inits);
    }

    public QLikes(Class<? extends Likes> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.aloneCook.user.QAccount(forProperty("account")) : null;
        this.recipe = inits.isInitialized("recipe") ? new com.aloneCook.recipe.QRecipe(forProperty("recipe"), inits.get("recipe")) : null;
    }

}

