package com.android.quizapp.database;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.quizapp.dao.AppDao;
import com.android.quizapp.dao.Converters;
import com.android.quizapp.feature.model.Result;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Aashis on 31,March,2024
 */
@Database(entities = {Result.class},version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MyDatabase extends RoomDatabase {

    private static final String DATABASE_NAME="quiz";
    public abstract AppDao appDao();
    private static volatile MyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

     public static MyDatabase getDatabase( Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MyDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

