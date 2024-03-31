package com.android.quizapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.android.quizapp.feature.model.Result;
import java.util.List;

/**
 * Created by Aashis on 31,March,2024
 */
@androidx.room.Dao
public interface AppDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Result> result);

    @Query("SELECT * FROM result")
    LiveData<List<Result>> getAllQuiz();

    @Query("DELETE FROM result")
    void deleteAll();

}