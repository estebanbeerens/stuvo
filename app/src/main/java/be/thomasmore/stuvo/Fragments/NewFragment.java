package be.thomasmore.stuvo.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import be.thomasmore.stuvo.Database.HttpReader;
import be.thomasmore.stuvo.Database.HttpWriter;
import be.thomasmore.stuvo.Database.JsonHelper;
import be.thomasmore.stuvo.Models.Activity;
import be.thomasmore.stuvo.Models.Campus;
import be.thomasmore.stuvo.R;

public class NewFragment extends Fragment {

    private TextInputLayout inputLayoutName, inputLayoutAddress, inputLayoutPrice, inputLayoutAmountOfStudents, inputLayoutDescription, inputLayoutCampus;
    private AutoCompleteTextView inputCampus;
    private DatePicker datePicker;

    private EditText inputName, inputAddress, inputPrice, inputAmountOfStudents, inputDescription;
    private ArrayList<String> campusNames = new ArrayList<String>();
    private ArrayList<Campus> campusesFromDropdown = new ArrayList<Campus>();

    private Campus campus;


    private View RootView ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FillCampusDropdown();

        RootView = inflater.inflate(R.layout.fragment_new, container, false);

        final Button button = (Button) RootView.findViewById(R.id.newFragment_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newFragmentrequestButton_onClick();
            }
        });


        return RootView;
    }

    //------------------------------------------//
    //    HIER ALLES VAN NEW REQUEST FRAGMENT   //
    //------------------------------------------//



    public void newFragmentrequestButton_onClick() {
        //EXTRA VOOR VALIDATIE + LEZEN ACTIVITY
        ReadInputfields();



//        CHECKEN OP VALIDE CAMPUS
        String str = inputCampus.getText().toString();
        boolean b = true;
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

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new RequestedFragment()).commit();
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
        activity.setCampusId(Integer.parseInt(campus.getId()+""));
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


        View v = getView();

        inputLayoutName = (TextInputLayout) RootView.findViewById(R.id.newFragmentName);
        inputName = inputLayoutName.getEditText();

        inputLayoutAddress = RootView.findViewById(R.id.newFragmentAddress);
        inputAddress = inputLayoutAddress.getEditText();

        inputLayoutPrice = RootView.findViewById(R.id.newFragmentPrice);
        inputPrice = inputLayoutPrice.getEditText();

        inputLayoutAmountOfStudents = RootView.findViewById(R.id.newFragmentAmountPlayers);
        inputAmountOfStudents = inputLayoutAmountOfStudents.getEditText();

        inputLayoutDescription = RootView.findViewById(R.id.newFragmentDescription);
        inputDescription = inputLayoutDescription.getEditText();

        inputLayoutCampus = RootView.findViewById(R.id.newFragmentCampusLayout);
        inputCampus = RootView.findViewById(R.id.newFragmentCampus);

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

        datePicker = RootView.findViewById(R.id.newFragmentDate);
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
                Log.e("666", result);
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
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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


                AutoCompleteTextView editText = getView().findViewById(R.id.newFragmentCampus);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, campusNames);

                editText.setAdapter(adapter);

            }
        });
        httpReader.execute("https://beerensco.sinners.be/Maes/phpFiles/readCampuses.php");
    }



}
