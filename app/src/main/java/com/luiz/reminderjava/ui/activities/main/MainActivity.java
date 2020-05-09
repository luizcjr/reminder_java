package com.luiz.reminderjava.ui.activities.main;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.luiz.reminderjava.BR;
import com.luiz.reminderjava.R;
import com.luiz.reminderjava.databinding.MainBinding;
import com.luiz.reminderjava.ui.base.BaseActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseActivity<MainBinding, MainViewModel> {

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int binding() {
        return BR.mainViewModel;
    }

    @Override
    public MainViewModel viewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        this.viewModel().verifyHasToken();
    }

    protected NavOptions getNavOptions() {

        NavOptions navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.activity_slide_pop_vertical_open_in)
                .setExitAnim(R.anim.activity_slide_pop_vertical_open_out)
                .setPopEnterAnim(R.anim.activity_slide_pop_vertical_open_in)
                .setPopExitAnim(R.anim.activity_slide_pop_vertical_open_out)
                .build();

        return navOptions;
    }
}
