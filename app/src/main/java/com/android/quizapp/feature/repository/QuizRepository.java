package com.android.quizapp.feature.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.android.quizapp.feature.model.ResponseQuiz;
import com.android.quizapp.network.ApiService;
import com.android.quizapp.utils.constants.ApiConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizRepository {

    private ApiService retroServiceInterface;

    public QuizRepository(ApiService retroServiceInterface) {
        this.retroServiceInterface = retroServiceInterface;
    }

    public void makeAPICall(MutableLiveData<ResponseQuiz> liveData) {
        Call<ResponseQuiz> call  = retroServiceInterface.getEvent(10,27, ApiConstants.medium, ApiConstants.multiple);
        call.enqueue(new Callback<ResponseQuiz>() {
            @Override
            public void onResponse(@NonNull Call<ResponseQuiz> call, Response<ResponseQuiz> response) {
                if(response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseQuiz> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }
}