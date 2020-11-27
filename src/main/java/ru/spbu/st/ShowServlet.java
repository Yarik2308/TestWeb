package ru.spbu.st;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kovshov
 */
public class ShowServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()){
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShowParams</title>");
            out.println("<link rel=\"shortcut icon\" href=\"ic.png\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet at " + request.getContextPath() + "</h1>");
            out.println("<h2> Servlet name <i>" + getServletName() + "</i>." );
            out.println("<h2> Servlet info <i>" + getServletInfo() + "</i>." );


            //Display the value of parameter "a"
            out.println("<h3> Parameter by name </h3>");
            out.println("a : " + request.getParameter("a"));
            out.println("<hr>");

            //Display first values of all parameters.
            out.println("<h3> All parameters </h3>");
            for(Enumeration en = request.getParameterNames(); en.hasMoreElements();){
                String name = (String) en.nextElement();
                out.println("<p> parameter <em> " + name + "</em> = <strong>" + request.getParameter(name) + "</strong>");
            }
            out.println("<hr>");

            //Display values of all parameters even if some parameters have multiply values
            out.println("<h3> All parameters with multiply values if any</h3>");
            Map<String, String[]> params = request.getParameterMap();
            Set<String> setkeys = params.keySet();
            for(String name : setkeys){
                String[] param = params.get(name);
//                if(param.getClass().isArray()){
                if(param.length > 1){
                    out.println("<p> parameter <em> " + name + "</em><br /> ");
                    for (int i = 0; i < param.length; i++) {
                        String object = param[i];
                        out.println("<strong>" + object + "</strong><br />");
                    }
                    out.println("</p>");
                } else {
                    out.println("<p> parameter <em> " + name + "</em> = <strong>" +  param[0] + "</strong></p>");
                }
            }
            out.println("<hr>");
            out.println("<p> Init parameter <b>abcdefg:</b> <i>" + getInitParameter("abcdefg") + "</i></p>");
            out.println("<h3> All init parameters: </h3>");
            for (Enumeration<String> en = getInitParameterNames(); en.hasMoreElements();) {
                String paramName = en.nextElement();
                out.println("<p> Init parameter <b>" + paramName + ":</b> <i>" + getInitParameter(paramName) + "</i></p>");
            }
            out.println("<hr>");
            out.println("<h3>Session parametres</h3>");
            HttpSession session = request.getSession();
            for (Enumeration<String> en = session.getAttributeNames(); en.hasMoreElements();) {
                String paramName = en.nextElement();
                out.println("<p> Session parameter <b>" + paramName + ":</b> <i>" + session.getAttribute(paramName) + "</i></p>");
            }
            session.setAttribute("sa", request.getParameter("a"));

            out.println("</body>");
            out.println("</html>");
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
