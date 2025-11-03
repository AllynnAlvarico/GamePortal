package alvarico.allynn;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PlayerServlet extends HttpServlet{
    private final DatabaseConnection dc = new DatabaseConnection();
    private final Connection connect;
    private final HTMLGenerator hg = new HTMLGenerator();
    private User userHolder;
    private String outputMessage;

    public PlayerServlet() throws SQLException {
        System.out.println(PlayerServlet.class.getName());
        System.out.println("Connecting");
        connect = DriverManager.getConnection(dc.getJdbc() + dc.getSchema(), dc.getUser(), dc.getPassword());
        System.out.println("Connection Established");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        hg.creatingHTML(response, userHolder, outputMessage);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gamerTag = request.getParameter("gamerTag");
        String password = request.getParameter("password");
        int balance = Integer.parseInt(request.getParameter("balance"));
        int credit = Integer.parseInt(request.getParameter("credit"));
        int newBalance;

        if (request.getParameter("credit") == null || request.getParameter("credit").isEmpty()){
            credit = 0;
            outputMessage = bannerError(true, "Cannot be empty");
//            response.sendRedirect("");
            hg.creatingHTML(response, userHolder, outputMessage);
        }

        userHolder = retrieveUser(gamerTag, password);
        if (request.getParameter("add") != null) {
            System.out.println("Hello I am Add Button");
            System.out.println("Balance is " + balance);
            System.out.println("Amount to be Added is " + credit);
            newBalance = balance + credit;
            System.out.println("New Balance is " + newBalance);
            userHolder.setBalance(newBalance);
            playerUpdateBalance(gamerTag, newBalance);
        } else if (request.getParameter("spend") != null) {
            System.out.println("Hey There I am Spend Button");
            System.out.println("Balance is " + balance);
            System.out.println("Amount to be Added is " + credit);
            if (credit > balance){
                System.out.println("Cannot spend more money as balance is 0");
                outputMessage = bannerError(true, "Cannot Spend Money");
            } else {
                newBalance = balance - credit;
                System.out.println("New Balance is " + newBalance);
                userHolder.setBalance(newBalance);
                playerUpdateBalance(gamerTag, newBalance);
            }
        }

        response.sendRedirect(request.getContextPath() + "/PlayerServlet");
    }

    public User retrieveUser(String validGameTag, String validPassword){
        ArrayList<User> temp = new ArrayList<>();
        try {
            ResultSet rs = connect.createStatement().executeQuery("SELECT * FROM " + dc.getTable());
            System.out.println("Retrieving Records");
            User foundUser = null;

            while (rs.next()) {
                temp.add(new User(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4),
                        Integer.parseInt(rs.getString(5))));
                System.out.println("Records Size " + temp.size());
            }

            for (int index = 0; index <= temp.size(); index++) {
                if (temp.get(index).getGamerTag().equals(validGameTag) && temp.get(index).getPassword().equals(validPassword)){
                    System.out.println("Found User");
                    System.out.println("Retrieving User Data");
                    foundUser = temp.get(index);
                    return foundUser;
                }
            }
            return foundUser;
        } catch ( SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String bannerError(boolean error, String message){
        if (!error) {
            return "";
        } else {
            return """
                <div class="bg-red-500 py-2 px-4 rounded-md text-white text-center fixed bottom-4 right-4 flex gap-4">
                    <p>%s</p>
                    <span class="cursor-pointer font-bold" onclick="return this.parentNode.remove()"><sup>X</sup></span>
                </div>
                """.formatted(message);
        }
    }

    public void playerUpdateBalance(String gamerTag, int newBalance) {
        try {
            String updateSQL = "UPDATE " + dc.getTable() + " SET balance = ? WHERE gamerTag = ?";
            try (PreparedStatement ps = connect.prepareStatement(updateSQL)) {
                ps.setInt(1, newBalance);
                ps.setString(2, gamerTag);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
