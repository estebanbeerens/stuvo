package be.thomasmore.stuvo.ui.previous;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PreviousViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PreviousViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}