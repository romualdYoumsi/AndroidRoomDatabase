package com.ry.androidroomdatabase.database;

import com.ry.androidroomdatabase.model.User;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by netserve on 14/09/2018.
 */

public class UserRepository implements IUserDataSource {
    private IUserDataSource mLocalDataSource;

    private static UserRepository mInstance;

    public UserRepository(IUserDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static UserRepository getInstance(IUserDataSource mLocalDataSource) {
        if (mInstance == null) {
            mInstance = new UserRepository(mLocalDataSource);
        }

        return mInstance;
    }

    @Override
    public Flowable<User> getUserbyId(int userId) {
        return mLocalDataSource.getUserbyId(userId);
    }

    @Override
    public Flowable<List<User>> getAllUsers() {
        return mLocalDataSource.getAllUsers();
    }

    @Override
    public void insertUser(User... users) {
        mLocalDataSource.insertUser(users);
    }

    @Override
    public void updateUser(User... users) {
        mLocalDataSource.updateUser(users);
    }

    @Override
    public void deleteUser(User users) {
        mLocalDataSource.deleteUser(users);
    }

    @Override
    public void deleteAllUser() {
        mLocalDataSource.deleteAllUser();
    }
}
