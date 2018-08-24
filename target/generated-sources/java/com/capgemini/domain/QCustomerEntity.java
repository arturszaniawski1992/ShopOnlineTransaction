package com.capgemini.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerEntity is a Querydsl query type for CustomerEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCustomerEntity extends EntityPathBase<CustomerEntity> {

    private static final long serialVersionUID = -767992489L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerEntity customerEntity = new QCustomerEntity("customerEntity");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    public final com.capgemini.embeded.QAdressData adressData;

    //inherited
    public final DateTimePath<java.util.Date> createdTime = _super.createdTime;

    public final DatePath<java.time.LocalDate> dateOfBirth = createDate("dateOfBirth", java.time.LocalDate.class);

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastName = createString("lastName");

    public final StringPath mail = createString("mail");

    public final StringPath mobile = createString("mobile");

    public final ListPath<TransactionEntity, QTransactionEntity> transactions = this.<TransactionEntity, QTransactionEntity>createList("transactions", TransactionEntity.class, QTransactionEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.util.Date> updatedTime = _super.updatedTime;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QCustomerEntity(String variable) {
        this(CustomerEntity.class, forVariable(variable), INITS);
    }

    public QCustomerEntity(Path<? extends CustomerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerEntity(PathMetadata metadata, PathInits inits) {
        this(CustomerEntity.class, metadata, inits);
    }

    public QCustomerEntity(Class<? extends CustomerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.adressData = inits.isInitialized("adressData") ? new com.capgemini.embeded.QAdressData(forProperty("adressData")) : null;
    }

}

