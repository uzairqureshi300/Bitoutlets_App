package com.bitoutlets_app.Profile_fragments;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Constants;
import com.bitoutlets_app.Database.AndroidOpenDbHelper;
import com.bitoutlets_app.Model_classes.Fetch_class;
import com.bitoutlets_app.Model_classes.Product_class;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Recycler_Adapters.Product_recyclerView;
import com.bitoutlets_app.Recycler_Adapters.Whishlist_recyclerView_adapter;
import com.bitoutlets_app.Volley_Singleton.MySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Whishlist_Fragment extends Fragment implements View.OnClickListener
{
    private RecyclerView whishlist;
    private ArrayList<Product_class> pro_list=new ArrayList<Product_class>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v =  inflater.inflate(R.layout.whishlist_fragment, container, false);
        whishlist=(RecyclerView)v.findViewById(R.id.whishlist_view);
        get_data();
        return v;
    }
    private void get_data() {

        String First_name = "";
        String types = "";
        AndroidOpenDbHelper androidOpenDbHelperObj = new AndroidOpenDbHelper(getActivity());
        SQLiteDatabase sqliteDatabase = androidOpenDbHelperObj.getReadableDatabase();
        Cursor cursor = sqliteDatabase.query(AndroidOpenDbHelper.TABLE_NAME_Cart, null, null, null, null, null, null);
        getActivity().startManagingCursor(cursor);
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_add)).equals("whishlist")) {

                Product_class product_class = new Product_class();
                product_class.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                product_class.setProduct_id(cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_id)));
                product_class.setTitle(cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_title)));
                product_class.setPrice(cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_price)));
                product_class.setShipping_cost(cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_shipping)));
                product_class.setFeatures(cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_features)));
                product_class.setTags(cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_tags)));
                product_class.setCurrent_stock(cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_current_stock)));
                product_class.setNo_of_views(cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_no_of_views)));
                product_class.setDiscount(cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_discount)));
                product_class.setTax(cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_tax)));
                product_class.setDescription(cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_description)));
                product_class.setImage(cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_image)));
                product_class.setUnit(cursor.getString(cursor.getColumnIndex(AndroidOpenDbHelper.product_unit)));

                pro_list.add(product_class);
            }


            Whishlist_recyclerView_adapter whishlist_recyclerView_adapter = new
                    Whishlist_recyclerView_adapter(getActivity(), pro_list);

            RecyclerView.LayoutManager		mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            whishlist.setLayoutManager(mLayoutManager);
            whishlist.setAdapter(whishlist_recyclerView_adapter);

        }
    }


    @Override
    public void onClick(View view) {

    }
}
