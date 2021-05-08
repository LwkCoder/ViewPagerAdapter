# ViewPagerAdapter
ViewPager通用适配器
</br>

该适配器简化了使用ViewPager时创建适配器的工作，支持子Item布局不一样的情况，且内部实现了Holder复用和状态缓存恢复功能。

### 引用方式
最新版本请查看[这里](https://github.com/Vanish136/ViewPagerAdapter/releases)，替换掉下方的`last-version`
```
dependencies {
   #迁移到MavenCentral后引用方式如下：
   implementation 'com.lwkandroid.library:ViewPagerAdapter:last-version'
}
```
</br>

### VPSingleItemAdapter：子Item样式一致的适配器

继承VPSingleItemAdapter并在泛型指定数据类型即可
```
public class TestSingleAdapter extends VPSingleItemAdapter<String>
{
    public TestSingleAdapter(Context context, List<String> dataList)
    {
        super(context, dataList);
    }

    @Override
    public VPHolder onCreateViewHolder(ViewGroup parent)
    {
       //创建ViewHolder的地方
       //可以选择引用布局来创建：createHolderFromLayout(Context context，ViewGroup parent，int layoutId)
       //也可选择引用View来创建：createHolderFromView(Context context, View view)
       
        return createHolderFromLayout(getContext(), parent, R.layout.adapter_item_view01);
    }

    @Override
    public void onBindView(VPHolder holder, String s, int position)
    {
        //绑定视图和数据的方法
        holder.setTvText(R.id.tv_adapter01, s);
    }
}
```
</br>

### VPMultiItemAdapter：子Item样式不一致的适配器

需要分别创建不同样式的ItemView，然后关联到适配器中
```
//继承VPBaseItemView并在泛型指定数据类型，创建第一种样式的ItemView
public class TestItemView01 extends VPBaseItemView<TestData>
{
    public TestItemView01(Context context, VPMultiItemAdapter<TestData> mAdapter)
    {
        super(context, mAdapter);
    }

    @Override
    public boolean isForViewType(TestData item, int position)
    {
        //这里写引用该样式的判断条件
        return item.getType() == 0;
    }

    @Override
    public VPHolder onCreateViewHolder(ViewGroup parent)
    {
       //创建ViewHolder的地方
       //可以选择引用布局来创建：createHolderFromLayout(Context context，ViewGroup parent，int layoutId)
       //也可选择引用View来创建：createHolderFromView(Context context, View view)
        return createHolderFromLayout(getAdapter().getContext(), parent, R.layout.adapter_item_view01);
    }

    @Override
    public void onBindView(VPHolder holder, TestData testData, int position)
    {
        //绑定视图和数据的方法
        holder.setTvText(R.id.tv_adapter01, testData.getData());
    }
}

//继承VPBaseItemView并在泛型指定数据类型，创建第一种样式的ItemView
public class TestItemView02 extends VPBaseItemView<TestData>
{
    public TestItemView02(Context context, VPMultiItemAdapter<TestData> mAdapter)
    {
        super(context, mAdapter);
    }

    @Override
    public boolean isForViewType(TestData item, int position)
    {
        //这里写引用该样式的判断条件
        return item.getType() == 1;
    }

    @Override
    public VPHolder onCreateViewHolder(ViewGroup parent)
    {
       //创建ViewHolder的地方
       //可以选择引用布局来创建：createHolderFromLayout(Context context，ViewGroup parent，int layoutId)
       //也可选择引用View来创建：createHolderFromView(Context context, View view)
        return createHolderFromLayout(getAdapter().getContext(), parent, R.layout.adapter_item_view01);
    }

    @Override
    public void onBindView(VPHolder holder, TestData testData, int position)
    {
        //绑定视图和数据的方法
        holder.setTvText(R.id.tv_adapter02, testData.getData());
    }
}

//继承VPMultiItemAdapter并在泛型指定数据类型
public class TestMultiAdapter extends VPMultiItemAdapter<TestData>
{
    public TestMultiAdapter(Context context, List<TestData> dataList)
    {
        super(context, dataList);
    }

    @Override
    public VPBaseItemView<TestData>[] createAllItemViews()
    {
        //将两种样式的item关联进来即可
        TestItemView01 itemView01 = new TestItemView01(getContext(), this);
        TestItemView02 itemView02 = new TestItemView02(getContext(), this);
        return new VPBaseItemView[]{itemView01, itemView02};
    }
}
```
</br>

### VPFragmentPagerAdapter/VPFragmentStatePagerAdapter
这两个适配器其实是对FragmentPagerAdapter/FragmentStatePagerAdapter的优化，是从鴻洋的一篇推文中学习到的，相关连接请[点我](https://mp.weixin.qq.com/s/MOWdbI5IREjQP1Px-WJY1Q)，以FragmentStatePagerAdapter示例如下：
```
public class TestStatePagerAdapter extends VPFragmentStatePagerAdapter
{
    public TestStatePagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment createFragment(int position)
    {
        //这里需要根据位置来初始化Fragment
        return TestFragment.createInstance(position);
    }

    @Override
    public int getCount()
    {
        //这里需要指定Pager的数量
        return 500;
    }
}
```

### Proguard混淆
无须特别混淆规则
</br>

### 感谢
 状态缓存恢复参考的该项目：[ViewStatePagerAdapter](https://github.com/NightlyNexus/ViewStatePagerAdapter)

