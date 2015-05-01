package com.douwe.notes.web;

import com.douwe.notes.entities.Departement;
import com.douwe.notes.service.IInsfrastructureService;
import com.douwe.notes.service.ServiceException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vincent Douwe <douwevincent@yahoo.fr>
 */
public class MaServlet extends HttpServlet {

    @EJB
    private IInsfrastructureService insfrastructureService;

    @Override
    public void init() throws ServletException {
        super.init();
        /*System.out.println("Hello de la part d'un connard");
         ApplicationContext cxt = new ClassPathXmlApplicationContext("classpath:config/spring-config.xml");
         IAnneeAcademiqueDao an = cxt.getBean(IAnneeAcademiqueDao.class);*/
    }

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
            throws ServletException, IOException, ServiceException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Departement dep = new Departement();
            dep.setCode("INFOTEL");
            dep.setFrenchDescription("Informatique et Telecommunications");
            insfrastructureService.saveOrUpdateDepartement(dep);
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MaServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MaServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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
        try {
            processRequest(request, response);
        } catch (ServiceException ex) {
            Logger.getLogger(MaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ServiceException ex) {
            Logger.getLogger(MaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public IInsfrastructureService getInsfrastructureService() {
        return insfrastructureService;
    }

    public void setInsfrastructureService(IInsfrastructureService insfrastructureService) {
        this.insfrastructureService = insfrastructureService;
    }
    
}
