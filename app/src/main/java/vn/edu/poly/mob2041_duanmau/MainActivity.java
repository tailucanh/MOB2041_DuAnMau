package vn.edu.poly.mob2041_duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import vn.edu.poly.mob2041_duanmau.ACTIVITYS.LoginActivity;
import vn.edu.poly.mob2041_duanmau.FRAGMENT.BookManagementFragment;
import vn.edu.poly.mob2041_duanmau.FRAGMENT.HomeFragment;
import vn.edu.poly.mob2041_duanmau.FRAGMENT.KindOfBookFragment;
import vn.edu.poly.mob2041_duanmau.FRAGMENT.LibrarianFragment;
import vn.edu.poly.mob2041_duanmau.FRAGMENT.LoanSlipFragment;
import vn.edu.poly.mob2041_duanmau.FRAGMENT.MemberFragment;
import vn.edu.poly.mob2041_duanmau.FRAGMENT.RevenueFragment;
import vn.edu.poly.mob2041_duanmau.FRAGMENT.TopBookFragment;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    public static DrawerLayout drawerLayout;
    private boolean isBackPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewMapping();



        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new HomeFragment()).commit();
        //Navigation  view
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new HomeFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.ic_librarian:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new LibrarianFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.ic_loan_slip:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new LoanSlipFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.ic_kind_of_book:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new KindOfBookFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.ic_book:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new BookManagementFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.ic_member:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new MemberFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.ic_top_book:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new TopBookFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.ic_revenue:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new RevenueFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.ic_change_pass:
                        dialogChangePass(MainActivity.this);
                        break;
                    case R.id.ic_exit:
                        dialogLogout(MainActivity.this);
                        break;
                }
                return true;
            }
        });
    }

    private void viewMapping(){
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_View);

    }


    @Override
    public void onBackPressed() {
        if (isBackPressedOnce) {
            super.onBackPressed();
            return;
        }
        Toast.makeText(MainActivity.this,"Chạm lần nữa để thoát !", Toast.LENGTH_SHORT).show();
        isBackPressedOnce = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressedOnce = false;
            }
        },2000);
    }


    public void dialogChangePass(Context context){

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_change_pass);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        SharedPreferences sharedPreferences = getSharedPreferences("form_login", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String password = sharedPreferences.getString("pass", "");

        TextView tvName = dialog.findViewById(R.id.tvUsername);
        TextInputLayout tvPass = dialog.findViewById(R.id.tilPass);
        TextInputLayout changePass = dialog.findViewById(R.id.tilChangePass);
        tvName.setText(name);
        tvPass.getEditText().setText(password);


        dialog.findViewById(R.id.btnChange).setOnClickListener(btn ->{
            if(changePass.getEditText().getText().toString().equals("")){
                Toast.makeText(context, "Hãy nhập mật khẩu mới.", Toast.LENGTH_SHORT).show();
            }else if(changePass.getEditText().getText().toString().equals(password)){
                Toast.makeText(context, "Trùng mật khẩu cũ! Hãy nhập lại.", Toast.LENGTH_SHORT).show();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Đổi mật khẩu?");
                builder.setIcon(R.drawable.ic_exit);
                builder.setMessage("Bạn có chắc chắn đổi mật khẩu không ? ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("pass",changePass.getEditText().getText().toString());
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                        dialogInterface.dismiss();
                        dialog.cancel();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Đã hủy", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        dialog.show();

    }


    private void dialogLogout(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Đăng xuất?");
        builder.setIcon(R.drawable.ic_exit);
        builder.setMessage("Bạn có chắc chắn đăng xuất không ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Đã hủy", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}