package com.example.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.List;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class totalTime extends AppCompatActivity {
    private String user_name;
    private int total_Hour;
    private int total_Minite;
    private int total_Second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this,"6c17f3efc0810e0c59ae1d87c72f2b39");
        setContentView(R.layout.activity_total_time);
        final TextView tv = findViewById(R.id.tv);
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
        BmobQuery<Person> query = new BmobQuery<>();
        query.addWhereEqualTo("user_name",user_name);
        query.findObjects(new FindListener<Person>() {
            @Override
            public void done(List<Person> list, BmobException e) {
                if(e == null && list.size() > 0){
                    total_Hour = list.get(0).getHour();
                    total_Minite = list.get(0).getMinite();
                    total_Second = list.get(0).getSecond();
                    tv.setText("截至至今，您当前累计的时长为" + total_Hour + "小时" + total_Minite +"分" + total_Second + "秒");
                }
            }
        });
    }
}
