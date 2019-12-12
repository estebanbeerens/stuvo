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

//import be.thomasmore.stuvo.Adapters.ListViewAdapterRequested;
import be.thomasmore.stuvo.Adapters.ListViewAdapterRequested;
import be.thomasmore.stuvo.Database.HttpReader;
import be.thomasmore.stuvo.Database.JsonHelper;
import be.thomasmore.stuvo.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import be.thomasmore.stuvo.Models.Activity;

public class PreviousFragment extends Fragment {

    private View RootView;
    private int studentId = 0;
    private String username;

    private Context context;

    private static ListViewAdapterRequested adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.fragment_previous ,container, false);

        Bundle bundleRecieve = getArguments();
        studentId = bundleRecieve.getInt("studentId");
        username = bundleRecieve.getString("username");

        context = inflater.getContext();

        leesPreviousActivities();

        return RootView;
    }

    private void leesPreviousActivities() {
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                Log.e("666", "yes we zijn er");
                JsonHelper jsonHelper = new JsonHelper();
                List<Activity> activities = jsonHelper.getPreviousActivities(result);
                toonActivities(activities);
            }
        });
        httpReader.execute("https://beerensco.sinners.be/Maes/phpFiles/readPreviousActivitiesByStudentId.php?studentId="+studentId);
    }

    private void toonActivities(final List<Activity> activities) {
//        ArrayAdapter<Activity> adapter = new ArrayAdapter<Activity>(context, android.R.layout.simple_list_item_1, activities);
        adapter = new ListViewAdapterRequested(activities, context);

        final ListView listViewActivities = (ListView) getView().findViewById(R.id.previousFragmentListView);
        listViewActivities.setAdapter(adapter);

        listViewActivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putLong("activityId", activities.get(position).getId());
                bundle.putInt("studentId", studentId);
                bundle.putString("username", username);

                RequestDetailFragment RDF = new RequestDetailFragment();
                RDF.setArguments(bundle);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, RDF).commit();
            }
        });
    }
}
