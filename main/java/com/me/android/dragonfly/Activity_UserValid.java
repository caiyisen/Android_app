package com.me.android.dragonfly;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Activity_UserValid extends AppCompatActivity {

    EditText mname;
    EditText mschoolID;
    String filepath = "/storage/emulated/0/Android/data/com.me.android.dragonfly/files/pic.jpg";
    Button add;
    Button compile;
    private Bitmap myBitmap;
    private byte[] photo1; //本地相册获取的图片
    private byte[] photo2; //相机拍照获取的图片
    final int CAMERA_RESULT_CODE=101;
    final int CAMERA_REQUEST_CODE=1;
    final int GALLERY_REQUEST_CODE = 2;
    AVObject Info = new AVObject("Info");
    int choice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__user_valid);

        mname = (EditText)findViewById(R.id.name);
        mschoolID = (EditText)findViewById(R.id.schoolid);

        compile = (Button)findViewById(R.id.compile);
        add = (Button)findViewById(R.id.add_idcard);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_UserValid.this);
                builder.setTitle("选择照片");
                builder.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent_camera = new Intent(Activity_UserValid.this,Camera.class);
                        startActivityForResult(intent_camera,CAMERA_REQUEST_CODE);
                    }
                });
                builder.setNegativeButton("本地相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent_gallery = new Intent(Intent.ACTION_PICK);
                        intent_gallery.setType("image/*");
                        startActivityForResult(intent_gallery,GALLERY_REQUEST_CODE);
                    }
                });
                builder.create().show();

            }
        });
        compile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptvalid();
                Intent intent_compile = new Intent(Activity_UserValid.this,MainView.class);
                startActivity(intent_compile);
            }
        });

    }

    public void setphoto() throws Exception {
        File file = new File(filepath);
        InputStream in = new FileInputStream(file);
        photo2 = readStream(in);
        if(file.exists()){
//            Toast.makeText(Activity_UserValid.this,"找到图片",Toast.LENGTH_SHORT).show();
            Bitmap bm = BitmapFactory.decodeFile(filepath);
            add.setBackgroundDrawable(new BitmapDrawable(bm));
            add.setText(" ");
        }else{
           Toast.makeText(Activity_UserValid.this,"找不到图片",Toast.LENGTH_SHORT).show();;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE){
            if(resultCode == CAMERA_RESULT_CODE){
                try {
                    setphoto();
                    choice = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }else if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                Uri uri = data.getData();
                try {
                    photo1 = readStream(getContentResolver().openInputStream(Uri.parse(uri.toString())));
                    myBitmap  = getPicFromBytes(photo1,null);
                    add.setBackgroundDrawable(new BitmapDrawable(myBitmap));
                    add.setText(" ");
                    choice = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }


//                Toast.makeText(Activity_UserValid.this,"已设置图片！！！",Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public static byte[] readStream(InputStream in) throws Exception{
        byte[] buffer  =new byte[1024];
        int len  =-1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        while((len=in.read(buffer))!=-1){
            outStream.write(buffer, 0, len);
        }
        byte[] data  =outStream.toByteArray();
        outStream.close();
        in.close();
        return data;
    }
    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    public void attemptvalid(){
        String name = mname.getText().toString();
        String schoolID = mschoolID.getText().toString();
        Info.put("school_ID",schoolID);
        Info.put("name",name);
        if(choice == 0){
            Info.put("photo",new AVFile(name,photo2));
        }else if(choice == 1){
            Info.put("photo",new AVFile(name,photo1));
        }

        Info.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d("saved","success!");
                }else{
                    Toast.makeText(Activity_UserValid.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
