//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class User {
    private Connection cn;
    private Scanner sc;

    public User(Connection cn, Scanner sc) {
        this.cn = cn;
        this.sc = sc;
    }

    public void register() {
        this.sc.nextLine();
        System.out.println("Enter the  full name:");
        String name = this.sc.nextLine();
        System.out.println("Enter the Email:");
        String email = this.sc.nextLine();
        System.out.println("Enter the password");
        String apass = sc.nextLine();
        if (this.user_exist(email)) {
            System.out.println("User is Exist");
        }

        String q = "insert into user(name ,email,pass) values (? ,?, ?) ";

        try {
            PreparedStatement pr = this.cn.prepareStatement(q);
            pr.setString(1, name);
            pr.setString(2, email);
            pr.setString(3,apass);
            int rs = pr.executeUpdate();
            if (rs > 0) {
                System.out.println("successful");
            } else {
                System.out.println("reg failed");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public String login() {
        this.sc.nextLine();
        System.out.println("Enter the Email:");
        String email = this.sc.nextLine();
        this.sc.nextLine();
        System.out.println("Enter the password");
        String pass = this.sc.nextLine();
        this.sc.nextLine();
        String q = "select * from user where email=?";

        try {
            PreparedStatement pr = this.cn.prepareStatement(q);
            pr.setString(1, email);
            ResultSet rs = pr.executeQuery();
            if (rs.next())
            {
                return email;
            }
            else
            {
                return  null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean user_exist(String email) {
        String q = "select * from user where email=?";

        try {
            PreparedStatement pr = this.cn.prepareStatement(q);
            pr.setString(1, email);
            ResultSet rs = pr.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
