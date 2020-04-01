package com.example.talk.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talk.Model.Private_Chatroom;
import com.example.talk.Model.SessionManagement;
import com.example.talk.Model.User;
import com.example.talk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListRecyclerAdapter extends RecyclerView.Adapter<UserListRecyclerAdapter.MyViewHolder> {
    private List<User> users;
    private Context context;
    private String sessionid;
    private FirebaseFirestore db;
    private SessionManagement sessionManagement;


    public UserListRecyclerAdapter(Context context, List<User> users,String current_userId) {
        this.users = users;
        this.context = context;
        this.sessionid=current_userId;
    }

    @NonNull
    @Override
    public UserListRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_single_item, null);
        return new UserListRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.username.setText(users.get(position).getUsername());
        db = FirebaseFirestore.getInstance();
        sessionManagement=new SessionManagement(context);
        holder.single_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildChatroomPrivate(position);
            }
        });
        holder.single_user.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(context, " "+getItemCount(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void buildChatroomPrivate(final int position) {
        Private_Chatroom chatroom = new Private_Chatroom();
        chatroom.setReceiver(users.get(position).getUsername());
        chatroom.setCreator(sessionManagement.getUserName());
        final DocumentReference mdef=db.collection("Private_ChatRoom").document();
        final String s=mdef.getId();
        chatroom.setChatroom_id(s);
        final Map<String,Object> id=new HashMap<>();
        id.put("list",s);

        mdef.set(chatroom).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                db.collection("Users").document(sessionid)
                        .collection("PrivateChatroom_id").document().set(id);

                db.collection("Users").document(users.get(position).getUser_id())
                        .collection("PrivateChatroom_id").document().set(id);
                Toast.makeText(context, "Added!", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Please, try after some time!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private LinearLayout single_user;
        private CheckBox checkbox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            single_user = itemView.findViewById(R.id.single_user);
            checkbox = itemView.findViewById(R.id.checkbox);

        }
    }

}
