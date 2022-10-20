package vn.edu.poly.mob2041_duanmau.ADAPTER;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import vn.edu.poly.mob2041_duanmau.DAO.BooksDAO;
import vn.edu.poly.mob2041_duanmau.DAO.KindOfBooksDAO;
import vn.edu.poly.mob2041_duanmau.DTO.BooksDTO;
import vn.edu.poly.mob2041_duanmau.DTO.KindOfBooksDTO;
import vn.edu.poly.mob2041_duanmau.FRAGMENT.KindOfBookFragment;
import vn.edu.poly.mob2041_duanmau.FRAGMENT.LibrarianFragment;
import vn.edu.poly.mob2041_duanmau.R;
import vn.edu.poly.mob2041_duanmau.SPINNER.SpinnerKindOfBooks;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolderBook> {
    ArrayList<BooksDTO> listBooks;
    BooksDAO booksDAO;
    Context context;
    ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


    public BooksAdapter(ArrayList<BooksDTO> listBooks, BooksDAO booksDAO, Context context) {
        this.listBooks = listBooks;
        this.booksDAO = booksDAO;
        this.context = context;
    }

    public void setFilter(ArrayList<BooksDTO> filterList){
        this.listBooks = filterList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolderBook onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_one_item_books,parent,false);
        return new BooksAdapter.MyViewHolderBook(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderBook holder, int position) {
        final  int index = position;
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        BooksDTO booksDTO = listBooks.get(position);
        BooksDTO showKindOfBook = booksDAO.selectNameKindOfBook(booksDTO.getIdBook());
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(booksDTO.getIdKindOfBook()));
        holder.titleBook.setText(booksDTO.getNameBook());
        holder.tvKindOfBook.setText(showKindOfBook.getNameKindOfBook());
        String stringPriceBeforeTemp = en.format(booksDTO.getPriceBook());

        holder.tvPriceBefore.setText(stringPriceBeforeTemp+"đ ");
        holder.tvPriceBefore.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvDiscount.setText(booksDTO.getDiscountBook()+"%");

        int priceBefore = booksDTO.getPriceBook();
        int discount = booksDTO.getDiscountBook();
        int priceAfter = priceBefore - (priceBefore * discount / 100);
        String stringPriceAfterTemp = en.format(priceAfter);
        holder.tvPriceAfter.setText(stringPriceAfterTemp+"đ");

        holder.imgDelete.setOnClickListener((img) ->{
            deleteBook(context, booksDTO,index);

        });
        holder.imgUpdate.setOnClickListener((img) ->{
            updateBooks(context,booksDTO,index);
        });

    }


    @Override
    public int getItemCount() {
        return listBooks.size();
    }


    public class MyViewHolderBook extends RecyclerView.ViewHolder{
        TextView titleBook,tvKindOfBook,tvPriceAfter,tvPriceBefore,tvDiscount;
        ImageView imgUpdate, imgDelete;
        SwipeRevealLayout swipeRevealLayout;

        public MyViewHolderBook(@NonNull View view) {
            super(view);
            swipeRevealLayout = view.findViewById(R.id.swiper_layout);
            titleBook = view.findViewById(R.id.tvTitleBook);
            tvKindOfBook = view.findViewById(R.id.tvKindOfBook);
            tvPriceAfter = view.findViewById(R.id.tvPriceAfter);
            tvPriceBefore = view.findViewById(R.id.tvPriceBefore);
            tvDiscount = view.findViewById(R.id.tvDiscount);
            imgUpdate = view.findViewById(R.id.imgUpdate);
            imgDelete = view.findViewById(R.id.imgDelete);
        }

    }


    public void dialogAddBooks(Context context, LinearLayout linearLayout ) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_book);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextInputLayout tilTitleBooks = dialog.findViewById(R.id.tilTitleBooks);
        TextInputLayout tilPrice= dialog.findViewById(R.id.tilPrice);
        TextInputLayout tilDiscount= dialog.findViewById(R.id.tilDiscount);

        final Spinner spinner = dialog.findViewById(R.id.spinnerNameKindOfBook);
        KindOfBooksDAO kindOfBooksDAO = new KindOfBooksDAO(context);
        SpinnerKindOfBooks spinnerKindOfBooks = new SpinnerKindOfBooks(kindOfBooksDAO.selectAllKindOfBook());
        spinner.setAdapter(spinnerKindOfBooks);

         dialog.findViewById(R.id.btnSave).setOnClickListener((button) ->{
             if (tilTitleBooks.getEditText().getText().toString().equals("")){
                 tilTitleBooks.setError("Hãy nhập tên sách.");
             }else if(tilPrice.getEditText().getText().toString().equals("")){
                 tilTitleBooks.setError("");
                 tilPrice.setError("Hãy nhập giá sách.");
             }else if(Double.parseDouble(tilPrice.getEditText().getText().toString()) < 0 ){
                 tilPrice.setError("Hãy nhập giá sách > 0.");
             } else if(tilDiscount.getEditText().getText().toString().equals("")){
                 tilPrice.setError("");
                 tilDiscount.setError("Hãy nhập % giảm giá.");
             } else if(Double.parseDouble(tilDiscount.getEditText().getText().toString()) < 0 || Double.parseDouble(tilDiscount.getEditText().getText().toString()) > 100){
                 tilDiscount.setError("Số % giảm phải  > 0 và < 100.");
             } else if(spinner.getSelectedItem() == null) {
                 errSnackBar(linearLayout, context,"Chưa có thể loại sách! Thêm tại mục loại sách!");
                 dialog.dismiss();
             }else{
                 tilDiscount.setError("");
                 AlertDialog.Builder builder = new AlertDialog.Builder(context);
                 builder.setIcon(R.drawable.ic_save);
                 builder.setTitle("Confirm !!!");
                 builder.setMessage("Xác nhận lưu thông tin !");
                 builder.setCancelable(false);

                 BooksDTO booksDTO = new BooksDTO();
                 KindOfBooksDTO kindOfBooksDTO = (KindOfBooksDTO) spinner.getSelectedItem();
                 booksDTO.setIdKindOfBook(kindOfBooksDTO.getIdKindOfBook());

                 booksDTO.setNameBook( tilTitleBooks.getEditText().getText().toString());
                 booksDTO.setPriceBook( Integer.parseInt(tilPrice.getEditText().getText().toString()));
                 booksDTO.setDiscountBook(Integer.parseInt(tilDiscount.getEditText().getText().toString()));


                 builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog1, int which) {
                         long res = booksDAO.insertBooks(booksDTO);
                         if(res > 0){
                             listBooks.clear();
                             listBooks.addAll(booksDAO.selectAllBook());
                             notifyDataSetChanged();
                             Toast.makeText(context, "Đã thêm mới! ", Toast.LENGTH_SHORT).show();
                             dialog1.cancel();
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

    private void updateBooks(Context context, BooksDTO booksDTO,int index){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_book);
        dialog.setCancelable(false);
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
        builder.setCancelable(false);

        TextInputLayout tilTitleBooks = dialog.findViewById(R.id.tilTitleBooks);
        TextInputLayout tilPrice= dialog.findViewById(R.id.tilPrice);
        TextInputLayout tilDiscount= dialog.findViewById(R.id.tilDiscount);

        tilTitleBooks.getEditText().setText(booksDTO.getNameBook());
        tilPrice.getEditText().setText(booksDTO.getPriceBook() +"");
        tilDiscount.getEditText().setText(booksDTO.getDiscountBook() +"");

        final Spinner spinner = dialog.findViewById(R.id.spinnerNameKindOfBook);
        KindOfBooksDAO kindOfBooksDAO = new KindOfBooksDAO(context);
        ArrayList<KindOfBooksDTO> lisKindOfBook = kindOfBooksDAO.selectAllKindOfBook();
        SpinnerKindOfBooks spinnerKindOfBooks = new SpinnerKindOfBooks(lisKindOfBook);
        spinner.setAdapter(spinnerKindOfBooks);

        for(int i = 0; i < lisKindOfBook.size(); i++){
            KindOfBooksDTO temp = lisKindOfBook.get(i);
            if(temp.getIdKindOfBook() == booksDTO.getIdKindOfBook()){
                spinner.setSelection(i);
                spinner.setSelected(true);
            }
        }


        btnChange.setOnClickListener( button ->{
            booksDTO.setNameBook(tilTitleBooks.getEditText().getText().toString());
            booksDTO.setPriceBook(Integer.parseInt(tilPrice.getEditText().getText().toString()));
            booksDTO.setDiscountBook(Integer.parseInt(tilDiscount.getEditText().getText().toString()));


            KindOfBooksDTO kindOfBooksDTO = (KindOfBooksDTO) spinner.getSelectedItem();
            booksDTO.setIdKindOfBook(kindOfBooksDTO.getIdKindOfBook());

            int res = booksDAO.updateBook(booksDTO);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog1, int which) {

                    if(res > 0){
                        listBooks.set(index,booksDTO);
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



    public void deleteBook(Context context,BooksDTO booksDTO,int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa sách?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Có chắc chắn xóa : " + booksDTO.getNameBook());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int kq = booksDAO.deleteBook(booksDTO);
                if(kq > 0)
                {
                    listBooks.remove(index);
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
    private void errSnackBar(LinearLayout linearLayout, Context context, String errText){
        final Snackbar snackbar = Snackbar.make(linearLayout,"",Snackbar.LENGTH_LONG);
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
