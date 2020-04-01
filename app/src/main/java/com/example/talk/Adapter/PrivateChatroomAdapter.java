package com.example.talk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talk.Model.Private_Chatroom;
import com.example.talk.Model.SessionManagement;
import com.example.talk.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PrivateChatroomAdapter extends RecyclerView.Adapter<PrivateChatroomAdapter.MyViewHolder> {
    private Context context;
    private List<Private_Chatroom> details;
    private SessionManagement sessionManagement;
    private FirebaseFirestore db;

    public PrivateChatroomAdapter(Context context, List<Private_Chatroom> details) {
        this.context=context;
        this.details=details;
    }

    @NonNull
    @Override
    public PrivateChatroomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_single_item_chatroom, null);
        return new PrivateChatroomAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrivateChatroomAdapter.MyViewHolder holder, int position) {
        sessionManagement=new SessionManagement(context);
        db=FirebaseFirestore.getInstance();

        Private_Chatroom particular=new Private_Chatroom();
        particular.setReceiver(details.get(position).getReceiver());
        particular.setCreator(details.get(position).getCreator());
        particular.setChatroom_id(details.get(position).getChatroom_id());

        if(sessionManagement.getUserName()==particular.getReceiver()){
            holder.display_name.setText(particular.getCreator());
        }
        else{
            holder.display_name.setText(particular.getReceiver());
        }
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView display_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            display_name=itemView.findViewById(R.id.display_name);
        }
    }
}
