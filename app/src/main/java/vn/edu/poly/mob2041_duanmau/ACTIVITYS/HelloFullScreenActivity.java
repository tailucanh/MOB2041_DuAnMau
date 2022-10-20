package vn.edu.poly.mob2041_duanmau.ACTIVITYS;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.poly.mob2041_duanmau.R;


public class HelloFullScreenActivity extends AppCompatActivity {

    TextView text1, text2;
    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_full_screen);
        viewMapping();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);

    }


   private void viewMapping(){
        background = findViewById(R.id. background_hello_screen);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        animationImageView(background);
        animationTextView(text1);
        animationTextView(text2);

    }



    private void animationTextView(TextView tv){
        tv.setAlpha(0f);
        tv.setTranslationZ(-150);
        tv.animate().alpha(1f).translationZBy(150).setDuration(1000);

    }


    private void animationImageView(ImageView img){
        img.setAlpha(0f);
        img.setTranslationZ(-150);
        img.animate().alpha(1f).translationZBy(150).setDuration(1000);
    }




}