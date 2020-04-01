package com.example.talk.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Private_Chatroom implements Parcelable {

    private String creator;
    private String receiver;
    private String chatroom_id;


    public Private_Chatroom(String creator,String receiver, String chatroom_id) {
        this.creator = creator;
        this.receiver=receiver;
        this.chatroom_id = chatroom_id;
    }

    public Private_Chatroom() {

    }


    protected Private_Chatroom(Parcel in) {
        creator = in.readString();
        receiver=in.readString();
        chatroom_id = in.readString();
    }

    public static final Creator<Private_Chatroom> CREATOR = new Creator<Private_Chatroom>() {
        @Override
        public Private_Chatroom createFromParcel(Parcel in) {
            return new Private_Chatroom(in);
        }

        @Override
        public Private_Chatroom[] newArray(int size) {
            return new Private_Chatroom[size];
        }
    };

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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
                "creator='" + creator + '\'' +
                ", receiver='" + receiver + '\'' +
                ", chatroom_id='" + chatroom_id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(creator);
        dest.writeString(receiver);
        dest.writeString(chatroom_id);
    }
}
