package vn.edu.poly.mob2041_duanmau.ADAPTER;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
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
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import vn.edu.poly.mob2041_duanmau.DAO.BooksDAO;
import vn.edu.poly.mob2041_duanmau.DAO.KindOfBooksDAO;
import vn.edu.poly.mob2041_duanmau.DAO.LoanSlipDAO;
import vn.edu.poly.mob2041_duanmau.DTO.BooksDTO;
import vn.edu.poly.mob2041_duanmau.DTO.KindOfBooksDTO;
import vn.edu.poly.mob2041_duanmau.DTO.TopBooks;
import vn.edu.poly.mob2041_duanmau.R;
import vn.edu.poly.mob2041_duanmau.SPINNER.SpinnerKindOfBooks;

public class TopBooksAdapter extends RecyclerView.Adapter<TopBooksAdapter.MyViewHolderTopBook> {
    ArrayList<TopBooks> listTopBooks;
    Context context;

    public TopBooksAdapter(ArrayList<TopBooks> listTopBooks,  Context context) {
        this.listTopBooks = listTopBooks;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolderTopBook onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_one_item_top_book,parent,false);
        return new TopBooksAdapter.MyViewHolderTopBook(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderTopBook holder, int position) {

        TopBooks topBooks = listTopBooks.get(position);
        holder.tvTitleBook.setText(topBooks.getTitleBook());
        holder.tvCount.setText(topBooks.getCountBook()+"");


    }


    @Override
    public int getItemCount() {
        return listTopBooks.size();
    }


    public class MyViewHolderTopBook extends RecyclerView.ViewHolder{
        TextView tvTitleBook, tvCount;
        public MyViewHolderTopBook(@NonNull View view) {
            super(view);
            tvTitleBook = view.findViewById(R.id.tvNameBook);
            tvCount = view.findViewById(R.id.tvCountBorrowings);
        }

    }




}
