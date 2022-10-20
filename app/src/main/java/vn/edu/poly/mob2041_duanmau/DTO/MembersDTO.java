package vn.edu.poly.mob2041_duanmau.DTO;

public class MembersDTO {
   private int idMember;
   private String nameMember;
    private String birthDate;

    public static final String TB_NAME = "tbMember";
    public static final String COL_ID = "idMember";
    public static final String COL_NAME_MEMBER = "nameMember";
    public static final String COL_BIRTH_DATE= "birthDate";
    public MembersDTO() {
    }

    public MembersDTO(int idMember, String nameMember, String birthDate) {
        this.idMember = idMember;
        this.nameMember = nameMember;
        this.birthDate = birthDate;
    }

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public String getNameMember() {
        return nameMember;
    }

    public void setNameMember(String nameMember) {
        this.nameMember = nameMember;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
