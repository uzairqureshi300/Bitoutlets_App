package com.bitoutlets_app.Categories_Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Activities.Categories;
import com.bitoutlets_app.Activities.MainActivity;
import com.bitoutlets_app.Constants;
import com.bitoutlets_app.Model_classes.Category_class;
import com.bitoutlets_app.Model_classes.Product_class;
import com.bitoutlets_app.Model_classes.SubCategory_Class;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Recycler_Adapters.RecyclerItemClickListener;
import com.bitoutlets_app.Recycler_Adapters.SimpleDividerItemDecoration;
import com.bitoutlets_app.Recycler_Adapters.Sub_Categories_recyclerView;
import com.bitoutlets_app.Singletons.Category_Singletons;
import com.bitoutlets_app.Singletons.Product_Singletons;
import com.bitoutlets_app.Volley_Singleton.MySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.bitoutlets_app.Constants.transition_value;


public class Sub_Categories_Fragment extends Fragment
{  private RecyclerView sub_category_list;
    private String Sub_id;
    private Product_Singletons product_singletons;
    private ProgressBar progressBar;

    private Sub_Categories_recyclerView sub_categories_recyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Constants.back_value=0;

        View v =  inflater.inflate(R.layout.categories_fragment, container, false);
        sub_category_list=(RecyclerView)v.findViewById(R.id.category_list);
        progressBar=(ProgressBar)v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        sub_category_list.setVisibility(View.VISIBLE);
        transition_value=2;
        SharedPreferences share=getActivity().getSharedPreferences("CAT", Context.MODE_PRIVATE);
        String data=share.getString("movieModel","no");
        final Category_class movieModel = new Gson().fromJson(data, Category_class.class);
        sub_categories_recyclerView = new Sub_Categories_recyclerView(getActivity(), movieModel.getSub_category());

        RecyclerView.LayoutManager		mLayoutManager = new GridLayoutManager(getActivity(),3);

        sub_category_list.setLayoutManager(mLayoutManager);
        sub_categories_recyclerView.notifyDataSetChanged();
        sub_category_list.setAdapter(sub_categories_recyclerView);


        sub_category_list.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        Constants.product_fragment_value=0;
                        Constants.sub_cat_id= movieModel.getSub_category().get(i).getId();
                        ((Categories)getActivity()).Integrate_fragments(3);
                   }
                })
        );
        product_singletons=Product_Singletons.getInstance();
        product_singletons.setProduct_list();

        return v;
    }

}
