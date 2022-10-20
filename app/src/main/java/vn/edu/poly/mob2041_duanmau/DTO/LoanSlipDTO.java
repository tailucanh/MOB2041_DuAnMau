package vn.edu.poly.mob2041_duanmau.DTO;

public class LoanSlipDTO {
    private int idLoanSlip;
    private int idMember;
    private int idBook;
    private int idLibrarian;
    private String borrowedDate;
    private int rentCost;
    private String borrowedState;

    private String nameMember;
    private String nameBook;
    private String nameLibrarian;



    public static final String TB_NAME = "tbLoanSlip";
    public static final String COL_ID = "idLoanSlip";
    public static final String COL_ID_MEMBER = "idMember";
    public static final String COL_ID_BOOK= "idBook";
    public static final String COL_ID_LIBRARIAN= "idLibrarian";
    public static final String COL_BORROWED_DATE= "borrowedDate";
    public static final String COL_RENT_COST= "rentCost";
    public static final String COL_BORROWED_STATE= "borrowedState";


    public LoanSlipDTO() {
    }

    public int getIdLoanSlip() {
        return idLoanSlip;
    }

    public void setIdLoanSlip(int idLoanSlip) {
        this.idLoanSlip = idLoanSlip;
    }

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getIdLibrarian() {
        return idLibrarian;
    }

    public void setIdLibrarian(int idLibrarian) {
        this.idLibrarian = idLibrarian;
    }

    public String getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(String borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public String getBorrowedState() {
        return borrowedState;
    }

    public void setBorrowedState(String borrowedState) {
        this.borrowedState = borrowedState;
    }

    public String getNameMember() {
        return nameMember;
    }

    public void setNameMember(String nameMember) {
        this.nameMember = nameMember;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getNameLibrarian() {
        return nameLibrarian;
    }

    public void setNameLibrarian(String nameLibrarian) {
        this.nameLibrarian = nameLibrarian;
    }

    public int getRentCost() {
        return rentCost;
    }

    public void setRentCost(int rentCost) {
        this.rentCost = rentCost;
    }
}
