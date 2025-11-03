package alvarico.allynn;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
    private final DatabaseConnection dc = new DatabaseConnection();
    private final Connection connect;
    private PrintWriter pw;

    public RegisterServlet() throws SQLException {
        System.out.println("Connecting");
        connect = DriverManager.getConnection(dc.getJdbc() + dc.getSchema(), dc.getUser(), dc.getPassword());
        System.out.println("Connection Established");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("errorRegister.html");
        System.out.println("Error Registration Processs");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        pw = response.getWriter();

        System.out.println("doPost Method");
        response.setContentType("text/html");
        System.out.println("created text/html");
        String firstname = request.getParameter("fname");
        System.out.println("created name");
        String lastname = request.getParameter("lname");
        System.out.println("created last name");
        String gamerTag = request.getParameter("gamerTag");
        System.out.println("gamerTag: " + gamerTag);
        System.out.println("created gamerTag");
        String password = request.getParameter("password");
        System.out.println("created password");
        String confirmPassword = request.getParameter("cpassword");
        System.out.println("created cpassword");

        System.out.println("Entering PrintWriter");

        System.out.println("create html content");
        pw.println("<html>");
        pw.println("<body>");

        if (password.equals(confirmPassword)){
            pw.println("<h1>Player "+ gamerTag +" successfully registered</h1");
            databaseConnection(firstname, lastname, gamerTag, password);
            pw.println("</body>");
            pw.println("<script>");
            pw.println("setTimeout(function() { window.location.href = 'login.html'; }, 2000);");
            pw.println("</script>");
        } else {
            response.sendRedirect(request.getContextPath() + "/RegisterServlet");
            pw.println("<h1>Error!</h1> </body>");
        }
        pw.println("</body></html>");
//        try {
//            Thread.sleep(2000);
//            response.sendRedirect("login.html");
//        } catch (InterruptedException | IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void databaseConnection(String firstname, String lastname, String gamerTag, String password)   {
        try {
            System.out.println("Creating User Object");
            User newUser = new User(firstname, lastname, gamerTag, password);
            System.out.println("Entering Data to Table");
            createUser(preparedStatement(this.connect), newUser);
            System.out.println("Data successfully stored");
        } catch (SQLException e) {
            pw.println(e);
            displayError(e);
            throw new RuntimeException(e);
        }

    }

    public PreparedStatement preparedStatement(Connection connect) throws SQLException {
        System.out.println("Creating User");
        return connect.prepareStatement("INSERT INTO " + dc.getTable() + "(fname, lname, gamerTag, password, balance)" + " VALUES(?, ?, ?, ?, ?)");
    }
    public void createUser(PreparedStatement prep, User user) throws SQLException {
        System.out.println("setting name");
        prep.setString(1, user.getFirst_name());
        System.out.println("setting surname");
        prep.setString(2, user.getLast_name());
        System.out.println("setting gamer tag");
        prep.setString(3, user.getGamerTag());
        System.out.println("setting password");
        prep.setString(4, user.getPassword());
        System.out.println("setting balance");
        prep.setString(5, String.valueOf(user.getBalance()));
        System.out.println("executing update");
        prep.executeUpdate();
        System.out.println("Prepared Statement Closing");
        prep.close();
        System.out.println("Closing Connection");
    }

    public void displayError(Exception e){
        System.out.printf("Method displayError Line 92 error in class:%s", RegisterServlet.class.getName());
        System.out.printf("\nException Trace:%s", e);
    }
    public void displayTableData() throws SQLException {
        ResultSet rs = connect.createStatement().executeQuery("SELECT * FROM " + dc.getTable());
        String fname;
        String lname;
        String gamerTag;
        String password;
        int balance;
        System.out.println("Retrieving Records");
        while (rs.next()) {
            fname = rs.getString(1);
            lname = rs.getString(2);
            gamerTag = rs.getString(3);
            password = rs.getString(4);
            if (rs.getString(5) != null) {
                balance = Integer.parseInt(rs.getString(5));
            } else {
                balance = 0;
            }

            String formatted = String.format("Full Name: %s %s | GamerTag: %s | Password: %s | balance: %s", fname, lname, gamerTag, password, balance);
            System.out.println(formatted);
        }
    }
//    public void redirectPage(HttpServletResponse response){
//        try {
//            Thread.sleep(2000);
//            response.sendRedirect("login.html");
//        } catch (InterruptedException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
