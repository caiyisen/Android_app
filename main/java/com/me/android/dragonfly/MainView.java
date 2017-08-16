package com.me.android.dragonfly;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainView extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    // 底部菜单4个Linearlayout
    private LinearLayout ll_ground;
    private LinearLayout ll_message;
    private LinearLayout ll_mine;

    static boolean newadd = false;//判断有没有新增
    static boolean listview_initial =true;//不加这个会覆盖掉前面新增的
    // 底部菜单3个ImageView
    private ImageView iv_ground;
    private ImageView iv_message;
    private ImageView iv_mine;

    // 底部菜单3个菜单标题
    private TextView tv_ground;
    private TextView tv_message;
    private TextView tv_mine;
    private MyAdapter myAdapter;
    final List<Map<String , Object>> listItems =
            new ArrayList<Map<String, Object>>();
    final List<Map<String , Object>> listItems1 =
            new ArrayList<Map<String, Object>>();
    // 中间内容区域
    private ViewPager viewPager;

    // ViewPager适配器ContentAdapter
    private ContentAdapter adapter;

    private List<View> views;

    private String[] usernames = new String[] {"赵某某", "钱某某", "孙某某", "李某某", "周某某", "吴某某", "郑某某", "王某某", "陈某某", "沈某某","蔡某某"};
    private String[] times = new String[]
            {"刚刚" , "1分钟前" , "1分钟前" , "2分钟前" , "3分钟前" , "5分钟前" , "6分钟前" , "7分钟前" , "8分钟前" , "8分钟前", "9分钟前"};
    private int[] icons = new int[]
            {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5, R.drawable.pic6, R.drawable.pic7, R.drawable.pic8, R.drawable.pic9, R.drawable.pic10,R.drawable.pic11};
    private String[] locations = new String[]
            {"中山大学至善园1号" , "中山大学至善园1号" , "中山大学至善园2号" , "中山大学至善园3号" , "中山大学至善园4号" , "中山大学至善园5号" ,
                    "中山大学至善园6号" , "中山大学至善园7号" , "中山大学至善园8号" , "中山大学至善园9号","中山大学至善园10号" };
    private String[] texts = new String[] {
            "有没有人要取快递啊！！求帮拿送到至善园1号。。。",
            "有没有人要取快递啊求帮拿送到至善园1号。。。",
            "有没有人要取快递啊求帮拿送到至善园2号。。。"
            ,"有没有人要取快递啊求帮拿送到至善园3号。。。"
            ,"有没有人要取快递啊求帮拿送到至善园4号。。。"
            ,"有没有人要取快递啊求帮拿送到至善园5号。。。"
            ,"有没有人要取快递啊求帮拿送到至善园6号。。。"
            ,"有没有人要取快递啊求帮拿送到至善园7号。。。"
            ,"有没有人要取快递啊求帮拿送到至善园8号。。。"
            ,"有没有人要取快递啊求帮拿送到至善园9号。。。"
            ,"有没有人要取快递啊求帮拿送到至善园10号。。。"};

    public static TextView top_title ;
    private FloatingActionButton add_button;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    //    private LinearLayout ll_main;
    private Toolbar toolbar;
    private ActionBar actionbar;
    private NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        actionbar = this.getSupportActionBar();
        // 初始化控件
        initView();
        // 初始化底部按钮事件
        initEvent();

        Button sign = (Button)mNavigationView.getHeaderView(0).findViewById(R.id.sign);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainView.this,Activity_login.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                System.out.println("函数  onOptionsItemSelected 被执行");
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initEvent() {
        // 设置按钮监听
        ll_ground.setOnClickListener(this);
        ll_message.setOnClickListener(this);
        ll_mine.setOnClickListener(this);

        //设置ViewPager滑动监听
        viewPager.setOnPageChangeListener(this);
    }

    private void initView() {

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mNavigationView = (NavigationView)findViewById(R.id.nv_main_menu);
        // 底部菜单3个Linearlayout
        this.ll_ground = (LinearLayout) findViewById(R.id.ll_ground);
        this.ll_message = (LinearLayout) findViewById(R.id.ll_message);
        this.ll_mine = (LinearLayout) findViewById(R.id.ll_mine);

        // 底部菜单3个ImageView
        this.iv_ground = (ImageView) findViewById(R.id.iv_ground);
        this.iv_message = (ImageView) findViewById(R.id.iv_message);
        this.iv_mine = (ImageView) findViewById(R.id.iv_mine);


        // 底部菜单3个菜单标题
        this.tv_ground = (TextView) findViewById(R.id.tv_ground);
        this.tv_message = (TextView) findViewById(R.id.tv_message);
        this.tv_mine = (TextView) findViewById(R.id.tv_mine);


        // 中间内容区域ViewPager
        this.viewPager = (ViewPager) findViewById(R.id.vp_content);


        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        // 适配器
        View page_01 = View.inflate(MainView.this, R.layout.page_01, null);
        View page_02 = View.inflate(MainView.this, R.layout.page_02, null);
        View page_03 = View.inflate(MainView.this, R.layout.page_03, null);

        for (int i = 0; i < usernames.length;i++)
        {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("icon",icons[i]);
            listItem.put("username",usernames[i]);
            listItem.put("time",times[i]);
            listItem.put("location","来自 ["+locations[i]+"]");
            listItem.put("text",texts[i]);
            listItems.add(listItem);
        }

        ListView listView = (ListView) page_01.findViewById(R.id.page01_listview);
        myAdapter = new MyAdapter(this,listItems);
        listView.setAdapter(myAdapter);

        for(int i = 0; i <usernames.length;i++){
            Map<String, Object> listItem1 = new HashMap<String, Object>();
            listItem1.put("icon",icons[i]);
            listItem1.put("username",usernames[i]);
            listItems1.add(listItem1);
        }
        ListView listView1 = (ListView) page_02.findViewById(R.id.list_contacts);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,listItems1,R.layout.page_02_item,
                new String[]{"icon","username"},new int[]{R.id.image2,R.id.name2});
        listView1.setAdapter(simpleAdapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainView.this,Activity_Chat.class);
                startActivity(intent);
            }
        });


        add_button = (FloatingActionButton)page_01.findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("新增按钮被点击");
                Intent new_items = new Intent(MainView.this,New_item.class);
                startActivity(new_items);
            }
        });

        if (newadd){//如果新增了条目
            //接受MainActivity传来的数据
            Intent intent = getIntent();
            String newtext = intent.getStringExtra("text");
            String newlocate = intent.getStringExtra("locate");
            System.out.println("新增条目的");
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("icon",icons[0]);
            listItem.put("username",usernames[0]);
            listItem.put("time",times[0]);
            listItem.put("location","来自 ["+newlocate+"]");
            listItem.put("text",newtext);
//            listItem.put("money","1");
            listItems.add(0,listItem);
            myAdapter = new MyAdapter(this,listItems);
            listView.setAdapter(myAdapter);
            newadd=false;

        }

        views = new ArrayList<View>();
        views.add(page_01);
        views.add(page_02);
        views.add(page_03);


        this.adapter = new ContentAdapter(views);
        viewPager.setAdapter(adapter);


        //初始化ToolBar
        toolbar.setTitle("蜻蜓取件");
        System.out.println("110AA");
        setSupportActionBar(toolbar);
