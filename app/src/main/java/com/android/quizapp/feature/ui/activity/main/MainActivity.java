package com.android.quizapp.feature.ui.activity.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.android.quizapp.R;
import com.android.quizapp.databinding.ActivityMainBinding;
import com.android.quizapp.feature.model.ResponseQuiz;
import com.android.quizapp.feature.model.Result;
import com.android.quizapp.feature.shared.adapters.QuestionListAdapter;
import com.android.quizapp.feature.ui.fragment.quiz.QuizFragment;
import com.android.quizapp.utils.extension.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<Fragment> fragmentList =new ArrayList<>();
    private QuestionListAdapter questionListAdapter;
    private List<Result> resultList = new ArrayList<>();
    private int myTotalPoint = 0;
    private String name;

    CountDownTimer countDownTimer;

    QuizViewModel viewModel;
    QuizDataBaseViewModel  quizDataBaseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setup();
    }

    public void setup(){
         viewModel = new ViewModelProvider(this).get(QuizViewModel.class);
         quizDataBaseViewModel = new ViewModelProvider(this).get(QuizDataBaseViewModel.class);
        initListener();
        initViewModel();
    }

    private void setProgressSuccess() {
        binding.prbSuccess.setRounded(true);
        binding.prbSuccess.setProgressWidth(28);
        binding.prbSuccess.setProgressColor(getColor(R.color.blue));
        binding.prbSuccess.setProgress(myTotalPoint);
    }

    private void checkConnectivity(){
        Log.d("indexCheck", "checkConnectivity"  );

        showAndHideShimmer(true);
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
//            initViewModel();
            viewModel.makeApiCall();
        } else {
            getDataFromDb();
        }
    }

    private void initListener() {
        binding.btnTakeAnotherShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completed(true);
                startQuizAgain();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("indexCheck", "btnSubmit"  );

                checkConnectivity();
                myTotalPoint = 0;
                name = Objects.requireNonNull(binding.edtName.getText()).toString();
                binding.crdWelcome.setVisibility(View.GONE);
                binding.vwpQuestions.setVisibility(View.VISIBLE);
                binding.imvBackQuestion.setVisibility(View.GONE);
                binding.txvTimer.setVisibility(View.VISIBLE);
                KeyboardUtils.hideKeyboard(MainActivity.this);
                countDownTimer();
            }
        });
    }

    public void startQuizAgain(){
        binding.vwpQuestions.setCurrentItem(0, false);
        fragmentList.clear();
        questionListAdapter.notifyDataSetChanged();
        binding.crdWelcome.setVisibility(View.VISIBLE);
        binding.vwpQuestions.setVisibility(View.GONE);
        binding.crdCompleted.setVisibility(View.GONE);
    }

    public void countDownTimer(){
        countDownTimer =   new CountDownTimer(120000, 1000) {
            @SuppressLint("DefaultLocale")
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                long minutes = secondsRemaining / 60;
                long seconds = secondsRemaining % 60;
                binding.txvTimer.setText(String.format("%02d:%02d", minutes, seconds));
            }

            public void onFinish() {
                quizSubmit();
            }
        }.start();
    }

    public void getDataFromDb() {
//        QuizDataBaseViewModel viewModel = new ViewModelProvider(this).get(QuizDataBaseViewModel.class);

        quizDataBaseViewModel.getAllQuizList().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                resultList.clear();
                resultList.addAll(results);
                Log.d("indexCheck", "getDataFromDb"  );
                if (!resultList.isEmpty())
                    initViewPager();
                else
                    showNoData();
            }
        });
    }

    public void showNoData(){
        binding.cltNoData.setVisibility(View.VISIBLE);
        binding.txvTimer.setVisibility(View.GONE);
        binding.vwpQuestions.setVisibility(View.GONE);
        binding.sflQuestion.setVisibility(View.GONE);
    }

    public void showAndHideShimmer(boolean showShimmer) {
        if (showShimmer) {
            binding.sflQuestion.setVisibility(View.VISIBLE);
            binding.vwpQuestions.setVisibility(View.GONE);
        } else {
            binding.sflQuestion.setVisibility(View.GONE);
            binding.vwpQuestions.setVisibility(View.VISIBLE);
        }
    }

    private void initViewModel() {
        Log.d("indexCheck", "initViewModel"  );

        viewModel.getLiveData().observe(this, new Observer<ResponseQuiz>() {
            @Override
            public void onChanged(ResponseQuiz recyclerData) {
                if(recyclerData != null) {
                    quizDataBaseViewModel.deleteQuiz();
                    resultList.clear();
                    resultList.addAll(recyclerData.getResults());
                    Log.d("indexCheck", "obser"  );
                    initViewPager();
                    quizDataBaseViewModel.insertQuiz(recyclerData.getResults());
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.errorGettingData), Toast.LENGTH_SHORT).show();;
                }
            }
        });
//        viewModel.makeApiCall();
    }

    private void initViewPager(){
        Log.d("indexCheck", "initViewPager"  );
        Log.d("indexCheck", "checkList" + resultList.toString() );
        for (int i = 0; i < resultList.size(); i++){
            QuizFragment  quizFragment = new QuizFragment();
            quizFragment.setQuestionTitle(resultList.get(i).getQuestion());
            quizFragment.setQuestionIndex(i + 1);

            Random random = new Random();
            List<String> listItems = new ArrayList<>(resultList.get(i).getIncorrect_answers());
            int randomIndex = random.nextInt(listItems.size() + 1);
            listItems.add(randomIndex, resultList.get(i).getCorrect_answer());
            quizFragment.setAnswerList(listItems);
            fragmentList.add((QuizFragment)quizFragment);
        }

        questionListAdapter = new QuestionListAdapter(fragmentList, this);
        binding.vwpQuestions.setAdapter(questionListAdapter);
        binding.vwpQuestions.setUserInputEnabled(false);
        binding.vwpQuestions.setCurrentItem(0, false);
        binding.vwpQuestions.setOffscreenPageLimit(fragmentList != null ? fragmentList.size() : 1);
        showAndHideShimmer(false);
    }

    public void completed(boolean startAgain){
        if(startAgain) {
            binding.crdCompleted.setVisibility(View.GONE);
            binding.cltNoData.setVisibility(View.GONE);
        }else{
            binding.crdCompleted.setVisibility(View.VISIBLE);
            binding.vwpQuestions.setVisibility(View.GONE);
        }
    }

    public void toNextPage(String quizAnswer){
        int currentPageIndex =  binding.vwpQuestions.getCurrentItem();
        checkPoint(quizAnswer, currentPageIndex);
        binding.vwpQuestions.setCurrentItem(currentPageIndex += 1);
        if(currentPageIndex == 9){
            quizSubmit();
        }
    }

    public void quizSubmit(){
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        completed(false);
        setMyTotalPoint();
        binding.txvName.setText(getString(R.string.wellPlayed, name));
        binding.imvBackQuestion.setVisibility(View.GONE);
        binding.txvTimer.setVisibility(View.GONE);
        setProgressSuccess();
    }

    public void setMyTotalPoint(){
        binding.txvTotalPoints.setText(getString(R.string.totalPoints, myTotalPoint));
        binding.txvProPoints.setText(String.valueOf(myTotalPoint));
    }

    public void checkPoint(String quizAnswer, int currentPageIndex){
        for (int i = 0; i < resultList.size(); i++) {
            if (i == currentPageIndex){
               String correctAns = resultList.get(i).getCorrect_answer();

               if (quizAnswer == correctAns){
                   myTotalPoint += 10;
               }
            }
        }
    }
}