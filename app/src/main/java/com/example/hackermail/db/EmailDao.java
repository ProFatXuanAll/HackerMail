package com.example.hackermail.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface EmailDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Email... email);

    @Update
    public void update(Email... emails);

    @Delete
    void delete(Email email);

    @Query("select * from email where email_id = :emailId")
    public LiveData<Email> getEmail(int emailId);

    @Query("SELECT * FROM email LIMIT 1")
    public Email[] getAnyEmail();

    @Query("SELECT * FROM email")
    public LiveData<List<Email>> getAllEmails();
}
