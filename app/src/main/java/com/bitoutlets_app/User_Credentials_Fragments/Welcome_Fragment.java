package com.bitoutlets_app.User_Credentials_Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Activities.MainActivity;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Volley_Singleton.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Welcome_Fragment extends Fragment  implements View.OnClickListener, com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener
{
    private ImageView login_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);



        View v =  inflater.inflate(R.layout.welcome_fragment, container, false);
        login_btn=(ImageView)v.findViewById(R.id.btn_login);
        login_btn.setOnClickListener(this);
        return v;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                ((MainActivity)getActivity()).Integrate_fragments(2);
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Log.e("response",response.toString());

    }


}
