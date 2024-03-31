package com.android.quizapp.feature.ui.fragment.quiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.android.quizapp.feature.ui.activity.main.MainActivity;
import com.android.quizapp.R;
import com.android.quizapp.databinding.FragmentQuizBinding;
import com.android.quizapp.feature.shared.adapters.AnswerListAdapter;
import com.android.quizapp.feature.shared.listener.QuizAnswerListener;
import java.util.List;

public class QuizFragment extends Fragment {

    private FragmentQuizBinding binding;
    private String questionTitle;
    private int questionIndex = 0;
    private AnswerListAdapter recyclerViewAdapter;
    private List<String> listItems;
    QuizAnswerListener quizAnswerListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setup();
    }

    public void setup(){
        Log.d("indexCheck", "" + questionIndex);
        binding.txvQuestion.setText(questionTitle);
        binding.prbProgressBarQuestion.setProgress(questionIndex);
        int totalQuestion = 10;
        binding.txvQuestionNumber.setText(getString(R.string.question, questionIndex, totalQuestion));
        setClickListenerAnswer();
        initRecyclerView();
        checkQuizCompletion();
        iniListener();
    }

    public void iniListener(){
     /*   binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).quizSubmit();
            }
        });*/
    }

    public void checkQuizCompletion(){
//        if(questionIndex == 10){
//            binding.btnSubmit.setVisibility(View.VISIBLE);
//        }
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public void setQuestionIndex(int i){
        questionIndex = i;
    }

    public void setAnswerList(List<String> listItems) {
        this.listItems = listItems;
    }

    private void initRecyclerView() {
        binding.rcvAnswer.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerViewAdapter = new AnswerListAdapter(listItems, quizAnswerListener);
        binding.rcvAnswer.setAdapter(recyclerViewAdapter);
    }

    public void setClickListenerAnswer(){
        quizAnswerListener = new QuizAnswerListener() {
            @Override
            public void QuizAnswerListener(String quizAnswer) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).toNextPage(quizAnswer);
                }
            }
        };
    }
}
