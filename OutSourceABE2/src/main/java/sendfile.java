/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DLK 1
 */
public class sendfile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String id="",rname,fname,fid,email;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter(); 
        {
            try {
                id=request.getParameter("id");
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/utility", "root", "root");
                String Qu="select * from req where id='"+id+"'";
                PreparedStatement pss=con.prepareStatement(Qu);
                ResultSet rss=pss.executeQuery();
                if(rss.next())
                {
                    rname=rss.getString("rname");
                    fname=rss.getString("fname");
                    fid=rss.getString("fid");
                }
                String Qu1="select * from ureg where name='"+rname+"'";
                PreparedStatement ps=con.prepareStatement(Qu1);
                ResultSet rs=ps.executeQuery();
                if(rs.next())
                {
                    email=rs.getString("email");
                }
                 MailProjectClass oo = new MailProjectClass();
                 oo.fun(fname, email);
                 out.println("<script>"
				+"alert('Request Send Successfully')"
				+"</script>");
                 PreparedStatement p=con.prepareStatement("update req set status='Active' where id='"+id+"'");
                p.executeUpdate();
                RequestDispatcher rd=request.getRequestDispatcher("publisher.jsp");
		rd.include(request, response);
            } catch (Exception ex) {
                Logger.getLogger(sendfile.class.getName()).log(Level.SEVERE, null, ex);
            }
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
