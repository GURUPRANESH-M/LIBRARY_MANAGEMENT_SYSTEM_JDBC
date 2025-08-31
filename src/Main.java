import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try(Connection con  = db.getConnection()){
            Scanner sc = new Scanner(System.in);
            mainOperation m = new mainOperation(con);
            while (true) {
                System.out.println("=========== LIBRARY MANAGEMENT SYSTEM ===========");
                System.out.println("1. ADD BOOK");
                System.out.println("2. ADD USER");
                System.out.println("3. VIEW BOOKS");
                System.out.println("4. VIEW USERS");
                System.out.println("5. BORROW BOOK");
                System.out.println("6. RETURN BOOK");
                System.out.println("7. VIEW BORROWED BOOKS");
                System.out.println("8. EXIT");

                System.out.println("ENTER YOUR CHOICE:");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        m.addBook(sc);
                        break;
                    case 2:
                        m.addUser(sc);
                        break;
                    case 3:
                        m.viewBooks();
                        break;
                    case 4:
                        m.viewUsers();
                        break;
                    case 5:
                        m.borrowBook(sc);
                        break;
                    case 6:
                        m.returnBook(sc);
                        break;
                    case 7:
                        m.viewBorrowedBooks(sc);
                        break;
                    case 8:
                        System.out.println("EXIT SUCCESSFUL.");
                        return;
                    default:
                        System.out.println("INVALID CHOICE. TRY AGAIN.");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}