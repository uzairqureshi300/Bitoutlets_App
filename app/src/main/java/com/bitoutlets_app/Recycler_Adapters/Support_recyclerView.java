package com.bitoutlets_app.Recycler_Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitoutlets_app.Model_classes.Category_class;
import com.bitoutlets_app.Model_classes.Support_Tickets_Class;
import com.bitoutlets_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


/**
 * Created by uzair on 09/05/2017.
 */

public class Support_recyclerView extends RecyclerView.Adapter<Support_recyclerView.MyViewHolder> implements View.OnClickListener {

    private List<Support_Tickets_Class> horizontalList=new ArrayList<Support_Tickets_Class>();
    private Context context;

    @Override
    public void onClick(View view) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name,creation_date;

        public MyViewHolder(View view) {
            super(view);
            name=(TextView)view.findViewById(R.id.subject);
            creation_date=(TextView)view.findViewById(R.id.view_message);
        }
    }


    public Support_recyclerView(Context context, List<Support_Tickets_Class> horizontalList) {
        this.context=context;
        this.horizontalList = horizontalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.support_view_items, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(horizontalList.get(position).getSubject());
        holder.creation_date.setText(horizontalList.get(position).getCreation_date());


    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}