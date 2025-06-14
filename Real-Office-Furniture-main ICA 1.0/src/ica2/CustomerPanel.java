/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ica2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author v8255920
 */
public class CustomerPanel extends JFrame implements ActionListener{
    
    JLabel label1, label2,label3,label4,label5, label6, label7, label8;
    JTextField t1, t2, t3, t4, t5, t6, t7, t8;
    JButton submit;
    
    
    CustomerPanel()
    {
        JFrame window = new JFrame("");
        JPanel panel1 = new JPanel();
        window.setTitle("Registration Form");
        window.setSize(700,700);
        window.setLocationRelativeTo(null);
        window.setBackground(Color.RED);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(null);
        
        window.add(panel1);
        //setBounds(int x-coordinate, int y-coordinate, int width, int height)
        //first name
        label1 = new JLabel("First Name");
        label1.setBounds(20,50,100,20);
        c.add(label1);
        
        t1 = new JTextField();
        t1.setBackground(Color.RED);
        t1.setBounds(130,50,100,20);
        c.add(t1);
        
        //surname
        label2 = new JLabel("Surname");
        label2.setBounds(250,50,100,20);
        c.add(label2);
        
        t2= new JTextField();
        t2.setBounds(330,50,100,20);
        c.add(t2);
        
        // house number
        label3 = new JLabel("House Number");
        label3.setBounds(20,100,100,20);
        c.add(label3);
        
        t3 = new JTextField();
        t3.setBounds(130,100,50,20);
        c.add(t3);
        
        //Street Address
        label4 = new JLabel("Street Address");
        label4.setBounds(20,150,100,20);
        c.add(label4);
        
        t4 = new JTextField();
        t4.setBounds(130,150,170,20);
        c.add(t4);
        
        //address line 2
        label5 = new JLabel("Address #2");
        label5.setBounds(20,200,100,20);
        c.add(label5);
        
        t5 = new JTextField();
        t5.setBounds(130,200,170,20);
        c.add(t5);
        
        label6 = new JLabel("Town or City");
        label6.setBounds(20,250,100,20);
        c.add(label6);
        
        t6 = new JTextField();
        t6.setBounds(130,250,170,20);
        c.add(t6);
        
        label7 = new JLabel("County");
        label7.setBounds(20,300,100,20);
        c.add(label7);
        
        t7 = new JTextField();
        t7.setBounds(130,300,170,20);
        c.add(t7);
        
        
        label8 = new JLabel("Postcode");
        label8.setBounds(20,350,100,20);
        c.add(label8);
        
        t8 = new JTextField();
        t8.setBounds(130,350,100,20);
        c.add(t8);
        

        
        submit = new JButton("save");
        submit.setBounds(130,450,70,50);
        c.add(submit);
        submit.addActionListener(new ActionListener(){
        
        
        
        
    
      @Override
      public  void actionPerformed(ActionEvent e){
          
        String firstName = t1.getText();
        String lastName = t2.getText();
        String houseNumber = t3.getText();
        String addy1 = t4.getText();
        String addy2 = t5.getText();
        String townOrCity = t6.getText();
        String county = t7.getText();
        String postcode = t8.getText();
        
        
        try{
            
            Connection conn = DriverManager.getConnection("jdbc:sqlite:db/customers.db");
            
            String query = "INSERT INTO customers('" + firstName + "','" + lastName +"')";
            
            Statement statement = conn.createStatement();
            int x = statement.executeUpdate(query);
            if (x == 0){
                JOptionPane.showMessageDialog(submit, "Account already exists");
            }
            else {
                JOptionPane.showMessageDialog(submit, "Congratulations" 
                        + firstName + "Account created successfully");
                                  
            }
            conn.close();
                
            }
        catch (Exception exception){
            exception.printStackTrace();
        }
        }
      
        });
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

                
    
    
    
}
