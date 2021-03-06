package com.luiz.reminderjava.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.luiz.reminderjava.api.models.User;
import com.luiz.reminderjava.ui.activities.main.MainActivity;
import com.luiz.reminderjava.util.Utils;

public abstract class BaseFragment<T extends ViewDataBinding, D extends BaseViewModel> extends Fragment {

    private void initializeDatabinding(LayoutInflater inflater, ViewGroup container) {
        vDatabinding = DataBindingUtil.inflate(inflater, layoutId(), container, false);
        vModel = viewModel();
        vDatabinding.setVariable(binding(), vModel);
        vDatabinding.executePendingBindings();
        mRootView = vDatabinding.getRoot();
    }

    public T viewDataBinding() {
        return vDatabinding;
    }

    @LayoutRes
    public abstract int layoutId();

    public abstract int binding();

    public abstract D viewModel();

    private T vDatabinding;
    private D vModel;
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        initializeDatabinding(inflater, container);

        return mRootView;
    }

    protected Observer<Boolean> loadingLiveDataObserver = isLoading -> {
        if (isLoading) {
            Utils.onStartLoading(getContext());
        } else {
            Utils.onStopLoading();
        }
    };

    protected Observer<String> errorLiveDataObserver = error -> {
        if (error != null) {
            Utils.alertError(getContext(), error);
        }
    };

    protected Observer<User> userLiveDataObserver = user -> {
        if (user != null) {
            getParentActivity().setTitle(user.getName());
        }
    };

    protected MainActivity getParentActivity() {
        return ((MainActivity) getActivity());
    }
}
