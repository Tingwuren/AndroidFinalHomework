package edu.bupt.shopeasy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import edu.bupt.shopeasy.DetailActivity;
import edu.bupt.shopeasy.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private static final String TAG = "HomeAdapter";
    // 定义一个Context对象，用于获取上下文
    private Context context;
    // 定义一个Cursor对象，用于存储查询商品数据库的结果
    private Cursor cursor;

    // 构造方法，传入Context对象和Cursor对象
    public HomeAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    // 定义一个ViewHolder类，继承自RecyclerView.ViewHolder，用于封装商品列表项的视图
    public class HomeViewHolder extends RecyclerView.ViewHolder {
        // 定义视图中的控件，例如ImageView，TextView等
        ImageView productImage; // 商品图片
        TextView productName; // 商品名称
        TextView productPrice; // 商品价格
        TextView productType; // 商品类型

        // 构造方法，传入一个View对象，用于初始化控件
        public HomeViewHolder(View view) {
            super(view);

            productImage = view.findViewById(R.id.product_image);
            productName = view.findViewById(R.id.product_name);
            productPrice = view.findViewById(R.id.product_price);
            productType = view.findViewById(R.id.product_type);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 获取当前点击的商品的id
                    int position = getAdapterPosition();
                    cursor.moveToPosition(position);
                    String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                    Log.i(TAG, "id: "+ id);
                    // 使用Intent对象和Context对象，启动一个新的Activity，用于显示商品详情页
                    Intent intent = new Intent(context, DetailActivity.class);
                    // 传递商品的id作为额外数据
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            });
        }
    }

    // 重写onCreateViewHolder方法，用于创建ViewHolder对象
    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 使用LayoutInflater对象加载布局文件，创建一个新的视图对象
        View view = LayoutInflater.from(context).inflate(R.layout.product_home, parent, false);
        // 返回一个包含该视图的ViewHolder对象
        return new HomeViewHolder(view);
    }

    // 重写onBindViewHolder方法，用于绑定ViewHolder对象和数据
    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        // 使用Cursor对象移动到指定位置
        cursor.moveToPosition(position);
        // 获取商品的属性，例如name，price，type，image等
        // 使用@SuppressLint注解，忽略Range这个lint警告
        @SuppressLint("Range")
        // 获取商品的name字段的索引值，如果不存在，抛出异常
        int nameIndex = cursor.getColumnIndexOrThrow("name");

        // 获取商品的price字段的索引值，如果不存在，抛出异常
        int priceIndex = cursor.getColumnIndexOrThrow("price");
        // 获取商品的type字段的索引值，如果不存在，抛出异常
        int typeIndex = cursor.getColumnIndexOrThrow("type");
        // 获取商品的image字段的索引值，如果不存在，抛出异常
        int imageIndex = cursor.getColumnIndexOrThrow("image");
        // 使用索引值，获取商品的name，price，type，image等字段的值
        String name = cursor.getString(nameIndex);
        Log.i(TAG, "获取到名字： "+ name);
        double price = cursor.getDouble(priceIndex);
        Log.i(TAG, "获取到价格： "+ price);
        String type = cursor.getString(typeIndex);
        Log.i(TAG, "获取到类型： "+ type);
        String image = cursor.getString(imageIndex);
        Log.i(TAG, "获取到图片： "+ image);
        // 设置ViewHolder对象的视图内容，根据商品的属性
        holder.productName.setText(name);
        Log.i(TAG, "设置为名字： "+ name);
        holder.productPrice.setText("￥" + price);
        holder.productType.setText(type);
        // 使用if语句，判断你的Context对象是否等于null，如果是，打印出错误信息或抛出异常
        //Log.e(TAG, "Context"+ context);
        // 使用getIdentifier方法，传入文件名，资源类型，和包名，返回图片的资源id
        int imageId = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
        // 使用setImageResource方法，传入图片的资源id，设置商品图片
        Log.i(TAG, "设置为图片： "+ imageId);
        holder.productImage.setImageResource(imageId);
    }

    // 重写getItemCount方法，用于返回Cursor对象的行数，即商品的数量
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
