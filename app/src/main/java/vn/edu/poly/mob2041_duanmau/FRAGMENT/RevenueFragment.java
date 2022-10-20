package vn.edu.poly.mob2041_duanmau.FRAGMENT;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import vn.edu.poly.mob2041_duanmau.DAO.LoanSlipDAO;
import vn.edu.poly.mob2041_duanmau.MainActivity;
import vn.edu.poly.mob2041_duanmau.R;

public class RevenueFragment extends Fragment {

    AnyChartView pieChart;
    TextView tvTotalMoney;
    LoanSlipDAO loanSlipDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.revenue_fragment,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.iv_show_nav).setOnClickListener(imb ->{
            MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
        });
        loanSlipDAO = new LoanSlipDAO(getContext());
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);


        long totalMoney2022 = loanSlipDAO.selectTotalMoney2022();
        long totalMoney1 = loanSlipDAO.selectTotalMoneyTheFirstQuarter();
        long totalMoney2 = loanSlipDAO.selectTotalMoneyTheSecondQuarter();
        long totalMoney3 = loanSlipDAO.selectTotalMoneyTheThirdQuarter();
        long totalMoney4 = loanSlipDAO.selectTotalMoneyTheFourthQuarter();
        String stringTotalTemp = en.format(totalMoney2022);
        tvTotalMoney = view.findViewById(R.id.tvTotalRevenue);
        tvTotalMoney.setText(stringTotalTemp);


        String [] quarters = {"Quý 1","Quý 2","Quý 3","Quý 4"};
        long[] totalMoneyQuarter = {totalMoney1,totalMoney2,totalMoney3,totalMoney4};

        Log.e("zzz","Tổng: "+totalMoney2022);
        Log.e("zzz","Tổng quí 1: "+totalMoney1);
        Log.e("zzz","Tổng quí 2: "+totalMoney2);
        Log.e("zzz","Tổng quí 3: "+totalMoney3);
        Log.e("zzz","Tổng quí 4: "+totalMoney4);


        pieChart = view.findViewById(R.id.pieChartData);
        Pie pie = AnyChart.pie();
        ArrayList<DataEntry> dataEntries = new ArrayList<>();
        for(int i = 0 ; i < quarters.length; i++){
           dataEntries.add(new ValueDataEntry(quarters[i],totalMoneyQuarter[i]));
        }

        pie.data(dataEntries);

        pieChart.setChart(pie);
        




    }

}
