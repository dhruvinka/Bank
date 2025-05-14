package Bank;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Bankingapp {

    //database name bank;

    private static final String url = "jdbc:mysql://localhost:3306/bank";
    private static final String username = "root";
    private static final String password = "School@123";

    public static void main(String[] args) throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

//            System.out.println("hii");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {

            Connection cn= DriverManager.getConnection(url,username,password);
            Scanner sc=new Scanner(System.in);
            User us=new User(cn,sc);
            Account ac=new Account(cn,sc);
            AccoutManager ac2=new AccoutManager(cn,sc);

            String email;
            long account_number;

            while (true)
            {
                System.out.println("Welcome into bankingsystem");
                System.out.println();;
                System.out.println("1.Register");
                System.out.println("2.Login");
                System.out.println("3.Exist");
                System.out.println("Enter your choice");
                int choice=sc.nextInt();

                switch (choice)
                {
                    case 1:
                        us.register();
                        break;
                    case 2:
                        email=us.login();
                        if(email!=null)
                        {
                            System.out.println();
                            System.out.println("User Login");
                            if (!ac.accountExist(email))
                            {
                                System.out.println();
                                System.out.println("1.open account");
                                System.out.println("2.Exist");
                                if (sc.nextInt()==1)
                                {
                                    account_number=ac.open_account(email);
                                    System.out.println("Account is Created");
                                    System.out.println("Your Account Number is "+ account_number);

                                }
                                else
                                {
                                    break;
                                }

                            }
                            account_number=ac.getAccountNumber(email);
                            int choice2=0;
                            while (choice2!=5)
                            {
                                System.out.println();
                                System.out.println("1.Debit Money");
                                System.out.println("2.Credit Money");
                                System.out.println("3.transfer Money");
                                System.out.println("4.Check balance");
                                System.out.println("logout");
                                System.out.println("Enter your choice");
                                choice2=sc.nextInt();


                                switch (choice2)
                                {
                                    case 1:
                                        ac2.debit_money(account_number);
                                        break;
                                    case 2:
                                        ac2.credit_money(account_number);
                                    case 3:
                                        ac2.transfermoney(account_number);
                                    case 4:
                                        ac2.getbalance(account_number);
                                    case 5:
                                        break;
                                    default:
                                        System.out.println("Enter the Valid Choice");
                                        break;
                                }

                            }
                        }
                        else
                        {
                            System.out.println("Incorrect Email");
                        }
                    case 3:
                        System.out.println( "Thank you ......");
                        return;
                    default:
                        System.out.println("Enter the Valid Choice");
                        break;
                }




            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
