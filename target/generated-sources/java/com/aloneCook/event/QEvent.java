package com.aloneCook.event;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEvent is a Querydsl query type for Event
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEvent extends EntityPathBase<Event> {

    private static final long serialVersionUID = -517660594L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEvent event = new QEvent("event");

    public final com.aloneCook.user.QAccount account;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.aloneCook.recipe.QRecipe recipe;

    public QEvent(String variable) {
        this(Event.class, forVariable(variable), INITS);
    }

    public QEvent(Path<? extends Event> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEvent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEvent(PathMetadata metadata, PathInits inits) {
        this(Event.class, metadata, inits);
    }

    public QEvent(Class<? extends Event> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.aloneCook.user.QAccount(forProperty("account")) : null;
        this.recipe = inits.isInitialized("recipe") ? new com.aloneCook.recipe.QRecipe(forProperty("recipe"), inits.get("recipe")) : null;
    }

}

