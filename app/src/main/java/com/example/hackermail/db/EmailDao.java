package com.example.hackermail.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface EmailDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Email email);

    @Update
    public void update(Email... emails);

    @Query("select * from email where emailId = :emailId")
    public Email findEmail(int emailId);

    @Query("select * from email limit 1")
    public Email[] findAnyEmail();

    @Query("select * from email")
    public LiveData<List<Email>> findAllEmails();
}
