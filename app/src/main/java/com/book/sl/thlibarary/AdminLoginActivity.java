package com.book.sl.thlibarary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import DataBase.User;
import LocalDataSave.LocalDataManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminLoginActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    @BindView(R.id.username_field)
    EditText username;

    @BindView(R.id.password_field)
    EditText password;
    private List<User> users;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        ButterKnife.bind(this);
        users = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("user");

    }

    private void adminLogin() {
        String userName = username.getText().toString().trim();
        String pw = password.getText().toString().trim();
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(pw)) {

            for (User user : users) {
                if (user.isAdministrator() == true && user.getUserName().equals(userName) && user.getPassWord().equals(pw)) {
                    Toast.makeText(this, "Admin Login success", Toast.LENGTH_LONG).show();
                    LocalDataManager localDataManager = new LocalDataManager();
                    localDataManager.saveUserObject(user,this);
                    Intent intent = new Intent(this, BookAddActivity.class);
                    startActivity(intent);
                }
            }
        } else {
            Toast.makeText(this, "please fill your details", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.login_button)
    public void login(View view) {
        adminLogin();
    }

    @OnClick(R.id.cancel_button)
    public void cancel(View view) {
        finish();
    }
}