package com.me.android.dragonfly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

public class Activity_login extends AppCompatActivity {

//    EditText mphonenumber;
    EditText mpassword;
    EditText musername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button login = (Button)findViewById(R.id.bt_login);
        Button register = (Button)findViewById(R.id.bt_register);
//        mphonenumber = (EditText)findViewById(R.id.phonenumber);
        mpassword = (EditText)findViewById(R.id.password);
        musername =(EditText)findViewById(R.id.username);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_login.this,Activity_register.class);
                startActivity(intent);
            }
        });


    }
    private void attemptLogin(){
        final String username = musername.getText().toString();
        final String password = mpassword.getText().toString();

        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            Toast.makeText(Activity_login.this,"用户名或密码输出不能为空",Toast.LENGTH_SHORT).show();
        }else{
            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if(e == null){
                        Intent intent = new Intent(Activity_login.this,MainView.class);
                        startActivity(intent);
//                        Toast.makeText(Activity_login.this,"登录成功",Toast.LENGTH_SHORT).show();
//                        Activity_login.this.finish();
//                        startActivity(new Intent(???));
                    }else{
                        Toast.makeText(Activity_login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
