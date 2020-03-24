package com.example.talk;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talk.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListRecyclerAdapter extends RecyclerView.Adapter<UserListRecyclerAdapter.MyViewHolder> {
    List<User> users;
    Context context;
    private FirebaseFirestore db;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final String[] id = new String[1];
        holder.username.setText(users.get(position).getUsername());
        db=FirebaseFirestore.getInstance();
        holder.single_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CollectionReference chat_id= db.collection("ChatRoom");
                Map<String,Object> wal=new HashMap<>();
                wal.put("Message_id", "");
                wal.put("message","");
                wal.put("users","");
                chat_id.add(wal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                         id[0] =documentReference.getId();
                        Toast.makeText(context, "chat added to chatroom", Toast.LENGTH_SHORT).show();
                    }
                });

                db.collection("Users").document(FirebaseAuth.getInstance().getUid())
                        .update("chat_id", id[0]).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "chat added", Toast.LENGTH_SHORT).show();
                        db.collection("Users").document(users.get(position).getUser_id())
                                .update("chat_id", id[0]).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "chat added", Toast.LENGTH_SHORT).show();
                                // context.startActivity(context,MainActivity.class);
                            }
                        });
                    }
                });



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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);
            single_user=itemView.findViewById(R.id.single_user);

        }
    }
}
