package com.bitoutlets_app.Profile_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Constants;
import com.bitoutlets_app.R;
import com.bitoutlets_app.ViewPager.ViewPagerAdapter;
import com.bitoutlets_app.Volley_Singleton.MySingleton;

import org.json.JSONObject;


public class Support_CreateTicket_Fragment extends Fragment implements View.OnClickListener,com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener

{
    private EditText subject,message;
    private ImageView generate;
    private View snacke_view;
@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v =  inflater.inflate(R.layout.create_ticket_support_fragment, container, false);
        subject=(EditText)v.findViewById(R.id.ticket_subject);
        message=(EditText)v.findViewById(R.id.ticket_message);
        generate=(ImageView) v.findViewById(R.id.create_ticket_btn);
        snacke_view=(View)v.findViewById(R.id.snacke_view);
        generate.setOnClickListener(this);
    return v;
    }
    private void Ticket() {
        try {
            JSONObject json = new JSONObject();
            json.put("token", Constants.token);
            json.put("user_id", Constants.user_id);
            json.put("subject",subject.getText().toString());
            json.put("reply",message.getText().toString());
            JSONObject json2 = new JSONObject();
            json2.put("to", "bitoutlet");
            json2.put("methods", "ticket_generate");
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
        case R.id.create_ticket_btn:
            if(subject.getText().toString().equals("") || message.getText().toString().equals("")){

            }
            else{
                Ticket();
            }
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
