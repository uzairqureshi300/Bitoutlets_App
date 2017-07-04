package com.bitoutlets_app.Profile_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Constants;
import com.bitoutlets_app.Model_classes.Product_class;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Recycler_Adapters.Categories_recyclerView;
import com.bitoutlets_app.Recycler_Adapters.Featured_Product_recyclerView_Adapters;
import com.bitoutlets_app.Recycler_Adapters.Recent_Mostviewed_Product_recyclerView_Adapters;
import com.bitoutlets_app.ViewPager.ViewPagerAdapter;
import com.bitoutlets_app.Volley_Singleton.MySingleton;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Featured_Fragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener
        ,com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {
    private SliderLayout mDemoSlider;
    private RecyclerView featured_products_recyclerview,latest_products_recyclerview,recently_viewd_recyclerview,
            most_viewd_recyclerview;
    private String api_call;
    Featured_Product_recyclerView_Adapters   featured_product_recyclerView_adapters;
    private ArrayList<String> slider_images=new ArrayList<String>();
  //  private Featured_Product_recyclerView_Adapters featured_product_recyclerView_adapters;
    private Recent_Mostviewed_Product_recyclerView_Adapters recent_mostviewed_product_recyclerView_adapters;
    private List<Product_class> product_classList = new ArrayList<Product_class>();
    private List<Product_class> latest_product_classList = new ArrayList<Product_class>();
    private List<Product_class> recently_viewed_classList = new ArrayList<Product_class>();
    private List<Product_class> most_viewed_classList = new ArrayList<Product_class>();
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.featured_fragment, container, false);
        featured_products_recyclerview = (RecyclerView) v.findViewById(R.id.featured_product);
        most_viewd_recyclerview=(RecyclerView)v.findViewById(R.id.most_viewed);
        recently_viewd_recyclerview=(RecyclerView)v.findViewById(R.id.recently_viewed);
        latest_products_recyclerview = (RecyclerView) v.findViewById(R.id.latest_product);

        mDemoSlider = (SliderLayout) v.findViewById(R.id.slider);
        get_featured();
