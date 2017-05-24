package com.iot.bookshoppingproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CHJ on 2017-05-12.
 */

public class ContentParcel implements Parcelable {

    String userName;
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ContentParcel(String userName, String password){
        this.userName = userName;
        this.password = password;

    }

    public ContentParcel(Parcel parcel){
        this.userName = parcel.readString();
        this.password = parcel.readString();

    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new ContentParcel(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(password);

    }

    public String getTitle() {
        return userName;
    }

    public void setTitle(String title) {
        this.userName = title;
    }

}
