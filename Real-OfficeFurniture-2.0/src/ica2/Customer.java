/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ica2;

import java.util.Scanner;

/**
 *
 * @author v8255920
 */
public abstract class Customer {
    
    protected int customerId;
    protected String firstName;
    protected String address;
    protected String addressLine2;
    protected String postCode;
    protected String telNumber;
    protected String emailAddy;
    
    public Customer(String firstName, String address,
            String addressLine2, String postCode, String telNumber,
            String emailAddy){
        
        this.customerId = customerId;
        this.firstName = firstName;
        this.address = address;
        this.addressLine2 = addressLine2;
        this.postCode = postCode;
        this.telNumber = telNumber;
        this.emailAddy = emailAddy;
   
}
    
    public Customer(){
        this.customerId = 999;
        this.firstName = "";
        this.address = "";
        this.addressLine2 = "";
        this.postCode = "";
        this.telNumber = "";
        this.emailAddy = "";
    }
    
    //first name validation
    public static boolean isValidName(String firstName) {
        if (firstName.length()>15 || (firstName.length()==0)) 
            return false;
        else return true;
    }
    //first name entry
    public static String firstName(String firstName) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter customer's first name (eg. John - maximum of 15 characters): ");
        firstName = input.nextLine();
        while (!isValidName(firstName)){
            System.out.print("Invalid name, re-enter customer's first name (eg. John - maximum of 15 characters): ");
            firstName = input.nextLine();
        }
        return firstName;
    }

    public int getCustomerId() {
        return customerId;
    }

    
    
    public String getFirstName() {
        return firstName;
    }

    public String getAddress() {
        return address;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public String getEmailAddy() {
        return emailAddy;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public void setEmailAddy(String emailAddy) {
        this.emailAddy = emailAddy;
    }
    
    
}
