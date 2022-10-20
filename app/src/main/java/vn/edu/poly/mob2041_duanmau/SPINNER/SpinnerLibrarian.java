package vn.edu.poly.mob2041_duanmau.SPINNER;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.poly.mob2041_duanmau.DTO.BooksDTO;
import vn.edu.poly.mob2041_duanmau.DTO.LibrariansDTO;
import vn.edu.poly.mob2041_duanmau.R;


public class SpinnerLibrarian extends BaseAdapter {
    ArrayList<LibrariansDTO> listLibrarian;

    public SpinnerLibrarian(ArrayList<LibrariansDTO> listLibrarian) {
        this.listLibrarian = listLibrarian;
    }

    @Override
    public int getCount() {
        return listLibrarian.size();
    }

    @Override
    public Object getItem(int position) {
        LibrariansDTO librariansDTO = listLibrarian.get(position);
        return librariansDTO;
    }

    @Override
    public long getItemId(int position) {
        LibrariansDTO librariansDTO = listLibrarian.get(position);
        return librariansDTO.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemSpinner;
        if (convertView == null){
            itemSpinner = View.inflate(parent.getContext(), R.layout.layout_items_spinner_loan_slip,null);

        }else {
            itemSpinner = convertView;
        }

        final  LibrariansDTO librariansDTO = listLibrarian.get(position);
        LinearLayout layoutItems = itemSpinner.findViewById(R.id.layoutItemSpinner);
        TextView tvLib = itemSpinner.findViewById(R.id.spinnerName);
        ImageView icItem = itemSpinner.findViewById(R.id.icItemSpinner);
        if(position == 0){
            layoutItems.setBackground(parent.getContext().getDrawable(R.drawable.background_spinner_2));
            icItem.setVisibility(View.VISIBLE);
        }else {
            layoutItems.setBackground(parent.getContext().getDrawable(R.color.white));
            icItem.setVisibility(View.GONE);

        }

        tvLib.setText(librariansDTO.getNameLibrarian());

        return itemSpinner;
    }



}
