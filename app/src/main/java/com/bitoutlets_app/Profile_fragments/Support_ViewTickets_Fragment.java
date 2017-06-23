package com.bitoutlets_app.Profile_fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Activities.Categories;
import com.bitoutlets_app.Constants;
import com.bitoutlets_app.Model_classes.Category_class;
import com.bitoutlets_app.Model_classes.Support_Tickets_Class;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Recycler_Adapters.Product_recyclerView;
import com.bitoutlets_app.Recycler_Adapters.RecyclerItemClickListener;
import com.bitoutlets_app.Recycler_Adapters.SimpleDividerItemDecoration;
import com.bitoutlets_app.Recycler_Adapters.Support_recyclerView;
import com.bitoutlets_app.ViewPager.ViewPagerAdapter;
import com.bitoutlets_app.Volley_Singleton.MySingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Support_ViewTickets_Fragment extends Fragment implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener
{
    private RecyclerView ticlets_list;
    private ProgressBar progressBar;
    private String api_call;
    private Support_recyclerView support_recyclerView;
    private List<Support_Tickets_Class> support_list=new ArrayList<Support_Tickets_Class>();
    private View snack_view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v =  inflater.inflate(R.layout.support_viewtickets, container, false);
        ticlets_list=(RecyclerView)v.findViewById(R.id.support_list);
        progressBar=(ProgressBar)v.findViewById(R.id.progress_bar);
        snack_view=(View)v.findViewById(R.id.snacke_view);
        progressBar.setVisibility(View.VISIBLE);
        ticlets_list.setVisibility(View.GONE);
        get_Ticket();
        return v;
    }

    private void get_Ticket_msg(String id) {
        try {
            api_call="get_ticket_msg";
            JSONObject json = new JSONObject();
            json.put("token", Constants.token);
            json.put("user_id", Constants.user_id);
            json.put("ticket_id", id);

            JSONObject json2 = new JSONObject();
            json2.put("to", "bitoutlet");
            json2.put("methods", "get_message_detail");
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

    private void get_Ticket() {
        try {
            api_call="get_ticket";
            JSONObject json = new JSONObject();
            json.put("token", Constants.token);
            json.put("user_id", Constants.user_id);

            JSONObject json2 = new JSONObject();
            json2.put("to", "bitoutlet");
            json2.put("methods", "get_tickets");
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
        progressBar.setVisibility(View.GONE);
        Snackbar.make(snack_view,"no internet connection",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.e("response",response.toString());
        progressBar.setVisibility(View.GONE);
        ticlets_list.setVisibility(View.VISIBLE);
        try {
            switch (api_call){
                case "get_ticket":
                    if(response.getString("error").equals("0")) {
                        JSONArray jsonArray = response.getJSONArray("ticket");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Support_Tickets_Class support_tickets_class = new Support_Tickets_Class();
                            support_tickets_class.setCreation_date(jsonObject.getString("creation_time"));
                            support_tickets_class.setSubject(jsonObject.getString("subject"));
                            support_tickets_class.setTicket_id(jsonObject.getString("ticket_id"));
                            support_list.add(support_tickets_class);

                        }
                        support_recyclerView = new Support_recyclerView(getActivity(), support_list);

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        ticlets_list.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
                        ticlets_list.setLayoutManager(mLayoutManager);
                        ticlets_list.setAdapter(support_recyclerView);
                        list_click_listener(support_list);
                    }
                    break;
                case "get_ticket_msg":
                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        }
    private void  list_click_listener(final List<Support_Tickets_Class> categories_list){

        ticlets_list.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                       get_Ticket_msg(categories_list.get(i).getTicket_id());

                     //   ((Categories)getActivity()).Integrate_fragments(2);



                    }
                })
        );


    }
}
