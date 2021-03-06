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

import static com.bitoutlets_app.Constants.product_id;
import static com.bitoutlets_app.Constants.transition_value;


public class Product_Fragment extends Fragment implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {
    private ProgressBar progressBar, foot;
    private RecyclerView products_list;
    private Product_Singletons product_singletons;
    private Product_recyclerView product_recyclerView;
    List<Product_class> product;
    private int offset = 0, limit = 15;
    public View ftView;
    private boolean loading = true;
    public int currentId = 11;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    GridLayoutManager mLayoutManager;
    int s = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Constants.back_value = 0;
        transition_value = 3;
        View v = inflater.inflate(R.layout.categories_fragment, container, false);
        ftView = inflater.inflate(R.layout.footer_view, null);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        products_list = (RecyclerView) v.findViewById(R.id.category_list);
        foot = (ProgressBar) v.findViewById(R.id.foot);
        progressBar.setVisibility(View.GONE);
        products_list.setVisibility(View.VISIBLE);

        mLayoutManager = new GridLayoutManager(getActivity(), 3);


        products_list.setLayoutManager(mLayoutManager);

        product_singletons = Product_Singletons.getInstance();
        if (Constants.product_fragment_value == 0) {
            product_singletons.setProduct_list();
            get_Products(Constants.sub_cat_id, offset);
        }
        if (Constants.product_fragment_value == 1) {
            Log.e("save", "dsdsa");
            product = product_singletons.getProduct_list();
            product_recyclerView = new Product_recyclerView(getActivity(), product);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            products_list.setLayoutManager(mLayoutManager);
            product_recyclerView.notifyDataSetChanged();
            products_list.setAdapter(product_recyclerView);
            progressBar.setVisibility(View.GONE);
            products_list.setVisibility(View.VISIBLE);
            click_listener(product);
        }


        return v;
    }

    @Override
    public void onResponse(JSONObject response) {
        String logo;
        Log.e("products", response.toString());

        try {

            product = new ArrayList<Product_class>();
            totalItemCount = response.getInt("total");
            if (visibleItemCount != totalItemCount) {
                products_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        currentId = 12;

                        int lastvisibleitemposition = mLayoutManager.findLastVisibleItemPosition();
                        if (visibleItemCount == product_recyclerView.getItemCount()) {
                            loading = true;
                            if (visibleItemCount == totalItemCount) {
                                Toast.makeText(getActivity(), lastvisibleitemposition + "", Toast.LENGTH_SHORT).show();
                                loading = false;
                            }
                            if (loading == true) {


                                foot.setVisibility(View.VISIBLE);
                                offset = offset + 10;

                                get_Products(Constants.sub_cat_id, offset);
                            }
                            Log.e("ODDDD", offset + "");


                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        if (dy > 15) //check for scroll down
                        {


//                    if(visibleItemCount==totalItemCount) {
//                        foot.setVisibility(View.GONE);
//                        Toast.makeText(getActivity(), "Eend", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        if(((GridLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition()==visibleItemCount-1) {
//                            offset = offset + 10;
//                            foot.setVisibility(View.VISIBLE);
//                            get_Products(Constants.sub_cat_id, offset);
//
//                        }
//                    }

                        }
                    }
                });


            }


            JSONArray array = response.optJSONArray("product");
            Log.e("total", array.length() + "");

            if (array != null) {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    Product_class product_class = new Product_class();

                    logo = jsonObject.getString("image");
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
                    foot.setVisibility(View.GONE);
                    product_singletons.addPart(product_class);
                }
                visibleItemCount = visibleItemCount + product.size();
            } else {
                Toast.makeText(getActivity(), "no data", Toast.LENGTH_SHORT).show();
            }
            if (currentId == 12) {


                product_recyclerView.addListItemToAdapter((product));
                product_recyclerView.notifyDataSetChanged();

            } else {
                product_recyclerView = new Product_recyclerView(getActivity(), product);

                products_list.setAdapter(product_recyclerView);

            }
            progressBar.setVisibility(View.GONE);
            products_list.setVisibility(View.VISIBLE);
            click_listener(product);

        } catch (Exception ex) {
            Log.e("Exception", ex.toString());
        }
    }


    private void get_Products(String id, int offset) {
        try {
            JSONObject json = new JSONObject();
            json.put("token", Constants.token);
            json.put("user_id", Constants.user_id);
            json.put("subcategory", id);
            json.put("limit", limit + "");
            json.put("offset", offset + "");

            JSONObject json2 = new JSONObject();
            json2.put("to", "bitoutlet");
            json2.put("methods", "searches");
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


    private void click_listener(final List<Product_class> list) {

        products_list.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        Constants.product_images = list.get(i).getImage();
                        Constants.product_id = list.get(i).getProduct_id();
                        Constants.product_title = list.get(i).getTitle();
                        Constants.product_price = list.get(i).getPrice();
                        Constants.product_shippingcost = list.get(i).getShipping_cost();
                        Constants.product_features = list.get(i).getFeatures();
                        Constants.product_tags = list.get(i).getTags();
                        Constants.product_unit = list.get(i).getUnit();
                        Constants.product_current_stock = list.get(i).getCurrent_stock();
                        Constants.product_discount = list.get(i).getDiscount();
                        Constants.product_tax = list.get(i).getTax();
                        Constants.product_description = list.get(i).getDescription();
                        ((Categories) getActivity()).Integrate_fragments(4);
                    }
                })
        );
    }


}
