package com.example.signin;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {
    List<Fragment> fragmentList;
    private String user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
        final ViewPager viewPager = findViewById(R.id.vp);
        final RadioButton home_main = findViewById(R.id.main_home);
        final RadioButton home_my = findViewById(R.id.main_my);
        RadioGroup radioGroup = findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.main_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.main_my:
                        viewPager.setCurrentItem(1);
                        break;
                    default:
                        break;
                }
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentList = new ArrayList<>();
        home_main fragment_main = new home_main();
        home_my fragment_my = new home_my();
        Bundle bundle = new Bundle();
        bundle.putString("user_name",user_name);
        fragment_main.setArguments(bundle);
        fragment_my.setArguments(bundle);
        fragmentList.add(fragment_main);
        fragmentList.add(fragment_my);
        viewPager.setCurrentItem(0);
        home_main.setChecked(true);
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if(i==2){
                    switch(viewPager.getCurrentItem()){
                        case 0:
                            home_main.setChecked(true);
                            break;
                        case 1:
                            home_my.setChecked(true);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }
}
