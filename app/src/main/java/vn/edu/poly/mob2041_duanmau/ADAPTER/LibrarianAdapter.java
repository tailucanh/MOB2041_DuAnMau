package vn.edu.poly.mob2041_duanmau.ADAPTER;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.poly.mob2041_duanmau.DAO.LibrarianDAO;
import vn.edu.poly.mob2041_duanmau.DAO.MemberDAO;
import vn.edu.poly.mob2041_duanmau.DTO.LibrariansDTO;
import vn.edu.poly.mob2041_duanmau.DTO.MembersDTO;
import vn.edu.poly.mob2041_duanmau.R;

public class LibrarianAdapter extends RecyclerView.Adapter<LibrarianAdapter.MyViewHolderLibrarian> {
    ArrayList<LibrariansDTO> listLibrarian;
    LibrarianDAO librarianDAO;
    Context context;
    ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


    public LibrarianAdapter(  ArrayList<LibrariansDTO> listLibrarian,   LibrarianDAO librarianDAO, Context context) {
        this.listLibrarian = listLibrarian;
        this.librarianDAO = librarianDAO;
        this.context = context;
    }


    public void setFilter(ArrayList<LibrariansDTO> filterList){
        this.listLibrarian = filterList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolderLibrarian onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_one_item_librarian,parent,false);
        return new LibrarianAdapter.MyViewHolderLibrarian(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderLibrarian holder, int position) {
        final  int index = position;
        LibrariansDTO librariansDTO = listLibrarian.get(position);

        holder.nameLib.setText(librariansDTO.getNameLibrarian());
        holder.phoneLib.setText(librariansDTO.getPhoneNumbers());

        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(librariansDTO.getId()));
        holder.imgDelete.setOnClickListener((img) ->{
            deleteLibrarian(context, librariansDTO,index);

        });
        holder.imgUpdate.setOnClickListener((img) ->{
            updateLibrarian(context,librariansDTO,index);
        });

    }


    @Override
    public int getItemCount() {
        return listLibrarian.size();
    }


    public class MyViewHolderLibrarian extends RecyclerView.ViewHolder{
        TextView nameLib, phoneLib;
        ImageView imgUpdate, imgDelete;
        SwipeRevealLayout swipeRevealLayout;

        public MyViewHolderLibrarian(@NonNull View view) {
            super(view);
            swipeRevealLayout = view.findViewById(R.id.swiper_layout);
            nameLib = view.findViewById(R.id.tvNameLibrarian);
            phoneLib = view.findViewById(R.id.tvPhoneLib);
            imgUpdate = view.findViewById(R.id.imgUpdate);
            imgDelete = view.findViewById(R.id.imgDelete);
        }
    }


    private void updateLibrarian(Context context, LibrariansDTO librariansDTO,int index){
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Material_Light_NoActionBar);
        dialog.setContentView(R.layout.dialog_add_librarians);
        context.setTheme(R.style.Theme_MOB2041_DuAnMau);
        dialog.setCancelable(false);

        dialog.findViewById(R.id.icBack).setOnClickListener(ic ->{
            dialog.dismiss();
            notifyDataSetChanged();
        });
        ImageView imgAddAvatar = dialog.findViewById(R.id.ic_add_img);
        imgAddAvatar.setVisibility(View.GONE);
        TextView tvTitle =  dialog.findViewById(R.id.tvTitleDialog);
        TextView tvContent =  dialog.findViewById(R.id.tvChangeAvatar);
        tvContent.setVisibility(View.INVISIBLE);
        Button btnChange =  dialog.findViewById(R.id.btnSaveLib);
        tvTitle.setText("Sửa thông tin");
        btnChange.setText("Lưu thay đổi");

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_change_2);
        builder.setTitle("Confirm !!!");
        builder.setMessage("Xác nhận sửa đổi thông tin!");
        builder.setCancelable(true);
        EditText nameLib = dialog.findViewById(R.id.edNameLib);
        EditText phoneLib = dialog.findViewById(R.id.edPhoneLib);
        EditText userLib = dialog.findViewById(R.id.edUserLib);
        TextInputLayout passLib = dialog.findViewById(R.id.tilPassLib);

        passLib.setEnabled(false);
        passLib.setFocusable(false);
        userLib.setEnabled(false);
        userLib.setFocusable(false);
        userLib.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        nameLib.setText(librariansDTO.getNameLibrarian());
        phoneLib.setText(librariansDTO.getPhoneNumbers());
        userLib.setText(librariansDTO.getUserName());
        passLib.getEditText().setText(librariansDTO.getPassword());


        btnChange.setOnClickListener( button ->{
            librariansDTO.setNameLibrarian(nameLib.getText().toString());
            librariansDTO.setPhoneNumbers(phoneLib.getText().toString());
            librariansDTO.setUserName(userLib.getText().toString());
            librariansDTO.setPassword(passLib.getEditText().getText().toString());

            int res = librarianDAO.updateLibrarian(librariansDTO);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog1, int which) {
                    if(res > 0){
                        listLibrarian.set(index,librariansDTO);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Đã sửa thông tin !", Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(context, "Không sửa được thông tin !" + res, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog1, int which) {
                    Toast.makeText(context,"Đã hủy !",Toast.LENGTH_SHORT).show();
                    dialog1.cancel();
                    dialog.dismiss();
                }
            });

            AlertDialog sh = builder.create();
            sh.show();
        });
        dialog.show();
    }


    public void deleteLibrarian(Context context,LibrariansDTO librariansDTO,int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa thành viên?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Có chắc chắn xóa : " + librariansDTO.getNameLibrarian());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int kq = librarianDAO.deleteLibrarian(librariansDTO);
                if(kq > 0)
                {
                    listLibrarian.remove(index);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã xóa!", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }else
                    Toast.makeText(context, "Không xóa được!" + kq, Toast.LENGTH_SHORT).show();
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
