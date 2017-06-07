package com.bitoutlets_app.User_Credentials_Fragments;

import android.app.ProgressDialog;
import android.media.Image;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Activities.MainActivity;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Volley_Singleton.MySingleton;

import org.json.JSONObject;


public class SignUp_Fragment extends Fragment  implements View.OnClickListener,com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {
    private EditText username,email,password,c_password,adress_one,address_two,surname,zip,state,city,country,number;
    private ImageView signup_btn;
    private ProgressDialog mProgressDialog;
    private View snack_view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.signup_fragment, container, false);
        username=(EditText)v.findViewById(R.id.name);
        email=(EditText)v.findViewById(R.id.email);
        password=(EditText)v.findViewById(R.id.password);
        c_password=(EditText)v.findViewById(R.id.confirm_password);
        adress_one=(EditText)v.findViewById(R.id.adress_one);
        address_two=(EditText)v.findViewById(R.id.address_two);
        surname=(EditText)v.findViewById(R.id.surname);
        zip=(EditText)v.findViewById(R.id.zip);
        state=(EditText)v.findViewById(R.id.state);
        country=(EditText)v.findViewById(R.id.country);
        city=(EditText)v.findViewById(R.id.city);
        number=(EditText)v.findViewById(R.id.number);
        signup_btn=(ImageView) v.findViewById(R.id.btn_Signup);
        snack_view=(View)v.findViewById(R.id.snack_signup);
        signup_btn.setOnClickListener(this);

        return v;
    }
    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setMessage("Please Wait..");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }




    private void Register(){
        showProgressDialog();
        try {
            JSONObject json = new JSONObject();
            json.put("username", username.getText().toString());
            json.put("email", email.getText().toString());
            json.put("address1", adress_one.getText().toString());
            json.put("phone", number.getText().toString());
            json.put("address2", address_two.getText().toString());
            json.put("surname", surname.getText().toString());
            json.put("zip", zip.getText().toString());
            json.put("city", city.getText().toString());
            json.put("state", state.getText().toString());
            json.put("country", country.getText().toString());
            json.put("password1", password.getText().toString());
            json.put("password2", c_password.getText().toString());

            JSONObject json2 = new JSONObject();
            json2.put("to", "bitoutlet");
            json2.put("methods", "customer_register");
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
        switch (view.getId()) {
            case R.id.btn_Signup:
                Register();
                break;
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        mProgressDialog.dismiss();
        Log.e("signup_response",response.toString());
        try {
            String error = response.getString("error");
            if(error.equals("0")) {
                Snackbar.make(snack_view, "Register Successfully", Snackbar.LENGTH_SHORT).show();
            }else{
                String msg = response.getString("msg");
                Snackbar.make(snack_view, msg, Snackbar.LENGTH_SHORT).show();

            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
