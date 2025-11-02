package alvarico.allynn;

public class User {
    private String first_name;
    private String last_name;
//    private String email;
    private String gamerTag;
    private String password;
    private int balance;

    public User(String first_name, String last_name, String gamerTag, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
//        this.email = "";
        this.gamerTag = gamerTag;
        this.password = password;
        this.balance = 500;
    }

    public User(String first_name, String last_name, String gamerTag, String password, int balance) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gamerTag = gamerTag;
        this.password = password;
        this.balance = balance;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

//    public void setEmail(String email) {
//        this.email = email;
//    }

    public void setGamerTag(String gamerTag) {
        this.gamerTag = gamerTag;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFullName(){
        return getFirst_name() + " " + getLast_name();
    }

    public String getGamerTag() {
        return gamerTag;
    }

    public String getPassword() {
        return password;
    }

    public int getBalance() {
        return balance;
    }
}
