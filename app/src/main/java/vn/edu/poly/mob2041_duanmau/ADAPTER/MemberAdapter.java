package vn.edu.poly.mob2041_duanmau.ADAPTER;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

import vn.edu.poly.mob2041_duanmau.DAO.KindOfBooksDAO;
import vn.edu.poly.mob2041_duanmau.DAO.MemberDAO;
import vn.edu.poly.mob2041_duanmau.DTO.KindOfBooksDTO;
import vn.edu.poly.mob2041_duanmau.DTO.MembersDTO;
import vn.edu.poly.mob2041_duanmau.R;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyViewHolderMember> {
    ArrayList<MembersDTO> listMember;
    MemberDAO memberDAO;
    Context context;
    ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private int selectedYear;
    private int selectedMonth;
    private int selectedDayOfMonth;


    public MemberAdapter(ArrayList<MembersDTO> listMember, MemberDAO memberDAO, Context context) {
        this.listMember = listMember;
        this.memberDAO = memberDAO;
        this.context = context;
    }

    public void setFilter(ArrayList<MembersDTO> filterList){
        this.listMember = filterList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolderMember onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_one_item_member,parent,false);
        return new MemberAdapter.MyViewHolderMember(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderMember holder, int position) {
        final  int index = position;
        MembersDTO membersDTO = listMember.get(position);

        holder.nameMember.setText(membersDTO.getNameMember());
        holder.birthDate.setText(membersDTO.getBirthDate());

        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(membersDTO.getIdMember()));
        holder.imgDelete.setOnClickListener((img) ->{
            deleteMember(context, membersDTO,index);

        });
        holder.imgUpdate.setOnClickListener((img) ->{
            updateMember(context,membersDTO,index);
        });


    }


    @Override
    public int getItemCount() {
        return listMember.size();
    }


    public class MyViewHolderMember extends RecyclerView.ViewHolder{
        TextView nameMember, birthDate;
        ImageView imgUpdate, imgDelete;
        SwipeRevealLayout swipeRevealLayout;

        public MyViewHolderMember(@NonNull View view) {
            super(view);
            swipeRevealLayout = view.findViewById(R.id.swiper_layout);
            nameMember = view.findViewById(R.id.tvNameMember);
            birthDate = view.findViewById(R.id.tvBirthDate);
            imgUpdate = view.findViewById(R.id.imgUpdate);
            imgDelete = view.findViewById(R.id.imgDelete);
        }

    }


    public void dialogAddMember(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_member);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextInputLayout tileName = dialog.findViewById(R.id.tilMemberName);
        TextInputLayout tileBirthDate = dialog.findViewById(R.id.tilBirthDate);
        dialog.findViewById(R.id.imgChooseBirthDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDate(tileBirthDate.getEditText(),context);
            }
        });

         dialog.findViewById(R.id.btnSave).setOnClickListener((button) ->{
             if (tileName.getEditText().getText().toString().equals("")){
                 tileName.setError("Hãy nhập tên thành viên.");
             }else if(tileBirthDate.getEditText().getText().toString().equals("")){
                 tileName.setError("");
                 tileBirthDate.setError("Hãy chọn ngày sinh.");
             } else{
                 tileBirthDate.setError("");
                 AlertDialog.Builder builder = new AlertDialog.Builder(context);
                 builder.setIcon(R.drawable.ic_save);
                 builder.setTitle("Confirm !!!");
                 builder.setMessage("Xác nhận lưu thông tin !");
                 builder.setCancelable(false);

                 MembersDTO membersDTO = new MembersDTO();
                 String nameMember = tileName.getEditText().getText().toString();
                 String birthDate = tileBirthDate.getEditText().getText().toString();
                 membersDTO.setNameMember(nameMember);
                 membersDTO.setBirthDate(birthDate);



                 builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog1, int which) {
                         long res = memberDAO.insertMember(membersDTO);
                         if(res > 0){
                             listMember.clear();
                             listMember.addAll(memberDAO.selectAllMember());
                             notifyDataSetChanged();
                             Toast.makeText(context, "Đã thêm mới! ", Toast.LENGTH_SHORT).show();
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
             }
        });
        dialog.show();
    }

    private void updateMember(Context context, MembersDTO membersDTO,int index){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_member);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvTitle =  dialog.findViewById(R.id.titleDialog);
        Button btnChange =  dialog.findViewById(R.id.btnSave);
        tvTitle.setText("Sửa thông tin");
        btnChange.setText("Lưu thay đổi");

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_change_2);
        builder.setTitle("Confirm !!!");
        builder.setMessage("Xác nhận sửa đổi thông tin!");
        builder.setCancelable(true);

        TextInputLayout tileName = dialog.findViewById(R.id.tilMemberName);
        TextInputLayout tileBirthDate = dialog.findViewById(R.id.tilBirthDate);
        dialog.findViewById(R.id.imgChooseBirthDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDate(tileBirthDate.getEditText(),context);
            }
        });

        tileName.getEditText().setText(membersDTO.getNameMember());
        tileBirthDate.getEditText().setText(membersDTO.getBirthDate());


        btnChange.setOnClickListener( button ->{
            membersDTO.setNameMember(tileName.getEditText().getText().toString());
            membersDTO.setBirthDate(tileBirthDate.getEditText().getText().toString());

            int res = memberDAO.updateMember(membersDTO);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog1, int which) {
                    if(res > 0){
                        listMember.set(index,membersDTO);
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



    public void deleteMember(Context context,MembersDTO membersDTO,int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa thành viên?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Có chắc chắn xóa : " + membersDTO.getNameMember());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int kq = memberDAO.deleteMember(membersDTO);
                if(kq > 0)
                {
                    listMember.remove(index);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã xóa! ", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }else
                    Toast.makeText(context, "Không xóa được! " + kq, Toast.LENGTH_SHORT).show();

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
    public void SelectDate(EditText editText, Context context){
        final Calendar calendar = Calendar.getInstance();
        this.selectedYear = calendar.get(Calendar.YEAR);
        this.selectedMonth = calendar.get(Calendar.MONTH);
        this.selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editText.setText(year + " - "+(month+1)+" - "+dayOfMonth);
                editText.setFocusable(false);
                selectedYear = year;
                selectedMonth = month;
                selectedDayOfMonth = dayOfMonth;
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,dateSetListener,selectedYear,
                selectedMonth,selectedDayOfMonth);
        datePickerDialog.show();
    }

}
