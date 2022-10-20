package vn.edu.poly.mob2041_duanmau.ACTIVITYS;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.poly.mob2041_duanmau.DAO.LibrarianDAO;
import vn.edu.poly.mob2041_duanmau.DATABASE.MyDatabaseImage;
import vn.edu.poly.mob2041_duanmau.DTO.LibrariansDTO;
import vn.edu.poly.mob2041_duanmau.FRAGMENT.HomeFragment;
import vn.edu.poly.mob2041_duanmau.FRAGMENT.LibrarianFragment;
import vn.edu.poly.mob2041_duanmau.MainActivity;
import vn.edu.poly.mob2041_duanmau.R;

public class DialogAddLibrarian extends AppCompatActivity {

    LinearLayout layoutAdd;
    CircleImageView imgAvatar;
    EditText nameLib,phoneLib,userLib;
    TextInputLayout passLib;
    Button btnAdd;
    MyDatabaseImage myDatabaseImage;
    Bitmap bitmapImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_librarians);
        findViewId();
        findViewById(R.id.icBack).setOnClickListener(ic ->{
            onBackPressed();
        });
        findViewById(R.id.ic_add_img).setOnClickListener(ic ->{
            ImagePicker.with(this).crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();
        });
       btnAdd.setOnClickListener(btn->{
           addLibrarian(DialogAddLibrarian.this);
       });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
       /* myDatabaseImage = new MyDatabaseImage(getApplicationContext());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(uri.getPath()));
            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf)))
                baos.write(buf, 0, n);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] img = baos.toByteArray();

        boolean insertImg = myDatabaseImage.insertImageBitmap(img);
        bitmapImg = myDatabaseImage.getImg();*/
        imgAvatar.setImageURI(uri);




    }

    private void findViewId(){
        layoutAdd= findViewById(R.id.layoutAdd);
         imgAvatar = findViewById(R.id.imgAvatarLibrarian);
         nameLib = findViewById(R.id.edNameLib);
         phoneLib = findViewById(R.id.edPhoneLib);
         userLib = findViewById(R.id.edUserLib);
         passLib = findViewById(R.id.tilPassLib);
        btnAdd = findViewById(R.id.btnSaveLib);
    }

    public void addLibrarian(Context context ){
        findViewId();
        LibrarianDAO librarianDAO = new LibrarianDAO(context);
        ArrayList<LibrariansDTO> listLibs = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("form_login", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("name", "");
        String password = sharedPreferences.getString("pass", "");

        if (nameLib.getText().toString().equals("")){
            errSnackBar(layoutAdd,context,"Hãy nhập tên!");
        }else if(phoneLib.getText().toString().equals("")){
            errSnackBar(layoutAdd,context,"Hãy nhập số điện thoại!");
        }else if(phoneLib.getText().toString().length() != 10){
            errSnackBar(layoutAdd,context,"Hãy nhập đủ 10 số điện thoại!");
        }else if(userLib.getText().toString().equals("")) {
            errSnackBar(layoutAdd, context, "Hãy nhập tên đăng nhập!");
        }else if(!userLib.getText().toString().equals(userName)) {
            errSnackBar(layoutAdd, context, "Tên đăng nhập sai!");
        } else if(passLib.getEditText().getText().toString().equals("")) {
            errSnackBar(layoutAdd, context, "Hãy nhập mật khẩu!");
        }else if(!passLib.getEditText().getText().toString().equals(password)){
            errSnackBar(layoutAdd, context, "Mật khẩu không khớp!");
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.drawable.ic_save);
            builder.setTitle("Confirm !!!");
            builder.setMessage("Xác nhận lưu thông tin !");
            builder.setCancelable(false);

            LibrariansDTO librariansDTO = new LibrariansDTO();

            librariansDTO.setNameLibrarian(nameLib.getText().toString());
            librariansDTO.setPhoneNumbers(phoneLib.getText().toString());
            librariansDTO.setUserName(userLib.getText().toString());
            librariansDTO.setPassword(passLib.getEditText().getText().toString());


            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog1, int which) {
                    long res = librarianDAO.insertLibrarian(librariansDTO);
                    if(res > 0){
                        listLibs.clear();
                        listLibs.addAll(librarianDAO.selectAllLibrarian());
                        Toast.makeText(context, "Đã thêm mới! ", Toast.LENGTH_SHORT).show();
                        dialog1.cancel();
                        onBackPressed();
                    }
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog1, int which) {
                    Toast.makeText(context,"Đã hủy !",Toast.LENGTH_SHORT).show();
                    dialog1.cancel();
                    onBackPressed();
                }
            });

            AlertDialog sh = builder.create();
            sh.show();
        }
    }


    private void errSnackBar(LinearLayout linearLayout,Context context, String errText){
        final Snackbar snackbar = Snackbar.make(linearLayout,"",Snackbar.LENGTH_SHORT);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_snackbar_error, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout  snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setPadding(0,0,0,0);
        TextView tv_err = view.findViewById(R.id.tv_error);
        tv_err.setText(errText);
        snackBarLayout.addView(view, 0);
        snackbar.show();
    }



}