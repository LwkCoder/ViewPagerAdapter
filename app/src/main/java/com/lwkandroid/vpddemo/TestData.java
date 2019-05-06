package com.lwkandroid.vpddemo;

/**
 * Description:
 *
 * @author LWK
 * @date 2019/5/6
 */
public class TestData
{
    private String data;
    private int type;

    public TestData(String data, int type)
    {
        this.data = data;
        this.type = type;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }
}
