package com.example.talk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talk.ChatActivity;
import com.example.talk.Model.Public_Chatroom;
import com.example.talk.R;

import java.util.List;

public class PublicChatroomAdapter extends RecyclerView.Adapter<PublicChatroomAdapter.MyViewHolder>{
    private Context context;
    private List<Public_Chatroom> details;
    public PublicChatroomAdapter(Context context,List<Public_Chatroom> details) {
        this.context=context;
        this.details=details;
    }

    @NonNull
    @Override
    public PublicChatroomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_single_item_chatroom, null);
        return new PublicChatroomAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicChatroomAdapter.MyViewHolder holder, int position) {
        Public_Chatroom classroom=new Public_Chatroom();
        classroom.setTitle(details.get(position).getTitle());
        holder.title.setText(classroom.getTitle());
        holder.chatrromrecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ChatActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private LinearLayout chatrromrecycler;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.display_name);
            chatrromrecycler=itemView.findViewById(R.id.chatroomrecycler);
        }
    }
}
