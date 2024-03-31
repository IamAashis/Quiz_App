package com.android.quizapp.feature.ui.activity.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.android.quizapp.feature.model.ResponseQuiz;
import com.android.quizapp.feature.repository.QuizRepository;
import com.android.quizapp.network.ApiService;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * Created by Aashis on 30,March,2024
 */
@HiltViewModel
public class QuizViewModel extends ViewModel {
    MutableLiveData<ResponseQuiz> liveData;
    @Inject
    ApiService retroServiceInterface;

    @Inject
    public QuizViewModel() {
        liveData = new MutableLiveData();
    }

    public MutableLiveData<ResponseQuiz> getLiveData() {
        return liveData;
    }

    public void makeApiCall() {
        QuizRepository retroRepository = new QuizRepository(retroServiceInterface);
        retroRepository.makeAPICall(liveData);
    }
}