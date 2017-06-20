package com.bitoutlets_app.Profile_fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Categories_Fragments.Category_Fragment;
import com.bitoutlets_app.Constants;
import com.bitoutlets_app.R;
import com.bitoutlets_app.ViewPager.ViewPagerAdapter;
import com.bitoutlets_app.Volley_Singleton.MySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;


public class Edit_Profile_UserInfo_Fragment extends Fragment implements View.OnClickListener,com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener
{
    private ProgressBar progressBar;
    private View scroll;
    private String api_call;
    private ProgressDialog mProgressDialog;
    private EditText name,email,address1,address2,phone,city,state,country,zip,surname;
    private ImageView update;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v =  inflater.inflate(R.layout.personal_info_fragment, container, false);
        name=(EditText)v.findViewById(R.id.edit_name);
        email=(EditText)v.findViewById(R.id.edit_email);
        address1=(EditText)v.findViewById(R.id.edit_address1);
        address2=(EditText)v.findViewById(R.id.edit_address2);
        phone=(EditText)v.findViewById(R.id.edit_phone);
        city=(EditText)v.findViewById(R.id.edit_city);
        state=(EditText)v.findViewById(R.id.edit_state);
        surname=(EditText)v.findViewById(R.id.edit_surname);
        country=(EditText)v.findViewById(R.id.edit_country);
        zip=(EditText)v.findViewById(R.id.edit_zip);
        update=(ImageView) v.findViewById(R.id.update);
        progressBar=(ProgressBar)v.findViewById(R.id.progress_bar);
        scroll=(View)v.findViewById(R.id.snake_views );
        update.setOnClickListener(this);
        progressBar.setVisibility(View.VISIBLE);
        scroll.setVisibility(View.GONE);
        Profile_info();
        return v;
    }
    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setMessage("Please Wait..");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }
    private void Edit_Pro() {
        try {
            showProgressDialog();
            api_call="edit_profile";

            JSONObject json = new JSONObject();
            json.put("token", Constants.token);
            json.put("user_id", Constants.user_id);
            json.put("username", name.getText().toString());
            json.put("surname", surname.getText().toString());
            json.put("phone", phone.getText().toString());
            json.put("zip", zip.getText().toString());
            json.put("address1", address1.getText().toString());
            json.put("address2", address2.getText().toString());
            json.put("city", city.getText().toString());
            json.put("state", state.getText().toString());
            json.put("country", country.getText().toString());

            JSONObject json2 = new JSONObject();
            json2.put("to", "bitoutlet");
            json2.put("methods", "edit_profile");
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

    private void Profile_info() {
        try {
            api_call="profile_info";
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
        switch (view.getId()){
            case R.id.update:
                if(name.getText().toString().equals("") ||email.getText().toString().equals("")||
                        address1.getText().toString().equals("")||address2.equals("")||phone.getText().toString().equals("")
                        || city.getText().toString().equals("") || state.getText().toString().equals("")||
                        country.getText().toString().equals("")||zip.getText().toString().equals("") ||surname.getText().toString().equals("")
                        ){
                    Snackbar.make(scroll,"complete all fields",Snackbar.LENGTH_SHORT).show();
                }
                else {
                    Edit_Pro();
                }
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Log.e("responseprofile",response.toString());
        try{
            switch (api_call){
                case "profile_info":
                    progressBar.setVisibility(View.GONE);
                    scroll.setVisibility(View.VISIBLE);
                    setData(response.getString("username"),response.getString("email"),response.getString("address1"),
                            response.getString("phone"),response.getString("city"),response.getString("state"),
                            response.getString("country"),response.getString("address2"),response.getString("zip"),
                            response.getString("surname"));

                    break;
                case "edit_profile":
                    mProgressDialog.dismiss();
                    if(response.getString("msg").equals("success")){
                        Constants.profile_value=0;
                        Snackbar.make(scroll,"Updated Successfully",Snackbar.LENGTH_SHORT).show();
                    }
                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private void setData(String p_name,String p_email,String p_address1,String p_phone,String p_city,
                         String p_state,String p_country,String p_address,String p_zip,String p_surname){
        name.setText(p_name);
        email.setText(p_email);
        address1.setText(p_address1);
        phone.setText(p_phone);
        city.setText(p_city);
        state.setText(p_state);
        country.setText(p_country);
        address2.setText(p_address);
        zip.setText(p_zip);
        surname.setText(p_surname);
    }
}
