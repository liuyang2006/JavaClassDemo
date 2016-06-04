package ch14;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBPoolDemo {
    public static void main(String[] args) throws NamingException, SQLException {
        Context ctx;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;

        DataSource dataSource =
                (DataSource) new InitialContext().lookup("sample");

        conn = dataSource.getConnection();
        stmt = conn.createStatement();
        rset = stmt.executeQuery("select * from customer");

        if (rset.next()) {
            do {
                String custID = rset.getString("customerid");
                String addr = rset.getString("address");
                String phone = rset.getString("phone");
                System.out.printf("%s %s %s\n", custID, addr, phone);
            } while (rset.next());
        }


    }
}
