package DataBase;

public class User {
    public User(String id, String userName, boolean isAdministrator, String phoneNoOrEmail, String passWord) {
        this.id = id;
        this.userName = userName;
        this.isAdministrator = isAdministrator;
        this.phoneNoOrEmail = phoneNoOrEmail;
        this.passWord = passWord;

    }
    private String id;
    private String userName;
    private boolean isAdministrator;
    private String phoneNoOrEmail;
    private String passWord;

    public User(){

    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhoneNoOrEmail() {
        return phoneNoOrEmail;
    }

    public void setPhoneNoOrEmail(String phoneNoOrEmail) {
        this.phoneNoOrEmail = phoneNoOrEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }
}
