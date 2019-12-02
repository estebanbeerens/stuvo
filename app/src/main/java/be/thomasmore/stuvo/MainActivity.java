package be.thomasmore.stuvo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import be.thomasmore.stuvo.Fragments.FriendsFragment;
import be.thomasmore.stuvo.Fragments.HomeFragment;
import be.thomasmore.stuvo.Fragments.NewFragment;
import be.thomasmore.stuvo.Fragments.PreviousFragment;
import be.thomasmore.stuvo.Fragments.RequestedFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_app_bar_open_drawer_description, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        toggle.syncState();

        Log.e("666666", savedInstanceState + "");
        if (savedInstanceState == null) {
            Log.e("666666", "inside, sethome" + "");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Log.e("666666", "Home" + "");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_friends:
                Log.e("666666", "Friends" + "");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FriendsFragment()).commit();
                break;
            case R.id.nav_new:
                Log.e("666666", "New" + "");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewFragment()).commit();
                break;
            case R.id.nav_previous:
                Log.e("666666", "Previous" + "");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PreviousFragment()).commit();
                break;
            case R.id.nav_requested:
                Log.e("666666", "Requested" + "");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RequestedFragment()).commit();
                break;
            case R.id.nav_logout:
                Log.e("666666", "Logout" + "");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    //------------------------------------------//
    //    HIER ALLES VAN NEW REQUEST FRAGMENT   //
    //------------------------------------------//

    public void newFragmentrequestButton_onClick(View v) {
        NewFragment newFragment = new NewFragment();
        newFragment.newFragmentrequestButton_onClick();
    }


    //------------------------------------------//
    //    HIER ALLES VAN PREVIOUS  FRAGMENT     //
    //------------------------------------------//


    //------------------------------------------//
    //    HIER ALLES VAN REQUESTED FRAGMENT     //
    //------------------------------------------//


    //------------------------------------------//
    //    HIER ALLES HOME FRAGMENT              //
    //------------------------------------------//


    //------------------------------------------//
    //    HIER ALLES VAN FRIENDS FRAGMENT       //
    //------------------------------------------//


    public void toon(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_LONG).show();
    }
}
