package com.me.android.dragonfly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

public class Activity_register extends AppCompatActivity {

    Button confirm;
    EditText mphonenumber;
    EditText mpassword;
    EditText musername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mphonenumber = (EditText)findViewById(R.id.phonenumber);
        mpassword = (EditText)findViewById(R.id.password);
        musername = (EditText)findViewById(R.id.username);
        confirm = (Button)findViewById(R.id.bt_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

    }
    private void attemptRegister(){

        String phonenumber = mphonenumber.getText().toString();
        String password = mpassword.getText().toString();
        String username = musername.getText().toString();
        if(TextUtils.isEmpty(phonenumber)||TextUtils.isEmpty(password)||TextUtils.isEmpty(username)){
            Toast.makeText(Activity_register.this,"输入不能为空",Toast.LENGTH_SHORT).show();
        } else if(!isPasswordValid(password)){
            Toast.makeText(Activity_register.this,"密码要大于4位",Toast.LENGTH_SHORT).show();
        }else if(!isPhonenumberValid(phonenumber)){
            Toast.makeText(Activity_register.this,"手机号码必须是11位",Toast.LENGTH_SHORT).show();
        }else{
            AVUser user = new AVUser();// 新建 AVUser 对象实例
            user.setUsername(username);
            user.setMobilePhoneNumber(phonenumber);
            user.setPassword(password);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if(e == null){
                        // 注册成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
                        startActivity(new Intent(Activity_register.this, Activity_UserValid.class));
                        Activity_register.this.finish();
                    }else{
                        Toast.makeText(Activity_register.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    private boolean isPhonenumberValid(String phonenumber) {
        //TODO: Replace this with your own logic
        return phonenumber.length() == 11;
    }
}
