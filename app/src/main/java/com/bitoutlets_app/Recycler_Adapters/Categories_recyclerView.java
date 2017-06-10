package com.bitoutlets_app.Recycler_Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitoutlets_app.Model_classes.Category_class;
import com.bitoutlets_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


/**
 * Created by uzair on 09/05/2017.
 */

public class Categories_recyclerView extends RecyclerView.Adapter<Categories_recyclerView.MyViewHolder> implements View.OnClickListener {

    private List<Category_class> horizontalList=new ArrayList<Category_class>();
    private Context context;

    @Override
    public void onClick(View view) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
    private ImageView cat_images;
        private TextView name;

        public MyViewHolder(View view) {
            super(view);
            cat_images = (ImageView) view.findViewById(R.id.img);
            name=(TextView)view.findViewById(R.id.name);
        }
    }


    public Categories_recyclerView(Context context,List<Category_class> horizontalList) {
        this.context=context;
        this.horizontalList = horizontalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categories_view_items, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(horizontalList.get(position).getName());
        Picasso.with(context).load("http://bitoutlets.com/uploads/category_image/"+horizontalList.get(position).getImage())
                .resize(250,250).centerCrop().
        transform(new RoundedCornersTransformation(15, 0,
                RoundedCornersTransformation.CornerType.ALL))
                .placeholder(R.drawable.default_avatar).into(holder.cat_images);


    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}