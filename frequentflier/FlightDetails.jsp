<%@page import="java.sql.*" %>

<%
    String flightid = request.getParameter("flightid");
    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn = DriverManager.getConnection(url, "ppark21", "whywharg");
    Statement stmt = conn.createStatement();
 
    ResultSet rs = stmt.executeQuery("SELECT f.dept_datetime AS flight_dept_datetime, f.arrival_datetime AS flight_arrival_datetime, f.flight_miles, t.trip_id, t.trip_miles FROM Flights f JOIN Flights_Trips ft ON f.flight_id = ft.flight_id JOIN Trips t ON ft.trip_id = t.trip_id WHERE f.flight_id = '" + flightid + "'");

    String output = "";
    
   
    while (rs.next()) {
        output += rs.getObject(1) + "," + rs.getObject(2) + "," + rs.getObject(3) + "," + rs.getObject(4) + ","+ rs.getObject(5) + "#";
    }
    
    conn.close();
    out.print(output);
%>
