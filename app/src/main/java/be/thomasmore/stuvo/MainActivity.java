package be.thomasmore.stuvo;

//import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.google.android.material.textfield.TextInputLayout;

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
    private TextInputLayout inputLayoutName, inputLayoutAddress, inputLayoutPrice, inputLayoutAmountOfStudents, inputLayoutDescription;
    private EditText inputName, inputAddress, inputPrice, inputAmountOfStudents, inputDescription;

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
//        final Spinner campus = (Spinner) findViewById(R.id.newFragmentCampus);

        //EXTRA VOOR VALIDATIE
        inputLayoutName = (TextInputLayout) findViewById(R.id.newFragmentName);
        inputName = inputLayoutName.getEditText();

        inputLayoutAddress = (TextInputLayout) findViewById(R.id.newFragmentAddress);
        inputAddress = inputLayoutAddress.getEditText();

        inputLayoutPrice = (TextInputLayout) findViewById(R.id.newFragmentPrice);
        inputPrice = inputLayoutPrice.getEditText();

        inputLayoutAmountOfStudents = (TextInputLayout) findViewById(R.id.newFragmentAmountPlayers);
        inputAmountOfStudents = inputLayoutAmountOfStudents.getEditText();

        inputLayoutDescription = (TextInputLayout) findViewById(R.id.newFragmentDescription);
        inputDescription = inputLayoutDescription.getEditText();

        if (validateNewActivity()) {


            //convert to String from datepicker
            String year = String.valueOf(date.getYear());
            String month = String.valueOf(date.getMonth());
            String day = String.valueOf(date.getDayOfMonth());

            // Creating activity + adding  values
            Activity activity = new Activity();

            activity.setDate(day + "-" + month + "-" + year);
            activity.setName(inputName.getText().toString().trim());
            activity.setAddress(inputAddress.getText().toString().trim());
            activity.setDescription(inputDescription.getText().toString().trim());
            activity.setPrice(Integer.parseInt(inputPrice.getText().toString()));
            activity.setAmountOfStudents(Integer.parseInt(inputAmountOfStudents.getText().toString()));
            //CAMPUS AANPASSEN
//        activity.setCampus(campus.getSelectedItem().toString());
            activity.setCampusId(1);
            activity.setAccepted(false);

            //UIT BUNDLE HALEN EN HIER INVOEGEN
            //STUDENTID AANPASSSEN
            activity.setStudentId(1);

//        Log.e("666", activity.getAddress());
//        Log.e("666", activity.getDate());
//        Log.e("666", activity.getName());
//        Log.e("666", activity.getDescription());
////        Log.e("666", activity.getAmountOfStudents()+"");
////        Log.e("666", activity.getPrice()+"");
//        Log.e("666", activity.getCampusId()+"");
//        Log.e("666", activity.getStudentId()+"");
//        Log.e("666", activity.isAccepted()+"");


            postActivity(activity);
        }
    }

    private Boolean validateNewActivity() {
        boolean b = true;

        if (!validateName()) {
            b = false;
        }

        if (!validateAddress()) {
            b = false;
        }

        if (!validateDescription()) {
            b = false;
        }

        if (!validateAmountOfStudents()) {
            b = false;
        }

        if (!validatePrice()) {
            b = false;
        }

        return b;
    }

    private void postActivity(Activity activity) {
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

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAddress() {
        if (inputAddress.getText().toString().trim().isEmpty()) {
            inputLayoutAddress.setError(getString(R.string.err_msg_address));
            requestFocus(inputAddress);
            return false;
        } else {
            inputLayoutAddress.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDescription() {
        if (inputDescription.getText().toString().trim().isEmpty()) {
            inputLayoutDescription.setError(getString(R.string.err_msg_description));
            requestFocus(inputDescription);
            return false;
        } else {
            inputLayoutDescription.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAmountOfStudents() {
        if (inputAmountOfStudents.getText().toString().trim().isEmpty()) {
            inputLayoutAmountOfStudents.setError(getString(R.string.err_msg_amountOfStudents));

            requestFocus(inputAmountOfStudents);
            return false;
        } else {
            inputLayoutAmountOfStudents.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePrice() {
        if (inputPrice.getText().toString().trim().isEmpty()) {
            inputLayoutPrice.setError(getString(R.string.err_msg_price));
            requestFocus(inputPrice);
            return false;
        } else {
            inputLayoutPrice.setErrorEnabled(false);
        }

        return true;
    }


    //ANDERE VALIDATES HIER

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
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


    private void toon(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_LONG).show();
    }
}
