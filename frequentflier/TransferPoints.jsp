<%@page import="java.sql.*" %>

<%
    String sourcePassengerId = request.getParameter("sourcePassengerId");
    String destinationPassengerId = request.getParameter("destinationPassengerId");
    int pointsToTransfer = Integer.parseInt(request.getParameter("pointsToTransfer"));

    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
    String url = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
    Connection conn = DriverManager.getConnection(url, "ppark21", "whywharg");
    Statement stmt = conn.createStatement();

    // Deduct points from the source passenger
    String deductPointsQuery = "UPDATE Point_Accounts SET TOTAL_POINTS = TOTAL_POINTS - " + pointsToTransfer + " WHERE PASSID = " + sourcePassengerId;
    int deductPointsResult = stmt.executeUpdate(deductPointsQuery);

    // Add points to the destination passenger
    String addPointsQuery = "UPDATE Point_Accounts SET TOTAL_POINTS = TOTAL_POINTS + " + pointsToTransfer + " WHERE PASSID = " + destinationPassengerId;
    int addPointsResult = stmt.executeUpdate(addPointsQuery);

    String output;
    
    if (deductPointsResult > 0 && addPointsResult > 0) {
        output = "Points transfer successful.";
    } else {
        output = "Points transfer failed.";
    }

    conn.close();
    out.print(output);
%>
