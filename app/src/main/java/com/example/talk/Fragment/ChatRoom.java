package com.example.talk.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talk.Adapter.PrivateChatroomAdapter;
import com.example.talk.Model.Private_Chatroom;
import com.example.talk.Model.SessionManagement;
import com.example.talk.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatRoom extends Fragment {

    private SessionManagement sessionManagement;
    private FirebaseFirestore db;
    private List<Private_Chatroom> details=new ArrayList<>();
    private RecyclerView private_chatroom_list;
    private Context context;

    public ChatRoom(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup)inflater.inflate(R.layout.chatroomui,container,false);

        db=FirebaseFirestore.getInstance();
        sessionManagement=new SessionManagement(context);
        private_chatroom_list=rootView.findViewById(R.id.private_chatrooms);

        final CollectionReference collectionReferencePrivate=db.collection("Private_ChatRoom");

       Toast.makeText(context, ""+sessionManagement.getUserDocumentId(), Toast.LENGTH_SHORT).show();
        db.collection("Users").document(sessionManagement.getUserDocumentId()).collection("PrivateChatroom_id")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Map<String,Object> userprivatechatroom=documentSnapshot.getData();
                    String privatechatroomid= (String) userprivatechatroom.get("list");
                    assert privatechatroomid != null;
                    collectionReferencePrivate.document(privatechatroomid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Map<String,Object> chatdetails=documentSnapshot.getData();
                            String receiver= (String) chatdetails.get("receiver");
                            String creator=(String)chatdetails.get("creator");
                            String id=(String)chatdetails.get("chatroom_id");
                            details.add(new Private_Chatroom(creator,receiver,id));
                            PrivateChatroomAdapter privateListRecyclerAdapter=new PrivateChatroomAdapter(getContext(),details);
                            private_chatroom_list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                            private_chatroom_list.setAdapter(privateListRecyclerAdapter);

                        }
                    });
                }
            }
        });

        return rootView;
    }
}
