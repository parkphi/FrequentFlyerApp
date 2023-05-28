package com.mycompany.frequentflier;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import java.sql.*;

@WebServlet("/loginservlet")
public class LoginServlet extends HttpServlet {

public void doGet(HttpServletRequest request, HttpServletResponse response){
    
    try{
        String user=request.getParameter("user");
        String pass=request.getParameter("pass");
        
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu","ppark21","whywharg");
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("Select * from login where username='"+user+"' AND passwd='"+pass+"'");
        PrintWriter out=response.getWriter();
        if(rs.next()){
            if(((String)rs.getObject(2)).equals(user)&&((String)rs.getObject(3)).equals(pass)){
                out.print("Yes:"+rs.getObject(1));
            }
            else{
                out.print("No");
            }
        }
        else{
            out.print("No");
        }
    }
    catch(Exception e){}
    }
}
