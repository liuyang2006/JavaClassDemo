package ch14;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBPoolDemo2 {
    public static void main(String[] args) throws NamingException, SQLException {
        Context ctx;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;

        DataSource dataSource =
                (DataSource) new InitialContext().lookup("jdbc/students1");

        conn = dataSource.getConnection();
        stmt = conn.createStatement();
        rset = stmt.executeQuery("select student.id, student.name,student.tel, address from student");

        if (rset.next()) {
            do {
                String sID = rset.getString("id");
                String name = rset.getString("name");
                String tel = rset.getString("tel");
                String address = rset.getString("address");
                System.out.printf("%s %s %s %s\n", sID, name, tel, address);
            } while (rset.next());
        }


    }
}
