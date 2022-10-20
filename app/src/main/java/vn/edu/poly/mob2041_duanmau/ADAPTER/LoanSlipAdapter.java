package vn.edu.poly.mob2041_duanmau.ADAPTER;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

import vn.edu.poly.mob2041_duanmau.DAO.BooksDAO;
import vn.edu.poly.mob2041_duanmau.DAO.KindOfBooksDAO;
import vn.edu.poly.mob2041_duanmau.DAO.LibrarianDAO;
import vn.edu.poly.mob2041_duanmau.DAO.LoanSlipDAO;
import vn.edu.poly.mob2041_duanmau.DAO.MemberDAO;
import vn.edu.poly.mob2041_duanmau.DTO.BooksDTO;
import vn.edu.poly.mob2041_duanmau.DTO.KindOfBooksDTO;
import vn.edu.poly.mob2041_duanmau.DTO.LibrariansDTO;
import vn.edu.poly.mob2041_duanmau.DTO.LoanSlipDTO;
import vn.edu.poly.mob2041_duanmau.DTO.MembersDTO;
import vn.edu.poly.mob2041_duanmau.R;
import vn.edu.poly.mob2041_duanmau.SPINNER.SpinnerBook;
import vn.edu.poly.mob2041_duanmau.SPINNER.SpinnerKindOfBooks;
import vn.edu.poly.mob2041_duanmau.SPINNER.SpinnerLibrarian;
import vn.edu.poly.mob2041_duanmau.SPINNER.SpinnerMember;

public class LoanSlipAdapter extends RecyclerView.Adapter<LoanSlipAdapter.MyViewHolderLoanSlip> {
    ArrayList<LoanSlipDTO> listLoanSlip;
    LoanSlipDAO loanSlipDAO;
    Context context;
    ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private int selectedYear;
    private int selectedMonth;
    private int selectedDayOfMonth;

    public LoanSlipAdapter(ArrayList<LoanSlipDTO> listLoanSlip, LoanSlipDAO loanSlipDAO, Context context) {
        this.listLoanSlip = listLoanSlip;
        this.loanSlipDAO = loanSlipDAO;
        this.context = context;
    }

    public void setFilter(ArrayList<LoanSlipDTO> filterList){
        this.listLoanSlip = filterList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolderLoanSlip onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_one_item_loan_slip,parent,false);
        return new LoanSlipAdapter.MyViewHolderLoanSlip(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderLoanSlip holder, int position) {
        final  int index = position;
        LoanSlipDTO loanSlipDTO = listLoanSlip.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(loanSlipDTO.getIdLoanSlip()));
        LoanSlipDTO nameMember = loanSlipDAO.selectNameMember(loanSlipDTO.getIdLoanSlip());
        LoanSlipDTO nameBook = loanSlipDAO.selectNameBook(loanSlipDTO.getIdLoanSlip());
        LoanSlipDTO nameLib = loanSlipDAO.selectNameLibrarian(loanSlipDTO.getIdLoanSlip());

        holder.tvNameLoanSlip.setText(nameMember.getNameMember());
        holder.tvNameBook.setText(nameBook.getNameBook());
        holder.tvState.setText(loanSlipDTO.getBorrowedState());


        if(loanSlipDTO.getBorrowedState().equalsIgnoreCase("Đã trả")){
            holder.tvState.setTextColor(Color.parseColor("#0283BD"));
            holder.imgCheckState.setImageResource(R.drawable.ic_circle_blue);
        }else if(loanSlipDTO.getBorrowedState().equalsIgnoreCase("Chưa trả")){
            holder.tvState.setTextColor(Color.parseColor("#EF2929"));
            holder.imgCheckState.setImageResource(R.drawable.ic_circle_red);
        }

        holder.layoutItems.setOnLongClickListener(layout -> {
            dialogSeeDetails(context,loanSlipDTO,nameMember,nameBook,nameLib,holder.layoutItems);
            return true;
        });

        holder.layoutItems.setOnClickListener(layout -> {
            holder.layoutItems.setBackground(context.getDrawable(R.drawable.custom_solid_one_items_1));
        });

