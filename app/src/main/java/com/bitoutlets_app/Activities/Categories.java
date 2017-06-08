package com.bitoutlets_app.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Model_classes.Category_class;
import com.bitoutlets_app.Model_classes.SubCategory_Class;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Recycler_Adapters.Categories_recyclerView;
import com.bitoutlets_app.Recycler_Adapters.RecyclerItemClickListener;
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

public class Categories extends AppCompatActivity implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {
    private ProgressDialog mProgressDialog;
    private List<Category_class> category=new ArrayList<Category_class>();
    private List<SubCategory_Class> sub_category=new ArrayList<SubCategory_Class>();
    private RecyclerView category_list;
    private Category_Singletons category_singletons;
    private Categories_recyclerView categories_recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        category_list=(RecyclerView)findViewById(R.id.category_list);
        category_singletons = Category_Singletons.getInstance();
        category_singletons.setCategory_list();
        Cat();
    }

    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(Categories.this, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setMessage("Please Wait..");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void Cat() {
        showProgressDialog();
        try {
            JSONObject json = new JSONObject();
            json.put("token", "19583803275");
            json.put("user_id", "15");

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
            MySingleton.getInstance(getApplication()).addToRequestQueue(jsObjRequest);
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
            mProgressDialog.dismiss();
            Gson gson = new Gson();
            JSONArray array = response.optJSONArray("category");
            for (int i = 0; i < array.length(); i++) {
              JSONObject jsonObject = array.getJSONObject(i);
                Category_class category_class = gson.fromJson(jsonObject.toString(), Category_class.class);
                category.add(category_class);

            }
            categories_recyclerView = new Categories_recyclerView(this, category);
            RecyclerView.LayoutManager		mLayoutManager = new GridLayoutManager(this,3);
        //    uGraduateNamesListView.addItemDecoration(new SimpleDividerItemDecoration(this));
            category_list.setLayoutManager(mLayoutManager);
            category_list.setAdapter(categories_recyclerView);
            list_click_listener(category);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
    private void  list_click_listener(final List<Category_class> categories_list){

        category_list.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        Log.e("list",categories_list.get(i).getSub_category().size()+"");
                        Category_class movieModel = categories_list.get(i); // getting the model
                        Intent intent = new Intent(Categories.this, Sub_Cat.class);
                        intent.putExtra("movieModel", new Gson().toJson(movieModel)); // converting model json into string type and sending it via intent
                        startActivity(intent);

                    }
                })
        );


    }

}
