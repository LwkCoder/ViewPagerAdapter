package com.lwkandroid.vpddemo;

import android.content.Context;
import android.view.ViewGroup;

import com.lwkandroid.vpadapter.VPHolder;
import com.lwkandroid.vpadapter.VPSingleItemAdapter;

import java.util.List;

/**
 * Description:
 *
 * @author LWK
 * @date 2019/5/6
 */
public class TestSingleAdapter extends VPSingleItemAdapter<String>
{
    public TestSingleAdapter(Context context, List<String> dataList)
    {
        super(context, dataList);
    }

    @Override
    public VPHolder onCreateViewHolder(ViewGroup parent)
    {
        return createHolderFromLayout(getContext(), parent, R.layout.adapter_item_view01);
    }

    @Override
    public void onBindView(VPHolder holder, String s, int position)
    {
        holder.setTvText(R.id.tv_adapter01, s);
    }
}
