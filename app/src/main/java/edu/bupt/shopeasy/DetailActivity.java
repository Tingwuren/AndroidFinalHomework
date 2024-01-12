package edu.bupt.shopeasy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import edu.bupt.shopeasy.adapter.CartAdapter;
import edu.bupt.shopeasy.database.CartDatabaseHelper;
import edu.bupt.shopeasy.database.ProductDatabaseHelper;

// 商品详情页面
public class DetailActivity extends Activity {
    private static final String TAG = "DetailActivity";
    private Context mContext;
    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;
    private TextView productType;
    private Button addToCart;

    CartDatabaseHelper cartDatabaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    String name;
    String image;
    double price;
    String type;
    SharedPreferences sp;
    String username;

    CartAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mContext = this;
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productType = findViewById(R.id.product_type);
        addToCart = findViewById(R.id.add_to_cart);
        Intent intent =getIntent();
        String id = intent.getStringExtra("id");
        // 根据商品的id，查询数据库，获取商品的详细信息
        ProductDatabaseHelper dbHelper = new ProductDatabaseHelper(mContext, "Product", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("product", null, "id = ?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            // 获取商品的属性，例如name，price，type，image等
            name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
            type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
            // 设置控件的内容，根据商品的属性
            productName.setText(name);
            productPrice.setText("￥" + price);
            productType.setText(type);
            // 使用getIdentifier方法，传入文件名，资源类型，和包名，返回图片的资源id
            int imageId = getResources().getIdentifier(image, "drawable", getPackageName());
            // 使用setImageResource方法，传入图片的资源id，设置商品图片
            productImage.setImageResource(imageId);
        }

        Listener();
    }
    // 添加购物车时的监听
    private void Listener() {
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartDatabaseHelper = new CartDatabaseHelper(mContext, "Cart", null, 1);
                sp = mContext.getSharedPreferences("username", MODE_PRIVATE);
                username = sp.getString("Loginname", "");
                if(username == ""){
                    Toast.makeText(mContext, "请先登录！", Toast.LENGTH_SHORT).show();
                    return;
                }
                int count = 1;
                boolean exist = cartDatabaseHelper.queryCart(name, username);
                if(exist){
                    cartDatabaseHelper.updateCart(name, username, count);
                    Toast.makeText(mContext, "商品已经在购物车了。", Toast.LENGTH_SHORT).show();
                }else {
                    cartDatabaseHelper.insertCart(name, price, type, image, count, username);
                    Toast.makeText(mContext, "添加成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void  onStart() {
        super.onStart();
    }
}
