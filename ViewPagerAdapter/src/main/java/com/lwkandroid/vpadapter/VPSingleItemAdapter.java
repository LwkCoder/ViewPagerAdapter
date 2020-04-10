package com.lwkandroid.vpadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.LayoutRes;

/**
 * Description:单类型ItemView的ViewPager适配器
 *
 * @author LWK
 * @date 2019/5/5
 */
public abstract class VPSingleItemAdapter<T> extends VPMultiItemAdapter<T>
{
    private VPBaseItemView<T> mSingleItemView;

    public VPSingleItemAdapter(Context context, List<T> dataList)
    {
        super(context, dataList);
    }

    @Override
    public VPBaseItemView<T>[] createAllItemViews()
    {
        mSingleItemView = new VPBaseItemView<T>(getContext(), this)
        {
            @Override
            public boolean isForViewType(T item, int position)
            {
                return true;
            }

            @Override
            public VPHolder onCreateViewHolder(ViewGroup parent)
            {
                return VPSingleItemAdapter.this.onCreateViewHolder(parent);
            }

            @Override
            public void onBindView(VPHolder holder, T t, int position)
            {
                VPSingleItemAdapter.this.onBindView(holder, t, position);
            }
        };
        mSingleItemView.setAdapter(this);
        return new VPBaseItemView[]{mSingleItemView};
    }

    /**
     * 通过引用布局快速创建ViewHolder的方法
     *
     * @param context  context环境
     * @param parent   父容器
     * @param layoutId 布局id
     * @return VPHolder
     */
    public VPHolder createHolderFromLayout(Context context, ViewGroup parent, @LayoutRes int layoutId)
    {
        return mSingleItemView.createHolderFromLayout(context, parent, layoutId);
    }

    /**
     * 通过引用View快速创建ViewHolder的方法
     *
     * @param context context环境
     * @param view    被引用的view
     * @return VPHolder
     */
    public VPHolder createHolderFromView(Context context, View view)
    {
        return mSingleItemView.createHolderFromView(context, view);
    }

    /**
     * 子类实现创建ViewHolder的方法
     *
     * @param parent 父容器
     * @return VPHolder
     */
    public abstract VPHolder onCreateViewHolder(ViewGroup parent);

    /**
     * 子类实现绑定视图和数据的方法
     *
     * @param holder   视图Holder
     * @param t        数据
     * @param position 位置
     */
    public abstract void onBindView(VPHolder holder, T t, int position);
}
