package com.bitoutlets_app.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitoutlets_app.Categories_Fragments.Product_Fragment;
import com.bitoutlets_app.Constants;
import com.bitoutlets_app.Database.AndroidOpenDbHelper;
import com.bitoutlets_app.Menu_Drawer.data.BaseItem;
import com.bitoutlets_app.Menu_Drawer.data.CustomDataProvider;
import com.bitoutlets_app.Menu_Drawer.views.LevelBeamView;
import com.bitoutlets_app.Model_classes.FriendsClass;
import com.bitoutlets_app.Profile_fragments.Edit_Profile_Fragment;
import com.bitoutlets_app.Profile_fragments.Featured_Fragment;
import com.bitoutlets_app.Profile_fragments.Profile_Fragment;
import com.bitoutlets_app.Profile_fragments.Support_Fragment;
import com.bitoutlets_app.Profile_fragments.Whishlist_Fragment;
import com.bitoutlets_app.R;
import com.bitoutlets_app.Singletons.Product_Singletons;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import pl.openrnd.multilevellistview.ItemInfo;
import pl.openrnd.multilevellistview.MultiLevelListAdapter;
import pl.openrnd.multilevellistview.MultiLevelListView;
import pl.openrnd.multilevellistview.OnItemClickListener;


public class Home extends AppCompatActivity {
    DrawerLayout drawer;
private Fragment fragment=null;
    private TextView username,email;
    private MultiLevelListView multiLevelListView;
    SharedPreferences sharedPreferences_login;
    private CircleImageView profile_picture;
    View head;
    private Product_Singletons product_singletons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        product_singletons=Product_Singletons.getInstance();
        product_singletons.setProduct_list();
        sharedPreferences_login=getSharedPreferences("Login",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences_login.edit();
        editor.putInt("value",1);
        editor.commit();
        setContentView(R.layout.home);
        SharedPreferences data=getSharedPreferences("User_details", Context.MODE_PRIVATE);
        Constants.token=data.getString("token","no");
        Constants.user_id=data.getString("user_id","no");
        Constants.image=data.getString("image","no");
        Constants.username=data.getString("username","no");
        Constants.email=data.getString("email","no");
       Log.e("DDDDD",Constants.image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        multiLevelListView = (MultiLevelListView) findViewById(R.id.multiLevelMenu);
        username=(TextView)findViewById(R.id.username);
        email=(TextView)findViewById(R.id.email);
        username.setText(Constants.username);
        email.setText(Constants.email);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//        NavigationView layout = (NavigationView) findViewById(R.id.nav_view);
//        View.inflate(this, R.layout.nav_header_main, layout);
//
        profile_picture = (CircleImageView)findViewById(R.id.imageView);
        Picasso.with(getApplication()).load(Constants.image)
                .placeholder(R.drawable.default_avatar).into(profile_picture);

        fragment = new Featured_Fragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(R.id.home_fragment, fragment);
        ft.addToBackStack(null);
        ft.commit();
        confMenu();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Constants.db_list.isEmpty() || Constants.db_list!=null) {
            Constants.db_list.clear();
            Constants.db_list = FriendsClass.load_db(Home.this);

        }
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    private void confMenu() {
        // custom ListAdapter
        ListAdapter listAdapter = new ListAdapter();

        multiLevelListView.setAdapter(listAdapter);
        multiLevelListView.setOnItemClickListener(mOnItemClickListener);

        listAdapter.setDataItems(CustomDataProvider.getInitialItems());
    }

    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
        Intent intent;
        private void showItemDescription(Object object, ItemInfo itemInfo) {
            StringBuilder builder = new StringBuilder("\"");
            builder.append(((BaseItem) object).getName());
            builder.append("\" clicked!\n");
            builder.append(getItemInfoDsc(itemInfo));
                switch (((BaseItem) object).getName()) {
                    case "Categories":
                        drawer.closeDrawers();
                        Constants.fragment_value=0;
                        Constants.back_value=0;
                        intent = new Intent(getApplication(), Categories.class);
                        startActivity(intent);
                        drawer.closeDrawers();
                        break;
                    case "Profile Info":
                        fragment = new Profile_Fragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        ft.replace(R.id.home_fragment, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                        drawer.closeDrawers();
                        break;
                    case "Edit Profile":
                        fragment = new Edit_Profile_Fragment();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        ft.replace(R.id.home_fragment, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                        drawer.closeDrawers();
                        break;
                    case "Support Ticket":
                        fragment = new Support_Fragment();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        ft.replace(R.id.home_fragment, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                        drawer.closeDrawers();
                        break;
                    case "Whishlist":
                        fragment = new Whishlist_Fragment();
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        ft.replace(R.id.home_fragment, fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                        drawer.closeDrawers();
                        break;

                    case "Logout":
                        sharedPreferences_login=getSharedPreferences("Login",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences_login.edit();
                        editor.clear();
                        editor.commit();

                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                        drawer.closeDrawers();
                        break;
                }




        }

        @Override
        public void onItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
            showItemDescription(item, itemInfo);
        }

        @Override
        public void onGroupItemClicked(MultiLevelListView parent, View view, Object item, ItemInfo itemInfo) {
            showItemDescription(item, itemInfo);
        }
    };

    private class ListAdapter extends MultiLevelListAdapter {

        private class ViewHolder {
            TextView nameView;
            TextView infoView;
            ImageView arrowView;
            LevelBeamView levelBeamView;
        }

        @Override
        public List<?> getSubObjects(Object object) {
            // DIEKSEKUSI SAAT KLIK PADA GROUP-ITEM
            return CustomDataProvider.getSubItems((BaseItem) object);
        }

        @Override
        public boolean isExpandable(Object object) {
            return CustomDataProvider.isExpandable((BaseItem) object);
        }

        @Override
        public View getViewForObject(Object object, View convertView, ItemInfo itemInfo) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(Home.this).inflate(R.layout.data_item, null);
                //viewHolder.infoView = (TextView) convertView.findViewById(R.id.dataItemInfo);
                viewHolder.nameView = (TextView) convertView.findViewById(R.id.dataItemName);
                viewHolder.arrowView = (ImageView) convertView.findViewById(R.id.dataItemArrow);
                viewHolder.levelBeamView = (LevelBeamView) convertView.findViewById(R.id.dataItemLevelBeam);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.nameView.setText(((BaseItem) object).getName());
            //viewHolder.infoView.setText(getItemInfoDsc(itemInfo));

            if (itemInfo.isExpandable()) {
                viewHolder.arrowView.setVisibility(View.VISIBLE);
                viewHolder.arrowView.setImageResource(itemInfo.isExpanded() ?
                        R.drawable.arrow_up : R.drawable.arrow_down);
            } else {
                viewHolder.arrowView.setVisibility(View.GONE);
            }

            viewHolder.levelBeamView.setLevel(itemInfo.getLevel());

            return convertView;
        }
    }

    private String getItemInfoDsc(ItemInfo itemInfo) {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("level[%d], idx in level[%d/%d]",
                itemInfo.getLevel() + 1, /*Indexing starts from 0*/
                itemInfo.getIdxInLevel() + 1 /*Indexing starts from 0*/,
                itemInfo.getLevelSize()));

        if (itemInfo.isExpandable()) {
            builder.append(String.format(", expanded[%b]", itemInfo.isExpanded()));
        }
        return builder.toString();
    }
}
