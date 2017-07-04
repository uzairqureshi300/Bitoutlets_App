package com.bitoutlets_app.Categories_Fragments;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Activities.Categories;
import com.bitoutlets_app.Constants;
import com.bitoutlets_app.Model_classes.Category_class;
import com.bitoutlets_app.Model_classes.SubCategory_Class;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Recycler_Adapters.Categories_recyclerView;
import com.bitoutlets_app.Recycler_Adapters.RecyclerItemClickListener;
import com.bitoutlets_app.Recycler_Adapters.SimpleDividerItemDecoration;
import com.bitoutlets_app.Singletons.Category_Singletons;
import com.bitoutlets_app.Volley_Singleton.MySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.bitoutlets_app.Constants.transition_value;


public class Category_Fragment extends Fragment  implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener
{
    private ProgressDialog mProgressDialog;
    private List<Category_class> category=new ArrayList<Category_class>();
    private List<SubCategory_Class> sub_category=new ArrayList<SubCategory_Class>();
    private RecyclerView category_list;
    private Category_Singletons category_singletons;
    private Categories_recyclerView categories_recyclerView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        transition_value=1;
  View v =  inflater.inflate(R.layout.categories_fragment, container, false);
        category_list=(RecyclerView)v.findViewById(R.id.category_list);
        progressBar=(ProgressBar)v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        category_list.setVisibility(View.GONE);
        category_singletons = Category_Singletons.getInstance();
        Constants.back_value=1;
        if(Constants.fragment_value==0) {
            category_singletons.setCategory_list();
        }
        if(Constants.fragment_value==1){

            category=category_singletons.getCategory_list();
            progressBar.setVisibility(View.GONE);
            category_list.setVisibility(View.VISIBLE);
             categories_recyclerView = new Categories_recyclerView(getActivity(), category);

            RecyclerView.LayoutManager		mLayoutManager = new GridLayoutManager(getActivity(),3);
            category_list.setLayoutManager(mLayoutManager);
            category_list.setAdapter(categories_recyclerView);
            list_click_listener(category);
         }
        else {
            Cat();
        }
        return v;
    }

    private void Cat() {
        try {
            JSONObject json = new JSONObject();
            json.put("token", Constants.token);
            json.put("user_id", Constants.user_id);

            JSONObject json2 = new JSONObject();
            json2.put("to", "bitoutlet");
            json2.put("methods", "fetch_category");
            json2.accumulate("complex", json);
            String url = "http://orupartners.com/cp/redirect_to.php";
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, json2, this, this) {

            };
            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Log.e("response", response.toString());
        try {

            Gson gson = new Gson();
            JSONArray array = response.optJSONArray("category");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                Category_class category_class = gson.fromJson(jsonObject.toString(), Category_class.class);
                category.add(category_class);
                category_singletons.addPart(category_class);

            }
            progressBar.setVisibility(View.GONE);
            category_list.setVisibility(View.VISIBLE);
            categories_recyclerView = new Categories_recyclerView(getActivity(), category);
            RecyclerView.LayoutManager		mLayoutManager = new GridLayoutManager(getActivity(),3);
           category_list.setLayoutManager(mLayoutManager);
            category_list.setAdapter(categories_recyclerView);
            list_click_listener(category);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
    private void  list_click_listener(final List<Category_class> categories_list){

        category_list.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        Category_class movieModel = categories_list.get(i); // getting the model
                        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("CAT", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("movieModel", new Gson().toJson(movieModel));
                        editor.commit();
                        ((Categories)getActivity()).Integrate_fragments(2);


                    }
                })
        );


    }
}
