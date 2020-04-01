package com.example.talk.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Public_Chatroom implements Parcelable {

    private String title;
    private String chatroom_id;


    public Public_Chatroom(String title, String chatroom_id) {
        this.title = title;
        this.chatroom_id = chatroom_id;
    }

    public Public_Chatroom() {

    }


    protected Public_Chatroom(Parcel in) {
        title = in.readString();
        chatroom_id = in.readString();
    }

    public static final Creator<Public_Chatroom> CREATOR = new Creator<Public_Chatroom>() {
        @Override
        public Public_Chatroom createFromParcel(Parcel in) {
            return new Public_Chatroom(in);
        }

        @Override
        public Public_Chatroom[] newArray(int size) {
            return new Public_Chatroom[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChatroom_id() {
        return chatroom_id;
    }

    public void setChatroom_id(String chatroom_id) {
        this.chatroom_id = chatroom_id;
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "title='" + title + '\'' +
                ", chatroom_id='" + chatroom_id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(chatroom_id);
    }
}
