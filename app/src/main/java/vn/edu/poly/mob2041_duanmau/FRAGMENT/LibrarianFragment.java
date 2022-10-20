package vn.edu.poly.mob2041_duanmau.FRAGMENT;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import vn.edu.poly.mob2041_duanmau.ACTIVITYS.DialogAddLibrarian;
import vn.edu.poly.mob2041_duanmau.ADAPTER.LibrarianAdapter;
import vn.edu.poly.mob2041_duanmau.DAO.KindOfBooksDAO;
import vn.edu.poly.mob2041_duanmau.DAO.LibrarianDAO;
import vn.edu.poly.mob2041_duanmau.DTO.KindOfBooksDTO;
import vn.edu.poly.mob2041_duanmau.DTO.LibrariansDTO;
import vn.edu.poly.mob2041_duanmau.DTO.MembersDTO;
import vn.edu.poly.mob2041_duanmau.MainActivity;
import vn.edu.poly.mob2041_duanmau.R;

public class LibrarianFragment extends Fragment {
    private RecyclerView recycleListLibrarians;
    private SearchView searchLibrarians;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView icShowSearch,icHideSearch,icChangeBackground,ivShowNav,imgLibrarians;
    private TextView titleFrag1,titleFrag2;
    LinearLayout layoutFragmentLibrarians;
    LibrarianDAO librarianDAO;
    LibrarianAdapter librarianAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.librarian_fragment,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findByIdView(view);
        view.findViewById(R.id.iv_show_nav).setOnClickListener(imb ->{
            MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
        });

        librarianDAO = new LibrarianDAO(getContext());
        librarianAdapter = new LibrarianAdapter(librarianDAO.selectAllLibrarian(),librarianDAO,getContext());
        imgLibrarians.setVisibility(View.VISIBLE);
        view.findViewById(R.id.fabAddLibrarian).setOnClickListener(fab ->{
            Intent intent = new Intent(getContext(), DialogAddLibrarian.class);
            startActivity(intent);
        });
        showHideSearchView(view);
        recycleListLibrarians.setAdapter(librarianAdapter);
        icChangeBackground.setOnClickListener(img ->{
            dialogChangeBackground(getContext());
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                librarianDAO.selectAllLibrarian().clear();
                recycleListLibrarians.setAdapter(librarianAdapter);
                librarianAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        librarianDAO = new LibrarianDAO(getContext());
        librarianAdapter = new LibrarianAdapter(librarianDAO.selectAllLibrarian(),librarianDAO,getContext());
        recycleListLibrarians.setAdapter(librarianAdapter);
    }

    private void findByIdView(View view){
        layoutFragmentLibrarians = view.findViewById(R.id.layoutFragmentLibrarians);
        ivShowNav = view.findViewById(R.id.iv_show_nav);
        recycleListLibrarians = view.findViewById(R.id.listLibrarians);
        swipeRefreshLayout = view.findViewById(R.id.swiperRefreshLayout);
        searchLibrarians = view.findViewById(R.id.searchLibrarians);
        icShowSearch = view.findViewById(R.id.ic_show_search);
        icHideSearch = view.findViewById(R.id.ic_hide_search);
        icChangeBackground = view.findViewById(R.id.changeBackground);
        imgLibrarians =view.findViewById(R.id.imgFragLibrarian);
        titleFrag1 = view.findViewById(R.id.tvTitleFrag1);
        titleFrag2 = view.findViewById(R.id.tvTitleFrag2);
    }


    private void showHideSearchView(View view){
        findByIdView(view);
        searchLibrarians.setVisibility(View.INVISIBLE);
        icShowSearch.setVisibility(View.VISIBLE);
        titleFrag2.setVisibility(View.VISIBLE);
        icHideSearch.setVisibility(View.INVISIBLE);
        icShowSearch.setOnClickListener(image ->{
            showSearchView(searchLibrarians,titleFrag2);
            icHideSearch.setVisibility(View.VISIBLE);
            icShowSearch.setVisibility(View.INVISIBLE);
            searchLibrarians.clearFocus();
            searchLibrarians.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    ArrayList<LibrariansDTO> lists = new ArrayList<>();
                    for(LibrariansDTO librariansDTO : librarianDAO.selectAllLibrarian()){
                        if(librariansDTO.getNameLibrarian().toLowerCase().contains(newText.toLowerCase())){
                            lists.add(librariansDTO);
                        }
                    }
                    if(lists.isEmpty()){
                        Toast.makeText(getContext(), "không tìm thấy!", Toast.LENGTH_SHORT).show();
                    }else {
                        librarianAdapter.setFilter(lists);
                    }

                    return true;
                }
            });
        });
        icHideSearch.setOnClickListener(image ->{
            hideSearchView(searchLibrarians,titleFrag2);
            icHideSearch.setVisibility(View.INVISIBLE);
            icShowSearch.setVisibility(View.VISIBLE);
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
        layoutFragmentLibrarians.setBackground(getContext().getDrawable(R.color.white));
        ivShowNav.setBackground(getContext().getDrawable(R.drawable.custom_border_nav_icon));
        changeTextColorBlack();
        searchLibrarians.setBackground(getContext().getDrawable(R.drawable.custom_search_view));
        imgLibrarians.setVisibility(View.VISIBLE);

    }

    private void changeBackgroundLayout(Drawable idDrawable){
        layoutFragmentLibrarians.setBackground(idDrawable);
        ivShowNav.setBackground(getContext().getDrawable(R.drawable.custom_border_nav_icon_2));
        searchLibrarians.setBackground(getContext().getDrawable(R.drawable.custom_search_view_2));
        changeTextColorWhite();
        imgLibrarians.setVisibility(View.INVISIBLE);


    }

    private void changeTextColorWhite(){
        titleFrag1.setTextColor(Color.parseColor("#FFFFFF"));
        titleFrag2.setTextColor(Color.parseColor("#FFFFFF"));

    }
    private void changeTextColorBlack() {
        titleFrag2.setTextColor(Color.parseColor("#000000"));
        titleFrag1.setTextColor(Color.parseColor("#000000"));
    }
    private void showSearchView(SearchView searchView,TextView tv) {
        searchView.setVisibility(View.VISIBLE);
        searchView.setAlpha(0f);
        searchView.setTranslationX(100);
        searchView.animate().alpha(1f).translationXBy(-100).setDuration(1500);
        tv.setText("");

    }
    private void hideSearchView(SearchView searchView,TextView tv) {
        searchView.setAlpha(1f);
        searchView.setTranslationX(-100);
        searchView.animate().alpha(0f).translationXBy(100).setDuration(1500);
        tv.setAlpha(0f);
        tv.setTranslationX(-100);
        tv.animate().alpha(1f).translationXBy(100).setDuration(1500);
        tv.setText("Danh sách thủ thư");
    }



}
