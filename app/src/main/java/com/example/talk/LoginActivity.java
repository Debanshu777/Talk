package com.example.talk;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.talk.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import static android.text.TextUtils.isEmpty;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity {

    private ImageView bookIconImageView;
    private TextView bookITextView,signup;
    private Button loginBtn;
    private ProgressBar loadingProgressBar;
    private RelativeLayout rootView, afterAnimationView;

    private static final String TAG = "LoginActivity";

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;

    // widgets
    private EditText mEmail, mPassword;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initViews();

        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                bookITextView.setVisibility(GONE);
                loadingProgressBar.setVisibility(GONE);
                rootView.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.colorBackground));
                bookIconImageView.setImageResource(R.drawable.onboard_page1);
                startAnimation();

            }
        }.start();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }

    private void initViews() {
        bookIconImageView = findViewById(R.id.iconImageView);
        bookITextView = findViewById(R.id.appname);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);

        rootView = findViewById(R.id.rootView);



        mEmail = findViewById(R.id.emailEditText);
        mPassword = findViewById(R.id.passwordEditText);
        mProgressBar = findViewById(R.id.progressBar);
        setupFirebaseAuth();
        loginBtn=findViewById(R.id.loginButton);
        signup=findViewById(R.id.signup);

        hideSoftKeyboard();

        afterAnimationView = findViewById(R.id.afterAnimationView);
    }
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(LoginActivity.this, "Authenticated with: " + user.getEmail(), Toast.LENGTH_SHORT).show();

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                            .setTimestampsInSnapshotsEnabled(true)
                            .build();
                    db.setFirestoreSettings(settings);

                    DocumentReference userRef = db.collection(getString(R.string.collection_users))
                            .document(user.getUid());

                    userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "onComplete: successfully set the user client.");
                                User user = task.getResult().toObject(User.class);
                                ((UserClient)(getApplicationContext())).setUser(user);
                            }
                        }
                    });

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }
    private void signIn(){
        //check if the fields are filled out
        if(!isEmpty(mEmail.getText().toString())
                && !isEmpty(mPassword.getText().toString())){
            Log.d(TAG, "onClick: attempting to authenticate.");

            showDialog();

            FirebaseAuth.getInstance().signInWithEmailAndPassword(mEmail.getText().toString(),
                    mPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            hideDialog();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    hideDialog();
                }
            });
        }else{
            Toast.makeText(LoginActivity.this, "You didn't fill in all the fields.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void startAnimation() {
        ViewPropertyAnimator viewPropertyAnimator = bookIconImageView.animate();
        viewPropertyAnimator.x(50f);
        viewPropertyAnimator.y(100f);
        viewPropertyAnimator.setDuration(1500);
        viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                afterAnimationView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
