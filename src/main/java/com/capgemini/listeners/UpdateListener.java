package com.capgemini.listeners;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.PreUpdate;

import com.capgemini.domain.AbstractEntity;


public class UpdateListener {


    @PreUpdate
    protected void onUpdate(AbstractEntity abstractEntity) {
        Date date = new Date();
        abstractEntity.setUpdatedTime(new Timestamp(date.getTime()));
    }


}