<%@page import="java.sql.*" %>

<%
    String passid = request.getParameter("passid");
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn = DriverManager.getConnection(url, "ppark21", "whywharg");
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT p.PNAME, pa.TOTAL_POINTS FROM Passengers p JOIN Point_Accounts pa ON p.PASSID = pa.PASSID WHERE p.PASSID = " + passid);
 
    String output = "";
    
    while (rs.next()) {
        output += rs.getObject(1) + "," + rs.getObject(2) + ",";
    }

    conn.close();
    out.print(output);
%>
