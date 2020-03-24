package com.example.talk;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talk.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserListActivity extends AppCompatActivity {

    private RecyclerView add_user_list;
    private FirebaseFirestore db;
    List<User> users =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_list);

        add_user_list=findViewById(R.id.add_user_list);
        db=FirebaseFirestore.getInstance();
        CollectionReference collectionReference=db.collection("Users");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Map<String,Object> userlist=documentSnapshot.getData();
                    String s= (String) userlist.get("username");
                    users.add(new User(documentSnapshot.getId(),s));
                    UserListRecyclerAdapter userListRecyclerAdapter=new UserListRecyclerAdapter(getApplicationContext(),users);
                    add_user_list.setLayoutManager(new LinearLayoutManager(UserListActivity.this,LinearLayoutManager.VERTICAL,false));
                    add_user_list.setAdapter(userListRecyclerAdapter);
                }
            }
        });


    }
}
