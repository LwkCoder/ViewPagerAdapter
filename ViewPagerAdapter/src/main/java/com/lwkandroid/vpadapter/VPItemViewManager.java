package com.lwkandroid.vpadapter;

import android.util.SparseArray;
import android.view.ViewGroup;


/**
 * Function:ItemView管理器
 */
final class VPItemViewManager<T>
{
    private SparseArray<VPBaseItemView<T>> mAllItemViews = new SparseArray();

    /**
     * 获取子布局数量
     *
     * @return 子布局数量
     */
    int getItemViewCount()
    {
        return mAllItemViews.size();
    }

    /**
     * 添加子布局（自动指定布局对应的ViewType）
     *
     * @param itemView 子布局
     */
    void addItemView(VPBaseItemView<T> itemView)
    {
        int viewType = mAllItemViews.size();
        mAllItemViews.put(viewType, itemView);
    }

    /**
     * 添加子布局（手动指定布局对应的ViewType）
     *
     * @param viewType 布局ViewType
     * @param itemView 子布局
     */
    void addItemView(int viewType, VPBaseItemView<T> itemView)
    {
        if (mAllItemViews.get(viewType) != null)
        {
            throw new IllegalArgumentException(
                    "An ItemView is already registered for the viewType = " + viewType);
        }
        mAllItemViews.put(viewType, itemView);
    }

    /**
     * 移除一种子布局
     *
     * @param itemView 子布局
     */
    void removeItemView(VPBaseItemView<T> itemView)
    {
        if (itemView == null)
        {
            throw new NullPointerException("ItemView object is null");
        }

        int indexToRemove = mAllItemViews.indexOfValue(itemView);
        if (indexToRemove >= 0)
        {
            mAllItemViews.removeAt(indexToRemove);
        }
    }

    /**
     * 移除一种子布局
     *
     * @param itemType 子布局对应的ViewType
     */
    void removeItemView(int itemType)
    {
        int indexToRemove = mAllItemViews.indexOfKey(itemType);

        if (indexToRemove >= 0)
        {
            mAllItemViews.removeAt(indexToRemove);
        }
    }

    /**
     * 创建ViewHolder
     *
     * @param viewType 子布局类型
     * @param parent   父布局
     * @return VPHolder
     */
    VPHolder onCreateViewHolder(int viewType, ViewGroup parent)
    {
        VPBaseItemView<T> itemView = mAllItemViews.get(viewType);
        return itemView != null ? itemView.onCreateViewHolder(parent) : null;
    }

    /**
     * 遍历所有子布局并关联数据
     *
     * @param holder   ViewHolder
     * @param dataItem 子布局
     * @param position 子布局位置
     */
    void bindView(VPHolder holder, T dataItem, int position)
    {
        int itemViewCounts = mAllItemViews.size();
        for (int i = 0; i < itemViewCounts; i++)
        {
            VPBaseItemView<T> itemView = mAllItemViews.valueAt(i);

            if (itemView.isForViewType(dataItem, position))
            {
                itemView.onBindView(holder, dataItem, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemView matched at position=" + position + " in data source");
    }

    /**
     * 获取某一位置下子布局对应的ViewType
     *
     * @param item     子布局
     * @param position 位置
     * @return 子布局ViewType
     */
    int getItemViewType(T item, int position)
    {
        int itemViewCounts = mAllItemViews.size();
        for (int i = 0; i < itemViewCounts; i++)
        {
            VPBaseItemView<T> itemView = mAllItemViews.valueAt(i);
            if (itemView.isForViewType(item, position))
            {
                return mAllItemViews.keyAt(i);
            }
        }
        throw new IllegalArgumentException("No ItemView added that matches position=" + position + " in data source");
    }

    /**
     * 获取某子布局的ViewType
     *
     * @param itemView
     * @return
     */
    int getItemViewType(VPBaseItemView itemView)
    {
        return mAllItemViews.indexOfValue(itemView);
    }
}
