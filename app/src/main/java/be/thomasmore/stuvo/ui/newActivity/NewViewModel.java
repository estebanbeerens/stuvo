package be.thomasmore.stuvo.ui.newActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is share fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}