package com.bitoutlets_app.Categories_Fragments;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Constants;
import com.bitoutlets_app.Database.AndroidOpenDbHelper;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Volley_Singleton.MySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.bitoutlets_app.Constants.product_title;
import static com.bitoutlets_app.Constants.transition_value;


public class Product_detail_Fragment extends Fragment  implements View.OnClickListener {
    private TextView description,price,title,tags,discount,stock;
    private TextView quantity;
    private SharedPreferences sharedPreferences;
    private ImageView image,add_cart,whish_list,compare,plus,minus;
    private int database_value=0;
    private int count=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        transition_value=4;
        sharedPreferences=getActivity().getSharedPreferences("CART", Context.MODE_PRIVATE);

        View v = inflater.inflate(R.layout.details_fragment, container, false);
        description=(TextView)v.findViewById(R.id.description);
        price=(TextView)v.findViewById(R.id.product_price);
        title=(TextView)v.findViewById(R.id.product_title);
        image=(ImageView) v.findViewById(R.id.product_image);
        add_cart=(ImageView)v.findViewById(R.id.add_cart);
        discount=(TextView)v.findViewById(R.id.discount);
        stock=(TextView)v.findViewById(R.id.stock);
        plus=(ImageView) v.findViewById(R.id.plus);
        minus=(ImageView) v.findViewById(R.id.minus);
        whish_list=(ImageView)v.findViewById(R.id.whislist);
        compare=(ImageView)v.findViewById(R.id.compare);

        add_cart.setOnClickListener(this);
        whish_list.setOnClickListener(this);
        compare.setOnClickListener(this);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        Product_details();


        Picasso.with(getActivity()).load(Constants.product_images).
                transform(new RoundedCornersTransformation(15, 0,RoundedCornersTransformation.CornerType.ALL))
                .placeholder(R.drawable.default_avatar).into(image);
        get_Id(Constants.product_id);
        return v;
    }
    private void Product_details(){
        count =    Integer.parseInt(Constants.product_current_stock);
        description.setText(Constants.product_description);
       price.setText(Constants.product_price);
        title.setText(Constants.product_title);
        stock.setText(Constants.product_current_stock);
        discount.setText(Constants.product_discount);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_cart:
            database_value=1;
                insert_data();
                break;
            case R.id.whislist:
                database_value=2;
                insert_data();
                break;

            case R.id.compare:
                database_value=3;
                insert_data();
                break;
            case R.id.plus:
                count++;
                stock.setText(count+"");
                break;
            case R.id.minus:
                count--;
                if(count<0){
                 count=1;
                }
                stock.setText(count+"");
                break;

        }


    }
    public void insert_data(){

        // First we have to open our DbHelper class by creating a new object of that
        AndroidOpenDbHelper androidOpenDbHelperObj = new AndroidOpenDbHelper(getActivity());

        // Then we need to get a writable SQLite database, because we are going to insert some values
        // SQLiteDatabase has methods to create, delete, execute SQL commands, and perform other common database management tasks.
        SQLiteDatabase sqliteDatabase = androidOpenDbHelperObj.getWritableDatabase();

        // ContentValues class is used to store a set of values that the ContentResolver can process.
        ContentValues contentValues = new ContentValues();

        // Get values from the POJO class and passing them to the ContentValues class
        contentValues.put(AndroidOpenDbHelper.product_image,Constants.product_images );
        contentValues.put(AndroidOpenDbHelper.product_id, Constants.product_id);
        contentValues.put(AndroidOpenDbHelper.product_title, Constants.product_title);
        contentValues.put(AndroidOpenDbHelper.product_price, Constants.product_price);
        contentValues.put(AndroidOpenDbHelper.product_shipping, Constants.product_shippingcost);
        contentValues.put(AndroidOpenDbHelper.product_features,Constants.product_features);
        contentValues.put(AndroidOpenDbHelper.product_tags, Constants.product_tags);
        contentValues.put(AndroidOpenDbHelper.product_unit, Constants.product_unit);
        contentValues.put(AndroidOpenDbHelper.product_current_stock ,Constants.product_current_stock);
        contentValues.put(AndroidOpenDbHelper.product_tax, Constants.product_tax);
        contentValues.put(AndroidOpenDbHelper.product_description, Constants.product_description);

        long affectedColumnId = 0;
        if(database_value==1) {
            // Now we can insert the data in to relevant table
            // I am going pass the id value, which is going to change because of our insert method, to a long variable to show in Toast
             affectedColumnId = sqliteDatabase.insert(AndroidOpenDbHelper.TABLE_NAME_Cart, null, contentValues);
            add_cart.setEnabled(false);
        }
        else if(database_value==2){

            affectedColumnId = sqliteDatabase.insert(AndroidOpenDbHelper.TABLE_NAME_Whish, null, contentValues);
            whish_list.setEnabled(false);
        }
        else if(database_value==3){

            affectedColumnId = sqliteDatabase.insert(AndroidOpenDbHelper.TABLE_NAME_Compare, null, contentValues);
            compare.setEnabled(false);
        }
        // It is a good practice to close the database connections after you have done with it
        sqliteDatabase.close();

        // I am not going to do the retrieve part in this post. So this is just a notification for satisfaction ;-)
        Toast.makeText(getActivity(), "Values inserted column ID is :" + affectedColumnId, Toast.LENGTH_SHORT).show();

    }
    private void get_Id(String id) {
        List<String> list_id=new ArrayList<String>();
        List<String> list_whish=new ArrayList<String>();
        List<String> list_compare=new ArrayList<String>();

        AndroidOpenDbHelper openHelperClass = new AndroidOpenDbHelper(getActivity());

        SQLiteDatabase sqliteDatabase = openHelperClass.getReadableDatabase();

        Cursor cursor = sqliteDatabase.query(AndroidOpenDbHelper.TABLE_NAME_Cart, null, null, null, null, null, null);
        getActivity().startManagingCursor(cursor);
        while (cursor.moveToNext()) {
            Log.e("count", cursor.toString());
            String First_name = cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_id));
            list_id.add(First_name);
        }
       for(int i=0;i<list_id.size();i++){
           if(id.equals(list_id.get(i))){
               add_cart.setEnabled(false);
           }
       }
        cursor = sqliteDatabase.query(AndroidOpenDbHelper.TABLE_NAME_Whish, null, null, null, null, null, null);
        getActivity().startManagingCursor(cursor);
        while (cursor.moveToNext()) {
            Log.e("count", cursor.toString());
            String First_name = cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_id));
            list_whish.add(First_name);
        }
        for(int i=0;i<list_whish.size();i++){
            if(id.equals(list_whish.get(i))){

                whish_list.setEnabled(false);
            }
        }
        cursor = sqliteDatabase.query(AndroidOpenDbHelper.TABLE_NAME_Compare, null, null, null, null, null, null);
        getActivity().startManagingCursor(cursor);
        while (cursor.moveToNext()) {
            Log.e("count", cursor.toString());
            String First_name = cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_id));
            list_compare.add(First_name);
        }
        for(int i=0;i<list_compare.size();i++){
            if(id.equals(list_compare.get(i))){

                compare.setEnabled(false);
            }
        }
    }
}
