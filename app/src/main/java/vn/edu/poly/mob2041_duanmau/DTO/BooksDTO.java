package vn.edu.poly.mob2041_duanmau.DTO;

public class BooksDTO {
    private int idBook;
    private String nameBook;
    private int priceBook;
    private int discountBook;
    private int idKindOfBook;
    private String nameKindOfBook;


    public static final String TB_NAME = "tbBooks";
    public static final String COL_ID = "idBook";
    public static final String COL_NAME_BOOK = "nameBook";
    public static final String COL_PRICE= "priceBook";
    public static final String COL_DISCOUNT= "discountBook";
    public static final String COL_ID_KIND_OF_BOOK= "idKindOfBook";

    public BooksDTO() {
    }

    public BooksDTO(int idBook, String nameBook, int priceBook, int discountBook, int idKindOfBook, String nameKindOfBook) {
        this.idBook = idBook;
        this.nameBook = nameBook;
        this.priceBook = priceBook;
        this.discountBook = discountBook;
        this.idKindOfBook = idKindOfBook;
        this.nameKindOfBook = nameKindOfBook;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public int getPriceBook() {
        return priceBook;
    }

    public void setPriceBook(int priceBook) {
        this.priceBook = priceBook;
    }

    public int getDiscountBook() {
        return discountBook;
    }

    public void setDiscountBook(int discountBook) {
        this.discountBook = discountBook;
    }

    public int getIdKindOfBook() {
        return idKindOfBook;
    }

    public void setIdKindOfBook(int idKindOfBook) {
        this.idKindOfBook = idKindOfBook;
    }

    public String getNameKindOfBook() {
        return nameKindOfBook;
    }

    public void setNameKindOfBook(String nameKindOfBook) {
        this.nameKindOfBook = nameKindOfBook;
    }
}

