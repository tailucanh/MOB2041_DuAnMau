package vn.edu.poly.mob2041_duanmau.DTO;

public class KindOfBooksDTO {
   private int idKindOfBook;
   private String titleBook;

    public static final String TB_NAME = "tbKindOfBook";
    public static final String COL_ID = "idKindOfBook";
    public static final String COL_TITLE_BOOK = "titleBook";

    public KindOfBooksDTO() {
    }

    public KindOfBooksDTO(int idKindOfBook, String titleBook) {
        this.idKindOfBook = idKindOfBook;
        this.titleBook = titleBook;
    }

    public int getIdKindOfBook() {
        return idKindOfBook;
    }

    public void setIdKindOfBook(int idKindOfBook) {
        this.idKindOfBook = idKindOfBook;
    }

    public String getTitleBook() {
        return titleBook;
    }

    public void setTitleBook(String titleBook) {
        this.titleBook = titleBook;
    }
}
