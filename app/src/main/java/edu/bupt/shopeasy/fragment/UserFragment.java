package edu.bupt.shopeasy.fragment;

import static android.content.Context.MODE_PRIVATE;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.bupt.shopeasy.LoginActivity;
import edu.bupt.shopeasy.R;
import edu.bupt.shopeasy.RegisterActivity;

public class UserFragment extends BaseFragment {
    private final String TAG = "UserFragment";
    private static final int LOGIN_REQUEST_CODE = 1;

    Button login_button;
    Button register_button;
    TextView show_hello;
    SharedPreferences sp;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        login_button = view.findViewById(R.id.login_button);
        register_button = view.findViewById(R.id.register_button);
        sp = getActivity().getSharedPreferences("username", MODE_PRIVATE);  //获取sharepreferences
        show_hello = view.findViewById(R.id.mainword);
        show_hello.setText("欢迎你！"+sp.getString("Loginname",""));     //获取用户名
        return view;
    }

    private void Listener() {
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "用户登录事件");
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "用户注册事件");
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initData() {
        super.initData();
        Log.i(TAG, "用户数据初始化");

        Listener();

    }

    @Override
    public void refreshData() {

    }

    @Override
    public void saveData() {

    }
}
