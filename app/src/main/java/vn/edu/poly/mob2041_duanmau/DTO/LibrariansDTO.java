package vn.edu.poly.mob2041_duanmau.DTO;

public class LibrariansDTO {
    private int id;
    private String nameLibrarian;
    private String phoneNumbers;
    private String userName;
    private String password;


    public static final String TB_NAME = "tbLibrarian";
    public static final String COL_ID = "idLibrarian";
    public static final String COL_NAME_LIBRARIAN = "nameLibrarian";
    public static final String COL_PHONE_NUMBER= "phoneLibrarian";
    public static final String COL_USER_NAME = "userLibrarian";
    public static final String COL_PASS_WORD = "passLibrarian";

    public LibrariansDTO() {
    }


    public LibrariansDTO(int id, String nameLibrarian, String phoneNumbers, String userName, String password) {
        this.id = id;
        this.nameLibrarian = nameLibrarian;
        this.phoneNumbers = phoneNumbers;
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNameLibrarian() {
        return nameLibrarian;
    }

    public void setNameLibrarian(String nameLibrarian) {
        this.nameLibrarian = nameLibrarian;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
