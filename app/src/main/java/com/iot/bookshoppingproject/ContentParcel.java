package com.iot.bookshoppingproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CHJ on 2017-05-12.
 */

public class ContentParcel implements Parcelable {

    String userName;
    String chatlog;

    public String getChatlog() {
        return chatlog;
    }

    public void setChatlog(String chatlog) {
        this.chatlog = chatlog;
    }

    public ContentParcel(String userName, String log){
        this.userName = userName;
        this.chatlog = log;

    }

    public ContentParcel(Parcel parcel){
        this.userName = parcel.readString();
        this.chatlog = parcel.readString();

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
        dest.writeString(chatlog);

    }

    public String getTitle() {
        return userName;
    }

    public void setTitle(String title) {
        this.userName = title;
    }

}
