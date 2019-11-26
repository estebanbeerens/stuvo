package be.thomasmore.stuvo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
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
import be.thomasmore.stuvo.Models.Campus;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextInputLayout inputLayoutName, inputLayoutAddress, inputLayoutPrice, inputLayoutAmountOfStudents, inputLayoutDescription, inputLayoutCampus;
    private AutoCompleteTextView inputCampus;
    private DatePicker datePicker;

    private EditText inputName, inputAddress, inputPrice, inputAmountOfStudents, inputDescription;
    private ArrayList<String> campusNames = new ArrayList<String>();
    private ArrayList<Campus> campusesFromDropdown = new ArrayList<Campus>();

    private Campus campus;


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
                FillCampusDropdown();
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
        //EXTRA VOOR VALIDATIE + LEZEN ACTIVITY
        ReadInputfields();

        inputCampus.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    // on focus off
                    String str = inputCampus.getText().toString();

                    ListAdapter listAdapter = inputCampus.getAdapter();
                    for(int i = 0; i < listAdapter.getCount(); i++) {
                        String temp = listAdapter.getItem(i).toString();
                        if(str.compareTo(temp) == 0) {
                            return;
                        }
                    }
                    inputCampus.setText("");

                }
            }
        });

        //CHECKEN OP VALIDE CAMPUS
        String str = inputCampus.getText().toString();
        Boolean b = true;
        ListAdapter listAdapter = inputCampus.getAdapter();
        for(int i = 0; i < listAdapter.getCount(); i++) {
            String temp = listAdapter.getItem(i).toString();
            if(str.compareTo(temp) == 0) {
                b = false;
                break;
            }

        }
        if (b){
            inputCampus.setText("");
        }


        //CHECKEN OPVALIDE FORM
        if (validateNewActivity()) {
            readCampusAndCreateAndPostActivity();
        }
    }

    private void readCampusAndCreateAndPostActivity() {
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
//                List<Campus> campuses = jsonHelper.getCampuses(result);
                campus = jsonHelper.getCampus(result);

                // Creating activity + adding  values
                Activity activity = CreateActivity();
//
                // ACTIVITY WEGSCHRIJVEN IN DB
                postActivity(activity);
            }
        });
        httpReader.execute("https://beerensco.sinners.be/Maes/phpFiles/readCampusByName.php?name=\""+ inputCampus.getText().toString() +"\"");
    }

    private Activity CreateActivity() {
        Activity activity = new Activity();

        activity.setDate(getDateString(datePicker));
        activity.setName(inputName.getText().toString().trim());
        activity.setAddress(inputAddress.getText().toString().trim());
        activity.setDescription(inputDescription.getText().toString().trim());
        activity.setPrice(Integer.parseInt(inputPrice.getText().toString()));
        activity.setAmountOfStudents(Integer.parseInt(inputAmountOfStudents.getText().toString()));
        activity.setAccepted(false);

        //CAMPUSID, STUDENTID AANPASSEN
        activity.setCampusId(Math.toIntExact(campus.getId()));
        activity.setStudentId(1);

        return activity;
    }

    private String getDateString(DatePicker datePicker) {
        //convert to String from datepicker
        String year = String.valueOf(datePicker.getYear());
        String month = String.valueOf(datePicker.getMonth());
        String day = String.valueOf(datePicker.getDayOfMonth());
        return day + "-" + month + "-" + year;
    }

    private void ReadInputfields() {
        inputLayoutName = findViewById(R.id.newFragmentName);
        inputName = inputLayoutName.getEditText();

        inputLayoutAddress = findViewById(R.id.newFragmentAddress);
        inputAddress = inputLayoutAddress.getEditText();

        inputLayoutPrice = findViewById(R.id.newFragmentPrice);
        inputPrice = inputLayoutPrice.getEditText();

        inputLayoutAmountOfStudents = findViewById(R.id.newFragmentAmountPlayers);
        inputAmountOfStudents = inputLayoutAmountOfStudents.getEditText();

        inputLayoutDescription = findViewById(R.id.newFragmentDescription);
        inputDescription = inputLayoutDescription.getEditText();

        inputLayoutCampus = findViewById(R.id.newFragmentCampusLayout);
        inputCampus = findViewById(R.id.newFragmentCampus);

        datePicker = findViewById(R.id.newFragmentDate);
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

        if (!validateCampus()) {
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

    private boolean validateCampus() {
        if (inputCampus.getText().toString().trim().isEmpty()) {
            inputLayoutCampus.setError(getString(R.string.err_msg_campus));
            requestFocus(inputCampus);
            return false;
        } else {
            inputLayoutCampus.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void FillCampusDropdown() {
        Log.e("666", "HIER GERAKEN WE TOCH1");
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
                List<Campus> campuses = jsonHelper.getCampuses(result);
                String tekst = " - ";

                Log.e("666", "HIER GERAKEN WE TOCH2");

                for (int i = 0; i < campuses.size(); i++) {
                    //DROPDOWN VULLEN? TOT HIER WERKT AL
                    tekst += campuses.get(i).getName() + " - ";
                    campusNames.add(campuses.get(i).getName());
                    campusesFromDropdown.add(campuses.get(i));
                }

                Log.e("666", campusNames.toString());
                toon(tekst);

                AutoCompleteTextView editText = findViewById(R.id.newFragmentCampus);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, campusNames);

                editText.setAdapter(adapter);
            }
        });
        httpReader.execute("https://beerensco.sinners.be/Maes/phpFiles/readCampuses.php");
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


    private void toon(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_LONG).show();
    }
}
