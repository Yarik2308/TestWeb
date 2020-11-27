package ru.spbu.st;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sqlite.JDBC;

/**
 *
 * @author Kovshov
 */
public class DBServlet extends HttpServlet {

    String dbURL = "jdbc:mysql://localhost/test";
    String dbUser = "student";
    String dbPass = "student";
    String jdbcClassName = "com.mysql.jdbc.Driver";
    
    @Override
    public void init(){
        dbURL = getInitParameter("dbAddress");
        dbUser = getInitParameter("dbLogin");
        dbPass = getInitParameter("dbPassw");
        jdbcClassName = getInitParameter("jdbcDriver");
    }

    /**
     * 
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();            
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyServlet</title>");            
            out.println("<style type=\"text/css\">");
            out.println("table, td, th { border:blue solid 2px; }");
            out.println("td, th {padding:10px;}");
            out.println("table {border-collapse:collapse;}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DbServlet at " + request.getContextPath() + "</h1>");
            String paramA = request.getParameter("a");
            out.println("a : " + paramA);
            String paramC = request.getParameter("c");
            out.println("с : " + paramC);
            //Get and display data from base
            //retrieveData(out, "select * from " + paramA + " where id <= " + paramC);
            retrieveData(out, "select * from city where country = '" + paramA + "' and population > " + paramC);
            out.println("---- List of system properties ----<br>");
            Properties prop = System.getProperties();
            for(Enumeration en = prop.propertyNames(); en.hasMoreElements();) {
                String name = (String) en.nextElement();
                out.println(name + "=" + prop.getProperty(name) + "<br>");
            }
            out.println("<hr>");
        } catch (Exception ex){
            out.println("<h3>Error</h3>" + ex.toString());
            ex.printStackTrace(System.out);
        } finally {            
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
    }

    private void retrieveData(PrintWriter out, String query)
            throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Connection conn = null;
        Statement stmt = null;
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL resource = classloader.getResource(dbURL);
        System.out.println("Resource Directory = " + resource);
        try {
            //(Driver)Class.forName(jdbcClassName).newInstance()
            DriverManager.registerDriver(new org.sqlite.JDBC());
            conn = DriverManager.getConnection("jdbc:sqlite:"+resource.toString(), dbUser, dbPass);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            out.println("<table>");
            out.println("<tr>");
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                out.println("<th>" + rsmd.getColumnName(i) +"</th>");
            }
            out.println("</tr>");

            while (rs.next()) {
                out.println("<tr>");
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String data = rs.getString(i);
                    if(data == null) data = "";
                    if(data.trim().length() == 0) data = " &nbsp; ";
                    out.println("<td>" + data + "</td>");
                }
                out.println("</tr>");
            }
            out.println("</table>");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
        

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

