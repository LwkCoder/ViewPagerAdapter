package com.lwkandroid.vpadapter;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Description:FragmentStatePagerAdapter的封装基类
 *
 * @author LWK
 * @date 2020/4/13
 */
public abstract class VPFragmentStatePagerAdapter extends FragmentStatePagerAdapter
{
    private SparseArray<Fragment> mAllFragmentsArray = new SparseArray<>();

    public VPFragmentStatePagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return createFragment(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mAllFragmentsArray.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        mAllFragmentsArray.remove(position);
        super.destroyItem(container, position, object);
    }

    public SparseArray<Fragment> getAllRegisteredFragments()
    {
        return mAllFragmentsArray;
    }

    /**
     * 子类实现，根据位置创建Fragment的方法
     *
     * @param position 位置
     * @return 初始化的Fragment
     */
    public abstract Fragment createFragment(int position);
}
