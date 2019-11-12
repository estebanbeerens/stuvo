package be.thomasmore.stuvo.ui.requested;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RequestedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RequestedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}