package be.thomasmore.stuvo.Database;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.thomasmore.stuvo.Models.Activity;
import be.thomasmore.stuvo.Models.Student;
import be.thomasmore.stuvo.Models.Campus;

public class JsonHelper {

    public List<Activity> getPreviousActivities(String jsonTekst) {
        List<Activity> activities = new ArrayList<Activity>();

        try {
            JSONArray jsonArrayActivities = new JSONArray(jsonTekst);

            for (int i = 0; i < jsonArrayActivities.length(); i++) {
                JSONObject jsonObjectActivity = jsonArrayActivities.getJSONObject(i);

                if (jsonObjectActivity.getBoolean("accepted")) {
                    Activity activity = new Activity();
                    activity.setId(jsonObjectActivity.getInt("id"));
                    activity.setName(jsonObjectActivity.getString("name"));
                    activity.setDate(jsonObjectActivity.getString("date"));
                    activity.setAddress(jsonObjectActivity.getString("address"));
                    activity.setPrice(jsonObjectActivity.getInt("price"));
                    activity.setAmountOfStudents(jsonObjectActivity.getInt("amountOfStudents"));
                    activity.setDescription(jsonObjectActivity.getString("description"));
                    activity.setCampusId(jsonObjectActivity.getInt("campusId"));
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

                if (!jsonObjectActivity.getBoolean("accepted")) {
                    Activity activity = new Activity();
                    activity.setId(jsonObjectActivity.getInt("id"));
                    activity.setName(jsonObjectActivity.getString("name"));
                    activity.setDate(jsonObjectActivity.getString("date"));
                    activity.setAddress(jsonObjectActivity.getString("address"));
                    activity.setPrice(jsonObjectActivity.getInt("price"));
                    activity.setAmountOfStudents(jsonObjectActivity.getInt("amountOfStudents"));
                    activity.setDescription(jsonObjectActivity.getString("description"));
                    activity.setCampusId(jsonObjectActivity.getInt("campusId"));
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


    public JSONObject getJSONActivity(Activity activity) {
        JSONObject jsonObjectActivity = new JSONObject();
        try {
            jsonObjectActivity.put("name", activity.getName());
            jsonObjectActivity.put("date", activity.getDate());
            jsonObjectActivity.put("address", activity.getAddress());
            jsonObjectActivity.put("price", activity.getPrice() + "");
            jsonObjectActivity.put("amountOfStudents", activity.getAmountOfStudents() + "");
            jsonObjectActivity.put("description", activity.getDescription());
            if (activity.isAccepted()) {
                jsonObjectActivity.put("accepted", "1");
            } else {
                jsonObjectActivity.put("accepted", "0");
            }
            jsonObjectActivity.put("studentId", activity.getStudentId() + "");
            jsonObjectActivity.put("campusId", activity.getCampusId() + "");
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jsonObjectActivity;
    }

    public Student getJSONStudent(String jsonTekst) {
        Student student = new Student();
        try {
            JSONArray jsonArrayStudent = new JSONArray(jsonTekst);
            student.setId(jsonArrayStudent.getJSONObject(0).getInt("id"));
            student.setNumber(jsonArrayStudent.getJSONObject(0).getString("username"));
            student.setFirstName(jsonArrayStudent.getJSONObject(0).getString("firstName"));
            student.setLastName(jsonArrayStudent.getJSONObject(0).getString("lastName"));
            student.setPassword(jsonArrayStudent.getJSONObject(0).getString("password"));
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return student;
    }


    public List<Campus> getCampuses(String jsonTekst) {
        List<Campus> campuses = new ArrayList<Campus>();

        try {
            JSONArray jsonArrayCampuses = new JSONArray(jsonTekst);

            for (int i = 0; i < jsonArrayCampuses.length(); i++) {
                JSONObject jsonObjectActivity = jsonArrayCampuses.getJSONObject(i);

                Campus campus = new Campus();
                campus.setId(jsonObjectActivity.getInt("id"));
                campus.setName(jsonObjectActivity.getString("name"));

                campuses.add(campus);

            }
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return campuses;
    }

    public Campus getCampus(String jsonTekst) {
        Campus campus = new Campus();
        try {
            JSONArray jsonArrayCampuses = new JSONArray(jsonTekst);

            JSONObject jsonObjectCampus = jsonArrayCampuses.getJSONObject(0);

            campus.setId(jsonObjectCampus.getInt("id"));
            campus.setName(jsonObjectCampus.getString("name"));
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return campus;
    }
}