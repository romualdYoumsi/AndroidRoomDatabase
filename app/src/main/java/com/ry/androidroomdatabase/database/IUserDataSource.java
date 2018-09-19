package com.ry.androidroomdatabase.database;

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

public interface IUserDataSource {
    Flowable<User> getUserbyId(int userId);
    Flowable<List<User>> getAllUsers();
    void insertUser(User... users);
    void updateUser(User... users);
    void deleteUser(User users);
    void deleteAllUser();
}
