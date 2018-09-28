//STEP 1. Import required packages
import java.sql.*;

public class FirstProg {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/bd";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "Raketa4";


    private Connection connect() {
        Connection conn = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void doSelect(Connection conn){
        Statement stmt = null;
        try {
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT city.idCity, CityTitle, NamePeople,SurnamePeople FROM bd.city JOIN bd.people WHERE city.idCity=people.idCity";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("idCity");
                String title = rs.getString("CityTitle");
                String namePeople=rs.getString("NamePeople");
                String surnamePeople=rs.getString("SurnamePeople");
               // String namePeople = rs.getString("NamePeople");
                //String surnamePeople = rs.getString("SurnamePeople");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Title of City: " + title);
                System.out.print(", Name: " + namePeople);
                System.out.println(", Surname: " + surnamePeople);
               // System.out.print(", Name: " + namePeople);
               // System.out.println(", Surname: " + surnamePeople);
            }
            rs.close();
            stmt.close();

        } catch(SQLException se2){
        // nothing we can do
            se2.printStackTrace();
            close(conn);
        }

    }

    private void doInsert(Connection conn) {
        Statement stmt = null;
        try {
            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();

            String sql = "INSERT INTO bd.people (NamePeople, SurnamePeople, idCity) VALUES ('Alina', 'Ivanova', '8')";

            stmt.executeUpdate(sql);
            System.out.println("Inserted records into the table...");
             stmt.close();
    }catch(SQLException se){
            se.printStackTrace();
        close(conn);
    }

    }

    private void doUpdate (Connection conn) {
        Statement stmt = null;
        try {
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "UPDATE people " +
                    "SET SurnamePeople = 'Kupina' WHERE idPeople =6";
            stmt.executeUpdate(sql);

            // Now you can extract all the records
            // to see the updated records
            sql = "SELECT idPeople, NamePeople, SurnamePeople FROM bd.people ";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("idPeople");
                String Name = rs.getString("NamePeople");
                String Surname = rs.getString("SurnamePeople");


                //Display values
                System.out.print("idPeople: " + id);
                System.out.print(", Name People: " + Name);
                System.out.println(", Surname People: " + Surname);
            }
            rs.close();
        }catch(SQLException se2){
            // nothing we can do
            se2.printStackTrace();
            close(conn);
        }
    }

    private void doDelete (Connection conn) {
        Statement stmt = null;
        try {
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "DELETE FROM people WHERE NamePeople='Nata'";
            stmt.executeUpdate(sql);

            // Now you can extract all the records
            // to see the remaining records
            sql = "SELECT idPeople, NamePeople, SurnamePeople FROM bd.people";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("idPeople");
                String namePeople = rs.getString("NamePeople");
                String surnamePeople = rs.getString("SurnamePeople");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Name: " + namePeople);
                System.out.println(", Surname: " + surnamePeople);
            }
            rs.close();
        } catch (SQLException se3) {
            se3.printStackTrace();
            close(conn);
        }
    }

    private void doCreate(Connection conn) {
        Statement stmt = null;
        try {
            System.out.println("Creating table in given database...");
            stmt = conn.createStatement();

            String sql = "CREATE TABLE STUDENT (" +
                    "id INTEGER not NULL," +
                    " NameStudent VARCHAR(255)," +
                    " SurnameStudent VARCHAR(255), " +
                    " idCity INTEGER);";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");
        } catch(SQLException ex){
            ex.printStackTrace();
            close(conn);
        }
    }

    private void doAlterTable(Connection conn){
        Statement stmt = null;
        try {
            System.out.println("Alter table in given database...");
            stmt = conn.createStatement();

            String sql = "ALTER TABLE student ADD  FOREIGN KEY (idCity) REFERENCES city (idCity)";

            stmt.executeUpdate(sql);
            System.out.println("Alter table in given database...");
        } catch(SQLException ex2){
            ex2.printStackTrace();
            close(conn);
        }
    }

    private void close(Connection conn){
        try {
            //STEP 6: Clean-up environment
            if(conn!= null) {
                conn.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FirstProg fp=new FirstProg();
        Connection c=fp.connect();
        //fp.doInsert(c);
       // fp.doSelect(c);
       // fp.doUpdate(c);
       // fp.doDelete(c);
       // fp.doCreate(c);
        fp.doAlterTable(c);
        fp.close(c);
        System.out.println("Goodbye!");
    }//end main



}//end FirstExample