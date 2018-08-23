package com.capgemini.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTransactionEntity is a Querydsl query type for TransactionEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTransactionEntity extends EntityPathBase<TransactionEntity> {

    private static final long serialVersionUID = -27226965L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTransactionEntity transactionEntity = new QTransactionEntity("transactionEntity");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> createdTime = _super.createdTime;

    public final QCustomerEntity customerEntity;

    public final DateTimePath<java.time.LocalDateTime> dateTransaction = createDateTime("dateTransaction", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<OrderEntity, QOrderEntity> orders = this.<OrderEntity, QOrderEntity>createList("orders", OrderEntity.class, QOrderEntity.class, PathInits.DIRECT2);

    public final EnumPath<com.capgemini.enums.TransactionStatus> transactionStatus = createEnum("transactionStatus", com.capgemini.enums.TransactionStatus.class);

    //inherited
    public final DateTimePath<java.util.Date> updatedTime = _super.updatedTime;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QTransactionEntity(String variable) {
        this(TransactionEntity.class, forVariable(variable), INITS);
    }

    public QTransactionEntity(Path<? extends TransactionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTransactionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTransactionEntity(PathMetadata metadata, PathInits inits) {
        this(TransactionEntity.class, metadata, inits);
    }

    public QTransactionEntity(Class<? extends TransactionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customerEntity = inits.isInitialized("customerEntity") ? new QCustomerEntity(forProperty("customerEntity"), inits.get("customerEntity")) : null;
    }

}

