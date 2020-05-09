package com.luiz.reminderjava.ui.fragments.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.luiz.reminderjava.BR;
import com.luiz.reminderjava.R;
import com.luiz.reminderjava.api.models.Notes;
import com.luiz.reminderjava.api.models.User;
import com.luiz.reminderjava.databinding.ProfileBinding;
import com.luiz.reminderjava.ui.base.BaseFragment;
import com.luiz.reminderjava.ui.custom_view.AlertDefault;
import com.luiz.reminderjava.util.Utils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment<ProfileBinding, ProfileViewModel> {

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public int layoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public int binding() {
        return BR.profilleViewModel;
    }

    @Override
    public ProfileViewModel viewModel() {
        return ViewModelProviders.of(this).get(ProfileViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupToolbar();
        setHasOptionsMenu(true);

        this.viewModel().loading.observe(getViewLifecycleOwner(), loadingLiveDataObserver);
        this.viewModel().loadError.observe(getViewLifecycleOwner(), errorLiveDataObserver);
        this.viewModel().user.observe(getViewLifecycleOwner(), userLiveDataObserver);
        this.viewModel().getUserInfo();
        this.viewModel().context = getContext();
    }

    private void setupToolbar() {
        getParentActivity().setSupportActionBar(this.viewDataBinding().toolbar);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.exit) {
            showDialogExit();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialogExit() {
        AlertDefault alertDefault = new AlertDefault(
                getContext(), "Atenção", "Deseja realmente sair?", true
        );

        alertDefault.addButton("Não", R.style.ButtonOutline, v -> {
            alertDefault.dismiss();
        });

        alertDefault.addButton("Sim", R.style.ButtonDefault, v -> {
            this.viewModel().logOut();
            alertDefault.dismiss();
        });
        alertDefault.show();
    }
}
