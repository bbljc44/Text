package com.example.signin;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class home_main extends Fragment implements View.OnClickListener{
    private LinearLayout linearLayout;
    private Button sign_in;
    private View view;
    private String[] startTime;
    private int nowMinite = -1;
    private int nowHour = 0;
    private int nowSecond;
    private String user_name;
    private boolean flag = false;
    private Handler handler = new Handler();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main,null);
        Bundle bundle = this.getArguments();
        sign_in = view.findViewById(R.id.main_sign_in);
        user_name = bundle.getString("user_name");
        linearLayout = view.findViewById(R.id.main_ll);
        sign_in = view.findViewById(R.id.main_sign_in);
        sign_in.setOnClickListener(this);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(flag == false) {
            writeTime();
        }
        flag = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(flag == false){
            writeTime();
        }
    }

    @Override
    public void onClick(View v) {
        WifiManager wifiManager = (WifiManager) view.getContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        if(info == null){
            Toast.makeText(view.getContext(), "请开启WIFI", Toast.LENGTH_SHORT).show();
        }else{
            //等测试
            String ssid = info.getSSID();
            String bssid = info.getBSSID();
            if(ssid.equals("\"ict2\"") && bssid.equals("48:7d:2e:45:a1:3c")){
                sign_in.setText("签到成功");
                sign_in.setEnabled(false);
                final long currentTime = System.currentTimeMillis();
                Date date = new Date(currentTime);
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                startTime = format.format(date).split(":");
                final TextView textView = (TextView)View.inflate(view.getContext(),R.layout.main_msg,null);
                linearLayout.addView(textView);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        long nowTime = System.currentTimeMillis();
                        Date date = new Date(nowTime);
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        String[] totalTime = format.format(date).split(":");

                        nowSecond = Integer.parseInt(totalTime[2]) - Integer.parseInt(startTime[2]);
                        if(nowSecond < 0){
                            nowSecond = Integer.parseInt(totalTime[2]) + 60 - Integer.parseInt(startTime[2]);
                        }
                        if(nowSecond == 0){
                            nowMinite++;
                        }
                        if(nowMinite == 60){
                            nowHour ++;
                            nowMinite = 0;
                        }
                        textView.setText("今天已累计使用此软件:" + nowHour +":" + nowMinite + ":" + nowSecond);
                        handler.postDelayed(this,1000);
                    }
                });
            }
            else{
                Toast.makeText(view.getContext(), "你不在制定的网络范围内签到", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void writeTime(){
        BmobQuery<Person> query = new BmobQuery<>();
        query.addWhereEqualTo("user_name",user_name);
        query.findObjects(new FindListener<Person>() {
            @Override
            public void done(List<Person> list, BmobException e) {
                if(e == null && list.size() > 0){
                    Person person = list.get(0);
                    int hour = person.getHour() + nowHour;
                    int minite = person.getMinite() + nowMinite;
                    int second = person.getSecond() + nowSecond;
                    if(second > 59){
                        int toMinite = second / 60;
                        second = Math.abs(60 * toMinite - second);
                        minite = minite + toMinite;
                    }
                    if(minite > 59){
                        int toHour = minite / 60;
                        minite = minite + Math.abs(60 * toHour - minite);
                        hour = hour + toHour;
                    }
                    person.setHour(hour);
                    person.setMinite(minite);
                    person.setSecond(second);
                    person.update(person.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e != null){
                                Toast.makeText(view.getContext(), "数据更新有误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
