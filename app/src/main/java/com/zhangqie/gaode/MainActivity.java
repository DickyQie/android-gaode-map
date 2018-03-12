package com.zhangqie.gaode;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.zhangqie.gaode.ui.basic.LocationActivity;
import com.zhangqie.gaode.ui.polyline.PolylineActivity;
import com.zhangqie.gaode.ui.route.RouteActivity;


/**
 * Created by zhangqie on 2017/3/12.
 *
 * 高德地图功能列表
 */

public class MainActivity extends AppCompatActivity {

    private static final String[] strList =new  String[]{
            "定位数据，显示地图，标注位置","两点绘制路线",
            "多点绘制路线"
    };


    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        listView = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showIntent(position);
            }
        });
    }

    private void showIntent(int index){
        switch (index){
            case 0:
                startActivity(new Intent(MainActivity.this,LocationActivity.class));
                break;
            case 1:
                startActivity(new Intent(MainActivity.this,RouteActivity.class));
                break;
            case 2:
                startActivity(new Intent(MainActivity.this,PolylineActivity.class));
                break;
        }
    }
}
