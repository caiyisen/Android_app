package com.me.android.dragonfly;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Yih_Cheng on 2016/12/14.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String , Object>> listItems ;//信息集合
    private LayoutInflater listContainer;//视图容器

    final String STATICACTION = "com.me.android.dragonfly.staticreceiver";

    public final class ListItemView{

        //    final Intent ground = new Intent(MainView.this,Ground.class);
//    Intent Message = new Intent(MainView.this, Mess.class);
    public ImageView potrait;//头像
    public ImageView receive ;//接单按钮
    public ImageView share;//分享按钮
    public ImageView comment;//评论按钮
    public TextView username;//用户昵称
    public TextView send_time;//发布时间
    public TextView send_location;//发布地点
    public TextView send_text;//发布内容
    public LinearLayout ll_receive;//接受LL
    public LinearLayout ll_share;//分享LL
    public LinearLayout ll_comment;//评论LL
    }
//            new ArrayList<Map<String, Object>>();
    public MyAdapter(Context context, List<Map<String , Object>> listItems){
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.listItems = listItems;

    }
    @Override
    public int getCount(){
        if (listItems == null){
            return 0;
        }
        return listItems.size();
    }
    @Override

    public Object getItem(int i){
        if (listItems == null){
            return null;
        }
        return listItems.get(i);
    }
    @Override
    public long getItemId(int i){
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        View convertView;
        final ViewHolder viewHolder;
        if (view == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.page_01_item,null);
//            viewHolder.comment = convertView.findViewById(R.id.)
            viewHolder = new ViewHolder();
            viewHolder.ll_comment = (LinearLayout) convertView.findViewById(R.id.ll_comment);
            viewHolder.ll_receive = (LinearLayout) convertView.findViewById(R.id.ll_receive);
            viewHolder.ll_share = (LinearLayout) convertView.findViewById(R.id.ll_share);
            viewHolder.potrait = (ImageView) convertView.findViewById(R.id.portrait);
            viewHolder.receive = (ImageView) convertView.findViewById(R.id.receive);
            viewHolder.send_location = (TextView) convertView.findViewById(R.id.send_location);
            viewHolder.send_text = (TextView) convertView.findViewById(R.id.send_text);
            viewHolder.send_time = (TextView) convertView.findViewById(R.id.send_time);
            viewHolder.username = (TextView) convertView.findViewById(R.id.username);
            convertView.setTag(viewHolder);
        }else {
            convertView = view;
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.username.setText((String)listItems.get(i).get("username"));
//        viewHolder.potrait.setBackgroundResource((Integer)listItems.get(i).get("icon"));
        viewHolder.potrait.setImageResource((Integer)listItems.get(i).get("icon"));
        viewHolder.send_time.setText((String)listItems.get(i).get("time"));
        viewHolder.send_location.setText((String)listItems.get(i).get("location"));
        viewHolder.send_text.setText((String)listItems.get(i).get("text"));

        //为“接单”按钮设置点击事件
        viewHolder.ll_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.receive.getTag().equals("0")){
                    viewHolder.receive.setBackgroundResource(R.drawable.like_full);
                    viewHolder.receive.setTag("1");
                    Intent intent = new Intent();
                    intent.setAction(STATICACTION);

                    context.sendBroadcast(intent);

                }else {
                    viewHolder.receive.setBackgroundResource(R.drawable.like);
                    viewHolder.receive.setTag("0");
                }

            }
        });
        viewHolder.ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,"此处应从底部弹出分享界面",Toast.LENGTH_SHORT);
                System.out.println("此处应从底部弹出分享界面");
            }
        });
        viewHolder.ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,"此处应跳到评论界面",Toast.LENGTH_SHORT);
                Intent comment_page = new Intent(context, com.me.android.dragonfly.comment_page.class);
                context.startActivity(comment_page);
                System.out.println("此处应跳到评论界面");
            }
        });


//        listItem.put("icon",R.drawable.dragonfly_1);
//        listItem.put("potrait","李某某");
//        listItem.put("time","6分钟前");
//        listItem.put("location","中山大学至善园6号");
//        listItem.put("text","有没有人要取快递啊   (๑•ั็ω•็ั๑) (๑•ั็ω•็ั๑) (๑•ั็ω•็ั๑) (๑•ั็ω•็ั๑) (๑•ั็ω•็ั๑) (๑•ั็ω•็ั๑)求帮拿送到至善园6号。。。");
        return convertView;
    }
    private class ViewHolder{
        public ImageView potrait;//头像
        public ImageView receive ;//接单按钮
//        public ImageView share;//分享按钮
//        public ImageView comment;//评论按钮
        public TextView username;//用户昵称
        public TextView send_time;//发布时间
        public TextView send_location;//发布地点
        public TextView send_text;//发布内容
        public LinearLayout ll_receive;//接受LL
        public LinearLayout ll_share;//分享LL
        public LinearLayout ll_comment;//评论LL

    }
}
