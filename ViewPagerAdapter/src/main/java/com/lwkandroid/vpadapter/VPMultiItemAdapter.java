package com.lwkandroid.vpadapter;

import android.content.Context;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Description:ViewPager通用适配器基类
 *
 * @author LWK
 * @date 2019/5/5
 */
public abstract class VPMultiItemAdapter<T> extends PagerAdapter
{
    private Context mContext;
    private List<T> mDataList = new LinkedList<>();
    private VPItemViewManager<T> mItemViewManager = new VPItemViewManager<>();
    private SparseArray<VPHolder> mHolderCacheArray = new SparseArray<>();
    private final SparseArray<View> mAttachedViewsArray;
    private SparseArray<SparseArray<Parcelable>> mDetachedStatesArray;
    //存储点击事件的map
    protected HashMap<Integer, OnChildClickListener<T>> mChildListenerMap;

    public VPMultiItemAdapter(Context context, List<T> dataList)
    {
        this(context, dataList, 3);
    }

    /**
     * 构造方法
     *
     * @param context  context
     * @param dataList 数据集合
     * @param capacity 保存View状态的初始容量
     */
    public VPMultiItemAdapter(Context context, List<T> dataList, int capacity)
    {
        this.mContext = context;
        if (dataList != null)
        {
            this.mDataList.addAll(dataList);
        }
        VPBaseItemView<T>[] itemViews = createAllItemViews();
        if (itemViews != null)
        {
            for (VPBaseItemView<T> itemView : itemViews)
            {
                addItemView(itemView);
            }
        }
        mAttachedViewsArray = new SparseArray<>(capacity);
        mDetachedStatesArray = new SparseArray<>();
    }

    @Override
    public int getCount()
    {
        return mDataList != null ? mDataList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        VPHolder holder = (VPHolder) object;
        return view == holder.getContentView();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position)
    {
        T data = getItemData(position);
        VPHolder holder = mHolderCacheArray.get(position);
        if (holder == null)
        {
            int viewType = mItemViewManager.getItemViewType(data, position);
            holder = mItemViewManager.createViewHolder(viewType, container);
            if (holder == null)
            {
                throw new IllegalArgumentException("No VPHolder matched for ViewType=" + viewType + " in data source");
            } else
            {
                mHolderCacheArray.put(position, holder);
            }
        }

        //恢复状态
        SparseArray<Parcelable> viewState = mDetachedStatesArray.get(position);
        if (viewState != null)
        {
            holder.getContentView().restoreHierarchyState(viewState);
        }

        mItemViewManager.bindView(holder, data, position);
        //回调子View点击监听
        if (mChildListenerMap != null)
        {
            for (Map.Entry<Integer, OnChildClickListener<T>> entry : mChildListenerMap.entrySet())
            {
                final int viewId = entry.getKey();
                final T t = data;
                holder.setClickListener(viewId, new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        OnChildClickListener<T> listener = mChildListenerMap.get(viewId);
                        if (listener != null)
                        {
                            listener.onChildClicked(viewId, v, t, position);
                        }
                    }
                });
            }
        }

        container.addView(holder.getContentView());
        mAttachedViewsArray.put(position, holder.getContentView());
        return holder;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        VPHolder holder = (VPHolder) object;
        if (holder.getContentView() != null)
        {
            putInDetached(position, holder.getContentView());
            container.removeView(holder.getContentView());
            mAttachedViewsArray.remove(position);
        }
    }

    @Override
    public final VPSaveState saveState()
    {
        for (int i = 0, size = mAttachedViewsArray.size(); i < size; i++)
        {
            int position = mAttachedViewsArray.keyAt(i);
            View view = mAttachedViewsArray.valueAt(i);
            putInDetached(position, view);
        }
        return new VPSaveState(mDetachedStatesArray);
    }

    @Override
    public final void restoreState(Parcelable state, ClassLoader loader)
    {
        VPSaveState savedState = (VPSaveState) state;
        mDetachedStatesArray = savedState.mDetachedStatesArray;
    }

    /**
     * 获取某位置上的数据
     *
     * @param position 位置
     */
    public T getItemData(int position)
    {
        return position < getCount() ? mDataList.get(position) : null;
    }

    /**
     * 获取某位置上的Holder
     *
     * @param position 位置
     * @return VPHolder
     */
    public VPHolder getItemHolder(int position)
    {
        return position < getCount() ? mHolderCacheArray.get(position) : null;
    }

    /**
     * 添加ItemView类型
     *
     * @param itemView ItemView
     */
    public void addItemView(VPBaseItemView<T> itemView)
    {
        mItemViewManager.addItemView(itemView);
    }

    /**
     * 添加ItemView类型
     *
     * @param viewType ItemView类型
     * @param itemView ItemView
     */
    public void addItemView(int viewType, VPBaseItemView<T> itemView)
    {
        mItemViewManager.addItemView(viewType, itemView);
    }

    /***
     * 刷新数据的方法
     * @param dataList 数据集合
     */
    public void refreshData(List<T> dataList)
    {
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param data 数据对象
     */
    public void addData(T data)
    {
        mDataList.add(data);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param index 添加的位置
     * @param data  数据对象
     */
    public void addData(int index, T data)
    {
        mDataList.add(index, data);
        notifyDataSetChanged();
    }

    /**
     * 删除数据
     *
     * @param index 删除的位置
     */
    public void removeData(int index)
    {
        if (index >= 0 && index < getCount())
        {
            mDataList.remove(index);
            notifyDataSetChanged();
        }
    }

    /**
     * 删除数据
     *
     * @param data 删除的数据对象
     */
    public void removeData(T data)
    {
        mDataList.remove(data);
        notifyDataSetChanged();
    }

    /**
     * 获取关联的Context
     *
     * @return Context
     */
    public Context getContext()
    {
        return mContext;
    }

    /**
     * 添加子View点击事件
     *
     * @param viewId   view的id
     * @param listener 点击监听
     */
    public void setOnChildClickListener(int viewId, OnChildClickListener<T> listener)
    {
        if (mChildListenerMap == null)
        {
            mChildListenerMap = new HashMap<>();
        }
        mChildListenerMap.put(viewId, listener);
    }

    private void putInDetached(int position, View view)
    {
        SparseArray<Parcelable> viewState = new SparseArray<>();
        view.saveHierarchyState(viewState);
        mDetachedStatesArray.put(position, viewState);
    }

    /**
     * 子类实现，指定关联的ItemView集合
     *
     * @return ItemView集合
     */
    public abstract VPBaseItemView<T>[] createAllItemViews();

    /**
     * 子View点击事件
     */
    public interface OnChildClickListener<T>
    {
        void onChildClicked(int viewId, View view, T t, int layoutPosition);
    }
}
