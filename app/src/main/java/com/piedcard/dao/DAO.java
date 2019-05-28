package com.piedcard.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

public interface DAO<T> {

    @Insert
    long save(T t);

    @Update
    int update(T t);

    @Delete
    int delete(T t);
}