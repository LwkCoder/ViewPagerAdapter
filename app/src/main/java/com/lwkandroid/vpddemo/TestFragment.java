package com.lwkandroid.vpddemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Description:
 *
 * @author LWK
 * @date 2020/4/13
 */
public class TestFragment extends Fragment
{
    private int mIndex = -1;

    public static TestFragment createInstance(int index)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mIndex = getArguments().getInt("index", -1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View layout = inflater.inflate(R.layout.fragmeng_test, container, false);
        TextView textView = layout.findViewById(R.id.tv_fragment);
        textView.setText("Index:" + mIndex);
        return layout;
    }
}
