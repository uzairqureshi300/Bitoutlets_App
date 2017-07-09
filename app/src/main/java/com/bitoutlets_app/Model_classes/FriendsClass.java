package com.bitoutlets_app.Model_classes;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bitoutlets_app.Constants;
import com.bitoutlets_app.Database.AndroidOpenDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uzair on 7/1/2017.
 */

public class FriendsClass {
    public static List<String> lsiting=new ArrayList<String>();
     public  static Context c;
    public FriendsClass(Context context) {
        this.c = context;
    }

  public static List<Fetch_class> load_db(Context c){
      String pro_id="";
        AndroidOpenDbHelper androidOpenDbHelperObj = new AndroidOpenDbHelper(c);
        SQLiteDatabase sqliteDatabase = androidOpenDbHelperObj.getReadableDatabase();
        Cursor cursor = sqliteDatabase.query(AndroidOpenDbHelper.TABLE_NAME_Cart, null, null, null, null, null, null);
        ((Activity)c).startManagingCursor(cursor);
        while (cursor.moveToNext()) {
                Fetch_class fetch_class=new Fetch_class();
             fetch_class.setId(cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_id)));
            fetch_class.setType(cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_add)));
            Log.e("count",cursor.getLong(cursor.getColumnIndex("_id"))+"");
            Constants.db_list.add(fetch_class);

        }
        return Constants.db_list;
    }
}
