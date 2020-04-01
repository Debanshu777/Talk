package com.example.talk;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talk.Adapter.UserListRecyclerAdapter;
import com.example.talk.Model.Public_Chatroom;
import com.example.talk.Model.SessionManagement;
import com.example.talk.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserListActivity extends AppCompatActivity {

    private RecyclerView add_user_list;
    private FirebaseFirestore db;
    private ImageView grp_btn,bck_btn;
    private CheckBox chechbox;
    private Context context;
    private String sessionId;
    private ProgressBar mProgressBar;
    List<User> users =new ArrayList<>();
    private SessionManagement sessionManagement;
    private UserListRecyclerAdapter userListRecyclerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_list);



        add_user_list=findViewById(R.id.add_user_list);
        grp_btn=findViewById(R.id.group_button);
        bck_btn=findViewById(R.id.back_button);
        chechbox=findViewById(R.id.checkbox);
        mProgressBar=findViewById(R.id.progressBar);


        sessionManagement=new SessionManagement(this);
        sessionId=getIntent().getStringExtra("Sessionid");

        db=FirebaseFirestore.getInstance();
        CollectionReference collectionReference=db.collection("Users");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Map<String,Object> userlist=documentSnapshot.getData();
                    String s= (String) userlist.get("username");
                    users.add(new User(documentSnapshot.getId(),s));
                    userListRecyclerAdapter=new UserListRecyclerAdapter(UserListActivity.this,users,sessionId);
                    add_user_list.setLayoutManager(new LinearLayoutManager(UserListActivity.this,LinearLayoutManager.VERTICAL,false));
                    add_user_list.setAdapter(userListRecyclerAdapter);
                }
                userListRecyclerAdapter.notifyDataSetChanged();
            }
        });
        grp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newChatroomDialog();
            }
        });


    }
    private void newChatroomDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter a Public Chatroom name");

        final EditText input = new EditText(this);
        input.setPadding(10,5,10,5);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!input.getText().toString().equals("")){
                    buildNewChatroom(input.getText().toString());
                }
                else {
                    Toast.makeText(UserListActivity.this, "Enter a chatroom name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    private void buildNewChatroom(String chatroomName){

        final Public_Chatroom chatroom = new Public_Chatroom();
        chatroom.setTitle(chatroomName);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        DocumentReference newChatroomRef = db
                .collection("Public_ChatRoom")
                .document();

        chatroom.setChatroom_id(newChatroomRef.getId());

        newChatroomRef.set(chatroom).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideDialog();

                if(task.isSuccessful()){
                    navChatroomActivity(chatroom);
                }else{
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void navChatroomActivity(Public_Chatroom chatroom){
        Intent intent = new Intent(UserListActivity.this, MainActivity.class);
        intent.putExtra(getString(R.string.intent_chatroom), chatroom);
        startActivity(intent);
    }
    private void hideDialog(){
        mProgressBar.setVisibility(View.GONE);
    }
}
