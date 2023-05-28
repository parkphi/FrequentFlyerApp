<%@page import="java.sql.*" %>

<%
    String passid = request.getParameter("passid");
    String awardid = request.getParameter("awardid");
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn = DriverManager.getConnection(url, "ppark21", "whywharg");
    Statement stmt = conn.createStatement();
     
    ResultSet rs = stmt.executeQuery("SELECT a.a_description, a.points_required, rh.redemption_date, ec.center_name FROM Awards a JOIN Redemption_History rh ON a.award_id = rh.award_id JOIN ExchgCenters ec ON rh.center_id = ec.center_id WHERE a.award_id = " + awardid + " AND rh.passid = " + passid);
    
    String output = "";
   
    while (rs.next()) {
        output += rs.getObject(1) + "," + rs.getObject(2) + "," + rs.getObject(3) + "," + rs.getObject(4) + "#";
    }
    conn.close();
    out.print(output);
%>
