package vn.edu.poly.mob2041_duanmau.FRAGMENT;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import vn.edu.poly.mob2041_duanmau.ADAPTER.TopBooksAdapter;
import vn.edu.poly.mob2041_duanmau.DAO.LoanSlipDAO;
import vn.edu.poly.mob2041_duanmau.DTO.TopBooks;
import vn.edu.poly.mob2041_duanmau.MainActivity;
import vn.edu.poly.mob2041_duanmau.R;

public class TopBookFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerViewTopBook;
    TopBooksAdapter topBooksAdapter;
    LoanSlipDAO loanSlipDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.top_books_fragment,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.iv_show_nav).setOnClickListener(imb ->{
            MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
        });
        swipeRefreshLayout = view.findViewById(R.id.swiperRefreshLayout);
        recyclerViewTopBook = view.findViewById(R.id.listTopBook);
        loanSlipDAO = new LoanSlipDAO(getContext());
        ArrayList<TopBooks> lists = loanSlipDAO.selectListTopBooks();
        Log.e("zzz","list: "+lists);
        topBooksAdapter = new TopBooksAdapter(lists,getContext());

        recyclerViewTopBook.setAdapter(topBooksAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loanSlipDAO.selectListTopBooks().clear();
                recyclerViewTopBook.setAdapter(topBooksAdapter);
                topBooksAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });



    }





}
