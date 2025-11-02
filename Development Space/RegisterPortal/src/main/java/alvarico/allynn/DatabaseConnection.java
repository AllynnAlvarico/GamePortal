package alvarico.allynn;

public class DatabaseConnection {

    private String db_schema = "webdev";
//    private String jdbcConnection = "jdbc:mysql://192.168.178.145:3306/"; local network connection to my database server
//    private String jdbcConnection = "jdbc:mysql://194.125.24.208:3306/"; outside connection to my database server

//    Main Database user and password
//    private String db_user = "webdevass1";
//    private String db_password = "webdevelopmentassignment";

//    private String db_schema = "tu914";
    private String jdbc= "jdbc:mysql://localhost:3306/";
    private String db_userTable = "user";
    private String db_user = "root";
//    laptop Password
//    String db_password = "9542MEnw#";
//    Pc Password
    String db_password = "@admin2110";

    public String getSchema() {
        return db_schema;
    }

    public String getJdbc() {
        return jdbc;
    }

    public String getTable() {
        return db_userTable;
    }

    public String getUser() {
        return db_user;
    }

    public String getPassword() {
        return db_password;
    }
}
