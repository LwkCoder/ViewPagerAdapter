package com.lwkandroid.vpddemo;

import android.content.Context;

import com.lwkandroid.vpadapter.VPBaseItemView;
import com.lwkandroid.vpadapter.VPMultiItemAdapter;

import java.util.List;

/**
 * Description:
 *
 * @author LWK
 * @date 2019/5/6
 */
public class TestMultiAdapter extends VPMultiItemAdapter<TestData>
{
    public TestMultiAdapter(Context context, List<TestData> dataList)
    {
        super(context, dataList);
    }

    @Override
    public VPBaseItemView<TestData>[] createAllItemViews()
    {
        TestItemView01 itemView01 = new TestItemView01(getContext(), this);
        TestItemView02 itemView02 = new TestItemView02(getContext(), this);
        return new VPBaseItemView[]{itemView01, itemView02};
    }
}
