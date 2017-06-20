package com.bitoutlets_app.Profile_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bitoutlets_app.Constants;
import com.bitoutlets_app.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class Edit_Profile_ChangePassword_Fragment extends Fragment implements View.OnClickListener,com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener
{
    private EditText old_password,new_password,confirm_password;
    private ProgressBar progressBar;
    private TextView t_purchase;
    private ImageView update;
    private View scroll;
    private String image;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v =  inflater.inflate(R.layout.profile_change_password, container, false);
        old_password =(EditText)v.findViewById(R.id.profile_password);
        new_password=(EditText)v.findViewById(R.id.profile_new_password);
        confirm_password=(EditText)v.findViewById(R.id.profile_cnfrm_password);
         update=(ImageView)v.findViewById(R.id.update_btn_password );
        update.setOnClickListener(this);

        return v;
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

    }

}
