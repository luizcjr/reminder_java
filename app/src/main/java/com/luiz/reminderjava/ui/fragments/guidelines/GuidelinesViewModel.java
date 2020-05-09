package com.luiz.reminderjava.ui.fragments.guidelines;

import androidx.lifecycle.MutableLiveData;

import com.luiz.reminderjava.api.models.Notes;
import com.luiz.reminderjava.api.models.User;
import com.luiz.reminderjava.api.responses.NotesResponse;
import com.luiz.reminderjava.api.responses.UserResponse;
import com.luiz.reminderjava.ui.activities.include_reminde.IncludeRemindeActivity;
import com.luiz.reminderjava.ui.activities.login.LoginActivity;
import com.luiz.reminderjava.ui.base.BaseViewModel;
import com.luiz.reminderjava.util.Utils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GuidelinesViewModel extends BaseViewModel {

    public MutableLiveData<User> user = new MutableLiveData<>();
    public MutableLiveData<List<Notes>> notes = new MutableLiveData<>();

    public void getAllNotes() {
        this.beforeRequest();

        disposable.add(
                apiRepository.getAllNotes()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<NotesResponse>() {

                            @Override
                            public void onSuccess(NotesResponse notesResponse) {
                                afterRequest();

                                notes.postValue(notesResponse.getNotes());
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                afterRequest(e);

                                notes.postValue(null);
                            }
                        })
        );
    }

    public void getUserInfo() {
        this.beforeRequest();

        disposable.add(
                apiRepository.getUserInfo(Utils.lastUserSession().getId())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<UserResponse>() {

                            @Override
                            public void onSuccess(UserResponse userResponse) {
                                afterRequest();

                                user.postValue(userResponse.getUser());
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                afterRequest(e);

                                user.postValue(null);
                            }
                        })
        );
    }

    public void redirectToIncludeReminder() {
        Utils.startActivity(context, IncludeRemindeActivity.class);
    }

    private void redirectToLogin() {
        Utils.startActivity(context, LoginActivity.class);
    }

    public void logOut() {
        Utils.setApiToken(null);
        Utils.setLastUserSession(null);
        redirectToLogin();
    }

    @Override
    protected void onCleared() {
        disposable.clear();

        super.onCleared();
    }
}
