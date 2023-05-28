<%@page import="java.sql.*" %>

<%
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String url="jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn=DriverManager.getConnection(url,"ppark21","whywharg");
    Statement stmt=conn.createStatement();
    ResultSet rs=stmt.executeQuery("Select * from passengers");
    String output="";
    
    while(rs.next()) {
        output+=rs.getObject(1)+","+rs.getObject(2)+","+rs.getObject(3)+"#";
    }
    conn.close();
    out.print(output);
%>
    
