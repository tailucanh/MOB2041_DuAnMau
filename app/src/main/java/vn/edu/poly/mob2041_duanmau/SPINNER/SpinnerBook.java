package vn.edu.poly.mob2041_duanmau.SPINNER;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.poly.mob2041_duanmau.DTO.BooksDTO;
import vn.edu.poly.mob2041_duanmau.DTO.MembersDTO;
import vn.edu.poly.mob2041_duanmau.R;


public class SpinnerBook extends BaseAdapter {
    ArrayList<BooksDTO> listBooks;

    public SpinnerBook(ArrayList<BooksDTO> listBooks) {
        this.listBooks = listBooks;
    }

    @Override
    public int getCount() {
        return listBooks.size();
    }

    @Override
    public Object getItem(int position) {
        BooksDTO booksDTO = listBooks.get(position);
        return booksDTO;
    }

    @Override
    public long getItemId(int position) {
        BooksDTO booksDTO = listBooks.get(position);
        return booksDTO.getIdBook();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemSpinner;
        if (convertView == null){
            itemSpinner = View.inflate(parent.getContext(), R.layout.layout_items_spinner_loan_slip,null);

        }else {
            itemSpinner = convertView;
        }
        final  BooksDTO booksDTO = listBooks.get(position);
        LinearLayout layoutItems = itemSpinner.findViewById(R.id.layoutItemSpinner);
        TextView tvTitleBook = itemSpinner.findViewById(R.id.spinnerName);
        ImageView icItem = itemSpinner.findViewById(R.id.icItemSpinner);


        if(position == 0){
            layoutItems.setBackground(parent.getContext().getDrawable(R.drawable.background_spinner_2));
            icItem.setVisibility(View.VISIBLE);
        }else {
            layoutItems.setBackground(parent.getContext().getDrawable(R.color.white));
            icItem.setVisibility(View.GONE);

        }
        tvTitleBook.setText(booksDTO.getNameBook());

        return itemSpinner;
    }



}
