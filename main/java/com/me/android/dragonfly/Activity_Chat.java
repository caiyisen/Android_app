package com.me.android.dragonfly;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Activity_Chat extends Activity {

    String[] from={"image","text"};
    int[] to={R.id.chatlist_image_me,R.id.chatlist_text_me,
            R.id.chatlist_image_other,R.id.chatlist_text_other};
    int[] layout={R.layout.message_from_me_listitem,R.layout.message_from_h_listitem};
    ArrayList<HashMap<String,Object>> chatList;
    ListView chatListView;
    Button chatSendButton;
    EditText editText;

    MyChatAdapter adapter;
    public final static int OTHER=1;
    public final static int ME=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__chat);


        chatList=new ArrayList<HashMap<String,Object>>();
        addTextToList("你好在吗，我要去拿快递顺便帮你拿呗", ME);
        addTextToList("真的吗，那太好了", OTHER);
        addTextToList("嗯嗯", ME);
        addTextToList("好的谢谢！", OTHER);

        chatSendButton=(Button)findViewById(R.id.chat_bottom_sendbutton);
        editText=(EditText)findViewById(R.id.chat_bottom_edittext);
        chatListView=(ListView)findViewById(R.id.chat_list);

        adapter=new MyChatAdapter(this,chatList,layout,from,to);

        chatSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myWord;
                myWord=(editText.getText()+"").toString();
                if(myWord.length()==0)
                    return;
                addTextToList(myWord, ME);

                editText.setText("");
                adapter.notifyDataSetChanged();
                chatListView.setSelection(chatList.size()-1);
            }
        });
        chatListView.setAdapter(adapter);
    }

    protected void addTextToList(String text, int who){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("person",who );
        map.put("image", who==ME?R.drawable.pic1:R.drawable.pic2);
        map.put("text", text);
        chatList.add(map);
    }

    private class MyChatAdapter extends BaseAdapter {

        Context context=null;
        ArrayList<HashMap<String,Object>> chatList=null;
        int[] layout;
        String[] from;
        int[] to;



        public MyChatAdapter(Context context,
                             ArrayList<HashMap<String, Object>> chatList, int[] layout,
                             String[] from, int[] to) {
            super();
            this.context = context;
            this.chatList = chatList;
            this.layout = layout;
            this.from = from;
            this.to = to;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return chatList.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        class ViewHolder{
            public ImageView imageView=null;
            public TextView textView=null;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder=null;
            int who=(Integer)chatList.get(position).get("person");

            convertView= LayoutInflater.from(context).inflate(
                    layout[who==ME?0:1], null);
            holder=new ViewHolder();
            holder.imageView=(ImageView)convertView.findViewById(to[who*2+0]);
            holder.textView=(TextView)convertView.findViewById(to[who*2+1]);

            System.out.println(holder);
            System.out.println("WHYWHYWHYWHYW");
            System.out.println(holder.imageView);
            holder.imageView.setBackgroundResource((Integer)chatList.get(position).get(from[0]));
            holder.textView.setText(chatList.get(position).get(from[1]).toString());
            return convertView;
        }

    }

}
