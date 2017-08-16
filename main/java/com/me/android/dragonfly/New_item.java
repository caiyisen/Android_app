package com.me.android.dragonfly;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Yih_Cheng on 2016/12/14.
 */

public class New_item extends AppCompatActivity {
    private TextView mLocationTextView;
    private EditText newText;
    private Button mChangeLocation;
    private Button postnew;

//    private TextView mLocationTextView;

    private SensorUtils mSensorUtils;


    private SensorUtils.OnSensorUpdateListener mOnSensorUpdateListener = new SensorUtils.OnSensorUpdateListener() {
        @Override
        public void onSensorUpdate(int type, float[] values) {
            if(type == SensorUtils.TYPE_LOCATION_CHANGE){
                //调用updateLocation函数更新当前位置
                updateLocation(mSensorUtils.getCurrentLocation());
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
//        MainView.top_title.setText("发布新内容");
        setContentView(R.layout.new_item);
        System.out.println("进入发布界面了");

        mLocationTextView = (TextView) findViewById(R.id.auto_location);
        newText = (EditText) findViewById(R.id.new_text);
        mChangeLocation = (Button)findViewById(R.id.changelocation);
        postnew = (Button)findViewById(R.id.post);
        mSensorUtils = SensorUtils.getInstance();
        mSensorUtils.setOnSensorUpdateListener(mOnSensorUpdateListener);//监听传感器信息的变化

        updateLocation(mSensorUtils.getCurrentLocation());

        postnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainView.newadd = true;
                Intent intent = new Intent(New_item.this , MainView.class);//新建Intent
                intent.putExtra("text",newText.getText().toString());
                intent.putExtra("locate",mLocationTextView.getText().toString());
                System.out.println(newText.getText().toString());
                System.out.println(mLocationTextView.getText().toString());
                startActivity(intent);//启动新建的Intent
            }
        });



}




    @Override
    protected void onResume() {
        super.onResume();

        mSensorUtils.registerSensor();
    }

    @Override
    protected void onPause() {
        mSensorUtils.unregisterSensor();

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void updateLocation(Location location) {
        if (location == null) {
//            mLongitudeTextView.setText(String.format(getString(R.string.longitude), 0f));
//            mLatitudeTextView.setText(String.format(getString(R.string.latitude), 0f));
            System.out.println("位置为空");
        } else {
//            mLongitudeTextView.setText(String.format(getString(R.string.longitude), location.getLongitude()));//经度
//            mLatitudeTextView.setText(String.format(getString(R.string.latitude), location.getLatitude()));//纬度
            System.out.println("位置不为空，即将进行逆地理编码");

            new GeoDecoder().execute(location);
        }
    }

    private class GeoDecoder extends AsyncTask<Location, Integer, String> {

        @Override
        protected String doInBackground(Location... params) {
            Location param = params[0];
            try {
                //发起请求获取定位服务
                String result = sendHttpRequest(String.format(getString(R.string.coor_change),
                        param.getLongitude(), param.getLatitude(), getString(R.string.server_ak)));
                JSONObject jObject = new JSONObject(result);
                JSONArray jsonArray = jObject.getJSONArray("result");
                JSONObject locObject = jsonArray.getJSONObject(0);
                //获取当前位置的坐标
                double x = locObject.getDouble("x");
                double y = locObject.getDouble("y");
                //通过逆编码获取地理位置信息
                String decode = sendHttpRequest(String.format(getString(R.string.geo_decode),
                        y, x, getString(R.string.server_ak)));
                JSONObject geoObject = new JSONObject(decode);
                return geoObject.getString("formatted_address");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return "Error";
        }

        @Override
        protected void onPostExecute(String s) {
            mLocationTextView.setText(s);
        }
        //发起请求获取定位服务
        private String sendHttpRequest(String request) throws IOException {
            URL url = new URL(request);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String response = "";
            String readLine;
            while ((readLine = br.readLine()) != null) {
                response = response + readLine;
            }
            is.close();
            br.close();
            connection.disconnect();
            return response;
        }
    }






}
