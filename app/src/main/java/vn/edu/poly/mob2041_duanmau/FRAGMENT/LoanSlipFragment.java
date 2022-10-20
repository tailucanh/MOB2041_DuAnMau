package vn.edu.poly.mob2041_duanmau.FRAGMENT;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import vn.edu.poly.mob2041_duanmau.ADAPTER.BooksAdapter;
import vn.edu.poly.mob2041_duanmau.ADAPTER.LoanSlipAdapter;
import vn.edu.poly.mob2041_duanmau.DAO.BooksDAO;
import vn.edu.poly.mob2041_duanmau.DAO.LoanSlipDAO;
import vn.edu.poly.mob2041_duanmau.DAO.MemberDAO;
import vn.edu.poly.mob2041_duanmau.DTO.BooksDTO;
import vn.edu.poly.mob2041_duanmau.DTO.LibrariansDTO;
import vn.edu.poly.mob2041_duanmau.DTO.LoanSlipDTO;
import vn.edu.poly.mob2041_duanmau.DTO.MembersDTO;
import vn.edu.poly.mob2041_duanmau.MainActivity;
import vn.edu.poly.mob2041_duanmau.R;

public class LoanSlipFragment extends Fragment {
    FloatingActionButton fabMenu,fabAdd,fabSearch,fabChangeBackground;
    Animation rotateForward,rotateBackward;
    boolean isFabOpen = false;
    private RecyclerView recycleListLoanSlips;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchLoanSlip;
    private ImageView icShowNav,imgLoanSlip,icHideSearchView;
    private TextView titleFrag1;
    LinearLayout layoutFragmentLoanSlip;
    LoanSlipDAO loanSlipDAO;
    LoanSlipAdapter loanSlipAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.loan_slip_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findByIdView(view);
        view.findViewById(R.id.iv_show_nav).setOnClickListener(imb ->{
            MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
        });

        loanSlipDAO = new LoanSlipDAO(getContext());
        loanSlipAdapter = new LoanSlipAdapter(loanSlipDAO.selectAllLoanSlip(), loanSlipDAO,getContext());

