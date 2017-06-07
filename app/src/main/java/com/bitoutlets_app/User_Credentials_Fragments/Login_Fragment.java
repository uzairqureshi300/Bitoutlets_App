package com.bitoutlets_app.User_Credentials_Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Activities.Home;
import com.bitoutlets_app.Activities.MainActivity;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Volley_Singleton.MySingleton;

import org.json.JSONArray;
import org.json.JSONObject;


public class Login_Fragment extends Fragment  implements View.OnClickListener,com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {
    private ProgressDialog mProgressDialog;
    private EditText enter_email,enter_password;
    private ImageView login_button;
    private TextView no_account;
    private String[] user_id;
    private View snack_bar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
          View v =  inflater.inflate(R.layout.login_fragment, container, false);
          enter_email=(EditText)v.findViewById(R.id.enter_email);
          enter_password=(EditText)v.findViewById(R.id.enter_password);
        login_button=(ImageView)v.findViewById(R.id.btn_login);
        no_account=(TextView)v.findViewById(R.id.text_signup);
        snack_bar=(View)v.findViewById(R.id.snack_view);
          login_button.setOnClickListener(this);
        no_account.setOnClickListener(this);
 //       Cat();
        return v;
    }
    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setMessage("Please Wait..");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }
    private void Login(){
        showProgressDialog();
        try {
            JSONObject json = new JSONObject();
            json.put("email", "aftab.sharif@hotmail.com");
            json.put("password", "e9bafdb0fec4");

            JSONObject json2 = new JSONObject();
            json2.put("to", "bitoutlet");
            json2.put("methods", "customer_log_in");
            json2.accumulate("complex", json);
            String url = "http://orupartners.com/cp/redirect_to.php";
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, json2, this, this) {

            };
            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest);
        }catch(Exception ex){
            ex.printStackTrace();

        }
        }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                if(enter_email.getText().toString().equals("") || enter_password.getText().toString().equals("")){
                    Snackbar.make(snack_bar,"Complete All fields",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    Login();
                }
                break;
            case R.id.text_signup:
                ((MainActivity)getActivity()).Integrate_fragments(3);
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mProgressDialog.dismiss();
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.e("login_response",response.toString());
        try{


        mProgressDialog.dismiss();

            SharedPreferences data= getActivity().getSharedPreferences("User_details", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = data.edit();
        edit.putString("token",response.getString("token"));
        edit.putString("user_id",response.getString("user_id"));
        edit.commit();
            Intent i=new Intent(getActivity(), Home.class);
            startActivity(i);
            getActivity().finish();
        }catch(Exception ex){
            Log.e("exception",ex.toString());
            ex.printStackTrace();
        }
    }
}