//        setActionBar(toolbar);
        System.out.println("110BB");
        // 为ActionBar左上角图标加上一个返回箭头的图标
        final ActionBar actionbar_1 = getSupportActionBar();
        System.out.println("getActionBar()的返回值： "+getActionBar());
        System.out.println("getActionBar()的返回值： "+getSupportActionBar());
        actionbar_1.setDisplayHomeAsUpEnabled(true);
        System.out.println("15");
        // 使左上角图标可以点击
//        actionbar.setHomeButtonEnabled(true);
        // 设置ActionBarDrawerToggle与DrawerLayout通过setDrawerListener相关联
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open,
                R.string.drawer_close);

        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        System.out.println("13");
//        drawerLayout.setDrawerListener(drawerToggle);
        System.out.println("14");

        System.out.println("16");
        // true:显示左上角图标 false:不显示图标，只显示一个标题
        actionbar_1.setDisplayShowHomeEnabled(false);
        System.out.println("17");
        //设置侧滑菜单点击监听
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                System.out.println("点击的id"+id);
                switch (id) {
                    //返回首页
                    case R.id.nav_home:
                        viewPager.setCurrentItem(0);
                        break;
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });

        System.out.println("18");
    }


    @Override
    public void onClick(View v) {

        // 在每次点击后将所有的底部按钮(ImageView,TextView)颜色改为灰色，然后根据点击着色
        restartBotton();
        // ImageView和TetxView置为绿色，页面随之跳转
        switch (v.getId()) {
            case R.id.ll_ground:
                iv_ground.setImageResource(R.drawable.tab_ground_pressed);
                tv_ground.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(0);
                break;

            case R.id.ll_message:
                iv_message.setImageResource(R.drawable.tab_message_pressed);
                tv_message.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(1);
                break;

            case R.id.ll_mine:
                iv_mine.setImageResource(R.drawable.tab_message_pressed);
                tv_mine.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(2);
                break;

            default:
                break;
        }

    }

    private void restartBotton() {
        // ImageView置为灰色
        iv_ground.setImageResource(R.drawable.tab_ground_unpressed);
        iv_message.setImageResource(R.drawable.tab_message_unpressed);
        iv_mine.setImageResource(R.drawable.tab_mine_unpressed);

        // TextView置为白色
        tv_ground.setTextColor(0xffffffff);
        tv_message.setTextColor(0xffffffff);
        tv_mine.setTextColor(0xffffffff);

    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        restartBotton();
        //当前view被选择的时候,改变底部菜单图片，文字颜色
        switch (position) {
            case 0:
                iv_ground.setImageResource(R.drawable.tab_ground_pressed);
                tv_ground.setTextColor(0xff1B940A);
                break;

            case 1:
                iv_message.setImageResource(R.drawable.tab_message_pressed);
                tv_message.setTextColor(0xff1B940A);
                break;

            case 2:
                iv_mine.setImageResource(R.drawable.tab_mine_pressed);
                tv_mine.setTextColor(0xff1B940A);
                break;

            default:
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
