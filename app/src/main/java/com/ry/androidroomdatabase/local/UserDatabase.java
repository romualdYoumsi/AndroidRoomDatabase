package com.ry.androidroomdatabase.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ry.androidroomdatabase.model.User;

import static com.ry.androidroomdatabase.local.UserDatabase.DATABASE_VERSION;
/**
 * Created by netserve on 14/09/2018.
 */

@Database(entities = {User.class}, version = DATABASE_VERSION)
public abstract class UserDatabase extends RoomDatabase {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RY_db_room";

    public abstract UserDAO userDAO();

    private static UserDatabase mInstance;

    public static UserDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context, UserDatabase.class, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build();
        }

        return mInstance;
    }
}
