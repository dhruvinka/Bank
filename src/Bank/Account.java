//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Account {
    private Connection cn;
    private Scanner sc;

    public Account(Connection cn, Scanner sc) {
        this.cn = cn;
        this.sc = sc;
    }

    public long open_account(String email) {
        if (!this.accountExist(email)) {
            this.sc.nextLine();
            System.out.println("Enter the  full name:");
            String name = this.sc.nextLine();
            this.sc.nextLine();
            System.out.println("Enter the Initial Amount:");
            double amount = this.sc.nextDouble();
            this.sc.nextLine();
            System.out.println("Enter the password");
            String pass = this.sc.nextLine();
            this.sc.nextLine();
            String q = "insert into account(a_number,name,email,balance,pin) values(? ,?, ?, ?, ?)";

            try {
                long a_number = this.genrateAccountNumebr();
                PreparedStatement pr = this.cn.prepareStatement(q);
                pr.setLong(1, a_number);
                pr.setString(2, name);
                pr.setString(3, email);
                pr.setDouble(4, amount);
                pr.setString(5, pass);
                int res = pr.executeUpdate();
                if (res > 0) {
                    System.out.println("record insert success");
                } else {
                    System.out.println("record is not insert");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        throw new RuntimeException("Account already Exist");
    }

    public boolean accountExist(String email) {
        String q = "select * from account where email=?";

        try {
            PreparedStatement pr = cn.prepareStatement(q);
            pr.setString(1, email);
            ResultSet rs = pr.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public long getAccountNumber(String email) {
        String q = "select a_number from account where email= ?";

        try {
            PreparedStatement pr = cn.prepareStatement(q);
            pr.setString(1, email);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return rs.getLong("a_number");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return 0L;
    }

    public long genrateAccountNumebr() {
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("select a_number from account ORDER BY a_number DESC LIMIT 1");
            if (rs.next()) {
                long last_account = rs.getLong("a_number");
                return last_account + 1;
            } else {
                return 10000100;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 10000100;
        }
    }
}
