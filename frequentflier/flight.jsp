<%@page import="java.sql.*" %>

<%
    String passid = request.getParameter("passid");
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn = DriverManager.getConnection(url, "ppark21", "whywharg");
    Statement stmt = conn.createStatement();   
    ResultSet rs = stmt.executeQuery("SELECT f.flight_id, f.flight_miles, f.destination FROM passengers p JOIN flights f ON p.passid = f.passid WHERE p.passid = " + passid);

    String output = "";
    
    while(rs.next()) {
        output+=rs.getObject(1)+","+rs.getObject(2)+","+rs.getObject(3)+"#";
    }
    
    conn.close();
    out.print(output);
%>
