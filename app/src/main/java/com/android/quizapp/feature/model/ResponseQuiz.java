package com.android.quizapp.feature.model;
import java.util.List;

/**
 * Created by Aashis on 30,March,2024
 */

public class ResponseQuiz {
    private int response_code;
    private List<Result> results;

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}



