package com.lwkandroid.vpddemo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Description:
 *
 * @author 20180004
 * @date 2020/4/10
 */
public class TestAdapter extends FragmentStatePagerAdapter
{
    public TestAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return null;
    }

    @Override
    public int getCount()
    {
        return 0;
    }
}
