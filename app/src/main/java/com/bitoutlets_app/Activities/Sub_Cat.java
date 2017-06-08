package com.bitoutlets_app.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Model_classes.Category_class;
import com.bitoutlets_app.Model_classes.SubCategory_Class;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Recycler_Adapters.Categories_recyclerView;
import com.bitoutlets_app.Recycler_Adapters.RecyclerItemClickListener;
import com.bitoutlets_app.Recycler_Adapters.Sub_Categories_recyclerView;
import com.bitoutlets_app.Singletons.Category_Singletons;
import com.bitoutlets_app.Volley_Singleton.MySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uzair on 06/06/2017.
 */

public class Sub_Cat extends AppCompatActivity {
    private List<Category_class> category=new ArrayList<Category_class>();
    private List<SubCategory_Class> sub_category=new ArrayList<SubCategory_Class>();
    private RecyclerView sub_category_list;
    private Category_Singletons category_singletons;
    private Sub_Categories_recyclerView sub_categories_recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        sub_category_list=(RecyclerView)findViewById(R.id.category_list);
        Bundle bundle = getIntent().getExtras();

        Intent i=getIntent();
      String data=bundle.getString("movieModel");
        Category_class movieModel = new Gson().fromJson(data, Category_class.class);
            Log.e("SIZE",movieModel.getSub_category().size()+"");
        sub_categories_recyclerView = new Sub_Categories_recyclerView(this, movieModel.getSub_category());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
            //    uGraduateNamesListView.addItemDecoration(new SimpleDividerItemDecoration(this));
            sub_category_list.setLayoutManager(mLayoutManager);
            sub_categories_recyclerView.notifyDataSetChanged();
            sub_category_list.setAdapter(sub_categories_recyclerView);


        }


    }











