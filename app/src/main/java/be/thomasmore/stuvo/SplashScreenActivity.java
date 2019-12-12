package be.thomasmore.stuvo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1500; // aantal miliseconden seconden splash screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              Intent splashScreenIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
              startActivity(splashScreenIntent);
              finish();
          }
        }, SPLASH_TIME_OUT
        );
    }
}
