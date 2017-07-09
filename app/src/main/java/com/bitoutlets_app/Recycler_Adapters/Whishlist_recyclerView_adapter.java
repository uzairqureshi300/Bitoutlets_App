package com.bitoutlets_app.Recycler_Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitoutlets_app.Constants;
import com.bitoutlets_app.Database.AndroidOpenDbHelper;
import com.bitoutlets_app.Model_classes.Fetch_class;
import com.bitoutlets_app.Model_classes.Product_class;

import com.bitoutlets_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Whishlist_recyclerView_adapter extends RecyclerView.Adapter<Whishlist_recyclerView_adapter.MyViewHolder>{
    private String image, p_id,title,price, ship_cost,features, tags,unit, current_stock,tax,
    description,type;
    private List<Product_class> horizontalList=new ArrayList<Product_class>();
    private Context context;
    private int list_position=0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public Whishlist_recyclerView_adapter(Context context, List<Product_class> horizontalList) {
        this.context=context;
        this.horizontalList = horizontalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.whishlist_view_items, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(horizontalList.get(position).getTitle());
        Picasso.with(context).load(horizontalList.get(position).getImage())
                .resize(300,160).centerCrop()
                .placeholder(R.drawable.default_avatar).into(holder.thumbnail);

    holder.overflow.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            list_position=position;
            Log.e("pos",position+"");
            image=horizontalList.get(position).getImage();
            p_id =horizontalList.get(position).getProduct_id();
            title=horizontalList.get(position).getTitle();
            price=horizontalList.get(position).getPrice();
            ship_cost=horizontalList.get(position).getShipping_cost();
            features=horizontalList.get(position).getFeatures();
            tags=horizontalList.get(position).getTags();
            unit=horizontalList.get(position).getUnit();
            current_stock=horizontalList.get(position).getCurrent_stock();
            tax=horizontalList.get(position).getTax();
            description=horizontalList.get(position).getDescription();
            type="cart";
            showPopupMenu(view,horizontalList.get(position).getId());


      }
    });

    }

    private void showPopupMenu(View view,long id) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_whishlist, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(context,id));
        popup.show();
    }
    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private Context c;
        private long id;

        public MyMenuItemClickListener(Context c,long id) {
            this.c=c;
            this.id=id;

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.cart:
                    String First_name="";
                    String types="";
                    if(Constants.db_list!=null || !Constants.db_list.isEmpty()) {
                        for (int i = 0; i < Constants.db_list.size(); i++) {
                            First_name=Constants.db_list.get(i).getId();
                            types=Constants.db_list.get(i).getType();
                            if (First_name.equals(p_id) && types.equals(type)) {
                                Toast.makeText(context, "Already added", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }

                    if(!First_name.equals(p_id) || !type.equals(types)){
                        insert_data();
                        Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();

                    }
                    break;

                case R.id.delete:
                    AndroidOpenDbHelper androidOpenDbHelperObj = new AndroidOpenDbHelper(c);
                    SQLiteDatabase sqliteDatabase = androidOpenDbHelperObj.getReadableDatabase();
                    sqliteDatabase.delete(androidOpenDbHelperObj.TABLE_NAME_Cart, "_id" + "="+id,null);
                    horizontalList.remove(list_position);
                    Whishlist_recyclerView_adapter.this.notifyDataSetChanged();
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    }
    public void insert_data(){
        AndroidOpenDbHelper androidOpenDbHelperObj = new AndroidOpenDbHelper(context);
        SQLiteDatabase sqliteDatabase = androidOpenDbHelperObj.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AndroidOpenDbHelper.product_image,image);
        contentValues.put(AndroidOpenDbHelper.product_id, p_id);
        contentValues.put(AndroidOpenDbHelper.product_title,title);
        contentValues.put(AndroidOpenDbHelper.product_price,price);
        contentValues.put(AndroidOpenDbHelper.product_shipping, ship_cost);
        contentValues.put(AndroidOpenDbHelper.product_features, features);
        contentValues.put(AndroidOpenDbHelper.product_tags, tags);
        contentValues.put(AndroidOpenDbHelper.product_unit, unit);
        contentValues.put(AndroidOpenDbHelper.product_current_stock ,current_stock);
        contentValues.put(AndroidOpenDbHelper.product_tax, tax);
        contentValues.put(AndroidOpenDbHelper.product_description,description);
        contentValues.put(AndroidOpenDbHelper.product_add, type);
        Fetch_class fetch_class =new Fetch_class();
        fetch_class.setId(p_id);
        fetch_class.setType(type);
        Constants.db_list.add(fetch_class);
        long affectedColumnId = 0;
        affectedColumnId = sqliteDatabase.insert(AndroidOpenDbHelper.TABLE_NAME_Cart, null, contentValues);

        // I am not going to do the retrieve part in this post. So this is just a notification for satisfaction ;-)
        Toast.makeText(context, "Values inserted column ID is :" + affectedColumnId, Toast.LENGTH_SHORT).show();
        Log.e("type",type);

    }
}