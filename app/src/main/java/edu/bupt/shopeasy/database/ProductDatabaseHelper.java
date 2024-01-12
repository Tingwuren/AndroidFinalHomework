package edu.bupt.shopeasy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ProductDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_PRODUCT = "Create table Product("
            + "id integer primary key autoincrement,"
            + "name text,"
            + "price real,"
            + "type text,"
            + "image text)";
    private Context mContext;
    public ProductDatabaseHelper(Context context,String name, SQLiteDatabase.CursorFactory factory,
                                 int version){
        super(context,name, factory,version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PRODUCT);
        ContentValues values = new ContentValues();
        // 插入第一个商品
        values.put("name","小米14");
        values.put("price", 5000);
        values.put("type","数码");
        values.put("image", "image_1");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第二个商品
        values.put("name","华为P40");
        values.put("price", 6000);
        values.put("type","数码");
        values.put("image", "image_2");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第三个商品
        values.put("name","苹果12");
        values.put("price", 8000);
        values.put("type","数码");
        values.put("image", "image_3");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第四个商品
        values.put("name","联想笔记本");
        values.put("price", 4000);
        values.put("type","电脑");
        values.put("image", "image_4");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第五个商品
        values.put("name","戴尔台式机");
        values.put("price", 3000);
        values.put("type","电脑");
        values.put("image", "image_5");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第六个商品
        values.put("name","海尔冰箱");
        values.put("price", 2000);
        values.put("type","家电");
        values.put("image", "image_6");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第七个商品
        values.put("name","美的空调");
        values.put("price", 2500);
        values.put("type","家电");
        values.put("image", "image_7");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第八个商品
        values.put("name","李宁运动鞋");
        values.put("price", 500);
        values.put("type","服饰");
        values.put("image", "image_8");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第九个商品
        values.put("name","阿迪达斯T恤");
        values.put("price", 300);
        values.put("type","服饰");
        values.put("image", "image_9");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第十个商品
        values.put("name","百雀羚护肤套装");
        values.put("price", 1000);
        values.put("type","美妆");
        values.put("image", "image_10");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第十一个商品
        values.put("name","飞利浦剃须刀");
        values.put("price", 800);
        values.put("type","美妆");
        values.put("image", "image_11");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第十二个商品
        values.put("name","韩束面膜");
        values.put("price", 200);
        values.put("type","美妆");
        values.put("image", "image_12");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第十三个商品
        values.put("name","耐克运动裤");
        values.put("price", 400);
        values.put("type","服饰");
        values.put("image", "image_13");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第十四个商品
        values.put("name","杰克琼斯牛仔裤");
        values.put("price", 600);
        values.put("type","服饰");
        values.put("image", "image_14");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第十五个商品
        values.put("name","格力空气净化器");
        values.put("price", 1500);
        values.put("type","家电");
        values.put("image", "image_15");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第十六个商品
        values.put("name","美的微波炉");
        values.put("price", 1000);
        values.put("type","家电");
        values.put("image", "image_16");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第十七个商品
        values.put("name","惠普打印机");
        values.put("price", 500);
        values.put("type","电脑");
        values.put("image", "image_17");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第十八个商品
        values.put("name","华硕显示器");
        values.put("price", 800);
        values.put("type","电脑");
        values.put("image", "image_18");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第十九个商品
        values.put("name","三星S21");
        values.put("price", 7000);
        values.put("type","数码");
        values.put("image", "image_19");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        // 插入第二十个商品
        values.put("name","索尼相机");
        values.put("price", 10000);
        values.put("type","数码");
        values.put("image", "image_20");
        sqLiteDatabase.insert("Product",null, values);
        values.clear();
        Toast.makeText(mContext, "Create  product table succeeded",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Product");
        onCreate(sqLiteDatabase);
    }
}
