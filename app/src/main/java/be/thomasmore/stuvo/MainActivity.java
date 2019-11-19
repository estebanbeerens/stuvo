package be.thomasmore.stuvo;

//import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
//import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import androidx.navigation.ui.AppBarConfiguration;
//import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import be.thomasmore.stuvo.Database.HttpReader;
import be.thomasmore.stuvo.Database.HttpWriter;
import be.thomasmore.stuvo.Database.JsonHelper;
import be.thomasmore.stuvo.Fragments.FriendsFragment;
import be.thomasmore.stuvo.Fragments.HomeFragment;
import be.thomasmore.stuvo.Fragments.NewFragment;
import be.thomasmore.stuvo.Fragments.PreviousFragment;
import be.thomasmore.stuvo.Fragments.RequestedFragment;
import be.thomasmore.stuvo.Models.Activity;

//import android.view.Menu;

//import be.thomasmore.stuvo.ui.previous.PreviousFragment;

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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

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
                ReadPreviousActivities();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PreviousFragment()).commit();
                break;
            case R.id.nav_requested:
                Log.e("666666", "Requested" + "");
                ReadRequestedActivities();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RequestedFragment()).commit();
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

        final DatePicker date = (DatePicker) findViewById(R.id.newFragmentDate);
        final EditText address = (EditText) findViewById(R.id.newFragmentAddress);
        final EditText price = (EditText) findViewById(R.id.newFragmentPrice);
        final EditText amountStudent = (EditText) findViewById(R.id.newFragmentAmountPlayers);
        final EditText description = (EditText) findViewById(R.id.newFragmentDescription);
        final Spinner campus = (Spinner) findViewById(R.id.newFragmentCampus);

        //convert to String from datepicker
        String year = String.valueOf(date.getYear());
        String month = String.valueOf(date.getMonth());
        String day = String.valueOf(date.getDayOfMonth());

        // Creating activity + adding  values
        Activity activity = new Activity();

        activity.setDate(day + "-" + month + "-" + year);
        activity.setAddress(address.getText().toString());
        // NAME AANPASSEN
        activity.setName("testname aanpassen");
        activity.setPrice(Integer.parseInt(price.getText().toString()));
        activity.setAmountOfStudents(Integer.parseInt(amountStudent.getText().toString()));
        activity.setDescription(description.getText().toString());
        //CAMPUS AANPASSEN
//        activity.setCampus(campus.getSelectedItem().toString());
        activity.setCampusId(1);
        activity.setAccepted(false);

        //UIT BUNDLE HALEN EN HIER INVOEGEN
        //STUDENTID AANPASSSEN
        activity.setStudentId(1);

        Log.e("666", activity.getAddress());
        Log.e("666", activity.getDate());
        Log.e("666", activity.getName());
        Log.e("666", activity.getDescription());
        Log.e("666", activity.getAmountOfStudents()+"");
        Log.e("666", activity.getPrice()+"");
        Log.e("666", activity.getCampusId()+"");
        Log.e("666", activity.getStudentId()+"");
        Log.e("666", activity.isAccepted()+"");

        postActivity(activity);
//
    }

    private void postActivity(Activity activity){
        JsonHelper jsonHelper = new JsonHelper();
        HttpWriter httpWriter = new HttpWriter();

        httpWriter.setJsonObject(jsonHelper.getJSONActivity(activity));
        httpWriter.setOnResultReadyListener(new HttpWriter.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                toon(result);
            }
        });
        httpWriter.execute("https://beerensco.sinners.be/Maes/phpFiles/writeActivity.php");
    }

    //------------------------------------------//
    //    HIER ALLES VAN PREVIOUS  FRAGMENT     //
    //------------------------------------------//
    private void ReadPreviousActivities() {
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
                List<Activity> activities = jsonHelper.getPreviousActivities(result);
                Log.e("666", "gelukt");
            }
        });
        httpReader.execute("https://beerensco.sinners.be/activities.php");
    }
    //------------------------------------------//
    //    HIER ALLES VAN REQUESTED FRAGMENT     //
    //------------------------------------------//
    private void ReadRequestedActivities() {
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
                List<Activity> activities = jsonHelper.getRequestedActivities(result);
                Log.e("666", "gelukt");
            }
        });
        httpReader.execute("https://beerensco.sinners.be/activities.php");
    }

    //------------------------------------------//
    //    HIER ALLES HOME FRAGMENT              //
    //------------------------------------------//


    //------------------------------------------//
    //    HIER ALLES VAN FRIENDS FRAGMENT       //
    //------------------------------------------//



    private void toon(String tekst)
    {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_LONG).show();
    }
}
