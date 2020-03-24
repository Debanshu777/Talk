package com.example.talk;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talk.Model.User;

import java.util.List;

public class UserListRecyclerAdapter extends RecyclerView.Adapter<UserListRecyclerAdapter.MyViewHolder> {
    List<User> users;
    Context context;
    public UserListRecyclerAdapter(Context applicationContext, List<User> users) {
        this.users=users;
        this.context=applicationContext;
    }

    @NonNull
    @Override
    public UserListRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_single_item,null);
        return new UserListRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.username.setText(users.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);

        }
    }
}
