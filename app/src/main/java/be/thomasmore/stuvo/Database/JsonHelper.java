package be.thomasmore.stuvo.Database;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import be.thomasmore.stuvo.Models.Activity;

public class JsonHelper {

    public List<Activity> getPreviousActivities(String jsonTekst) {
        List<Activity> activities = new ArrayList<Activity>();

        try {
            JSONArray jsonArrayActivities = new JSONArray(jsonTekst);

            for (int i = 0; i < jsonArrayActivities.length(); i++) {
                JSONObject jsonObjectActivity = jsonArrayActivities.getJSONObject(i);

                if (jsonObjectActivity.getBoolean("accepted")){
                    Activity activity = new Activity();
                    activity.setId(jsonObjectActivity.getInt("id"));
                    activity.setDate(jsonObjectActivity.getString("date"));
                    activity.setAddress(jsonObjectActivity.getString("address"));
                    activity.setPrice(jsonObjectActivity.getInt("price"));
                    activity.setAmountOfStudents(jsonObjectActivity.getInt("amountOfStudents"));
                    activity.setDescription(jsonObjectActivity.getString("description"));
                    activity.setCampus(jsonObjectActivity.getString("campus"));
                    activity.setAccepted(jsonObjectActivity.getBoolean("accepted"));
                    activity.setStudentId(jsonObjectActivity.getInt("studentId"));

                    activities.add(activity);
                }
            }
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return activities;
    }

    public List<Activity> getRequestedActivities(String jsonTekst) {
        List<Activity> activities = new ArrayList<Activity>();

        try {
            JSONArray jsonArrayActivities = new JSONArray(jsonTekst);

            for (int i = 0; i < jsonArrayActivities.length(); i++) {
                JSONObject jsonObjectActivity = jsonArrayActivities.getJSONObject(i);

                if (!jsonObjectActivity.getBoolean("accepted")){
                    Activity activity = new Activity();
                    activity.setId(jsonObjectActivity.getInt("id"));
                    activity.setDate(jsonObjectActivity.getString("date"));
                    activity.setAddress(jsonObjectActivity.getString("address"));
                    activity.setPrice(jsonObjectActivity.getInt("price"));
                    activity.setAmountOfStudents(jsonObjectActivity.getInt("amountOfStudents"));
                    activity.setDescription(jsonObjectActivity.getString("description"));
                    activity.setCampus(jsonObjectActivity.getString("campus"));
                    activity.setAccepted(jsonObjectActivity.getBoolean("accepted"));
                    activity.setStudentId(jsonObjectActivity.getInt("studentId"));

                    activities.add(activity);
                }
            }
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return activities;
    }
}