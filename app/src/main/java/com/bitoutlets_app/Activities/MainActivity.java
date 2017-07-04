package com.bitoutlets_app.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitoutlets_app.Categories_Fragments.Category_Fragment;
import com.bitoutlets_app.R;
import com.bitoutlets_app.User_Credentials_Fragments.Login_Fragment;
import com.bitoutlets_app.User_Credentials_Fragments.SignUp_Fragment;
import com.bitoutlets_app.User_Credentials_Fragments.Welcome_Fragment;

public class MainActivity extends AppCompatActivity{
    private int login_value=0;
    private SharedPreferences login;
    private Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        login=getSharedPreferences("Login", Context.MODE_PRIVATE);
        login_value=login.getInt("value",0);
        if(login_value== 1){
            Intent i=new Intent(getApplication(),Home.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(i);
            finish();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Integrate_fragments(1);
    }
    public void Integrate_fragments(int number){
        if(number==1) {
            fragment = new Welcome_Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        if(number==2) {
            fragment = new Login_Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        if(number==3) {
            fragment = new SignUp_Fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
