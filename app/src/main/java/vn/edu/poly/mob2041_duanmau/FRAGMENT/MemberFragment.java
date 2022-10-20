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

import java.util.ArrayList;

import vn.edu.poly.mob2041_duanmau.ADAPTER.KindOfBookAdapter;
import vn.edu.poly.mob2041_duanmau.ADAPTER.MemberAdapter;
import vn.edu.poly.mob2041_duanmau.DAO.KindOfBooksDAO;
import vn.edu.poly.mob2041_duanmau.DAO.MemberDAO;
import vn.edu.poly.mob2041_duanmau.DTO.KindOfBooksDTO;
import vn.edu.poly.mob2041_duanmau.DTO.MembersDTO;
import vn.edu.poly.mob2041_duanmau.MainActivity;
import vn.edu.poly.mob2041_duanmau.R;

public class MemberFragment extends Fragment {
    private RecyclerView recycleListMember;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchMember;
    private ImageView icShowSearch,icHideSearch,icChangeBackground,ivShowNav,imgMember;
    private TextView titleFrag1,titleFrag2;
    MemberDAO memberDAO;
    MemberAdapter memberAdapter;
    LinearLayout layoutFragmentMember;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.members_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findByIdView(view);
        view.findViewById(R.id.iv_show_nav).setOnClickListener(imb ->{
            MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
        });

        imgMember.setVisibility(View.VISIBLE);
        memberDAO = new MemberDAO(view.getContext());
        memberAdapter = new MemberAdapter(memberDAO.selectAllMember(),memberDAO,getContext());
        view.findViewById(R.id.fabAddMember).setOnClickListener(fab ->{
            memberAdapter.dialogAddMember(getContext());
        });

        showHideSearchView(view);
        recycleListMember.setAdapter(memberAdapter);

        icChangeBackground.setOnClickListener(img ->{
            dialogChangeBackground(getContext());
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                memberDAO.selectAllMember().clear();
                recycleListMember.setAdapter(memberAdapter);
                memberAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void findByIdView(View view){
        layoutFragmentMember = view.findViewById(R.id.layoutFragmentMember);
        ivShowNav = view.findViewById(R.id.iv_show_nav);
        recycleListMember = view.findViewById(R.id.listMembers);
        swipeRefreshLayout = view.findViewById(R.id.swiperRefreshLayout);
        searchMember = view.findViewById(R.id.searchMember);
        icShowSearch = view.findViewById(R.id.ic_show_search);
        icHideSearch = view.findViewById(R.id.ic_hide_search);
        icChangeBackground = view.findViewById(R.id.changeBackground);
        imgMember =view.findViewById(R.id.imgFragMember);
        titleFrag1 = view.findViewById(R.id.tvTitleFrag1);
        titleFrag2 = view.findViewById(R.id.tvTitleFrag2);
    }


    private void showHideSearchView(View view){
        findByIdView(view);
        memberDAO = new MemberDAO(view.getContext());
        searchMember.setVisibility(View.INVISIBLE);
        icShowSearch.setVisibility(View.VISIBLE);
        titleFrag2.setVisibility(View.VISIBLE);
        icHideSearch.setVisibility(View.INVISIBLE);
        icShowSearch.setOnClickListener(image ->{
            showSearchView(searchMember,titleFrag2);
            icHideSearch.setVisibility(View.VISIBLE);
            icShowSearch.setVisibility(View.INVISIBLE);
            searchMember.clearFocus();
            searchMember.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    ArrayList<MembersDTO> lists = new ArrayList<>();
                    for(MembersDTO membersDTO : memberDAO.selectAllMember()){
                        if(membersDTO.getNameMember().toLowerCase().contains(newText.toLowerCase())){
                            lists.add(membersDTO);
                        }
                    }
                    if(lists.isEmpty()){
                        Toast.makeText(getContext(), "không tìm thấy!", Toast.LENGTH_SHORT).show();
                    }else {
                        memberAdapter.setFilter(lists);
                    }
                    return true;
                }
            });
        });
        icHideSearch.setOnClickListener(image ->{
            hideSearchView(searchMember,titleFrag2);
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
        layoutFragmentMember.setBackground(getContext().getDrawable(R.color.white));
        ivShowNav.setBackground(getContext().getDrawable(R.drawable.custom_border_nav_icon));
        changeTextColorBlack();
        searchMember.setBackground(getContext().getDrawable(R.drawable.custom_search_view));
        imgMember.setVisibility(View.VISIBLE);

    }

    private void changeBackgroundLayout(Drawable idDrawable){
        layoutFragmentMember.setBackground(idDrawable);
        ivShowNav.setBackground(getContext().getDrawable(R.drawable.custom_border_nav_icon_2));
        searchMember.setBackground(getContext().getDrawable(R.drawable.custom_search_view_2));
        changeTextColorWhite();
        imgMember.setVisibility(View.INVISIBLE);

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
        tv.setText("Danh sách thể loại");
    }

}