//        get_slide();
        return v;
    }
    private void get_latest(String type) {
        try {
            api_call = type;
            JSONObject json = new JSONObject();
            json.put("token", Constants.token);
            json.put("user_id", Constants.user_id);
            json.put("types", type);

            JSONObject json2 = new JSONObject();
            json2.put("to", "bitoutlet");
            json2.put("methods", "get_latest");
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
    private void get_slide() {
        try {
            api_call = "slide";
            JSONObject json = new JSONObject();
            json.put("token", Constants.token);
            json.put("user_id", Constants.user_id);

            JSONObject json2 = new JSONObject();
            json2.put("to", "bitoutlet");
            json2.put("methods", "get_slider");
            json2.accumulate("complex", json);
            String url = "http://orupartners.com/cp/redirect_to.php";
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, json2, this, this) {
            };
            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }


    private void get_featured() {
        try {
            api_call = "featured_product";
            JSONObject json = new JSONObject();
            json.put("token", Constants.token);
            json.put("user_id", Constants.user_id);

            JSONObject json2 = new JSONObject();
            json2.put("to", "bitoutlet");
            json2.put("methods", "get_featured_screen");
            json2.accumulate("complex", json);
            String url = "http://orupartners.com/cp/redirect_to.php";
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, json2, this, this) {
            };
            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
    private void setSlider() {
        mDemoSlider.clearAnimation();
        mDemoSlider.removeAllSliders();



        if(slider_images.isEmpty()){
            mDemoSlider.removeAllSliders();
            mDemoSlider.setBackgroundResource(R.drawable.default_avatar);
        }
        else {
            mDemoSlider.setBackgroundResource(0);
            HashMap<String, String> file_maps = new HashMap<>();
//            for (DistributorsProperties d : distributorsProperties) {
//                file_maps.put(d.getName(), d.getPremiumImage());
//            }

            for (int i = 0; i < slider_images.size(); i++) {
                String name = slider_images.get(i);
                TextSliderView textSliderView = new TextSliderView(getActivity());
                textSliderView.image(name).setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);
                // initialize a SliderLayout
//                textSliderView
//                        .description(name)
//                        .image(file_maps.get(name))
//                        .setScaleType(BaseSliderView.ScaleType.Fit)
//                        .setOnSliderClickListener(this);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);
                textSliderView.getBundle()
                        .putInt("id", i);
                mDemoSlider.addSlider(textSliderView);
            }

            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener(this);
        }
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

        Log.e("response", response.toString());
        try {
            switch (api_call) {

                case "featured_product":
                    if (response.getString("error").equals("0")) {
                        Constants.home_products_value = 1;
                        JSONArray jsonArray = response.getJSONArray("product");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Product_class product_class = new Product_class();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            product_class.setProduct_id(jsonObject.getString("product_id"));
                            product_class.setTitle(jsonObject.getString("title"));
                            product_class.setPrice(jsonObject.getString("price"));
                            product_class.setImage(jsonObject.getString("image"));
                            product_classList.add(product_class);

                        }

                    featured_product_recyclerView_adapters = new Featured_Product_recyclerView_Adapters(getActivity(), product_classList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        //    uGraduateNamesListView.addItemDecoration(new SimpleDividerItemDecoration(this));
                        featured_products_recyclerview.setLayoutManager(mLayoutManager);
                        featured_products_recyclerview.setAdapter(featured_product_recyclerView_adapters);
                    }
                    get_latest("latest");
                    break;
                case "latest":

                    if (response.getString("error").equals("0")) {
                        Constants.home_products_value = 2;
                        JSONArray jsonArray = response.getJSONArray("product");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Product_class product_class = new Product_class();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            product_class.setProduct_id(jsonObject.getString("product_id"));
                            product_class.setTitle(jsonObject.getString("title"));
                            product_class.setPrice(jsonObject.getString("price"));
                            product_class.setImage(jsonObject.getString("image"));
                            latest_product_classList.add(product_class);
                        }

                        recent_mostviewed_product_recyclerView_adapters = new Recent_Mostviewed_Product_recyclerView_Adapters(getActivity(), latest_product_classList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        //    uGraduateNamesListView.addItemDecoration(new SimpleDividerItemDecoration(this));
                        latest_products_recyclerview.setLayoutManager(mLayoutManager);
                        latest_products_recyclerview.setAdapter(recent_mostviewed_product_recyclerView_adapters );
                        get_slide();

                    }
                    break;
                case "slide":
                    JSONArray array = response.optJSONArray("slider");
                    if (response.getString("error").equals("0")) {
                        for (int i = 0; i < array.length(); i++) {
                            slider_images.add(array.optString(i));

                        }
                        setSlider();
                        get_latest("recently_viewed");

                    }
                    break;
                case "recently_viewed":
                    if (response.getString("error").equals("0")) {
                        JSONArray jsonArray = response.getJSONArray("product");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Product_class product_class = new Product_class();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            product_class.setProduct_id(jsonObject.getString("product_id"));
                            product_class.setTitle(jsonObject.getString("title"));
                            product_class.setPrice(jsonObject.getString("price"));
                            product_class.setImage(jsonObject.getString("image"));
                            recently_viewed_classList.add(product_class);
                        }

                        recent_mostviewed_product_recyclerView_adapters  = new Recent_Mostviewed_Product_recyclerView_Adapters(getActivity(), recently_viewed_classList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        //    uGraduateNamesListView.addItemDecoration(new SimpleDividerItemDecoration(this));
                        recently_viewd_recyclerview.setLayoutManager(mLayoutManager);
                        recently_viewd_recyclerview.setAdapter(featured_product_recyclerView_adapters);
                        get_latest("most_viewed");

                    }
                    break;
                case "most_viewed":
                    if (response.getString("error").equals("0")) {
                        JSONArray jsonArray = response.getJSONArray("product");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Product_class product_class = new Product_class();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            product_class.setProduct_id(jsonObject.getString("product_id"));
                            product_class.setTitle(jsonObject.getString("title"));
                            product_class.setPrice(jsonObject.getString("price"));
                            product_class.setImage(jsonObject.getString("image"));
                            most_viewed_classList.add(product_class);
                        }

                        recent_mostviewed_product_recyclerView_adapters  = new Recent_Mostviewed_Product_recyclerView_Adapters(getActivity(),most_viewed_classList);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        //    uGraduateNamesListView.addItemDecoration(new SimpleDividerItemDecoration(this));
                        most_viewd_recyclerview.setLayoutManager(mLayoutManager);
                        most_viewd_recyclerview.setAdapter(featured_product_recyclerView_adapters);

                    }
                    break;
            }
        }
        catch (Exception ex) {

        }
    }

}
