/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplejdbc.CustomerEntity;
import simplejdbc.DAO;
import simplejdbc.DataSourceFactory;

/**
 *
 * @author lduphil
 */
@WebServlet(name = "ClientsEtat", urlPatterns = {"/ClientsEtat"})
public class ClientsEtat extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ClientsEtat</title>");            
            out.println("</head>");
            out.println("<body>");
            try {   // Trouver la valeur du paramètre HTTP customerID
                String val = request.getParameter("state");
                if (val == null) {
                    val="CA";
                }
                // on doit convertir cette valeur en entier (attention aux exceptions !)
                //String state = String.valueOf(val);
 
                
                
                Methodes daoEtats = new Methodes(DataSourceFactory.getDataSource());
                List<String> mesEtats = daoEtats.listeEtats();
                
               
                List<CustomerEntity> customer = daoEtats.customersInState(val);
                
                if (customer == null) {
                    throw new Exception("etat inconnu");
                }
                if (mesEtats == null) {
                    throw new Exception("aucun etat");
                }
                /*Liste déroulante*/
                out.println("<form>");
                out.println("<select name='state'>");
                for (String s : mesEtats){ 
                    out.printf("<option value='%s'> %s </option>",s,s );
                }
                
                out.println("</select>");
                out.println("<input type='submit'>");
                out.println("</form>");
                
                out.println("<table border=1>");
                out.println("<tr><th>Customer</th><th>Name</th><th>Address</th></tr>");
                for (CustomerEntity c : customer){      
                    out.printf("<tr><td> %d </td><td> %s </td><td> %s</td></tr>",
                        c.getCustomerId(),
                        c.getName(),
                        c.getAddressLine1());
                }
                out.println("</table>");
            } catch (Exception e) {
                out.printf("Erreur : %s", e.getMessage());
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
