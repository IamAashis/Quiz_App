package com.android.quizapp.network;

import com.android.quizapp.feature.model.ResponseQuiz;
import com.android.quizapp.utils.constants.ApiConstants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Aashis on 30,March,2024
 */
public interface ApiService {
    @GET(ApiConstants.EVENT)
    Call<ResponseQuiz> getEvent(
            @Query(ApiConstants.amount) int amount,
            @Query(ApiConstants.category) int category,
            @Query(ApiConstants.difficulty) String difficulty,
            @Query(ApiConstants.type) String type
    );
}

