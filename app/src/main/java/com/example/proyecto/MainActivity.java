package com.example.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, settings, share, about, logout, map, contacto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageSlider imageSlider = findViewById(R.id.imagesSlider1);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        about = findViewById(R.id.about);
        logout = findViewById(R.id.logout);
        settings = findViewById(R.id.settings);
        share = findViewById(R.id.share);
        map = findViewById(R.id.map);
        contacto = findViewById(R.id.contact);
        menu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openDrawer(drawerLayout);
        }
    });

       ;
    home.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            recreate();
        }
    });

    share.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            redirectActivity(MainActivity.this, ShareActivity.class);
        }
    });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, AboutActivity.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, LoginActivity.class);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, MapsActivity.class);
            }
        });
        contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MainActivity.this, ContactoActivity.class);
            }
        });

        slideModels.add(new SlideModel(R.drawable.mecan1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mecan2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mecan3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mecan4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mecan5, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);



}//oncreate


    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);


    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);

        }

    }


    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}
