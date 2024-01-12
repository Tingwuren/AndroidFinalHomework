package edu.bupt.shopeasy.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.GnssAntennaInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.bupt.shopeasy.R;
import edu.bupt.shopeasy.adapter.CartAdapter;
import edu.bupt.shopeasy.adapter.HomeAdapter;
import edu.bupt.shopeasy.database.CartDatabaseHelper;
import edu.bupt.shopeasy.database.ProductDatabaseHelper;

public class CartFragment extends BaseFragment {
    private final String TAG = "CartFragment";
    private RecyclerView recyclerView; // 购物车商品列表
    private CheckBox checkAll; // 全选
    private CheckBox deleteAll; // 清空
    private TextView total; // 总价
    private Button settle; //结算按键
    CartDatabaseHelper cartDatabaseHelper;
    SQLiteDatabase db;
    Cursor cursor;

    CartAdapter adapter;
    SharedPreferences sp;
    String username;
    @Override
    public View initView() {
        Log.i(TAG, "正在初始化购物车");
        View view = View.inflate(mContext, R.layout.fragment_cart, null);
        recyclerView = view.findViewById(R.id.recyclerView);
        checkAll = view.findViewById(R.id.check_all);
        deleteAll = view.findViewById(R.id.delete_all);
        total = view.findViewById(R.id.total);
        settle = view.findViewById(R.id.settle);
        return view;
    }

    public void initData(){
        super.initData();
        sp = mContext.getSharedPreferences("username", MODE_PRIVATE);
        username = sp.getString("Loginname", "");
        Log.i(TAG, "加载商品列表，用户为：" +username);
        cartDatabaseHelper = new CartDatabaseHelper(getActivity(), "Cart", null, 1);
        db = cartDatabaseHelper.getReadableDatabase();
        Log.i(TAG, "获取到数据库"+db);
        cursor = db.query("Cart", null, "username = ?", new String[]{username}, null, null, null);
        Log.i(TAG, "查询到数据" + cursor);
        adapter = new CartAdapter(mContext, cursor);
        Log.i(TAG, "新建adapter成功" + adapter);
        recyclerView.setAdapter(adapter);
        Log.i(TAG, "设置adapter成功");
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        Log.i(TAG, "设置为1列");
        //循环视图加载网格布局
        recyclerView.setLayoutManager(manager);

        Listener();
    }
    public void Listener() {

        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshData();
                total.setText("￥ "+String.valueOf(adapter.getTotalPrice()));
                settleListener();
            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartDatabaseHelper.deleteCart(username);
                refreshData();
            }
        });
    }
    public void settleListener() {
        // 给settle属性添加一个事件监听器
        settle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double total_price = adapter.getTotalPrice();
                cartDatabaseHelper.deleteCart(username);
                Toast.makeText(mContext, "您的总花费为"+ total_price + "元", Toast.LENGTH_SHORT).show();
                total.setText("￥ 0.0");
                refreshData();
            }
        });
    }
    @Override
    public void refreshData() {
        sp = mContext.getSharedPreferences("username", MODE_PRIVATE);
        username = sp.getString("Loginname", "");
        Log.i(TAG, "加载商品列表，用户为：" +username);
        cartDatabaseHelper = new CartDatabaseHelper(getActivity(), "Cart", null, 1);
        db = cartDatabaseHelper.getReadableDatabase();
        Log.i(TAG, "获取到数据库"+db);
        cursor = db.query("Cart", null, "username = ?", new String[]{username}, null, null, null);
        Log.i(TAG, "查询到数据" + cursor);
        adapter = new CartAdapter(mContext, cursor);
        Log.i(TAG, "新建adapter成功" + adapter);
        recyclerView.setAdapter(adapter);
        Log.i(TAG, "设置adapter成功");
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        Log.i(TAG, "设置为1列");
        //循环视图加载网格布局
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void saveData() {

    }
}
