package com.android.quizapp.feature.shared.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.android.quizapp.R;
import com.android.quizapp.feature.shared.listener.QuizAnswerListener;
import java.util.List;

/**
 * Created by Aashis on 30,March,2024
 */
public class AnswerListAdapter extends RecyclerView.Adapter<AnswerListAdapter.MyViewHolder> {

    private List<String> listItems;
    QuizAnswerListener quizAnswerListener;

    public AnswerListAdapter(List<String> listItems, QuizAnswerListener quizAnswerListener){
        this.listItems = listItems;
        this.quizAnswerListener = quizAnswerListener;
    }

    public void setListDataItems(List<String> listItems) {
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public AnswerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_quiz_answer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerListAdapter.MyViewHolder holder, int position) {

        holder.txvQuestionTitle.setText(listItems.get(position));

        holder.cltSetsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizAnswerListener.QuizAnswerListener(holder.txvQuestionTitle.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listItems == null)
            return 0;
        else
            return listItems.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView txvQuestionTitle;
        ConstraintLayout cltSetsList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txvQuestionTitle = itemView.findViewById(R.id.txvQuestionTitle);
            cltSetsList = itemView.findViewById(R.id.cltSetsList);
        }
    }
}
