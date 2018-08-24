package com.capgemini.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchasedProductEntity is a Querydsl query type for PurchasedProductEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPurchasedProductEntity extends EntityPathBase<PurchasedProductEntity> {

    private static final long serialVersionUID = -1966922523L;

    public static final QPurchasedProductEntity purchasedProductEntity = new QPurchasedProductEntity("purchasedProductEntity");

    public final QAbstractEntity _super = new QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.util.Date> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> margin = createNumber("margin", Double.class);

    public final ListPath<OrderEntity, QOrderEntity> orders = this.<OrderEntity, QOrderEntity>createList("orders", OrderEntity.class, QOrderEntity.class, PathInits.DIRECT2);

    public final NumberPath<Double> price = createNumber("price", Double.class);

    public final StringPath productName = createString("productName");

    //inherited
    public final DateTimePath<java.util.Date> updatedTime = _super.updatedTime;

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QPurchasedProductEntity(String variable) {
        super(PurchasedProductEntity.class, forVariable(variable));
    }

    public QPurchasedProductEntity(Path<? extends PurchasedProductEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPurchasedProductEntity(PathMetadata metadata) {
        super(PurchasedProductEntity.class, metadata);
    }

}

