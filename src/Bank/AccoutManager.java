package Bank;

import java.sql.*;
import  java.util.*;

public class AccoutManager {

    private Connection cn;
    private  Scanner sc;

    public  AccoutManager(Connection cn,Scanner sc)
    {
        this.cn=cn;
        this.sc=sc;
    }


    public  void  credit_money(long a_number)
    {
        sc.nextLine();
        System.out.println("Enter the  Amount:");
        double amount=sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter the password");
        String pass=sc.nextLine();
        sc.nextLine();

        try {

            cn.setAutoCommit(false);
            if (a_number!=0)
            {
                PreparedStatement pr=cn.prepareStatement("select * from account where pin=?");
                pr.setString(1,pass);
                ResultSet rs=pr.executeQuery();

                if (rs.next())
                {
                    String q="update account set balance=balance +? where a_number=?";
                    PreparedStatement pr1=cn.prepareStatement(q);
                    pr1.setDouble(1,amount);
                    pr1.setLong(2,a_number);
                    int res=pr1.executeUpdate();

                    if (res > 0)
                    {
                        System.out.println("sucessful to credit amount");
                        cn.commit();
                        cn.setAutoCommit(true);
                        return;
                    }
                    else
                    {
                        System.out.println("failed");
                        cn.rollback();
                        cn.setAutoCommit(true);
                    }

                }

            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public  void  debit_money(long a_number) throws SQLException {
        sc.nextLine();
        System.out.println("Enter the  Amount:");
        double amount=sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter the password");
        String pass=sc.nextLine();
        sc.nextLine();


        try {

            cn.setAutoCommit(false);
            if (a_number!=0)
            {
                PreparedStatement pr=cn.prepareStatement("select * from account where pin=? and a_number=?");
                pr.setString(1,pass);
                pr.setLong(2,a_number);
                ResultSet rs=pr.executeQuery();

                if (rs.next())
                {
                    double current_blance=rs.getDouble("balance");
                    if(amount<=current_blance ) {

                        String q = "update account set balance=balance - ? where a_number=?";
                        PreparedStatement pr1 = cn.prepareStatement(q);
                        pr1.setDouble(1, amount);
                        pr1.setLong(2, a_number);
                        int res = pr1.executeUpdate();

                        if (res > 0) {
                            System.out.println("sucessful to debited amount");
                            cn.commit();
                            cn.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("failed");
                            cn.rollback();
                            cn.setAutoCommit(true);
                        }
                    }
                    else
                    {
                        System.out.println("less then the avalabe balance");
                    }

                }

            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        cn.setAutoCommit(true);


    }





    public  void getbalance(long a_number)
    {

        sc.nextLine();
        System.out.println( "Enter the pass");
        String number=sc.nextLine();
        sc.nextLine();

        try {
            String q="select * from account where a_number = ? and pin = ?";
            PreparedStatement pr1=cn.prepareStatement(q);
            pr1.setDouble(1,a_number);
            pr1.setString(2,number);
            ResultSet res=pr1.executeQuery();

            if (res.next())
            {
                double balance =res.getDouble("balance");
                System.out.println("your current balance is  "+balance);
            }
            else
            {
                System.out.println("Invalid pin");
            }

        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }


    }


    public  void transfermoney(long a_number) throws SQLException {
        sc.nextLine();
        System.out.println( "Enter the Reciver Account Number");
        long rs_number=sc.nextLong();
        System.out.println("Enter the  Amount:");
        double amount=sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter the password");
        String pass=sc.nextLine();
        sc.nextLine();


        try {

            cn.setAutoCommit(false);
            if (a_number!=0 && rs_number !=0)
            {
                PreparedStatement pr=cn.prepareStatement("select * from account where a_number=? and pin=?");
                pr.setLong(1,a_number);
                pr.setString(2,pass);

                ResultSet rs=pr.executeQuery();

                if (rs.next())
                {
                    double current_blance=rs.getDouble("balance");
                    if(amount<=current_blance )
                    {

                        String q="update account set balance= balance - ? where a_number= ?";
                        String q2="update account set balance= balance + ? where a_number= ?";

                        PreparedStatement pr1=cn.prepareStatement(q);
                        PreparedStatement pr2=cn.prepareStatement(q2);
                        pr1.setDouble(1,amount);
                        pr1.setLong(2, a_number);
                        int res=pr1.executeUpdate();


                        pr2.setDouble(1,amount);
                        pr2.setLong(2, rs_number);
                        int res1=pr2.executeUpdate();

                        if (res > 0 && res1 > 0)
                        {
                            System.out.println("transaction successfully");
                            System.out.println("Rs."+amount+ " Transfer");
                            cn.commit();
                            cn.setAutoCommit(true);
                            return;
                        }
                        else
                        {
                            System.out.println("Transaction failed");
                            cn.rollback();
                            cn.setAutoCommit(true);
                        }

                    }
                    else
                    {
                        System.out.println("Insufficient balance");
                    }

                }
                else
                {
                    System.out.println("Invalid pin");
                }


            }
            else
            {
                System.out.println("Invalid Account number");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        cn.setAutoCommit(true);

    }

}