        holder.imgDelete.setOnClickListener((img) ->{
            deleteLoanSlip(context, loanSlipDTO,index);

        });
        holder.imgUpdate.setOnClickListener((img) ->{
            updateLoanSlip(context,loanSlipDTO,index);
        });

    }


    @Override
    public int getItemCount() {
        return listLoanSlip.size();
    }


    public class MyViewHolderLoanSlip extends RecyclerView.ViewHolder{
        TextView tvNameLoanSlip,tvNameBook,tvState;
        ImageView imgUpdate, imgDelete,imgCheckState;
        SwipeRevealLayout swipeRevealLayout;
        ConstraintLayout layoutItems;

        public MyViewHolderLoanSlip(@NonNull View view) {
            super(view);
            swipeRevealLayout = view.findViewById(R.id.swiper_layout);
            layoutItems = view.findViewById(R.id.layoutOneItems);
            tvNameLoanSlip = view.findViewById(R.id.tvNameLoanSlip);
            tvNameBook = view.findViewById(R.id.tvNameBook);
            tvState = view.findViewById(R.id.tvState);
            imgCheckState = view.findViewById(R.id.imgCheckState);
            imgUpdate = view.findViewById(R.id.imgUpdate);
            imgDelete = view.findViewById(R.id.imgDelete);
        }

    }


    public void dialogAddLoanSlip(Context context, LinearLayout linearLayout ) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_loan_slip);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        LoanSlipDTO loanSlipDTO  = new LoanSlipDTO();

        TextView tvBorrowedDate = dialog.findViewById(R.id.tvBorrowedDate);
        dialog.findViewById(R.id.imgChooseBorrowedDate).setOnClickListener(img ->{
            selectDate(tvBorrowedDate,context);
        });


        TextView tvState= dialog.findViewById(R.id.tvBorrowedState);
        SwitchCompat switchState = dialog.findViewById(R.id.switchCheck);
        switchState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchState.isChecked()){
                    tvState.setText("Đã trả");
                    tvState.setTextColor(Color.parseColor("#0283BD"));
                }else{
                    tvState.setText("Chưa trả");
                    tvState.setTextColor(Color.parseColor("#EF2929"));
                }
            }
        });

        final Spinner spinnerName = dialog.findViewById(R.id.spinnerNameMember);
        MemberDAO memberDAO = new MemberDAO(context);
        SpinnerMember spinnerMember = new SpinnerMember(memberDAO.selectAllMember());
        spinnerName.setAdapter(spinnerMember);

        final Spinner spinnerNameBook = dialog.findViewById(R.id.spinnerNameBook);
        BooksDAO booksDAO = new BooksDAO(context);
        SpinnerBook spinnerBook = new SpinnerBook(booksDAO.selectAllBook());
        spinnerNameBook.setAdapter(spinnerBook);
        TextView tvRentCost = dialog.findViewById(R.id.tvRentCost);


        spinnerNameBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<BooksDTO> lists = booksDAO.selectAllBook();
                BooksDTO priceBook = lists.get(position);
                int price = priceBook.getPriceBook() - (priceBook.getPriceBook() * priceBook.getDiscountBook() / 100) ;
                tvRentCost.setText(price+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        final Spinner spinnerNameLibrarian = dialog.findViewById(R.id.spinnerNameLibrarian);
        LibrarianDAO librarianDAO = new LibrarianDAO(context);
        SpinnerLibrarian spinnerLibrarian = new SpinnerLibrarian(librarianDAO.selectAllLibrarian());
        spinnerNameLibrarian.setAdapter(spinnerLibrarian);



         dialog.findViewById(R.id.btnSave).setOnClickListener((button) ->{
              if(spinnerName.getSelectedItem() == null) {
                 errSnackBar(linearLayout, context,"Chưa có thành viên! Thêm tại mục thành viên!");
                 dialog.dismiss();
             }else if(spinnerNameBook.getSelectedItem() == null){
                  errSnackBar(linearLayout, context,"Chưa có sách ! Thêm tại mục sách!");
                  dialog.dismiss();
              }
              else if(spinnerNameLibrarian.getSelectedItem() == null){
                  errSnackBar(linearLayout, context,"Chưa có thủ thư ! Thêm tại mục thủ thư!");
                  dialog.dismiss();
              } else if(tvBorrowedDate.getText().toString().equals("")){
                  Toast.makeText(context, "Hãy chọn ngày thuê sách!", Toast.LENGTH_SHORT).show();
              } else{

                 AlertDialog.Builder builder = new AlertDialog.Builder(context);
                 builder.setIcon(R.drawable.ic_save);
                 builder.setTitle("Confirm !!!");
                 builder.setMessage("Xác nhận lưu thông tin !");
                 builder.setCancelable(false);


                 MembersDTO membersDTO = (MembersDTO) spinnerName.getSelectedItem();
                 BooksDTO booksDTO = (BooksDTO) spinnerNameBook.getSelectedItem();
                 LibrariansDTO  librariansDTO = (LibrariansDTO) spinnerNameLibrarian.getSelectedItem();

                 loanSlipDTO.setIdMember(membersDTO.getIdMember());
                 loanSlipDTO.setIdBook(booksDTO.getIdBook());
                 loanSlipDTO.setIdLibrarian(librariansDTO.getId());
                 loanSlipDTO.setBorrowedDate(tvBorrowedDate.getText().toString());
                 loanSlipDTO.setRentCost(Integer.parseInt(tvRentCost.getText().toString()));
                 loanSlipDTO.setBorrowedState(tvState.getText().toString());


                 builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog1, int which) {
                         long res = loanSlipDAO.insertLoanSlip(loanSlipDTO);
                         if(res > 0){
                             listLoanSlip.clear();
                             listLoanSlip.addAll(loanSlipDAO.selectAllLoanSlip());
                             notifyDataSetChanged();
                             Toast.makeText(context, "Đã thêm mới! ", Toast.LENGTH_SHORT).show();
                             dialog1.cancel();
                             dialog.dismiss();
                         }else {
                             Toast.makeText(context, "Không thêm được! ", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });

                 builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog1, int which) {
                         Toast.makeText(context,"Đã hủy !",Toast.LENGTH_SHORT).show();
                         dialog1.cancel();
                         dialog.dismiss();
                     }
                 });

                 AlertDialog sh = builder.create();
                 sh.show();
             }
        });
        dialog.show();
    }


    private void updateLoanSlip(Context context, LoanSlipDTO loanSlipDTO,int index){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_loan_slip);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvTitle =  dialog.findViewById(R.id.titleDialog);
        Button btnChange =  dialog.findViewById(R.id.btnSave);
        tvTitle.setText("Sửa thông tin");
        btnChange.setText("Lưu thay đổi");

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_change_2);
        builder.setTitle("Confirm !!!");
        builder.setMessage("Xác nhận sửa đổi thông tin!");
        builder.setCancelable(false);

        TextView tvBorrowedDate = dialog.findViewById(R.id.tvBorrowedDate);
        dialog.findViewById(R.id.imgChooseBorrowedDate).setOnClickListener(img ->{
            selectDate(tvBorrowedDate,context);
        });

        TextView tvRentCost = dialog.findViewById(R.id.tvRentCost);
        TextView tvState= dialog.findViewById(R.id.tvBorrowedState);
        SwitchCompat switchState = dialog.findViewById(R.id.switchCheck);
        tvRentCost.setText(loanSlipDTO.getRentCost() +"");
        tvState.setText(loanSlipDTO.getBorrowedState());
        tvBorrowedDate.setText(loanSlipDTO.getBorrowedDate());
        if(loanSlipDTO.getBorrowedState().equals("Đã trả")){
            switchState.setChecked(true);
            tvState.setTextColor(Color.parseColor("#0283BD"));
        }else{
            switchState.setChecked(false);
            tvState.setTextColor(Color.parseColor("#EF2929"));
        }

        final Spinner spinnerName = dialog.findViewById(R.id.spinnerNameMember);
        MemberDAO memberDAO = new MemberDAO(context);
        ArrayList<MembersDTO> listMembers = memberDAO.selectAllMember();
        SpinnerMember spinnerMember = new SpinnerMember(listMembers);
        spinnerName.setAdapter(spinnerMember);

        final Spinner spinnerNameBook = dialog.findViewById(R.id.spinnerNameBook);
        BooksDAO booksDAO = new BooksDAO(context);
        ArrayList<BooksDTO> listBook = booksDAO.selectAllBook();
        SpinnerBook spinnerBook = new SpinnerBook(listBook);
        spinnerNameBook.setAdapter(spinnerBook);
        spinnerNameBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<BooksDTO> lists = booksDAO.selectAllBook();
                BooksDTO priceBook = lists.get(position);
                int price = priceBook.getPriceBook() - (priceBook.getPriceBook() * priceBook.getDiscountBook() / 100) ;
                tvRentCost.setText(price+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        final Spinner spinnerNameLibrarian = dialog.findViewById(R.id.spinnerNameLibrarian);
        LibrarianDAO librarianDAO = new LibrarianDAO(context);
        ArrayList<LibrariansDTO> listLibrarian = librarianDAO.selectAllLibrarian();
        SpinnerLibrarian spinnerLibrarian = new SpinnerLibrarian(listLibrarian);
        spinnerNameLibrarian.setAdapter(spinnerLibrarian);


        for(int i = 0; i < listMembers.size(); i++){
            MembersDTO temp = listMembers.get(i);
            if(temp.getIdMember() == loanSlipDTO.getIdMember()){
                spinnerName.setSelection(i);
                spinnerName.setSelected(true);
            }
        }
        for(int i = 0; i < listBook.size(); i++){
            BooksDTO temp = listBook.get(i);
            if(temp.getIdBook() == loanSlipDTO.getIdBook()){
                spinnerNameBook.setSelection(i);
                spinnerNameBook.setSelected(true);
            }
        }

        for(int i = 0; i < listLibrarian.size(); i++){
            LibrariansDTO temp = listLibrarian.get(i);
            if(temp.getId() == loanSlipDTO.getIdLibrarian()){
                spinnerNameLibrarian.setSelection(i);
                spinnerNameLibrarian.setSelected(true);
            }
        }
        switchState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchState.isChecked()){
                    tvState.setText("Đã trả");
                    tvState.setTextColor(Color.parseColor("#0283BD"));
                }else{
                    tvState.setText("Chưa trả");
                    tvState.setTextColor(Color.parseColor("#EF2929"));
                }
            }
        });

        btnChange.setOnClickListener( button ->{
            MembersDTO membersDTO = (MembersDTO) spinnerName.getSelectedItem();
            BooksDTO booksDTO = (BooksDTO) spinnerNameBook.getSelectedItem();
            LibrariansDTO  librariansDTO = (LibrariansDTO) spinnerNameLibrarian.getSelectedItem();

            loanSlipDTO.setIdMember(membersDTO.getIdMember());
            loanSlipDTO.setIdBook(booksDTO.getIdBook());
            loanSlipDTO.setIdLibrarian(librariansDTO.getId());
            loanSlipDTO.setBorrowedDate(tvBorrowedDate.getText().toString());
            loanSlipDTO.setRentCost(Integer.parseInt(tvRentCost.getText().toString()));
            loanSlipDTO.setBorrowedState(tvState.getText().toString());

            int res = loanSlipDAO.updateLoanSlip(loanSlipDTO);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog1, int which) {
                    if(res > 0){
                        listLoanSlip.set(index,loanSlipDTO);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Đã sửa thông tin !", Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(context, "Không sửa được thông tin !" + res, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog1, int which) {
                    Toast.makeText(context,"Đã hủy !",Toast.LENGTH_SHORT).show();
                    dialog1.cancel();
                    dialog.dismiss();
                }
            });


            AlertDialog sh = builder.create();
            sh.show();
        });
        dialog.show();

    }


    public void deleteLoanSlip(Context context,LoanSlipDTO loanSlipDTO,int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa sách?");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Có chắc chắn xóa phiếu mượn ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int kq = loanSlipDAO.deleteLoanSlip(loanSlipDTO);
                if(kq > 0)
                {
                    listLoanSlip.remove(index);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã xóa! ", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }else
                    Toast.makeText(context, "Không xóa được! " + kq, Toast.LENGTH_SHORT).show();

                dialogInterface.dismiss();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Đã hủy", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void dialogSeeDetails(Context context,LoanSlipDTO loanSlipDTO, LoanSlipDTO nameMember, LoanSlipDTO infoBook,LoanSlipDTO nameLib,ConstraintLayout layoutItem){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_see_details_loan_slip);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvNameMember = dialog.findViewById(R.id.tvNameMember);
        TextView tvNameBook = dialog.findViewById(R.id.tvNameBook);
        TextView tvNameLib = dialog.findViewById(R.id.tvNameLib);
        TextView tvDate= dialog.findViewById(R.id.tvBorrowedDate);
        TextView tvRentCost= dialog.findViewById(R.id.tvRentCost);
        TextView tvState= dialog.findViewById(R.id.tvBorrowedState);
        if(loanSlipDTO.getBorrowedState().equals("Đã trả")){
            tvState.setTextColor(Color.parseColor("#0283BD"));
        }else{
            tvState.setTextColor(Color.parseColor("#EF2929"));
        }
        tvNameMember.setText(nameMember.getNameMember());
        tvNameBook.setText(infoBook.getNameBook());
        tvNameLib.setText(nameLib.getNameLibrarian());
        tvRentCost.setText(infoBook.getRentCost()+"");
        tvDate.setText(loanSlipDTO.getBorrowedDate());
        tvState.setText(loanSlipDTO.getBorrowedState());
        layoutItem.setBackground(context.getDrawable(R.drawable.background_long_click_items));


        dialog.show();
    }


    private void selectDate(TextView textView, Context context){
        final Calendar calendar = Calendar.getInstance();
        this.selectedYear = calendar.get(Calendar.YEAR);
        this.selectedMonth = calendar.get(Calendar.MONTH);
        this.selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(month < 9){
                    textView.setText(year + "-"+"0"+(month+1)+"-"+dayOfMonth);
                }else {
                    textView.setText(year + "-"+(month+1)+"-"+dayOfMonth);
                }
                textView.setFocusable(false);
                selectedYear = year;
                selectedMonth = month;
                selectedDayOfMonth = dayOfMonth;
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,dateSetListener,selectedYear,
                selectedMonth,selectedDayOfMonth);
        datePickerDialog.show();
    }


    private void errSnackBar(LinearLayout linearLayout, Context context, String errText){
        final Snackbar snackbar = Snackbar.make(linearLayout,"",Snackbar.LENGTH_LONG);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_snackbar_error, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout  snackBarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarLayout.setPadding(0,0,0,0);
        TextView tv_err = view.findViewById(R.id.tv_error);
        tv_err.setText(errText);
        snackBarLayout.addView(view, 0);
        snackbar.show();
    }
}
