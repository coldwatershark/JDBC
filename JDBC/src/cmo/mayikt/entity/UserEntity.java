package cmo.mayikt.entity;

public class UserEntity {
    private Long Id;
    private String Phone;
    private String Pwd;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String pwd) {
        Pwd = pwd;
    }

    public UserEntity(Long id, String phone, String pwd) {
        Id = id;
        Phone = phone;
        Pwd = pwd;
    }
    public UserEntity( String phone, String pwd) {
        Phone = phone;
        Pwd = pwd;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "Id=" + Id +
                ", Phone='" + Phone + '\'' +
                ", Pwd='" + Pwd + '\'' +
                '}';
    }
}
