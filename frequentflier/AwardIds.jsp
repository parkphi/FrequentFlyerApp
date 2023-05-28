<%@page import="java.sql.*" %>

<%
    String passid = request.getParameter("passid");    
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn = DriverManager.getConnection(url, "ppark21", "whywharg");
    Statement stmt = conn.createStatement();
    
    ResultSet rs = stmt.executeQuery("SELECT DISTINCT a.award_id FROM Awards a JOIN Redemption_History rh ON a.award_id = rh.award_id WHERE rh.passid = " +  passid);

    String output = "";
    
    while (rs.next()) {
        output += rs.getString(1) + "#";
    }
    
    conn.close();
    out.print(output);
%>
