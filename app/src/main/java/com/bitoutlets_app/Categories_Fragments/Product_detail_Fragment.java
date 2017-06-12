package com.bitoutlets_app.Categories_Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bitoutlets_app.Constants;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Volley_Singleton.MySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.bitoutlets_app.Constants.transition_value;


public class Product_detail_Fragment extends Fragment  implements View.OnClickListener {
    private TextView description,price,title;
    private ImageView image,add_cart;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        transition_value=4;
        View v = inflater.inflate(R.layout.details_fragment, container, false);
        description=(TextView)v.findViewById(R.id.description);
        price=(TextView)v.findViewById(R.id.product_price);
        title=(TextView)v.findViewById(R.id.product_title);
        image=(ImageView) v.findViewById(R.id.product_image);
        add_cart=(ImageView)v.findViewById(R.id.add_cart);
        Product_details();
        Picasso.with(getActivity()).load(Constants.product_images)
                .resize(250,250).centerCrop().
                transform(new RoundedCornersTransformation(15, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .placeholder(R.drawable.default_avatar).into(image);
        return v;
    }
    private void Product_details(){
      description.setText(Constants.product_description);
       price.setText(Constants.product_price);
        title.setText(Constants.product_title);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }

    }


}
