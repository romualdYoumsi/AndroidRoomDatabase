package com.ry.androidroomdatabase.local;

import com.ry.androidroomdatabase.database.IUserDataSource;
import com.ry.androidroomdatabase.model.User;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by netserve on 14/09/2018.
 */

public class UserDataSource implements IUserDataSource {
    private UserDAO userDAO;
    private static UserDataSource mInstance;

    public UserDataSource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public static UserDataSource getInstance(UserDAO userDAO) {
        if (mInstance == null) {
            mInstance = new UserDataSource(userDAO);
        }
        return mInstance;
    }

    @Override
    public Flowable<User> getUserbyId(int userId) {
        return userDAO.getUserbyId(userId);
    }

    @Override
    public Flowable<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void insertUser(User... users) {
        userDAO.insertUser(users);
    }

    @Override
    public void updateUser(User... users) {
        userDAO.updateUser(users);
    }

    @Override
    public void deleteUser(User users) {
        userDAO.deleteUser(users);
    }

    @Override
    public void deleteAllUser() {
        userDAO.deleteAllUser();
    }
}
