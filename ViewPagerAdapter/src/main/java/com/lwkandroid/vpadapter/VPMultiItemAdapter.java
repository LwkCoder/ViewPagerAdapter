package com.lwkandroid.vpadapter;

import android.content.Context;
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
    private SparseArray<LinkedList<VPHolder>> mHolderArray = new SparseArray<>();
    private SparseArray<VPHolder> mHolderCacheArray = new SparseArray<>();
    //存储点击事件的map
    protected HashMap<Integer, OnChildClickListener<T>> mChildListenerMap;

    public VPMultiItemAdapter(Context context, List<T> dataList)
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
        int viewType = mItemViewManager.getItemViewType(data, position);
        VPHolder holder = getCachedHolder(viewType);
        if (holder == null)
        {
            holder = mItemViewManager.onCreateViewHolder(viewType, container);
        }
        if (holder == null)
        {
            throw new IllegalArgumentException("No VPHolder matched for ViewType=" + viewType + " in data source");
        }
        container.addView(holder.getContentView());
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
        return holder;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        VPHolder holder = (VPHolder) object;
        if (holder.getContentView() != null)
        {
            container.removeView(holder.getContentView());
        }
        cacheHolder(mItemViewManager.getItemViewType(getItemData(position), position), holder);
    }

    /**
     * 获取某位置上的数据
     */
    public T getItemData(int position)
    {
        return position < getCount() ? mDataList.get(position) : null;
    }

    /**
     * 添加子布局类型
     */
    public void addItemView(VPBaseItemView<T> itemView)
    {
        mItemViewManager.addItemView(itemView);
    }

    /**
     * 添加子布局类型
     */
    public void addItemView(int viewType, VPBaseItemView<T> itemView)
    {
        mItemViewManager.addItemView(viewType, itemView);
    }

    public void refreshData(List<T> dataList)
    {
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void addData(T data)
    {
        mDataList.add(data);
        notifyDataSetChanged();
    }

    public void addData(int index, T data)
    {
        mDataList.add(index, data);
        notifyDataSetChanged();
    }

    public void removeData(int index)
    {
        if (index >= 0 && index < getCount())
        {
            mDataList.remove(index);
            notifyDataSetChanged();
        }
    }

    public void removeData(T data)
    {
        mDataList.remove(data);
        notifyDataSetChanged();
    }

    public Context getContext()
    {
        return mContext;
    }

    private VPHolder getCachedHolder(int viewType)
    {
        LinkedList<VPHolder> cache = mHolderArray.get(viewType);
        if (cache != null && !cache.isEmpty())
        {
            return cache.pop();
        }
        return null;
    }

    //    private VPHolder getCachedHolder(int viewType)
    //    {
    //        return mHolderCacheArray.get(viewType);
    //    }

    //    private void cacheHolder(int viewType, VPHolder holder)
    //    {
    //        mHolderCacheArray.put(viewType, holder);
    //    }

    private void cacheHolder(int viewType, VPHolder holder)
    {
        LinkedList<VPHolder> cacheList = mHolderArray.get(viewType);
        if (cacheList == null)
        {
            cacheList = new LinkedList<VPHolder>();
            mHolderArray.put(viewType, cacheList);
        }
        cacheList.add(holder);
    }

    public abstract VPBaseItemView<T>[] createAllItemViews();

    /**
     * 添加子View点击事件
     */
    public void setOnChildClickListener(int viewId, OnChildClickListener<T> listener)
    {
        if (mChildListenerMap == null)
        {
            mChildListenerMap = new HashMap<>();
        }
        mChildListenerMap.put(viewId, listener);
    }

    /**
     * 子View点击事件
     */
    public interface OnChildClickListener<T>
    {
        void onChildClicked(int viewId, View view, T t, int layoutPosition);
    }
}
