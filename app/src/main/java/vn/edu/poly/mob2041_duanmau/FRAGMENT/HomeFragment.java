package vn.edu.poly.mob2041_duanmau.FRAGMENT;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import vn.edu.poly.mob2041_duanmau.DAO.BooksDAO;
import vn.edu.poly.mob2041_duanmau.DAO.KindOfBooksDAO;
import vn.edu.poly.mob2041_duanmau.DAO.LibrarianDAO;
import vn.edu.poly.mob2041_duanmau.DAO.LoanSlipDAO;
import vn.edu.poly.mob2041_duanmau.DAO.MemberDAO;
import vn.edu.poly.mob2041_duanmau.MainActivity;
import vn.edu.poly.mob2041_duanmau.R;

public class HomeFragment extends Fragment {
    ConstraintLayout layoutTopBook,layoutLoanSlip,layoutBooks,layoutKindOfBook,layoutMember,layoutLibrarian,layoutRevenue;
    TextView tvCountKindOfBook,tvCountMember,tvCountBooks,tvCountLibrarian,tvCountLoanSlip;
    KindOfBooksDAO kindOfBooksDAO;
    MemberDAO memberDAO;
    BooksDAO booksDAO;
    LibrarianDAO librarianDAO;
    LoanSlipDAO loanSlipDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewMapping(view);
        view.findViewById(R.id.iv_show_nav).setOnClickListener(imb ->{
            MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
        });
        kindOfBooksDAO = new KindOfBooksDAO(getContext());
        memberDAO = new MemberDAO(getContext());
        booksDAO = new BooksDAO(getContext());
        librarianDAO = new LibrarianDAO(getContext());
        loanSlipDAO = new LoanSlipDAO(getContext());

        tvCountKindOfBook.setText(kindOfBooksDAO.selectCountKindOfBook()+"");
        tvCountMember.setText(memberDAO.selectCountMember()+"");
        tvCountBooks.setText(booksDAO.selectCountBooks()+"");
        tvCountLibrarian.setText(librarianDAO.selectCountLibrarian()+"");
        tvCountLoanSlip.setText(loanSlipDAO.selectCountLoanSlip()+"");


        layoutTopBook.setOnClickListener(layout ->{
            aniViewClick(layoutTopBook);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new TopBookFragment()).commit();
        });
        layoutLoanSlip.setOnClickListener(layout ->{
            aniViewClick(layoutTopBook);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new LoanSlipFragment()).commit();
        });
        layoutKindOfBook.setOnClickListener(layout ->{
            aniViewClick(layoutTopBook);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new KindOfBookFragment()).commit();
        });
        layoutMember.setOnClickListener(layout ->{
            aniViewClick(layoutTopBook);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new MemberFragment()).commit();
        });
        layoutBooks.setOnClickListener(layout ->{
            aniViewClick(layoutTopBook);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new BookManagementFragment()).commit();
        });
        layoutLibrarian.setOnClickListener(layout ->{
            aniViewClick(layoutTopBook);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new LibrarianFragment()).commit();
        });

        layoutRevenue.setOnClickListener(layout ->{
            aniViewClick(layoutTopBook);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new RevenueFragment()).commit();
        });

    }

    private void viewMapping(View view){
        layoutTopBook = view.findViewById(R.id.layout_item_1);
        layoutLoanSlip = view.findViewById(R.id.layout_item_2);
        layoutKindOfBook = view.findViewById(R.id.layout_item_3);
        layoutBooks = view.findViewById(R.id.layout_item_4);
        layoutLibrarian = view.findViewById(R.id.layout_item_5);
        layoutMember = view.findViewById(R.id.layout_item_6);
        layoutRevenue = view.findViewById(R.id.layout_item_7);
        tvCountKindOfBook = view.findViewById(R.id.tvCountKindOfBook);
        tvCountMember = view.findViewById(R.id.tvCountMember);
        tvCountBooks = view.findViewById(R.id.tvCountBook);
        tvCountLibrarian = view.findViewById(R.id.tvCountLibrarian);
        tvCountLoanSlip = view.findViewById(R.id.tvCountLoanSlip);
    }


    private void aniViewClick(ConstraintLayout layout){
        layout.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(Color.parseColor("#DADADA"), PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }


}
