package com.lwkandroid.vpadapter;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

/**
 * Description: 保存状态的对象
 *
 * @author LWK
 * @date 2019/5/6
 */
final class VPSaveState implements Parcelable
{
    final SparseArray<SparseArray<Parcelable>> mDetachedStatesArray;

    VPSaveState(SparseArray<SparseArray<Parcelable>> detachedStates)
    {
        this.mDetachedStatesArray = detachedStates;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        writeNestedSparseArray(dest, mDetachedStatesArray, flags);
    }

    public static final Creator<VPSaveState> CREATOR = new Creator<VPSaveState>()
    {
        @Override
        public VPSaveState createFromParcel(Parcel in)
        {
            SparseArray<SparseArray<Parcelable>> detached =
                    readNestedSparseArray(in, VPSaveState.class.getClassLoader());
            return new VPSaveState(detached);
        }

        @Override
        public VPSaveState[] newArray(int size)
        {
            return new VPSaveState[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    private static SparseArray<SparseArray<Parcelable>> readNestedSparseArray(Parcel in, ClassLoader loader)
    {
        int size = in.readInt();
        if (size == -1)
        {
            return null;
        }
        SparseArray<SparseArray<Parcelable>> map = new SparseArray<>(size);
        while (size != 0)
        {
            int key = in.readInt();
            SparseArray<Parcelable> value = readSparseArray(in, loader);
            map.append(key, value);
            size--;
        }
        return map;
    }

    private static SparseArray<Parcelable> readSparseArray(Parcel in, ClassLoader loader)
    {
        int size = in.readInt();
        if (size == -1)
        {
            return null;
        }
        SparseArray<Parcelable> map = new SparseArray<>(size);
        while (size != 0)
        {
            int key = in.readInt();
            Parcelable value = in.readParcelable(loader);
            map.append(key, value);
            size--;
        }
        return map;
    }

    private static void writeNestedSparseArray(Parcel dest, SparseArray<SparseArray<Parcelable>> map, int flags)
    {
        if (map == null)
        {
            dest.writeInt(-1);
            return;
        }
        int size = map.size();
        dest.writeInt(size);
        int i = 0;
        while (i != size)
        {
            dest.writeInt(map.keyAt(i));
            writeSparseArray(dest, map.valueAt(i), flags);
            i++;
        }
    }

    private static void writeSparseArray(Parcel dest, SparseArray<Parcelable> map, int flags)
    {
        if (map == null)
        {
            dest.writeInt(-1);
            return;
        }
        int size = map.size();
        dest.writeInt(size);
        int i = 0;
        while (i != size)
        {
            dest.writeInt(map.keyAt(i));
            dest.writeParcelable(map.valueAt(i), flags);
            i++;
        }
    }
}