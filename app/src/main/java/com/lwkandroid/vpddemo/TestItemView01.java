package com.lwkandroid.vpddemo;

import android.content.Context;
import android.view.ViewGroup;

import com.lwkandroid.vpadapter.VPBaseItemView;
import com.lwkandroid.vpadapter.VPHolder;
import com.lwkandroid.vpadapter.VPMultiItemAdapter;

/**
 * Description:
 *
 * @author LWK
 * @date 2019/5/6
 */
public class TestItemView01 extends VPBaseItemView<TestData>
{
    public TestItemView01(Context context, VPMultiItemAdapter<TestData> mAdapter)
    {
        super(context, mAdapter);
    }

    @Override
    public boolean isForViewType(TestData item, int position)
    {
        return item.getType() == 0;
    }

    @Override
    public VPHolder onCreateViewHolder(ViewGroup parent)
    {
        return createHolderFromLayout(getAdapter().getContext(), parent, R.layout.adapter_item_view01);
    }

    @Override
    public void onBindView(VPHolder holder, TestData testData, int position)
    {
        holder.setTvText(R.id.tv_adapter01, testData.getData());
    }
}
