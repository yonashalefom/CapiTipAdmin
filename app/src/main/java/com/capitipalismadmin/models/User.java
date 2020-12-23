package com.capitipalismadmin.models;

public class User {
    private String facebook, image, instagram, status, twitter, userFirstName, userID, userLastName, userSponsorID, userURL;
    private long date;

    public User() {

    }

    public User(String facebook, String image, String instagram, String status, String twitter, String userFirstName, String userID, String userLastName, String userSponsorID, String userURL, long date) {
        this.facebook = facebook;
        this.image = image;
        this.instagram = instagram;
        this.status = status;
        this.twitter = twitter;
        this.userFirstName = userFirstName;
        this.userID = userID;
        this.userLastName = userLastName;
        this.userSponsorID = userSponsorID;
        this.userURL = userURL;
        this.date = date;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserSponsorID() {
        return userSponsorID;
    }

    public void setUserSponsorID(String userSponsorID) {
        this.userSponsorID = userSponsorID;
    }

    public String getUserURL() {
        return userURL;
    }

    public void setUserURL(String userURL) {
        this.userURL = userURL;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
