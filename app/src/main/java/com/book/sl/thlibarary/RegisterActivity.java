package com.book.sl.thlibarary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import DataBase.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    @BindView(R.id.username_field)
    EditText username;
    @BindView(R.id.email_field)
    EditText emailOrPhoneNumber;
    @BindView(R.id.password_field)
    EditText password;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_member_account);
        ButterKnife.bind(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("user");

    }

    private void addUser() {
        String userName = username.getText().toString().trim();
        String emailOrPhone = emailOrPhoneNumber.getText().toString().trim();
        String pw = password.getText().toString().trim();

        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(emailOrPhone) && !TextUtils.isEmpty(pw)) {
            String id = databaseReference.push().getKey();
            User user = new User(id, userName, false, emailOrPhone,pw);
            databaseReference.child(id).setValue(user);
            Toast.makeText(this, "User Register", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "please fill your details", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.create_account_button)
    public void createAccount(View view) {
        addUser();
        finish();
    }

    @OnClick(R.id.cancel_button)
    public void cancel(View view) {
        finish();
    }
}
