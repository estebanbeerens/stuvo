package be.thomasmore.stuvo.Fragments;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import be.thomasmore.stuvo.Adapters.ListViewAdapterRequested;
import be.thomasmore.stuvo.Database.HttpReader;
import be.thomasmore.stuvo.Database.JsonHelper;
import be.thomasmore.stuvo.Models.Activity;
import be.thomasmore.stuvo.R;

public class RequestDetailFragment extends Fragment {
    Bundle bundle = new Bundle();

    private View RootView;
    private int studentId = 0;
    private Long activityId;
    private String username;

    private Context context;

    private static ListViewAdapterRequested adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RootView = inflater.inflate(R.layout.fragment_request_detail, container, false);

        Bundle bundleRecieve = getArguments();
        studentId = bundleRecieve.getInt("studentId");
        username = bundleRecieve.getString("username");
        activityId = bundleRecieve.getLong("activityId");

        context = inflater.getContext();

        Log.e("666", "s"+studentId + " a"+activityId + " u"+username);

        leesDetailActivity();

        return RootView;
    }

    private void leesDetailActivity() {
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                Log.e("666", "yes we zijn er");
                JsonHelper jsonHelper = new JsonHelper();
                Activity activity = jsonHelper.getActivity(result);
                toonActivity(activity);
            }


        });
        httpReader.execute("https://beerensco.sinners.be/Maes/phpFiles/readActivityById.php?id="+activityId);
    }
    private void toonActivity(Activity activity) {
        Log.e("666", activity.getName()+"");
       TextView tv = RootView.findViewById(R.id.requestDetailFragmentName);
       tv.setText(activity.getName());
    }
}
