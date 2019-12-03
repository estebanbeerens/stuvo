package be.thomasmore.stuvo.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import be.thomasmore.stuvo.R;

public class RequestedFragment extends Fragment {

    private View RootView;
    private int studentId = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RootView = inflater.inflate(R.layout.fragment_requested, container, false);

        Bundle bundle = getArguments();
        studentId = bundle.getInt("studentId");

        return RootView;
    }
}
