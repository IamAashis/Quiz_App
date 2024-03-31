package com.android.quizapp.feature.ui.activity.main;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.android.quizapp.feature.model.Result;
import com.android.quizapp.feature.repository.QuizDbRepository;
import java.util.List;

/**
 * Created by Aashis on 31,March,2024
 */
public class QuizDataBaseViewModel extends AndroidViewModel {

    private final QuizDbRepository quizRepository;
    private final LiveData<List<Result>> listLiveData;
    public QuizDataBaseViewModel(@NonNull Application application) {
        super(application);
        quizRepository = new QuizDbRepository(application);
        listLiveData = quizRepository.getAllStudents();
    }

    public LiveData<List<Result>> getAllQuizList() {
        return quizRepository.getAllStudents();
    }

    public void insertQuiz(List<Result> result) {
        quizRepository.insertStudent(result);
    }

    public void deleteQuiz(){
        quizRepository.deleteQuiz();
    }

}

