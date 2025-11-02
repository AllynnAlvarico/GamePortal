package alvarico.allynn;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginServlet extends HttpServlet {
    private final DatabaseConnection dc = new DatabaseConnection();
    private final Connection connect;
    private final HTMLGenerator hg = new HTMLGenerator();

    public LoginServlet() throws SQLException {
        System.out.println(LoginServlet.class.getName());
        System.out.println("Connecting");
        connect = DriverManager.getConnection(dc.getJdbc() + dc.getSchema(), dc.getUser(), dc.getPassword());
        System.out.println("Connection Established");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("errorLogin.html");
        System.out.println("Error Registration Processs");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(LoginServlet.class.getName() + " | doPost Method" );
        response.setContentType("text/html");
        System.out.println("created text/html");
        String gamerTag = request.getParameter("gamerTag");
        System.out.println("created name");
        String password = request.getParameter("password");

        try {
            User userHolder = retrieveUser(gamerTag, password);
            System.out.println("Returning User Details");
            if (userHolder != null){
                System.out.println(userHolder.getLast_name());
                System.out.println(userHolder.getBalance());
                hg.creatingHTML(response, userHolder,"");
            } else {
                response.sendRedirect(request.getContextPath() + "/LoginServlet");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User retrieveUser(String validGameTag, String validPassword) throws SQLException {
        ArrayList<User> listUsers = new ArrayList<>();
        ResultSet rs = connect.createStatement().executeQuery("SELECT * FROM " + dc.getTable());
        System.out.println("Retrieving Records");
        User foundUser = null;

        while (rs.next()) {
            listUsers.add(new User(rs.getString(1), rs.getString(2),
                            rs.getString(3), rs.getString(4),
                            Integer.parseInt(rs.getString(5))));
            System.out.println("Records Size " + listUsers.size());
        }

        for (int index = 0; index <= listUsers.size(); index++) {
            if (listUsers.get(index).getGamerTag().equals(validGameTag) && listUsers.get(index).getPassword().equals(validPassword)){
                System.out.println("Found User");
                System.out.println("Retrieving User Data");
                foundUser = listUsers.get(index);
                return foundUser;
            }
        }
        return foundUser;
    }


}
