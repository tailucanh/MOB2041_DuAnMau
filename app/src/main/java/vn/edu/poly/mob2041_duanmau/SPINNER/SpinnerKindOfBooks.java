package vn.edu.poly.mob2041_duanmau.SPINNER;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.poly.mob2041_duanmau.DTO.KindOfBooksDTO;
import vn.edu.poly.mob2041_duanmau.R;


public class SpinnerKindOfBooks extends BaseAdapter {
    ArrayList<KindOfBooksDTO> listKindOfBooks;

    public SpinnerKindOfBooks(ArrayList<KindOfBooksDTO> listKindOfBooks) {
        this.listKindOfBooks = listKindOfBooks;
    }

    @Override
    public int getCount() {
        return listKindOfBooks.size();
    }

    @Override
    public Object getItem(int position) {
        KindOfBooksDTO kindOfBooksDTO = listKindOfBooks.get(position);
        return kindOfBooksDTO;
    }

    @Override
    public long getItemId(int position) {
        KindOfBooksDTO kindOfBooksDTO = listKindOfBooks.get(position);
        return kindOfBooksDTO.getIdKindOfBook();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemSpinner;
        if (convertView == null){
            itemSpinner = View.inflate(parent.getContext(), R.layout.layout_items_spinner_kind_of_book,null);

        }else {
            itemSpinner = convertView;
        }
        final  int index = position;
        final KindOfBooksDTO kindOfBooksDTO = listKindOfBooks.get(position);
        LinearLayout layoutItems = itemSpinner.findViewById(R.id.layoutItemSpinner);
        TextView tvTitleBook = itemSpinner.findViewById(R.id.spinnerName);
        ImageView icItem = itemSpinner.findViewById(R.id.icItemSpinner);

        if(index % 2 == 0){
            if(position == 0){
                layoutItems.setBackground(parent.getContext().getDrawable(R.drawable.background_spinner));
                icItem.setImageResource(R.drawable.ic_item_spinner_3);
                tvTitleBook.setTextColor(Color.parseColor("#000000"));
                startRotation(icItem);
            }else {
                layoutItems.setBackground(parent.getContext().getDrawable(R.drawable.background_spinner_item));
                icItem.setImageResource(R.drawable.ic_item_spinner);
                tvTitleBook.setTextColor(Color.parseColor("#FFFFFF"));
            }

        }else{
            layoutItems.setBackground(parent.getContext().getDrawable(R.drawable.background_spinner_item_2));
            icItem.setImageResource(R.drawable.ic_item_spinner_2);
            tvTitleBook.setTextColor(Color.parseColor("#000000"));
        }

        tvTitleBook.setText(kindOfBooksDTO.getTitleBook());

        return itemSpinner;
    }


    public void startRotation(ImageView imageView){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                imageView.animate().rotationBy(360).withEndAction(this).setDuration(3000)
                        .setInterpolator(new LinearInterpolator()).start();
            }
        };
        imageView.animate().rotationBy(-360).withEndAction(runnable).setDuration(3000)
                .setInterpolator(new LinearInterpolator()).start();
    }

    public void stopRotation(ImageView imageView){
        imageView.animate().cancel();
    }


}
