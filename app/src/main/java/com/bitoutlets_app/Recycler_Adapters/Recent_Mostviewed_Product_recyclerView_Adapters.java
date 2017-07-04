package com.bitoutlets_app.Recycler_Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitoutlets_app.Constants;
import com.bitoutlets_app.Database.AndroidOpenDbHelper;
import com.bitoutlets_app.Model_classes.Product_class;
import com.bitoutlets_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class Recent_Mostviewed_Product_recyclerView_Adapters extends RecyclerView.Adapter<Recent_Mostviewed_Product_recyclerView_Adapters.MyViewHolder> implements View.OnClickListener {

    private List<Product_class> horizontalList = new ArrayList<Product_class>();
    private Context context;
    private int database_value=0;
    @Override
    public void onClick(View view) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView cat_images,cart,whislist,compare;
        private TextView title, shop, price;

        public MyViewHolder(View view) {
            super(view);
                    cat_images = (ImageView) view.findViewById(R.id.image);
                    title = (TextView) view.findViewById(R.id.title);
                    shop = (TextView) view.findViewById(R.id.shop);
                    price = (TextView) view.findViewById(R.id.price);
                    cart=(ImageView)view.findViewById(R.id.cart);
                    whislist=(ImageView)view.findViewById(R.id.whishlist);
                    compare=(ImageView)view.findViewById(R.id.compare);

        }
    }


    public Recent_Mostviewed_Product_recyclerView_Adapters(Context context, List<Product_class> horizontalList) {
        this.context = context;
        this.horizontalList = horizontalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.featured_products_view_items, parent, false);

        MyViewHolder holder = new MyViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(horizontalList.get(position).getTitle());
        holder.price.setText(horizontalList.get(position).getPrice());
        Picasso.with(context).load(horizontalList.get(position).getImage())
                .resize(360, 300).centerCrop().
                transform(new RoundedCornersTransformation(15, 0, RoundedCornersTransformation.CornerType.ALL))
                .placeholder(R.drawable.default_avatar).into(holder.cat_images);

//        setClickListener(holder,horizontalList.get(position).getImage(),horizontalList.get(position).getProduct_id(),
//                horizontalList.get(position).getTitle(),horizontalList.get(position).getPrice(),
//                horizontalList.get(position).getShipping_cost(),horizontalList.get(position).getFeatures(),
//                horizontalList.get(position).getTags(),horizontalList.get(position).getUnit(),
//                horizontalList.get(position).getCurrent_stock(),horizontalList.get(position).getTax(),
//                horizontalList.get(position).getDescription()
//                );
    }
    private void setClickListener(MyViewHolder holder,final String image, final String id, final String title, final String price, final String ship_cost, final String features,
                                  final String tags, final String unit, final String current_stock, final String tax, final String description){

        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert_data(image,id,title,price,ship_cost,features,tags,unit,current_stock,tax,description);
            }
        });

    }
    public void insert_data(final String image, final String id, final String title, final String price, final String ship_cost, final String features,
                            final String tags, final String unit, final String current_stock, final String tax, final String description){
      AndroidOpenDbHelper androidOpenDbHelperObj = new AndroidOpenDbHelper(context);
        SQLiteDatabase sqliteDatabase = androidOpenDbHelperObj.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AndroidOpenDbHelper.product_image,image );
        contentValues.put(AndroidOpenDbHelper.product_id, id);
        contentValues.put(AndroidOpenDbHelper.product_title, title);
        contentValues.put(AndroidOpenDbHelper.product_price,price);
        contentValues.put(AndroidOpenDbHelper.product_shipping, ship_cost);
        contentValues.put(AndroidOpenDbHelper.product_features,features);
        contentValues.put(AndroidOpenDbHelper.product_tags, tags);
        contentValues.put(AndroidOpenDbHelper.product_unit, unit);
        contentValues.put(AndroidOpenDbHelper.product_current_stock ,current_stock);
        contentValues.put(AndroidOpenDbHelper.product_tax, tax);
        contentValues.put(AndroidOpenDbHelper.product_description, description);

        long affectedColumnId = 0;
            affectedColumnId = sqliteDatabase.insert(AndroidOpenDbHelper.TABLE_NAME_Cart, null, contentValues);


        // It is a good practice to close the database connections after you have done with it
        sqliteDatabase.close();

        // I am not going to do the retrieve part in this post. So this is just a notification for satisfaction ;-)
        Toast.makeText(context, "Values inserted column ID is :" + affectedColumnId, Toast.LENGTH_SHORT).show();

    }



    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}