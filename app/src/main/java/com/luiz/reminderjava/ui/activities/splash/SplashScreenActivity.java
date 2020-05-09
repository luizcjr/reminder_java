package com.luiz.reminderjava.ui.activities.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.luiz.reminderjava.R;
import com.luiz.reminderjava.ui.activities.login.LoginActivity;
import com.luiz.reminderjava.ui.activities.main.MainActivity;
import com.luiz.reminderjava.util.Utils;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (Utils.getApiToken() != null) {
                Utils.startActivity(this, MainActivity.class);
            } else {
                Utils.startActivity(this, LoginActivity.class);
            }
        }, 2500);
    }

    //Quando o botão de retornar físico do aparelho é apertado, nada acontece para não atrapalhar a transição de tela
    @Override
    public void onBackPressed() {

    }
}
