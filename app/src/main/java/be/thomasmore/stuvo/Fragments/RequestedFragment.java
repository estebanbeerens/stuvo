package be.thomasmore.stuvo.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import be.thomasmore.stuvo.Adapters.ListViewAdapterRequested;
import be.thomasmore.stuvo.Database.HttpReader;
import be.thomasmore.stuvo.Database.JsonHelper;
import be.thomasmore.stuvo.R;

import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import be.thomasmore.stuvo.Models.Activity;

public class RequestedFragment extends Fragment {

    private View RootView;
    private int studentId = 0;
    private String username;

    private Context context;

    private static ListViewAdapterRequested adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RootView = inflater.inflate(R.layout.fragment_requested, container, false);

        Bundle bundleRecieve = getArguments();
        studentId = bundleRecieve.getInt("studentId");
        username = bundleRecieve.getString("username");

        context = inflater.getContext();

        leesRequestedActivities();

        return RootView;
    }

    private void leesRequestedActivities() {
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                Log.e("666", "yes we zijn er");
                JsonHelper jsonHelper = new JsonHelper();
                List<Activity> activities = jsonHelper.getRequestedActivities(result);
                toonActivities(activities);
            }
        });
        httpReader.execute("https://beerensco.sinners.be/Maes/phpFiles/readRequestedActivitiesByStudentId.php?studentId="+studentId);
    }

    private void toonActivities(final List<Activity> activities) {
//        ArrayAdapter<Activity> adapter = new ArrayAdapter<Activity>(context, android.R.layout.simple_list_item_1, activities);
        adapter = new ListViewAdapterRequested(activities, context);

        final ListView listViewActivities = (ListView) getView().findViewById(R.id.requestedFragmentListView);
        listViewActivities.setAdapter(adapter);

        listViewActivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Activity activity= activities.get(position);

                Snackbar.make(view, activity.getName()+"\n"+activity.getDate()+" API: "+activity.getDescription(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
    }
}
