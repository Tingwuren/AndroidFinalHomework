package edu.bupt.shopeasy.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import edu.bupt.shopeasy.DetailActivity;
import edu.bupt.shopeasy.R;
import edu.bupt.shopeasy.database.CartDatabaseHelper;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private static final String TAG = "CartAdapter";
    // 定义一个Context对象，用于获取上下文
    private Context context;
    // 定义一个Cursor对象，用于存储查询商品数据库的结果
    private Cursor cursor;
    // 构造方法，传入Context对象和Cursor对象
    public CartAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    // 定义一个ViewHolder类，继承自RecyclerView.ViewHolder，用于封装商品列表项的视图
    public class CartViewHolder extends RecyclerView.ViewHolder {
        public String name;
        public String username;
        public int count;
        public double price;
        // 定义视图中的控件，例如ImageView，TextView等
        ImageView productImage; // 商品图片
        TextView productName; // 商品名称
        TextView productPrice; // 商品价格
        TextView productType; // 商品类型
        TextView productCount; // 商品数量
        Button productMinus;
        Button productAdd;

        CartDatabaseHelper cartDatabaseHelper;

        // 构造方法，传入一个View对象，用于初始化控件
        public CartViewHolder(View view) {
            super(view);

            productImage = view.findViewById(R.id.product_image);
            productName = view.findViewById(R.id.product_name);
            productPrice = view.findViewById(R.id.product_price);
            productType = view.findViewById(R.id.product_type);
            productCount = view.findViewById(R.id.product_count);
            productMinus = view.findViewById(R.id.product_minus );
            productAdd = view.findViewById(R.id.product_add);



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
            productMinus.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "减少商品数量按钮监听方法");
                    cartDatabaseHelper = new CartDatabaseHelper(context, "Cart", null, 1);
                    Log.i(TAG, "减少"+name +username);
                    // 判断count是否大于1
                    if (count > 1) {
                        // 减少数量
                        count--;
                        Log.i(TAG, "减少" + name + username + "的数量为" + String.valueOf(count));
                        cartDatabaseHelper.updateCart(name, username, count);
                        productCount.setText("数目："+count);
                        productPrice.setText("￥ "+ count*price);
                    } else {
                        Toast.makeText(context, "不能再减少了", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            productAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "增加商品数量按钮监听方法");

                    cartDatabaseHelper = new CartDatabaseHelper(context, "Cart", null, 1);
                    count++;
                    cartDatabaseHelper.updateCart(name, username, count);
                    Log.i(TAG, "增加"+name +username);
                    Log.i(TAG, "count"+count);
                    productCount.setText("数目："+count);
                    productPrice.setText("￥ "+ count*price);
                }
            });
        }
    }

    // 重写onCreateViewHolder方法，用于创建ViewHolder对象
    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 使用LayoutInflater对象加载布局文件，创建一个新的视图对象
        View view = LayoutInflater.from(context).inflate(R.layout.product_cart, parent, false);
        // 返回一个包含该视图的ViewHolder对象
        return new CartViewHolder(view);
    }

    // 重写onBindViewHolder方法，用于绑定ViewHolder对象和数据
    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
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
        int countIndex = cursor.getColumnIndexOrThrow("count");
        int usernameIndex = cursor.getColumnIndexOrThrow("username");
        // 使用索引值，获取商品的name，price，type，image等字段的值
        String name = cursor.getString(nameIndex);
        Log.i(TAG, "获取到名字： "+ name);
        double price = cursor.getDouble(priceIndex);
        Log.i(TAG, "获取到价格： "+ price);
        String type = cursor.getString(typeIndex);
        Log.i(TAG, "获取到类型： "+ type);
        String image = cursor.getString(imageIndex);
        Log.i(TAG, "获取到图片： "+ image);
        int count = cursor.getInt(countIndex);
        Log.i(TAG, "获取到数量： "+ count);
        String username = cursor.getString(usernameIndex);

        // 给holder的name,username,count,price等属性赋值
        holder.name = name;
        holder.username = username;
        holder.count = count;
        holder.price = price;

        // 设置ViewHolder对象的视图内容，根据商品的属性
        holder.productName.setText(name);
        Log.i(TAG, "设置为名字： "+ name);
        holder.productPrice.setText("￥" + count*price);
        holder.productType.setText(type);
        // 使用if语句，判断你的Context对象是否等于null，如果是，打印出错误信息或抛出异常
        //Log.e(TAG, "Context"+ context);
        // 使用getIdentifier方法，传入文件名，资源类型，和包名，返回图片的资源id
        int imageId = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
        // 使用setImageResource方法，传入图片的资源id，设置商品图片
        Log.i(TAG, "设置为图片： "+ imageId);
        holder.productImage.setImageResource(imageId);

        holder.productCount.setText("数目："+count);
    }

    // 重写getItemCount方法，用于返回Cursor对象的行数，即商品的数量
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
    // 定义一个计算属性，用于计算购物车中所有商品的总价
    public double getTotalPrice() {
        // 初始化总价为0
        double totalPrice = 0;
        // 遍历Cursor对象
        for (int i = 0; i < cursor.getCount(); i++) {
            // 移动到指定位置
            cursor.moveToPosition(i);
            // 获取商品的价格和数量
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
            int count = cursor.getInt(cursor.getColumnIndexOrThrow("count"));
            // 计算商品的小计，并累加到总价
            totalPrice += price * count;
        }
        // 返回总价
        return totalPrice;
    }
}
