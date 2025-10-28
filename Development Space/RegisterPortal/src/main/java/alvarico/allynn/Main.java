package alvarico.allynn;

import javax.servlet.ServletException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ServletException, SQLException {
        RegisterServlet rs = new RegisterServlet();
        LoginServlet ls = new LoginServlet();
        try {
//            rs.databaseConnection("eric", "selvig", "science", "123456");
//            rs.displayTableData();
            System.out.println(ls.retrieveUser("husky", "food").getFullName());
        } catch (SQLException e) {
            rs.displayError(e);
            throw new RuntimeException(e);
        }
    }
}