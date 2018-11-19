package com.example.loggerdoc;


import java.io.Serializable;



public class User implements Serializable {

    private String userID;
    private String emailAddress;
    private String phoneNumber;
    private String role;
    private Integer elasticID;

    /**
     *
     * @param userID A unique username for the User that is at least 8 characters long
     * @param emailAddress email address of the user
     * @param phoneNumber Users phone number
     * @param role Role that the user will take. Either a Patient or CareGiver
     */
    public User(String userID, String emailAddress, String phoneNumber ,String role) {

        this.userID = userID;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.elasticID = this.hashCode();
    }
    /**
     *
     * @param userID A unique username for the User that is at least 8 characters long
     * @param emailAddress email address of the user
     * @param phoneNumber Users phone number
     * @param role Role that the user will take. Either a Patient or CareGiver
     * @param elasticID Unique identifier for the object.
     */
    public User(String userID, String emailAddress, String phoneNumber ,String role,Integer elasticID) {

        this.userID = userID;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.elasticID = elasticID;
    }

    /**
     *
     * @return Returns the users username
     */
    public String getUserID() {
        return userID;
    }

    /**
     *
     * @param userID New username that will replace the old userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     *
     * @return Returns the users email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     *
     * @param emailAddress new email address to change the old one
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     *
     * @return Returns the users phone number as a String
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber new phone number to change the old one
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    /**
     *
     * @return Returns the tole that the user is. Either Patient or CareGiver
     */
    public String getRole() {
        return role;
    }


    public Integer getElasticID() {
        return elasticID;
    }

    public void setElasticID(Integer elasticID) {
        this.elasticID = elasticID;
    }
}
