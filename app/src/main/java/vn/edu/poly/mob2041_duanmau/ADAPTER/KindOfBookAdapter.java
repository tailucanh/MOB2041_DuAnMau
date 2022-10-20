package vn.edu.poly.mob2041_duanmau.ADAPTER;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import vn.edu.poly.mob2041_duanmau.DAO.KindOfBooksDAO;
import vn.edu.poly.mob2041_duanmau.DTO.KindOfBooksDTO;
import vn.edu.poly.mob2041_duanmau.R;

public class KindOfBookAdapter extends RecyclerView.Adapter<KindOfBookAdapter.MyViewHolderBooks> {
    ArrayList<KindOfBooksDTO> listKindOfBook;
    KindOfBooksDAO kindOfBooksDAO;
    Context context;
    ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


    public KindOfBookAdapter(ArrayList<KindOfBooksDTO> listKindOfBook, KindOfBooksDAO kindOfBooksDAO,Context context) {
        this.listKindOfBook = listKindOfBook;
        this.kindOfBooksDAO = kindOfBooksDAO;
        this.context = context;
    }

    public void setFilter(ArrayList<KindOfBooksDTO> filterList){
        this.listKindOfBook = filterList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolderBooks onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_one_item_kind_of_book,parent,false);
        return new KindOfBookAdapter.MyViewHolderBooks(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderBooks holder, int position) {
        final  int index = position;
        KindOfBooksDTO kindOfBooksDTO = listKindOfBook.get(position);
        holder.titleBook.setText(kindOfBooksDTO.getTitleBook());
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(kindOfBooksDTO.getIdKindOfBook()));
        holder.imgDelete.setOnClickListener((img) ->{
            deleteKindOfBook(context, kindOfBooksDTO,index);

        });
        holder.imgUpdate.setOnClickListener((img) ->{
            updateKindOfBook(context,kindOfBooksDTO,index);
        });



    }



    @Override
    public int getItemCount() {
        return listKindOfBook.size();
    }


    public class MyViewHolderBooks extends RecyclerView.ViewHolder{
        TextView titleBook;
        ImageView imgUpdate, imgDelete;
        SwipeRevealLayout swipeRevealLayout;

        public MyViewHolderBooks(@NonNull View view) {
            super(view);
            swipeRevealLayout = view.findViewById(R.id.swiper_layout);
            titleBook = view.findViewById(R.id.titleBook);
            imgUpdate = view.findViewById(R.id.imgUpdate);
            imgDelete = view.findViewById(R.id.imgDelete);
        }

    }


    public void dialogAddKindOfBooks(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_kind_of_book);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextInputLayout tilKindOfBook = dialog.findViewById(R.id.tilKindOfBook);

         dialog.findViewById(R.id.btnSave).setOnClickListener((button) ->{
             if (tilKindOfBook.getEditText().getText().toString().equals("")){
                 tilKindOfBook.setError("Hãy nhập tên loại sách.");
             }else{
                 tilKindOfBook.setError("");
                 AlertDialog.Builder builder = new AlertDialog.Builder(context);
                 builder.setIcon(R.drawable.ic_save);
                 builder.setTitle("Confirm !!!");
                 builder.setMessage("Xác nhận lưu thông tin !");
                 builder.setCancelable(false);

                 KindOfBooksDTO kindOfBooksDTO = new KindOfBooksDTO();
                 String titleBook = tilKindOfBook.getEditText().getText().toString();
                 kindOfBooksDTO.setTitleBook(titleBook);


                 builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog1, int which) {
                         long res = kindOfBooksDAO.insertKindOfBook(kindOfBooksDTO);
                         if(res > 0){
                             listKindOfBook.clear();
                             listKindOfBook.addAll(kindOfBooksDAO.selectAllKindOfBook());
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

    private void updateKindOfBook(Context context, KindOfBooksDTO kindOfBooksDTO,int index){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_kind_of_book);
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

        TextInputLayout tilKindOfBook = dialog.findViewById(R.id.tilKindOfBook);
        tilKindOfBook.getEditText().setText(kindOfBooksDTO.getTitleBook());

        btnChange.setOnClickListener( button ->{
            kindOfBooksDTO.setTitleBook(tilKindOfBook.getEditText().getText().toString());
            int res = kindOfBooksDAO.updateKindOfBook(kindOfBooksDTO);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog1, int which) {
                    if(res > 0){
                        listKindOfBook.set(index,kindOfBooksDTO);
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



    public void deleteKindOfBook(Context context,KindOfBooksDTO kindOfBooksDTO,int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa loại sách?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Có chắc chắn xóa : " + kindOfBooksDTO.getTitleBook());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int kq = kindOfBooksDAO.deleteKindOfBook(kindOfBooksDTO);
                if(kq > 0)
                {
                    listKindOfBook.remove(index);
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
}
