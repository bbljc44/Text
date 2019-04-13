package com.example.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class home_my extends Fragment {
    private ListView lv;
    private String[] list_item = {"查看累积时间","排行榜"};
    private String user_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,null);
        Bundle bundle = this.getArguments();
        user_name = bundle.getString("user_name");
        lv = view.findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(view.getContext(),R.layout.support_simple_spinner_dropdown_item,list_item));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        Intent intent = new Intent(getContext(),totalTime.class);
                        intent.putExtra("user_name",user_name);
                        startActivity(intent);
                        break;
                    case 1:
                        //排行榜

                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }
}
