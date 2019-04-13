package com.example.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_name;
    private EditText et_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this,"6c17f3efc0810e0c59ae1d87c72f2b39");
        et_name = findViewById(R.id.user_name);
        et_pwd = findViewById(R.id.user_pwd);
        Button login = findViewById(R.id.login);
        Button register = findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login:
                final String user_name = et_name.getText().toString().trim();
                final String user_pwd = et_pwd.getText().toString().trim();
                BmobQuery<Person> query = new BmobQuery<>();
                query.addWhereEqualTo("user_name",user_name);
                query.findObjects(new FindListener<Person>() {
                    @Override
                    public void done(List<Person> list, BmobException e) {
                        if(e == null && list.size() > 0){
                            if(list.get(0).getPassword().equals(user_pwd)){
                                Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),home.class);
                                intent.putExtra("user_name",user_name);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(MainActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                break;
            case R.id.register:
                Intent intent = new Intent(this,register.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
