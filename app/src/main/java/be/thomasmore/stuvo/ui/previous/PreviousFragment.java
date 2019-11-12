package be.thomasmore.stuvo.ui.previous;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import be.thomasmore.stuvo.R;

public class PreviousFragment extends Fragment {

    private PreviousViewModel previousViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        previousViewModel =
                ViewModelProviders.of(this).get(PreviousViewModel.class);
        View root = inflater.inflate(R.layout.fragment_previous, container, false);
        final TextView textView = root.findViewById(R.id.text_previous);
        previousViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}