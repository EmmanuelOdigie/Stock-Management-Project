package stockManagement;

import java.sql.*;

public class Database {

    private static String database = "jdbc:ucanaccess://StockManagement.accdb";
    private static Connection con;

    public static Connection getCon() {
        if(con == null){
            try{
                con = DriverManager.getConnection(database);

            }catch (Exception e){
                e.printStackTrace();
            }
        } return con;
    }
}
