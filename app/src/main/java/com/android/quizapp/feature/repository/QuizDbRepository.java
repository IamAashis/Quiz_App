package com.android.quizapp.feature.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.android.quizapp.dao.AppDao;
import com.android.quizapp.database.MyDatabase;
import com.android.quizapp.feature.model.Result;

import java.util.List;

/**
 * Created by Aashis on 31,March,2024
 */
public class QuizDbRepository {

    private AppDao appDao;
    private LiveData<List<Result>> getAllQuiz;

    public QuizDbRepository(Application application) {
        MyDatabase database = MyDatabase.getDatabase(application);
        appDao = database.appDao();
        getAllQuiz = appDao.getAllQuiz();
    }

    public void insertStudent(List<Result> results) {
        MyDatabase.databaseWriteExecutor.execute(() -> appDao.insert(results));
    }

    public LiveData<List<Result>> getAllStudents() {
        return getAllQuiz;
    }

    public void deleteQuiz(){
        MyDatabase.databaseWriteExecutor.execute(() -> appDao.deleteAll());
    }
}