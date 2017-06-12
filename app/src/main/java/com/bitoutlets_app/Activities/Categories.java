package com.bitoutlets_app.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Categories_Fragments.Category_Fragment;
import com.bitoutlets_app.Categories_Fragments.Product_Fragment;
import com.bitoutlets_app.Categories_Fragments.Product_detail_Fragment;
import com.bitoutlets_app.Categories_Fragments.Sub_Categories_Fragment;
import com.bitoutlets_app.Constants;
import com.bitoutlets_app.Model_classes.Category_class;
import com.bitoutlets_app.Model_classes.SubCategory_Class;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Recycler_Adapters.Categories_recyclerView;
import com.bitoutlets_app.Recycler_Adapters.RecyclerItemClickListener;
import com.bitoutlets_app.Singletons.Category_Singletons;
import com.bitoutlets_app.User_Credentials_Fragments.SignUp_Fragment;
import com.bitoutlets_app.User_Credentials_Fragments.Welcome_Fragment;
import com.bitoutlets_app.Volley_Singleton.MySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.bitoutlets_app.Constants.transition_value;


public class Categories extends AppCompatActivity implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {
    private Fragment fragment = null;
    private Toolbar toolbar;
    private TextView toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar_title=(TextView)findViewById(R.id.title_toolbar);

        Integrate_fragments(1);
        toolbar();
    }
    private void toolbar(){
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        ab.setDisplayShowTitleEnabled(false);

    }
    public void Integrate_fragments(int number) {
        if (number == 1) {
            toolbar_title.setText("Categories");

            fragment = new Category_Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_right,0);
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        if (number == 2) {
            toolbar_title.setText("Sub_Categories");

            fragment = new Sub_Categories_Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_right,0);
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        if (number == 3) {
            toolbar_title.setText("Products");

            fragment = new Product_Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_right,0);
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        if (number == 4) {
            toolbar_title.setText("Products Details");

            fragment = new Product_detail_Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_right,0);
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (  transition_value == 4) {
            Log.e("error", getSupportFragmentManager().getBackStackEntryCount() + "");
            toolbar_title.setText("Product");
            Constants.product_fragment_value=1;
            fragment = new Product_Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
            if (  transition_value == 3) {
                Constants.fragment_value = 1;
                Log.e("error", getSupportFragmentManager().getBackStackEntryCount() + "");
                toolbar_title.setText("Sub_Categories");

                fragment = new Sub_Categories_Fragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                ft.replace(R.id.content_frame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
             if(transition_value==2){
                 Log.e("error", getSupportFragmentManager().getBackStackEntryCount() + "");
                 toolbar_title.setText("Categories");

                 fragment = new Category_Fragment();
                 FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                 ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                 ft.replace(R.id.content_frame, fragment);
                 ft.addToBackStack(null);
                 ft.commit();

             }
        if(transition_value==1){
            Log.e("error", getSupportFragmentManager().getBackStackEntryCount() + "");
            finish();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (Constants.back_value == 1) {
                    finish();
                } else {
                    if (  transition_value == 4) {
                        Log.e("error", getSupportFragmentManager().getBackStackEntryCount() + "");
                        toolbar_title.setText("Product");
                        Constants.product_fragment_value=1;
                        fragment = new Product_Fragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        ft.replace(R.id.content_frame, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                    if (  transition_value == 3) {
                        Constants.fragment_value = 1;
                        Log.e("error", getSupportFragmentManager().getBackStackEntryCount() + "");
                        toolbar_title.setText("Sub_Categories");

                        fragment = new Sub_Categories_Fragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        ft.replace(R.id.content_frame, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                    if(transition_value==2){
                        Log.e("error", getSupportFragmentManager().getBackStackEntryCount() + "");
                        toolbar_title.setText("Categories");

                        fragment = new Category_Fragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        ft.replace(R.id.content_frame, fragment);
                        ft.addToBackStack(null);
                        ft.commit();

                    }
                    if(transition_value==1){
                        Log.e("error", getSupportFragmentManager().getBackStackEntryCount() + "");
                        finish();

                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}