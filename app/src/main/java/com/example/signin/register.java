package com.example.signin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class register extends AppCompatActivity {
    private EditText user_name;
    private EditText user_pwd;
    String name;
    String pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bmob.initialize(this,"6c17f3efc0810e0c59ae1d87c72f2b39");
        user_name = findViewById(R.id.user_name);
        user_pwd = findViewById(R.id.user_pwd);
    }

    public void regist(View v){
        name = user_name.getText().toString().trim();
        pwd = user_pwd.getText().toString().trim();
        BmobQuery<Person> query = new BmobQuery<>();
        query.addWhereEqualTo("user_name",name);
        query.findObjects(new FindListener<Person>() {
            @Override
            public void done(List<Person> list, BmobException e) {
                if(e==null && list.size()>0 ){
                    Toast.makeText(register.this, "该用户已经被注册", Toast.LENGTH_SHORT).show();
                }else{
                    Person person = new Person();
                    person.setUser_name(name);
                    person.setPassword(pwd);
                    person.setHour(0);
                    person.setMinite(0);
                    person.setSecond(0);
                    person.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e != null){
                                Toast.makeText(register.this, "因未知原因注册失败", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(register.this, "注册成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    finish();
                }
            }
        });
    }
}
