package edu.bupt.shopeasy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CartDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_CART = "Create table Cart("
            + "id integer primary key autoincrement,"
            + "name text," // 商品名
            + "price real," // 价格
            + "type text," // 种类
            + "image text," // 图片
            + "count real," // 商品个数
            + "username text)"; // 用户名
    public CartDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insertCart(String name, double price, String type, String image, int count, String username) {
        // 获取一个可写的数据库对象
        SQLiteDatabase db = this.getWritableDatabase();
        // 创建一个ContentValues对象
        ContentValues values = new ContentValues();
        // 将值放入ContentValues对象中
        values.put("name", name);
        values.put("price", price);
        values.put("type", type);
        values.put("image", image);
        values.put("count", count);
        values.put("username", username);
        // 将值插入到Cart表中
        long cartId = db.insert("Cart", null, values); // 返回购物车id
        // 关闭数据库对象
        db.close();
    }
    public boolean queryCart(String name, String username) {
        // 获取一个可读的数据库对象
        SQLiteDatabase db = this.getReadableDatabase();
        // 查询Cart表中，name和username都匹配的记录
        Cursor cursor = db.query("Cart", null, "name = ? and username = ?", new String[]{name, username}, null, null, null);
        // 判断Cursor对象是否为空，以及是否有数据
        if (cursor != null && cursor.getCount() > 0) {
            // 如果不为空，且有数据，说明Cart表中存在name和username都匹配的记录，返回true
            cursor.close();
            db.close();
            return true;
        } else {
            // 如果为空，或者没有数据，说明Cart表中不存在name和username都匹配的记录，返回false
            cursor.close();
            db.close();
            return false;
        }
    }
    public int updateCart(String name, String username, int count) {
        // 获取一个可写的数据库对象
        SQLiteDatabase db = this.getWritableDatabase();
        // 创建一个ContentValues对象
        ContentValues values = new ContentValues();
        // 将count参数的值放入ContentValues对象中
        values.put("count", count);
        // 定义一个变量，用于存储返回的行数
        int rows = 0;
        // 判断count是否等于0
        if (count == 0) {
            // 如果等于0，就删除对应的表项
            rows = db.delete("Cart", "name = ? and username = ?", new String[]{name, username});
        } else {
            // 如果不等于0，就更新对应的表项
            rows = db.update("Cart", values, "name = ? and username = ?", new String[]{name, username});
        }
        // 关闭数据库对象
        db.close();
        // 返回更新的行数
        return rows;
    }

    public void deleteCart(String name, String username) {
        // 获取一个可写的数据库对象
        SQLiteDatabase db = this.getWritableDatabase();
        // 删除Cart表中，name和username都匹配的记录
        int rows = db.delete("Cart", "name = ? and username = ?", new String[]{name, username});
        // 关闭数据库对象
        db.close();
    }
    //定义一个方法，根据用户名删除Cart表中的数据
    public void deleteCart(String username) {
        // 获取一个可写的数据库对象
        SQLiteDatabase db = this.getWritableDatabase();
        // 删除Cart表中，username匹配的所有记录
        int rows = db.delete("Cart", "username = ?", new String[]{username});
        // 关闭数据库对象
        db.close();
    }
}
