package com.luiz.reminderjava.ui.fragments.guidelines;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.luiz.reminderjava.R;
import com.luiz.reminderjava.api.models.Notes;
import com.luiz.reminderjava.databinding.GuidelinesBinding;
import com.luiz.reminderjava.ui.activities.register.RegisterViewModel;
import com.luiz.reminderjava.ui.adapter.NotesAdapter;
import com.luiz.reminderjava.ui.base.BaseFragment;
import com.luiz.reminderjava.ui.custom_view.AlertDefault;
import com.luiz.reminderjava.util.Utils;
import com.mlykotom.valifi.BR;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuidelinesFragment extends BaseFragment<GuidelinesBinding, GuidelinesViewModel> {

    private NotesAdapter notesAdapter;

    private Observer<List<Notes>> notesLiveDataObserver = notes -> {
        if (notes != null && notes.size() > 0) {
            notesAdapter.setNotes(notes);
        } else {
            this.viewDataBinding().rvRemindes.setLayoutManager(new LinearLayoutManager(getContext()));
            this.viewDataBinding().rvRemindes.setAdapter(Utils.noResultAdapter(getContext(), getContext().getString(R.string.title_reminde_empty), R.drawable.ic_sad));
        }
    };

    public GuidelinesFragment() {
        // Required empty public constructor
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_guidelines;
    }

    @Override
    public int binding() {
        return BR.guidelineViewModel;
    }

    @Override
    public GuidelinesViewModel viewModel() {
        return ViewModelProviders.of(this).get(GuidelinesViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupToolbar();
        setHasOptionsMenu(true);
        initializeRecycler();

        this.viewModel().loading.observe(getViewLifecycleOwner(), loadingLiveDataObserver);
        this.viewModel().loadError.observe(getViewLifecycleOwner(), errorLiveDataObserver);
        this.viewModel().user.observe(getViewLifecycleOwner(), userLiveDataObserver);
        this.viewModel().notes.observe(getViewLifecycleOwner(), notesLiveDataObserver);
        this.viewModel().getAllNotes();
        this.viewModel().getUserInfo();
        this.viewModel().context = getContext();
    }

    private void initializeRecycler() {
        notesAdapter = new NotesAdapter();

        this.viewDataBinding().rvRemindes.setLayoutManager(new LinearLayoutManager(getContext()));
        this.viewDataBinding().rvRemindes.setAdapter(notesAdapter);
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
