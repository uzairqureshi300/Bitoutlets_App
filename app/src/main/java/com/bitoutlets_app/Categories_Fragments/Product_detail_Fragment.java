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
    private String product_type;
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
                get_Id(Constants.product_id,"cart");
                break;
            case R.id.whislist:
                get_Id(Constants.product_id,"whishlist");
                break;

            case R.id.compare:
                get_Id(Constants.product_id,"compare");
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
    public void insert_data(String type){
       AndroidOpenDbHelper androidOpenDbHelperObj = new AndroidOpenDbHelper(getActivity());
        SQLiteDatabase sqliteDatabase = androidOpenDbHelperObj.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
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
        contentValues.put(AndroidOpenDbHelper.product_add, type);

        long affectedColumnId = 0;
             affectedColumnId = sqliteDatabase.insert(AndroidOpenDbHelper.TABLE_NAME_Cart, null, contentValues);

        // I am not going to do the retrieve part in this post. So this is just a notification for satisfaction ;-)
        Toast.makeText(getActivity(), "Values inserted column ID is :" + affectedColumnId, Toast.LENGTH_SHORT).show();
        Log.e("type",type);

    }


    private void get_Id(String id,String add_type) {

        AndroidOpenDbHelper openHelperClass = new AndroidOpenDbHelper(getActivity());
       String First_name="";
        String type="";
        SQLiteDatabase sqliteDatabase = openHelperClass.getReadableDatabase();

        Cursor cursor = sqliteDatabase.query(AndroidOpenDbHelper.TABLE_NAME_Cart, null, null, null, null, null, null);
        getActivity().startManagingCursor(cursor);
        while (cursor.moveToNext()) {
            Log.e("count", cursor.toString());
            First_name = cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_id));
             type = cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_add));

            if(First_name.equals(id )&& type.equals(add_type)){
                Toast.makeText(getActivity(), "Already added", Toast.LENGTH_SHORT).show();
           break;
            }
        }

        if(!First_name.equals(id) || !type.equals(add_type)){
            insert_data(add_type);
        }


    }
}
