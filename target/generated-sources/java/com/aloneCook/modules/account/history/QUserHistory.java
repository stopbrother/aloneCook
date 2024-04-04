package com.aloneCook.modules.account.history;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserHistory is a Querydsl query type for UserHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserHistory extends EntityPathBase<UserHistory> {

    private static final long serialVersionUID = -1484480817L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserHistory userHistory = new QUserHistory("userHistory");

    public final com.aloneCook.modules.account.QAccount account;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.aloneCook.modules.recipe.QRecipe recipe;

    public final DateTimePath<java.time.LocalDateTime> timeStamp = createDateTime("timeStamp", java.time.LocalDateTime.class);

    public QUserHistory(String variable) {
        this(UserHistory.class, forVariable(variable), INITS);
    }

    public QUserHistory(Path<? extends UserHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserHistory(PathMetadata metadata, PathInits inits) {
        this(UserHistory.class, metadata, inits);
    }

    public QUserHistory(Class<? extends UserHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.aloneCook.modules.account.QAccount(forProperty("account")) : null;
        this.recipe = inits.isInitialized("recipe") ? new com.aloneCook.modules.recipe.QRecipe(forProperty("recipe"), inits.get("recipe")) : null;
    }

}

