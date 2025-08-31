import java.sql.*;
import java.sql.Date;
import java.time.*;
import java.util.*;

public class mainOperation {
    private Connection con;

    public mainOperation(Connection con) {
        this.con = con;
    }

    public void addBook(Scanner sc) {
        System.out.println("ENTER BOOK TITLE:");
        String title = sc.nextLine();
        System.out.println("ENTER BOOK AUTHOR:");
        String author = sc.nextLine();
        System.out.println("ENTER AVAILABILITY (YES/NO):");
        String available = sc.nextLine();

        String query = "INSERT INTO BOOKS (TITLE,AUTHOR,AVAILABLE) VALUES (? , ? , ?);";
        try(PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setString(1,title);
            stmt.setString(2,author);
            stmt.setString(3,available);

            stmt.executeUpdate();
            System.out.println("BOOK ADDED SUCCESSFULLY.");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void addUser(Scanner sc) {
        System.out.println("ENTER USER NAME:");
        String name = sc.nextLine();

        String query = "INSERT INTO USERS (name) VALUES (?);";
        try(PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setString(1,name);
            stmt.executeUpdate();
            System.out.println("USER ADDED SUCCESSFULLY.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void viewBooks() {
        String query = "SELECT * FROM BOOKS;";
        try(PreparedStatement stmt = con.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                System.out.println(rs.getInt("id") +" | "+rs.getString("title")+
                        " | "+rs.getString("author")+" | "+rs.getString("available"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void viewUsers() {
        String query = "SELECT * FROM USERS;";
        try(PreparedStatement stmt = con.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                System.out.println(rs.getInt("id")+" | "+rs.getString("name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void borrowBook(Scanner sc) {
        System.out.println("ENTER USER ID:");
        int uid = sc.nextInt();
        sc.nextLine();
        System.out.println("ENTER BOOK ID:");
        int bid = sc.nextInt();
        sc.nextLine();

        if(checkAvailable(bid)){
            String query1 = "UPDATE BOOKS SET AVAILABLE = 'NO' WHERE ID = ?";
            try(PreparedStatement stmt = con.prepareStatement(query1)){
                stmt.setInt(1,bid);
                stmt.executeUpdate();
                System.out.println("BOOK BORROWED SUCCESSFULLY.");
            }catch (Exception e){
                e.printStackTrace();
            }

            String query2 = "INSERT INTO BORROW (USER_ID,BOOK_ID) VALUES (? , ?);";
            try(PreparedStatement stmt = con.prepareStatement(query2)){
                stmt.setInt(1,uid);
                stmt.setInt(2,bid);
                stmt.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }


        }else{
            System.out.println("BOOK NOT AVAILABLE.");
        }


    }

    public void returnBook(Scanner sc) {
        System.out.println("ENTER USER ID:");
        int uid = sc.nextInt();
        sc.nextLine();
        System.out.println("ENTER BOOK ID:");
        int bid = sc.nextInt();
        sc.nextLine();
            String query1 = "UPDATE BOOKS SET AVAILABLE = 'YES' WHERE ID = ?";
            try(PreparedStatement stmt = con.prepareStatement(query1)){
                stmt.setInt(1,bid);
                stmt.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }

            String query2 = "UPDATE BORROW SET RETURN_DATE = ? WHERE USER_ID = ? AND BOOK_ID = ?;";
            try(PreparedStatement stmt = con.prepareStatement(query2)){
                LocalDateTime ld = LocalDateTime.now();
                stmt.setObject(1, ld);
                stmt.setInt(2,uid);
                stmt.setInt(3,bid);

                stmt.executeUpdate();
                System.out.println("BOOK RETURNED SUCCESSFULLY.");
            }catch (Exception e){
                e.printStackTrace();
            }


    }

    public boolean checkAvailable(int bid){
        String query = "SELECT * FROM BOOKS WHERE ID = ?;";
        try(PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setInt(1,bid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                String s = rs.getString("available");
                if(s.equalsIgnoreCase("yes")) return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void viewBorrowedBooks(Scanner sc) {
        System.out.println("ENTER USER ID:");
        int id = sc.nextInt();
        sc.nextLine();

        String query = "SELECT BK.ID AS BOOK_ID , BK.TITLE AS TITLE , BW.BORROW_DATE , BW.RETURN_DATE FROM BOOKS BK JOIN " +
                "BORROW BW ON BK.ID = BW.BOOK_ID AND BW.USER_ID = ?";
        try(PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                System.out.println(rs.getInt(1)+" | "+rs.getString(2)+" | "+
                        rs.getObject(3)+" | "+rs.getObject(4));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
