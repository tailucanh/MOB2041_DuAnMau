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
import vn.edu.poly.mob2041_duanmau.DTO.MembersDTO;
import vn.edu.poly.mob2041_duanmau.R;


public class SpinnerMember extends BaseAdapter {
    ArrayList<MembersDTO> listMember;

    public SpinnerMember(ArrayList<MembersDTO> listMember) {
        this.listMember = listMember;
    }

    @Override
    public int getCount() {
        return listMember.size();
    }

    @Override
    public Object getItem(int position) {
        MembersDTO membersDTO = listMember.get(position);
        return membersDTO;
    }

    @Override
    public long getItemId(int position) {
        MembersDTO membersDTO = listMember.get(position);
        return membersDTO.getIdMember();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemSpinner;
        if (convertView == null){
            itemSpinner = View.inflate(parent.getContext(), R.layout.layout_items_spinner_loan_slip,null);

        }else {
            itemSpinner = convertView;
        }
        final   MembersDTO membersDTO = listMember.get(position);
        LinearLayout layoutItems = itemSpinner.findViewById(R.id.layoutItemSpinner);
        TextView tvMember = itemSpinner.findViewById(R.id.spinnerName);
        ImageView icItem = itemSpinner.findViewById(R.id.icItemSpinner);
        if(position == 0){
            layoutItems.setBackground(parent.getContext().getDrawable(R.drawable.background_spinner_2));
            icItem.setVisibility(View.VISIBLE);
        }else {
            layoutItems.setBackground(parent.getContext().getDrawable(R.color.white));
            icItem.setVisibility(View.GONE);
        }

        tvMember.setText(membersDTO.getNameMember());

        return itemSpinner;
    }



}