        rotateForward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backward);
        hideSearchView(searchLoanSlip,icHideSearchView);

        fabMenu.setOnClickListener(fab ->{
            animateFab();
        });
        fabAdd.setOnClickListener(fab ->{
            loanSlipAdapter.dialogAddLoanSlip(getContext(),layoutFragmentLoanSlip);
            animateFab();
        });
        fabSearch.setOnClickListener(fab ->{
            showHideSearchView(view);
            animateFab();
        });
        fabChangeBackground.setOnClickListener(fab -> {
            dialogChangeBackground(getContext());
            animateFab();
        });
        recycleListLoanSlips.setAdapter(loanSlipAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loanSlipDAO.selectAllLoanSlip().clear();
                recycleListLoanSlips.setAdapter(loanSlipAdapter);
                loanSlipAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void findByIdView(View view){
        layoutFragmentLoanSlip = view.findViewById(R.id.layoutFragmentLoanSlip);
        icShowNav = view.findViewById(R.id.iv_show_nav);
        recycleListLoanSlips = view.findViewById(R.id.listLoanSlip);
        swipeRefreshLayout = view.findViewById(R.id.swiperRefreshLayout);
        searchLoanSlip = view.findViewById(R.id.searchLoanSlip);
        fabMenu = view.findViewById(R.id.fabMenu);
        fabAdd = view.findViewById(R.id.fabAddLoanSlip);
        fabSearch = view.findViewById(R.id.fabSearch);
        fabChangeBackground = view.findViewById(R.id.fabChangeBackground);
        imgLoanSlip =view.findViewById(R.id.imgFraLoanSlip);
        titleFrag1 = view.findViewById(R.id.titleFragLoanSlip);
        icHideSearchView = view.findViewById(R.id.ic_hide_search);
    }

    private  void animateFab(){
        if(isFabOpen){
            fabMenu.startAnimation(rotateBackward);

            fabAdd.setVisibility(View.GONE);
            fabChangeBackground.setVisibility(View.GONE);
            fabSearch.setVisibility(View.GONE);

            fabAdd.setClickable(false);
            fabSearch.setClickable(false);
            fabChangeBackground.setClickable(false);
            isFabOpen = false;
        }else {
            fabMenu.startAnimation(rotateForward);

            fabAdd.setVisibility(View.VISIBLE);
            fabSearch.setVisibility(View.VISIBLE);
            fabChangeBackground.setVisibility(View.VISIBLE);
            fabAdd.setClickable(true);
            fabSearch.setClickable(true);
            fabChangeBackground.setClickable(true);
            isFabOpen = true;
        }
    }


    private void showHideSearchView(View view){
        findByIdView(view);

        showSearchView(searchLoanSlip,icHideSearchView);
        searchLoanSlip.clearFocus();
        searchLoanSlip.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                return true;

            }
        });

        icHideSearchView.setOnClickListener(image ->{
            hideSearchView(searchLoanSlip,icHideSearchView);
        });
    }

    ImageView cavBackgroundHide;
    CardView cavBackground1,cavBackground2,cavBackground3,cavBackground4,cavBackground5;

    private void dialogChangeBackground(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_change_background);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        findByIdDialog(dialog);
        LinearLayout dialogLayout = dialog.findViewById(R.id.dialog_chooser_color);
        animationDialog(dialogLayout);


        cavBackgroundHide.setOnClickListener(cav ->{
            changeBackgroundLayoutDefault();
        });

        cavBackground1.setOnClickListener(cav ->{
            changeBackgroundLayout(getContext().getDrawable(R.drawable.background_1));
        });
        cavBackground2.setOnClickListener(cav ->{
            changeBackgroundLayout(getContext().getDrawable(R.drawable.background_2));
        });
        cavBackground3.setOnClickListener(cav ->{
            changeBackgroundLayout(getContext().getDrawable(R.drawable.background_3));
        });
        cavBackground4.setOnClickListener(cav ->{
            changeBackgroundLayout(getContext().getDrawable(R.drawable.background_4));
        });
        cavBackground5.setOnClickListener(cav ->{
            changeBackgroundLayout(getContext().getDrawable(R.drawable.background_5));

        });

        dialog.show();
    }
    private void findByIdDialog(Dialog dialog){

        cavBackgroundHide = dialog.findViewById(R.id.cav_background_hide);
        cavBackground1 = dialog.findViewById(R.id.cav_background_1);
        cavBackground2 = dialog.findViewById(R.id.cav_background_2);
        cavBackground3 = dialog.findViewById(R.id.cav_background_3);
        cavBackground4 = dialog.findViewById(R.id.cav_background_4);
        cavBackground5 = dialog.findViewById(R.id.cav_background_5);
    }



    private void animationDialog(LinearLayout dialog){
        dialog.setAlpha(0f);
        dialog.setTranslationY(150);
        dialog.animate().alpha(1f).translationYBy(-150).setDuration(1000);
    }
    private void changeBackgroundLayoutDefault(){
        layoutFragmentLoanSlip.setBackground(getContext().getDrawable(R.color.white));
        icShowNav.setBackground(getContext().getDrawable(R.drawable.custom_border_nav_icon));
        changeTextColorBlack();
        searchLoanSlip.setBackground(getContext().getDrawable(R.drawable.custom_search_view));
        imgLoanSlip.setVisibility(View.VISIBLE);
    }

    private void changeBackgroundLayout(Drawable idDrawable){
        layoutFragmentLoanSlip.setBackground(idDrawable);
        icShowNav.setBackground(getContext().getDrawable(R.drawable.custom_border_nav_icon_2));
        searchLoanSlip.setBackground(getContext().getDrawable(R.drawable.custom_search_view_2));
        changeTextColorWhite();
        imgLoanSlip.setVisibility(View.INVISIBLE);


    }

    private void changeTextColorWhite(){
        titleFrag1.setTextColor(Color.parseColor("#FFFFFF"));

    }
    private void changeTextColorBlack() {
        titleFrag1.setTextColor(Color.parseColor("#000000"));
    }
    private void showSearchView(SearchView searchView, ImageView img) {
        searchView.setVisibility(View.VISIBLE);
        img.setVisibility(View.VISIBLE);
        searchView.setAlpha(0f);
        searchView.setTranslationX(100);
        searchView.animate().alpha(1f).translationXBy(-100).setDuration(1500);
        img.setAlpha(0f);
        img.setTranslationZ(-100);
        img.animate().alpha(1f).translationZBy(100).setDuration(1500);

    }
    private void hideSearchView(SearchView searchView,ImageView img) {
        img.setVisibility(View.INVISIBLE);
        searchView.setVisibility(View.INVISIBLE);
    }


}
