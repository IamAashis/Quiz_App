package com.android.quizapp.feature.shared.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.List;

/**
 * Created by Aashis on 30,March,2024
 */
public class QuestionListAdapter extends FragmentStateAdapter {
    private List<Fragment> fragmentList;

    public QuestionListAdapter(List<Fragment> fragmentList, @NonNull FragmentActivity activity) {
        super(activity);
        this.fragmentList = fragmentList;
    }

    @Override
    public int getItemCount() {
        return fragmentList != null ? fragmentList.size() : 0;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (fragmentList != null && position >= 0 && position < fragmentList.size()) {
            return fragmentList.get(position);
        } else {
            return new Fragment();
        }
    }
}
