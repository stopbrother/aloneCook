package com.aloneCook.modules.recipe;

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

    private static final long serialVersionUID = -1344894961L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipe recipe = new QRecipe("recipe");

    public final NumberPath<Long> commentCount = createNumber("commentCount", Long.class);

    public final ListPath<com.aloneCook.modules.community.Community, com.aloneCook.modules.community.QCommunity> comments = this.<com.aloneCook.modules.community.Community, com.aloneCook.modules.community.QCommunity>createList("comments", com.aloneCook.modules.community.Community.class, com.aloneCook.modules.community.QCommunity.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createdDateTime = createDateTime("createdDateTime", java.time.LocalDateTime.class);

    public final BooleanPath drafted = createBoolean("drafted");

    public final StringPath foodImg = createString("foodImg");

    public final StringPath foodIntro = createString("foodIntro");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.aloneCook.modules.image.Image, com.aloneCook.modules.image.QImage> images = this.<com.aloneCook.modules.image.Image, com.aloneCook.modules.image.QImage>createList("images", com.aloneCook.modules.image.Image.class, com.aloneCook.modules.image.QImage.class, PathInits.DIRECT2);

    public final StringPath ingredients = createString("ingredients");

    public final EnumPath<Level> level = createEnum("level", Level.class);

    public final NumberPath<Long> likeCount = createNumber("likeCount", Long.class);

    public final ListPath<com.aloneCook.modules.like.Likes, com.aloneCook.modules.like.QLikes> likes = this.<com.aloneCook.modules.like.Likes, com.aloneCook.modules.like.QLikes>createList("likes", com.aloneCook.modules.like.Likes.class, com.aloneCook.modules.like.QLikes.class, PathInits.DIRECT2);

    public final SetPath<com.aloneCook.modules.account.Account, com.aloneCook.modules.account.QAccount> manager = this.<com.aloneCook.modules.account.Account, com.aloneCook.modules.account.QAccount>createSet("manager", com.aloneCook.modules.account.Account.class, com.aloneCook.modules.account.QAccount.class, PathInits.DIRECT2);

    public final StringPath path = createString("path");

    public final BooleanPath published = createBoolean("published");

    public final DateTimePath<java.time.LocalDateTime> publishedDateTime = createDateTime("publishedDateTime", java.time.LocalDateTime.class);

    public final StringPath steps = createString("steps");

    public final StringPath title = createString("title");

    public final StringPath videoUrl = createString("videoUrl");

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public final com.aloneCook.modules.account.QAccount writer;

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
        this.writer = inits.isInitialized("writer") ? new com.aloneCook.modules.account.QAccount(forProperty("writer")) : null;
    }

}

