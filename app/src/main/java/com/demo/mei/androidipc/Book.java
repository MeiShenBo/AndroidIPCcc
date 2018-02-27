package com.demo.mei.androidipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mei on 2018/2/25.
 */

public class Book implements Parcelable{
    private String name;
    private int pirce;

    public Book() {
    }

    public Book(String name, int pirce) {
        this.name = name;
        this.pirce = pirce;
    }

    protected Book(Parcel in) {
        name = in.readString();
        pirce = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPirce() {
        return pirce;
    }

    public void setPirce(int pirce) {
        this.pirce = pirce;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(pirce);
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", pirce=" + pirce +
                '}';
    }
}
