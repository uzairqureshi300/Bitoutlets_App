package com.bitoutlets_app.Profile_fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Activities.Categories;
import com.bitoutlets_app.Constants;
import com.bitoutlets_app.Model_classes.Category_class;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Recycler_Adapters.RecyclerItemClickListener;
import com.bitoutlets_app.Recycler_Adapters.Sub_Categories_recyclerView;
import com.bitoutlets_app.Singletons.Product_Singletons;
import com.bitoutlets_app.Volley_Singleton.MySingleton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bitoutlets_app.Constants.profile_name;
import static com.bitoutlets_app.Constants.transition_value;


public class Profile_Fragment extends Fragment implements View.OnClickListener,com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener
{
    private EditText name,email,address,contact,city,state,addrsss2,country;
    private ProgressBar progressBar;
    private TextView t_purchase;

    private ImageView change_pic,user_image;
    private View scroll;
    private String image;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v =  inflater.inflate(R.layout.profile_info, container, false);
        name=(EditText)v.findViewById(R.id.profile_name);
        email=(EditText)v.findViewById(R.id.profile_email);
        address=(EditText)v.findViewById(R.id.profile_address);
        addrsss2=(EditText)v.findViewById(R.id.profile_address2);
        country=(EditText)v.findViewById(R.id.profile_country);
        user_image=(ImageView)v.findViewById(R.id.profile_image);
        contact=(EditText)v.findViewById(R.id.profile_contact);
        t_purchase=(TextView)v.findViewById(R.id.t_purchase);
        city=(EditText)v.findViewById(R.id.profile_city);
        state=(EditText)v.findViewById(R.id.profile_state);
        scroll=(View)v.findViewById(R.id.scroll);
       progressBar=(ProgressBar)v.findViewById(R.id.progress_bar);
        change_pic=(ImageView)v.findViewById(R.id.change_pic);
        progressBar.setVisibility(View.VISIBLE);
        scroll.setVisibility(View.GONE);
        profile_check();
        return v;
    }
    private void profile_check(){
        switch (Constants.profile_value){
            case 0:
                Profile_info();
                break;
            case 1:
                scroll.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                name.setText(Constants.profile_name);
                email.setText(Constants.profile_email);
                address.setText(Constants.profile_address1);
                contact.setText(Constants.profile_country);
                city.setText(Constants.profile_city);
                state.setText(Constants.profile_state);
                country.setText(Constants.profile_country);
                addrsss2.setText(Constants.profile_address2);
                t_purchase.setText(Constants.profile_totla_purchase);

                Picasso.with(getActivity()).load(Constants.profile_image).placeholder(R.drawable.default_avatar).into(user_image);

        }
    }

    private void Profile_info() {
        try {
            JSONObject json = new JSONObject();
            json.put("token", Constants.token);
            json.put("user_id", Constants.user_id);

            JSONObject json2 = new JSONObject();
            json2.put("to", "bitoutlet");
            json2.put("methods", "fetch_user_detail");
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
    public void onClick(View view) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Log.e("responseprofile",response.toString());
        try {
            progressBar.setVisibility(View.GONE);
            scroll.setVisibility(View.VISIBLE);
           Constants.profile_name=response.getString("username");
            Constants.profile_email=response.getString("email");
            Constants.profile_address1=response.getString("address1");
            Constants.profile_address2=response.getString("address2");
            Constants.profile_phone=response.getString("phone");
            Constants.profile_city=response.getString("city");
            Constants.profile_state=response.getString("state");
            Constants.profile_country=response.getString("country");
          Constants.profile_image=response.getString("image");
            Constants.profile_totla_purchase=response.getString("total_purchase");
               Constants.profile_value=1;
            setData(Constants.profile_name,Constants.profile_email,Constants.profile_address1,
                    Constants.profile_phone,Constants.profile_city,Constants.profile_state,
                    Constants.profile_country,Constants.profile_address2,Constants.profile_image,
                    Constants.profile_totla_purchase
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void setData(String p_name,String p_email,String p_address1,String p_phone,String p_city,
                         String p_state,String p_country,String p_address, String p_image,String purchase){
        name.setText(p_name);
        email.setText(p_email);
        address.setText(p_address1);
        contact.setText(p_phone);
        city.setText(p_city);
        state.setText(p_state);
        country.setText(p_country);
        addrsss2.setText(p_address);
        t_purchase.setText(purchase);
        Log.e("image",p_image);
        Picasso.with(getActivity()).load(p_image).placeholder(R.drawable.default_avatar).into(user_image);

    }
}
