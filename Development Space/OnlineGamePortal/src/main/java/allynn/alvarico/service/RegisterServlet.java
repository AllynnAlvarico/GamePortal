package allynn.alvarico.service;

import allynn.alvarico.model.User;
import allynn.alvarico.repository.UserRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String inputName = request.getParameter("fname");
        String inputPassword = request.getParameter("password");

        response.setContentType("text/plain");

        String db_schema = "webdev";
//        String jdbcURL = "jdbc:mysql://webdevdatabase.zpulsehomelab.com/"+ db_schema;
//
//        String dbUser = "webdevass1";
//        String dbPassword = "webdevelopmentassignment";

        String jdbcURL = "jdbc:mysql://localhost:3306/"+ db_schema;
        String dbUser = "root";
        String dbPassword = "@admin2110";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            out.println("Connecting to database: " + jdbcURL);
            String sql = "INSERT INTO user (name, password) VALUES (?, ?)";
            out.println("Preparing SQL: " + sql);

            PreparedStatement statement = connection.prepareStatement(sql);
            out.println("Preparing Statement: " + statement);

            statement.setString(1, inputName);
            statement.setString(2, inputPassword);
            out.println("Inserting Data to the database");

            int rowsInserted = statement.executeUpdate();
            out.println("Executed");

            if (rowsInserted > 0) {
                out.println("Data inserted successfully!");
            } else {
                out.println("Failed to insert data.");
            }
            out.println("Registered Successfully!");

        } catch (Exception e) {
            out.println("Driver Connection: " + jdbcURL);
            throw new ServletException("DB error", e);
        }

    }


}
