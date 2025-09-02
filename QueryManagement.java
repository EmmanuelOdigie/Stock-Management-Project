package stockManagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class QueryManagement {

    private final Connection con; // database connection
    private User selectedUser; // selected user

    public QueryManagement() {
        this.con = Database.getCon();
    }

    public boolean authenticateUser(String email, String pass) {
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM User where email=\"%s\" and password=\"%s\"".formatted(email, pass)); // get users from database with given mail and password
            if (rs.next()) { // check for record
                selectedUser = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)); // create user object
                return true;  // return true as user exist
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String[][] getStocks() {
        ArrayList<ArrayList<String>> lis = new ArrayList<>();
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Product"); // query to get all product
            while (rs.next()) {
                ArrayList<String> list = new ArrayList<>(); // list of string
                list.add("" + rs.getInt(1));
                list.add("" + rs.getString(2));
                list.add("" + rs.getInt(3));
                list.add("" + rs.getString(4));
                list.add("" + rs.getInt(5));
                lis.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lis.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
    }

    public String[][] getAllPurchase() {
        ArrayList<ArrayList<String>> lis = new ArrayList<>();
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            // query to get all purchases
            ResultSet rs = st.executeQuery("SELECT p.prid, u.name, s.name, p.date, p.quantity, p.total FROM Purchase p, User u, Product s WHERE p.sid = s.pid and u.UID = p.uid;");
            while (rs.next()) {
                ArrayList<String> list = new ArrayList<>();
                list.add("" + rs.getInt(1));
                list.add("" + rs.getString(2));
                list.add("" + rs.getString(3));
                list.add("" + rs.getDate(4));
                list.add("" + rs.getInt(5));
                list.add("" + rs.getInt(6));
                lis.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lis.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
    }

    public String[][] getUserPurchase() {
        ArrayList<ArrayList<String>> lis = new ArrayList<>();
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            // query to get all purchases of logged-in user
            ResultSet rs = st.executeQuery("SELECT p.prid, s.name, p.date, p.quantity, p.total FROM Purchase p, User u, Product s WHERE p.sid = s.pid and p.uid = %d;".formatted(selectedUser.getId()));
            while (rs.next()) {
                ArrayList<String> list = new ArrayList<>();
                list.add("" + rs.getInt(1));
                list.add("" + rs.getString(2));
                list.add("" + rs.getDate(3));
                list.add("" + rs.getInt(4));
                list.add("" + rs.getInt(5));
                lis.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lis.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
    }

    public String[][] getRent() {
        ArrayList<ArrayList<String>> lis = new ArrayList<>();
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            // query to get all rent
            ResultSet rs = st.executeQuery("SELECT r.rid, u.name, p.name, r.rentDate, r.returnDate, r.quantity, r.price FROM Rent r, User u, Product p WHERE r.sid = p.pid and r.uid = u.UID;");
            while (rs.next()) {
                ArrayList<String> list = new ArrayList<>();
                list.add("" + rs.getInt(1));
                list.add("" + rs.getString(2));
                list.add("" + rs.getString(3));
                list.add("" + rs.getDate(4));
                list.add("" + rs.getDate(5));
                list.add("" + rs.getInt(6));
                list.add("" + rs.getInt(7));
                lis.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lis.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
    }

    public String[][] getUserRent() {
        ArrayList<ArrayList<String>> lis = new ArrayList<>();
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            // query to get all rent of logged-in user
            ResultSet rs = st.executeQuery("SELECT r.rid, u.name, p.name, r.rentDate, r.returnDate, r.quantity, r.price FROM Rent r, User u, Product p WHERE r.sid = p.pid and r.uid = %d;".formatted(selectedUser.getId()));
            while (rs.next()) {
                ArrayList<String> list = new ArrayList<>();
                list.add("" + rs.getInt(1));
                list.add("" + rs.getString(3));
                list.add("" + rs.getDate(4));
                list.add("" + rs.getDate(5));
                list.add("" + rs.getInt(6));
                list.add("" + rs.getInt(7));
                lis.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lis.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
    }

    public String[][] getOverDue() {
        ArrayList<ArrayList<String>> lis = new ArrayList<>();
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            // query to get all rent of overdue user
            ResultSet rs = st.executeQuery("SELECT r.rid, u.name, p.name, r.rentDate, r.returnDate, r.quantity, r.price FROM Rent r, User u, Product p WHERE r.sid = p.pid and r.uid = u.UID and r.returnDate < CURDATE();");
            while (rs.next()) {
                ArrayList<String> list = new ArrayList<>();
                list.add("" + rs.getInt(1));
                list.add("" + rs.getString(2));
                list.add("" + rs.getString(3));
                list.add("" + rs.getDate(4));
                list.add("" + rs.getDate(5));
                list.add("" + rs.getInt(6));
                list.add("" + rs.getInt(7));
                lis.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lis.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
    }

    public void addUser(String name, String email, String pass){
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            // query to get add user
            st.execute("INSERT INTO User(Name, email, password, balance) VALUES (\"%s\", \"%s\", \"%s\", %d)".formatted(name, email, pass, 5000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProduct(String name, int price, String description, int quantity){
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            // query to get add producr
            st.execute("INSERT INTO Product(name, price, description, quantity) VALUES (\"%s\", %d, \"%s\", %d)".formatted(name, price, description, quantity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPurchase(int sid, int quantity, int total, int quantityLeft, int balanceLeft) {
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            Date dt = new Date();
            // query to get add purchase
            st.execute("INSERT INTO Purchase(sid, uid, quantity, date, total) VALUES (%d, %d, %d,\"%s\", %d)".formatted(sid, selectedUser.getId(), quantity, DateManagement.getDatabaseDate(dt.getYear(), dt.getMonth(), dt.getDate()), total));
            // query to update product quantity
            st.execute("UPDATE Product SET quantity=%d WHERE pid=%d".formatted(quantityLeft, sid));
            // query to update product balance
            st.execute("UPDATE User SET balance=%d WHERE UID=%d".formatted(balanceLeft, selectedUser.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRent(int sid, String rud, int quantity, int total,  int quantityLeft, int balanceLeft) {
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            Date dt = new Date();
            String rd = DateManagement.getDatabaseDate(dt.getYear(), dt.getMonth(), dt.getDate());
            // query to add rent
            st.execute("INSERT INTO Rent(sid, uid, rentDate, returnDate, quantity, price) VALUES (%d, %d, \"%s\",\"%s\", %d, %d)".formatted(sid, selectedUser.getId(), rd, rud, quantity, total));
            // query to update quantity
            st.execute("UPDATE Product SET quantity=%d WHERE pid=%d".formatted(quantityLeft, sid));
            // query to update balance
            st.execute("UPDATE User SET balance=%d WHERE UID=%d".formatted(balanceLeft, selectedUser.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(int sid, int quantity) {
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            // query to get quantity of product
            ResultSet rs = st.executeQuery("SELECT quantity FROM Product where pid=%d".formatted(sid));
            if(rs.next()){
                quantity += rs.getInt(1);
                // query to update quantity of product
                st.execute("UPDATE Product SET quantity=%d WHERE pid=%d".formatted(quantity, sid));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteRent(int rid) {
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            // query to delete rent
            st.execute("DELETE FROM Rent WHERE rid=%d".formatted(rid));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int uid){
        try{
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            // query to delete user and its records
            st.execute("DELETE FROM Rent WHERE uid=%d".formatted(uid));
            st.execute("DELETE FROM Purchase WHERE uid=%d".formatted(uid));
            st.execute("DELETE FROM User WHERE UID=%d".formatted(uid));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String[][] getAllUser() {
        ArrayList<ArrayList<String>> lis = new ArrayList<>();
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM User u;");
            while (rs.next()) {
                ArrayList<String> list = new ArrayList<>();
                list.add("" + rs.getInt(1));
                list.add("" + rs.getString(2));
                list.add("" + rs.getString(3));
                list.add("" + rs.getString(4));
                list.add("" + rs.getInt(5));
                lis.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lis.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
    }

    public boolean userExist(int id) {
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM User u WHERE UID=%d;".formatted(id));
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void updateUser(int balance) {
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT balance FROM User u WHERE UID=%d;".formatted(selectedUser.getId()));
            if(rs.next()){
                int bal = rs.getInt(1);
                balance += bal;
                st.execute("UPDATE User SET balance=%d WHERE UID=%d".formatted(balance, selectedUser.getId()));
                selectedUser.setBalance(balance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] checkProduct(int pid) {
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT quantity, price FROM Product WHERE pid=%d;".formatted(pid));
            if(rs.next()){
                int arr[] = {rs.getInt(1), rs.getInt(2)};
                return arr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void returnRent(int rid){
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT sid, quantity FROM Rent where rid=%d".formatted(rid));
            if(rs.next()) {
                int sid = rs.getInt(1);
                int quantity = rs.getInt(2);
                updateProduct(sid, quantity);
                deleteRent(rid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean rentExist(int id) {
        try {
            Connection con = Database.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Rent where rid=%d".formatted(id));
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
