package com.ry.androidroomdatabase.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ry.androidroomdatabase.model.User;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by netserve on 14/09/2018.
 */

@Dao
public interface UserDAO {
    @Query("SELECT * FROM users WHERE id=:userId")
    Flowable<User> getUserbyId(int userId);

    @Query("SELECT * FROM users")
    Flowable<List<User>> getAllUsers();

    @Insert
    void insertUser(User... users);

    @Update
    void updateUser(User... users);

    @Delete
    void deleteUser(User users);

    @Query("DELETE FROM users")
    void deleteAllUser();
}
