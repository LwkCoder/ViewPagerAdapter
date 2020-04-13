package com.lwkandroid.vpddemo;

import com.lwkandroid.vpadapter.VPFragmentPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Description:
 *
 * @author LWK
 * @date 2020/4/13
 */
public class TestPagerAdapter extends VPFragmentPagerAdapter
{
    public TestPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment createFragment(int position)
    {
        return TestFragment.createInstance(position);
    }

    @Override
    public int getCount()
    {
        return 5;
    }
}
