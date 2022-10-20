package vn.edu.poly.mob2041_duanmau.ACTIVITYS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import vn.edu.poly.mob2041_duanmau.MainActivity;
import vn.edu.poly.mob2041_duanmau.R;


public class LoginActivity extends AppCompatActivity {

    ConstraintLayout layout;
    TextView signUp;
    Button btnLogin;
    TextInputLayout tilAccountName, tilPass;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        viewMapping();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        sharedPreferences = getSharedPreferences("form_login", Context.MODE_PRIVATE);
        String checkName = sharedPreferences.getString("name", "");
        String checkPass = sharedPreferences.getString("pass", "");

        if(checkPass.length() > 0){
            tilAccountName.getEditText().setText(checkName);
            tilPass.getEditText().setText(checkPass);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountName = tilAccountName.getEditText().getText().toString();
                String pass = tilPass.getEditText().getText().toString();
                if (accountName.equals("")){
                    errSnackBar(layout,getString(R.string.error_account_name));
                }else if(pass.equals("")){
                    errSnackBar(layout,getString(R.string.error_pass));
                }else if(!accountName.matches(checkName)){
                    errSnackBar(layout,getString(R.string.error_account_name_2));
                }
                else if(!pass.matches(checkPass)){
                    errSnackBar(layout,getString(R.string.error_pass_2));
                }else{
                    successSnackBar(layout,getString(R.string.successful_text));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);

                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("form_login", Context.MODE_PRIVATE);
        String checkName = sharedPreferences.getString("name", "");
        String checkPass = sharedPreferences.getString("pass", "");

        if(checkPass.length() > 0){
            tilAccountName.getEditText().setText(checkName);
            tilPass.getEditText().setText(checkPass);
        }
    }

    private void viewMapping(){
        signUp = findViewById(R.id.tv_sign_up);
        layout = findViewById(R.id.activityLogin);
        tilAccountName = findViewById(R.id.input_layout_user_login);
        tilPass = findViewById(R.id.input_layout_pass_login);

        btnLogin = findViewById(R.id.button_login);
        animationLayout(layout);

    }

    private void errSnackBar(ConstraintLayout constraintLayout,String errText){
        final Snackbar snackbar = Snackbar.make(constraintLayout,"",Snackbar.LENGTH_SHORT);
        View view = getLayoutInflater().inflate(R.layout.custom_snackbar_error, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout  snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setPadding(0,0,0,0);
        TextView tv_err = view.findViewById(R.id.tv_error);
        tv_err.setText(errText);

        snackBarLayout.addView(view, 0);
        snackbar.show();
    }

    private void successSnackBar(ConstraintLayout constraintLayout,String text){
        final Snackbar snackbar = Snackbar.make(constraintLayout,"",Snackbar.LENGTH_LONG);
        View view = getLayoutInflater().inflate(R.layout.custom_success_text, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout  snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setPadding(0,0,0,0);
        TextView tv_success = view.findViewById(R.id.tv_success);
        tv_success.setText(text);

        snackBarLayout.addView(view, 0);
        snackbar.show();
    }


    private void animationLayout(ConstraintLayout layout){
        layout.setAlpha(0f);
        layout.setTranslationZ(-100);
        layout.animate().alpha(1f).translationZBy(100).setDuration(1500);
    }
}