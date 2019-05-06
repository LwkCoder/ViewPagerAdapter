package com.lwkandroid.vpadapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

/**
 * Description:ViewPager适配器的ItemView
 *
 * @author LWK
 * @date 2019/5/5
 */
public abstract class VPBaseItemView<T>
{
    private VPMultiItemAdapter<T> mAdapter;
    private Context mContext;

    public VPBaseItemView(Context context, VPMultiItemAdapter<T> mAdapter)
    {
        this.mContext = context;
        this.mAdapter = mAdapter;
    }

    /**
     * 绑定适配器
     */
    public void setAdapter(VPMultiItemAdapter<T> adapter)
    {
        this.mAdapter = adapter;
    }

    /**
     * 获取适配器
     */
    protected VPMultiItemAdapter<T> getAdapter()
    {
        return mAdapter;
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
        return VPHolder.get(context, parent, layoutId);
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
        return VPHolder.get(context, view);
    }

    private Context getContext()
    {
        return mContext;
    }

    /**
     * 子类实现此方法决定引用该子布局的时机
     *
     * @param item     该position对应的数据
     * @param position position
     * @return 是否属于子布局
     */
    public abstract boolean isForViewType(T item, int position);

    /**
     * 子类实现，初始化ViewHolder的地方
     * 推荐使用VPHolder的静态方法创建
     *
     * @param parent 父布局
     * @return VPHolder
     */
    public abstract VPHolder onCreateViewHolder(ViewGroup parent);

    /**
     * 绑定UI和数据的方法
     *
     * @param holder   通用ViewHolder
     * @param t        数据
     * @param position 位置
     */
    public abstract void onBindView(VPHolder holder, T t, int position);
}
