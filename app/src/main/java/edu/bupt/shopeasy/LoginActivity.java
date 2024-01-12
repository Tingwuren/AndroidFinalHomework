package edu.bupt.shopeasy;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.bupt.shopeasy.database.UserDatabaseHelper;

public class LoginActivity extends Activity {

    EditText name,pwd;
    Button login;
    UserDatabaseHelper userDatabaseHelper;

    SQLiteDatabase db;
    SharedPreferences sp1, sp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = this.findViewById(R.id.name);            //用户名输入框
        pwd = this.findViewById(R.id.pwd);              //密码输入框
        Button login = (Button) findViewById(R.id.login);
        sp1 =  this.getSharedPreferences("useinfo",this.MODE_PRIVATE);
        sp2 = this.getSharedPreferences("username",this.MODE_PRIVATE);

        name.setText(sp1.getString("usname",null));
        pwd.setText(sp1.getString("uspwd",null));
        userDatabaseHelper = new UserDatabaseHelper(this,"Userinfo",null,1);      //建数据库或者取数据库
        db = userDatabaseHelper.getReadableDatabase();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "正在登录", Toast.LENGTH_LONG).show();
                String username = name.getText().toString();
                String password = pwd.getText().toString();                 //获取用户输入的用户名和密码
                //查询用户名和密码相同的数据
                Cursor cursor = db.query("users",new String[]{"usname","uspwd"}," usname=? and uspwd=?",new String[]{username,password},null,null,null);

                int flag = cursor.getCount();                            //查询出来的记录项的条数，若没有该用户则为0条
                if(flag!=0){                                            //若查询出的记录不为0，则进行跳转操作
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,MainActivity.class);            //设置页面跳转
                    SharedPreferences.Editor editor = sp2.edit();
                    cursor.moveToFirst();                                  //将光标移动到position为0的位置，默认位置为-1
                    String loginname = cursor.getString(0);
                    editor.putString("Loginname",loginname);
                    editor.commit();                                 //将用户名存到SharedPreferences中
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this,"用户名或密码错误！",Toast.LENGTH_LONG).show();             //提示用户信息错误或没有账号
                }
            }
        });
    }
}