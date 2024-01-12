package edu.bupt.shopeasy.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.bupt.shopeasy.R;
import edu.bupt.shopeasy.adapter.HomeAdapter;
import edu.bupt.shopeasy.adapter.TypeAdapter;
import edu.bupt.shopeasy.database.ProductDatabaseHelper;

import android.widget.Button;

public class TypeFragment extends BaseFragment {
    private static final String TAG = "TypeFragment";

    Button allButton; // 所有商品按键
    Button digitalButton; // 数码商品按键
    Button computerButton; // 电脑商品按键
    Button homeButton; // 家电商品按键
    Button clothingButton; // 服饰商品按键
    Button beautyButton; // 美妆商品按键
    RecyclerView recyclerView; // 商品列表

    ProductDatabaseHelper productDatabaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    private TypeAdapter adapter;
    // 定义一个数组，存放所有的 Button
    private Button[] buttons;
    // 定义一个变量，表示当前选择的商品类别，0 表示全部，1 表示数码，2 表示电脑，依次类推
    private int category = 0;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_type, null);
        allButton = view.findViewById(R.id.all_button);
        digitalButton = view.findViewById(R.id.digital_button);
        computerButton = view.findViewById(R.id.computer_button);
        homeButton = view.findViewById(R.id.home_button);
        clothingButton = view.findViewById(R.id.clothing_button);
        beautyButton = view.findViewById(R.id.beauty_button);
        recyclerView = view.findViewById(R.id.recyclerView);
        // 初始化数组
        buttons = new Button[]{allButton, digitalButton, computerButton, homeButton, clothingButton, beautyButton};

        return view;
    }
    public void initData() {
        super.initData();
        buttons[0].setBackgroundColor(Color.parseColor("#6750A4"));
        buttons[0].setTextColor(Color.parseColor("#FFFFFF"));
        Log.i(TAG, "加载商品列表");
        productDatabaseHelper = new ProductDatabaseHelper(getActivity(), "Product", null, 1);
        db = productDatabaseHelper.getReadableDatabase();
        Log.i(TAG, "获取到数据库"+db);
        cursor = db.query("Product", null, null, null, null, null, null);
        Log.i(TAG, "查询到数据" + cursor);
        adapter = new TypeAdapter(mContext, cursor);
        Log.i(TAG, "新建adapter成功" + adapter);
        recyclerView.setAdapter(adapter);
        Log.i(TAG, "设置adapter成功");
        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        Log.i(TAG, "设置为2列");
        //循环视图加载网格布局
        recyclerView.setLayoutManager(manager);

        Listener();
    }

    public void Listener() {
        Log.i(TAG, "buttons "+ buttons);
        Log.i(TAG, "button3 "+ buttons[3]);
        // 为每个 Button 添加点击事件监听器
        for (int i = 0; i < buttons.length; i++) {
            // 获取当前的 Button
            Button button = buttons[i];
            // 设置一个标签，表示 Button 的序号
            Log.i(TAG, "button"+ i + " "+buttons[i]);
            button.setTag(i);
            // 设置点击事件
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 获取当前点击的 Button 的 id
                    int id = v.getId();
                    // 根据 id，设置 category 的值
                    if (id == R.id.all_button) {
                        category = 0;
                    } else if (id == R.id.digital_button) {
                        category = 1;
                    } else if (id == R.id.computer_button) {
                        category = 2;
                    } else if (id == R.id.home_button) {
                        category = 3;
                    } else if (id == R.id.clothing_button) {
                        category = 4;
                    } else if (id == R.id.beauty_button) {
                        category = 5;
                    }
                    // 遍历所有的 Button，根据 category 的值，设置不同的背景颜色和文字颜色
                    for (int j = 0; j < buttons.length; j++) {
                        // 获取当前的 Button
                        Button b = buttons[j];
                        // 如果当前的 Button 的标签和 category 相同，表示选中，设置特殊的颜色
                        if (j == category) {
                            b.setBackgroundColor(Color.parseColor("#6750A4"));
                            b.setTextColor(Color.parseColor("#FFFFFF"));
                        } else {
                            // 否则，表示未选中，恢复默认的颜色
                            b.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            b.setTextColor(Color.parseColor("#000000"));
                        }
                    }
                    // 根据 category 的值，重新查询数据库，获取对应类别的商品数据
                    if (category == 0) {
                        // 如果 category 为 0，表示查询所有商品
                        cursor = db.query("Product", null, null, null, null, null, null);
                    } else {
                        // 否则，使用 selection 和 selectionArgs 参数，指定查询条件，比如 category = ?
                        //获取搜索框中的文本
                        Button b = buttons[category];
                        String query = b.getText().toString();
                        //执行搜索操作，query为用户输入的文本
                        db = productDatabaseHelper.getReadableDatabase();
                        Log.i(TAG, "查询 "+ query);
                        cursor = db.rawQuery("SELECT * FROM Product WHERE type LIKE '%" + query + "%'", null);
                    }
                    // 将查询结果的 Cursor 传递给 adapter，更新 RecyclerView 的数据源
                    adapter = new TypeAdapter(mContext, cursor);
                    recyclerView.setAdapter(adapter);
                    GridLayoutManager manager = new GridLayoutManager(mContext, 2);
                    //循环视图加载网格布局
                    recyclerView.setLayoutManager(manager);
                }
            });
        }
    }
    @Override
    public void refreshData() {
    }

    @Override
    public void saveData() {
    }
}
