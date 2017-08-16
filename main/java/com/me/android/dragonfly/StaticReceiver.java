package com.me.android.dragonfly;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by admin on 2016/10/18.
 */
public class StaticReceiver extends BroadcastReceiver{
    final String STATICACTION = "com.me.android.dragonfly.staticreceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("准备接收数据");
        if (intent.getAction().equals(STATICACTION)){

            Resources res = context.getResources();
            Bitmap icon = BitmapFactory.decodeResource(res,R.drawable.icon);
            //获取状态通知栏管理
            NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            //实例化通知栏构造器NotificationCompat.Builder
            Notification.Builder builder = new Notification.Builder(context);
            System.out.println("准备显示广播");
            //对builder进行配置
            builder.setContentTitle("通知")
                    .setContentText("您的订单已被接收！")
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setLargeIcon(icon)
                    .setSmallIcon(R.drawable.icon)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true);
            //绑定intent，点击图标时能够进入某activity
            Intent intent1 = new Intent(context,MainView.class);
            PendingIntent mPendingIntent = PendingIntent.getActivity(context,0,intent1,0);
            builder.setContentIntent(mPendingIntent);
            //绑定notification 发送请求
            Notification notify = builder.build();
            manager.notify(0,notify);


        }

    }
}
