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
                creatingHTML(response, userHolder);
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

    public void creatingHTML(HttpServletResponse response, User user) throws IOException {
        System.out.println("Entering PrintWriter");
        PrintWriter out = response.getWriter();
        System.out.println("create html content");
        out.println(htmlFile(user));
        System.out.println("Finished generating html file");
    }

    public String htmlFile(User user) {
        return """
                <!DOCTYPE html>
                <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                        <title>Player Profile</title>
                        <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
                        <link rel="stylesheet" href="css/background.css">
                    </head>
                    <body class="background flex flex-col justify-center items-center gap-2 my-10">
                
                        <div class="bg-green-500 p-10 w-1/2 grid grid-cols-5 grid-rows-5 gap-2 rounded-lg">
                            <div class="flex flex-col justify-center items-center col-span-5 row-span-1 bg-gray-100 rounded-lg">
                                <h1>Welcome back!</h1>
                                <h2>%s</h2>
                            </div>
                            <article class="text-center my-auto py-1.5 h-3/4 col-start-4 row-start-2 bg-gray-100 rounded-lg">Gamer Tag:</article>
                            <article class="text-center my-auto py-1.5 h-3/4 col-start-5 row-start-2 bg-gray-100 rounded-lg">%s</article>
                            <article class="text-center my-auto py-1.5 h-3/4 col-start-4 row-start-3 bg-gray-100 rounded-lg">Account Credit:</article>
                            <article class="text-center my-auto py-1.5 h-3/4 col-start-5 row-start-3 bg-gray-100 rounded-lg">%.2f</article>
                            <form action="PlayerServlet"
                                    class="w-full flex flex-col items-center justify-center gap-4 col-span-2 row-span-2 col-start-4 row-start-4 bg-gray-100 rounded-lg">
                                <label class="bg-gray-100 border-2 rounded overflow-hidden" for="credit">
                                    <input type="text" class="px-2 w-full" name="credit" id="credit" placeholder="Enter amount">
                                </label>
                                <div class="flex justify-between gap-4">
                                    <button type="button" class="bg-blue-400 w-34 rounded-lg cursor-pointer">Add Credits</button>
                                    <button type="button" class="bg-red-400 w-34 rounded-lg cursor-pointer">Spend Credits</button>
                                </div>
                            </form>
                            <div class="col-span-3 row-span-4 col-start-1 row-start-2 bg-gray-100 rounded-lg">
                                <h1 class="text-center my-5">
                                    Items
                                </h1>
                                <section class="grid grid-cols-3 grid-rows-3 gap-2 my-1 px-2">
                                    <div class="bg-yellow-400 flex flex-col h-10 text-center col-start-1 row-start-1 cursor-pointer rounded-lg">
                                        <sup class="mt-4">******</sup>
                                        Weapon
                                    </div>
                                    <div class="bg-yellow-400 flex flex-col h-10 text-center col-start-2 row-start-1 cursor-pointer rounded-lg">
                                        <sup class="mt-4">******</sup>
                                        Helmet
                                    </div>
                                    <div class="bg-yellow-400 flex flex-col h-10 text-center col-start-3 row-start-1 cursor-pointer rounded-lg">
                                        <sup class="mt-4">******</sup>
                                        Shield
                                    </div>
                                    <div class="bg-yellow-400 flex flex-col h-10 text-center col-start-1 row-start-2 cursor-pointer rounded-lg">
                                        <sup class="mt-4">******</sup>
                                        Gloves
                                    </div>
                                    <div class="bg-yellow-400 flex flex-col h-10 text-center col-start-2 row-start-2 cursor-pointer rounded-lg">
                                        <sup class="mt-4">******</sup>
                                        Armor
                                    </div>
                                    <div class="bg-yellow-400 flex flex-col h-10 text-center col-start-3 row-start-2 cursor-pointer rounded-lg">
                                        <sup class="mt-4">******</sup>
                                        Boots
                                    </div>
                
                                    <div class="bg-yellow-400 flex flex-col h-10 text-center col-start-1 row-start-3 cursor-pointer rounded-lg">
                                        <sup class="mt-4">******</sup>
                                        Ring
                                    </div>
                
                                    <div class="bg-yellow-400 flex flex-col h-10 text-center col-start-2 row-start-3 cursor-pointer rounded-lg">
                                        <sup class="mt-4">******</sup>
                                        Necklace
                                    </div>
                
                                    <div class="bg-yellow-400 flex flex-col h-10 text-center col-start-3 row-start-3 cursor-pointer rounded-lg">
                                        <sup class="mt-4">******</sup>
                                        Banner
                                    </div>
                
                                </section>
                            </div>
                        </div>
                        <button onclick="location.href='login.html'" class="bg-red-400 text-white p-4 cursor-pointer rounded-2xl">Log out</button>
                    </body>
                </html>
                                """.formatted(user.getFullName(), user.getGamerTag(), user.getBalance());
                    }
                }
