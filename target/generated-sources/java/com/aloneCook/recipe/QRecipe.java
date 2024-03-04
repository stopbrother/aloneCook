package com.aloneCook.recipe;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipe is a Querydsl query type for Recipe
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRecipe extends EntityPathBase<Recipe> {

    private static final long serialVersionUID = -651360714L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipe recipe = new QRecipe("recipe");

    public final NumberPath<Long> commentCount = createNumber("commentCount", Long.class);

    public final ListPath<com.aloneCook.community.Community, com.aloneCook.community.QCommunity> comments = this.<com.aloneCook.community.Community, com.aloneCook.community.QCommunity>createList("comments", com.aloneCook.community.Community.class, com.aloneCook.community.QCommunity.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createdDateTime = createDateTime("createdDateTime", java.time.LocalDateTime.class);

    public final BooleanPath drafted = createBoolean("drafted");

    public final StringPath foodImg = createString("foodImg");

    public final StringPath foodIntro = createString("foodIntro");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.aloneCook.image.Image, com.aloneCook.image.QImage> images = this.<com.aloneCook.image.Image, com.aloneCook.image.QImage>createList("images", com.aloneCook.image.Image.class, com.aloneCook.image.QImage.class, PathInits.DIRECT2);

    public final StringPath ingredients = createString("ingredients");

    public final EnumPath<Level> level = createEnum("level", Level.class);

    public final NumberPath<Long> likeCount = createNumber("likeCount", Long.class);

    public final ListPath<com.aloneCook.like.Likes, com.aloneCook.like.QLikes> likes = this.<com.aloneCook.like.Likes, com.aloneCook.like.QLikes>createList("likes", com.aloneCook.like.Likes.class, com.aloneCook.like.QLikes.class, PathInits.DIRECT2);

    public final SetPath<com.aloneCook.user.Account, com.aloneCook.user.QAccount> manager = this.<com.aloneCook.user.Account, com.aloneCook.user.QAccount>createSet("manager", com.aloneCook.user.Account.class, com.aloneCook.user.QAccount.class, PathInits.DIRECT2);

    public final StringPath path = createString("path");

    public final BooleanPath published = createBoolean("published");

    public final DateTimePath<java.time.LocalDateTime> publishedDateTime = createDateTime("publishedDateTime", java.time.LocalDateTime.class);

    public final StringPath steps = createString("steps");

    public final StringPath title = createString("title");

    public final StringPath videoUrl = createString("videoUrl");

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public final com.aloneCook.user.QAccount writer;

    public QRecipe(String variable) {
        this(Recipe.class, forVariable(variable), INITS);
    }

    public QRecipe(Path<? extends Recipe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipe(PathMetadata metadata, PathInits inits) {
        this(Recipe.class, metadata, inits);
    }

    public QRecipe(Class<? extends Recipe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.writer = inits.isInitialized("writer") ? new com.aloneCook.user.QAccount(forProperty("writer")) : null;
    }

}

