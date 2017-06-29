package com.bitoutlets_app.Recycler_Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitoutlets_app.Constants;
import com.bitoutlets_app.Model_classes.Product_class;
import com.bitoutlets_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


/**
 * Created by uzair on 09/05/2017.
 */

public class Featured_Product_recyclerView_Adapters extends RecyclerView.Adapter<Featured_Product_recyclerView_Adapters.MyViewHolder> implements View.OnClickListener {

    private List<Product_class> horizontalList = new ArrayList<Product_class>();
    private Context context;

    @Override
    public void onClick(View view) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView cat_images;
        private TextView title, shop, price;

        public MyViewHolder(View view) {
            super(view);
            switch (Constants.home_products_value) {
                case 1:
                    cat_images = (ImageView) view.findViewById(R.id.image);
                    title = (TextView) view.findViewById(R.id.title);
                    shop = (TextView) view.findViewById(R.id.shop);
                    price = (TextView) view.findViewById(R.id.price);
                    break;
                case 2:
                    cat_images = (ImageView) view.findViewById(R.id.latest_product_image);
                    title = (TextView) view.findViewById(R.id.latest_product_title);
                    price = (TextView) view.findViewById(R.id.latest_product_price);
                    break;

            }
        }
    }


    public Featured_Product_recyclerView_Adapters(Context context, List<Product_class> horizontalList) {
        this.context = context;
        this.horizontalList = horizontalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (Constants.home_products_value) {
            case 1:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.featured_products_view_items, parent, false);


                break;
            case 2:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.latest_products_view_items, parent, false);

                break;
        }
        MyViewHolder holder = new MyViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(horizontalList.get(position).getTitle());
        holder.price.setText(horizontalList.get(position).getPrice());

        Picasso.with(context).load(horizontalList.get(position).getImage())
                .resize(360, 300).centerCrop().
                transform(new RoundedCornersTransformation(15, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .placeholder(R.drawable.default_avatar).into(holder.cat_images);


    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}