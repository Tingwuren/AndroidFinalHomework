package edu.bupt.shopeasy.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.bupt.shopeasy.R;
import edu.bupt.shopeasy.adapter.HomeAdapter;
import edu.bupt.shopeasy.database.ProductDatabaseHelper;

public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    SearchView searchView;
    Button searchButton;
    RecyclerView recyclerView;
    ProductDatabaseHelper productDatabaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    private HomeAdapter adapter; // HomeAdapter的实例


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        searchView =view.findViewById(R.id.searchView); // 搜索框
        searchButton = view.findViewById(R.id.searchButton); // 搜索按键
        recyclerView = view.findViewById(R.id.recyclerView); // 商品列表
        return view;
    }

    public void initData() {
        super.initData();
        Log.i(TAG, "加载商品列表");
        productDatabaseHelper = new ProductDatabaseHelper(getActivity(), "Product", null, 1);
        db = productDatabaseHelper.getReadableDatabase();
        Log.i(TAG, "获取到数据库"+db);
        cursor = db.query("Product", null, null, null, null, null, null);
        Log.i(TAG, "查询到数据" + cursor);
        adapter = new HomeAdapter(mContext, cursor);
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //执行搜索操作，query为用户输入的文本
                db = productDatabaseHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM Product WHERE name LIKE '%" + query + "%'", null);
                adapter = new HomeAdapter(mContext, cursor);
                recyclerView.setAdapter(adapter);
                GridLayoutManager manager = new GridLayoutManager(mContext, 1);
                //循环视图加载网格布局
                recyclerView.setLayoutManager(manager);
                //显示搜索结果
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //用户输入文本变化时的操作
                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用点击事件处理方法
                onClickSearch(v);
            }
        });
    }
    public void onClickSearch(View view) {
        //获取搜索框中的文本
        String query = searchView.getQuery().toString();
        //执行搜索操作，query为用户输入的文本
        db = productDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Product WHERE name LIKE '%" + query + "%'", null);
        adapter = new HomeAdapter(mContext, cursor);
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        //循环视图加载网格布局
        recyclerView.setLayoutManager(manager);
        //显示搜索结果
    }
    @Override
    public void refreshData() {
        Log.i(TAG, "加载商品列表");
        productDatabaseHelper = new ProductDatabaseHelper(getActivity(), "Product", null, 1);
        db = productDatabaseHelper.getReadableDatabase();
        Log.i(TAG, "获取到数据库"+db);
        cursor = db.query("Product", null, null, null, null, null, null);
        Log.i(TAG, "查询到数据" + cursor);
        adapter = new HomeAdapter(mContext, cursor);
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
