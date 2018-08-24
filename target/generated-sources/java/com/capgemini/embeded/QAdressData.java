package com.capgemini.embeded;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdressData is a Querydsl query type for AdressData
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QAdressData extends BeanPath<AdressData> {

    private static final long serialVersionUID = -2145472534L;

    public static final QAdressData adressData = new QAdressData("adressData");

    public final StringPath city = createString("city");

    public final NumberPath<Integer> number = createNumber("number", Integer.class);

    public final StringPath postCode = createString("postCode");

    public final StringPath street = createString("street");

    public QAdressData(String variable) {
        super(AdressData.class, forVariable(variable));
    }

    public QAdressData(Path<? extends AdressData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdressData(PathMetadata metadata) {
        super(AdressData.class, metadata);
    }

}

