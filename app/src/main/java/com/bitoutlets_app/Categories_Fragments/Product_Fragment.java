package com.bitoutlets_app.Categories_Fragments;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Activities.Categories;
import com.bitoutlets_app.Constants;
import com.bitoutlets_app.Model_classes.Category_class;
import com.bitoutlets_app.Model_classes.Product_class;
import com.bitoutlets_app.Model_classes.SubCategory_Class;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Recycler_Adapters.Product_recyclerView;
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


public class Product_Fragment extends Fragment implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener
{
    private ProgressBar progressBar;
    private RecyclerView products_list;
    private Product_Singletons product_singletons;
    private Product_recyclerView product_recyclerView;
    private List<Product_class> product=new ArrayList<Product_class>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Constants.back_value=0;
        transition_value=3;
        View v =  inflater.inflate(R.layout.categories_fragment, container, false);
        progressBar=(ProgressBar)v.findViewById(R.id.progress_bar);
        products_list=(RecyclerView)v.findViewById(R.id.category_list);

        progressBar.setVisibility(View.VISIBLE);
        products_list.setVisibility(View.GONE);
        product_singletons=Product_Singletons.getInstance();
        if(Constants.product_fragment_value==0) {
            product_singletons.setProduct_list();
            get_Products(Constants.sub_cat_id);
        }
        if(Constants.product_fragment_value==1) {
            Log.e("save","dsdsa");
            product=product_singletons.getProduct_list();
            product_recyclerView = new Product_recyclerView(getActivity(), product);

            RecyclerView.LayoutManager		mLayoutManager = new GridLayoutManager(getActivity(),3);
            products_list.setLayoutManager(mLayoutManager);
            product_recyclerView.notifyDataSetChanged();
            products_list.setAdapter(product_recyclerView);
            progressBar.setVisibility(View.GONE);
            products_list.setVisibility(View.VISIBLE);
            click_listener(product);
        }




        return v;
    }
    private void get_Products(String id){
        try {
            JSONObject json = new JSONObject();
            json.put("token", Constants.token);
            json.put("user_id", Constants.user_id);
            json.put("sub_category_id", id);

            JSONObject json2 = new JSONObject();
            json2.put("to", "bitoutlet");
            json2.put("methods", "fetch_products");
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
        String logo;
        Log.e("products",response.toString());
        try{
            JSONArray array = response.optJSONArray("product");
            if(array!=null) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    Product_class product_class = new Product_class();

                    logo = jsonObject.getJSONObject("images").getString("name");
                    product_class.setDescription(jsonObject.getString("description"));

                    product_class.setImage(logo);
                    product_class.setProduct_id(jsonObject.getString("product_id"));
                    product_class.setTitle(jsonObject.getString("title"));
                    product_class.setPrice(jsonObject.getString("price"));
                    product_class.setShipping_cost(jsonObject.getJSONObject("shipping_cost").getString("Pakistan"));
                    product_class.setFeatures(jsonObject.getString("features"));
                    product_class.setTags(jsonObject.getString("tags"));
                    product_class.setUnit(jsonObject.getString("unit"));
                    product_class.setCurrent_stock(jsonObject.getString("current_stock"));
                    product_class.setDiscount(jsonObject.getString("discount"));
                    product_class.setTax(jsonObject.getString("tax"));
                    product_class.setDescription(jsonObject.getString("description"));
                    product.add(product_class);
                    product_singletons.addPart(product_class);
                }
            }
            else{
                Toast.makeText(getActivity(), "no data", Toast.LENGTH_SHORT).show();
            }
            product_recyclerView = new Product_recyclerView(getActivity(), product);

            RecyclerView.LayoutManager		mLayoutManager = new GridLayoutManager(getActivity(),3);
            products_list.setLayoutManager(mLayoutManager);
            product_recyclerView.notifyDataSetChanged();
            products_list.setAdapter(product_recyclerView);
            progressBar.setVisibility(View.GONE);
            products_list.setVisibility(View.VISIBLE);
            click_listener(product);
        }catch(Exception ex){
            Log.e("Exception",ex.toString());
        }
    }
    private void click_listener(final List<Product_class> list){

        products_list.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                  Constants.product_images= list.get(i).getImage();
                        Constants.product_id= list.get(i).getProduct_id();
                        Constants.product_title= list.get(i).getTitle();
                        Constants.product_price= list.get(i).getPrice();
                        Constants.product_shippingcost= list.get(i).getShipping_cost();
                        Constants.product_features= list.get(i).getFeatures();
                        Constants.product_tags= list.get(i).getTags();
                        Constants.product_unit= list.get(i).getUnit();
                        Constants.product_current_stock= list.get(i).getCurrent_stock();
                        Constants.product_discount= list.get(i).getDiscount();
                        Constants.product_tax= list.get(i).getTax();
                        Constants.product_description= list.get(i).getDescription();
                        ((Categories)getActivity()).Integrate_fragments(4);
                     }
                })

        );


    }

}
