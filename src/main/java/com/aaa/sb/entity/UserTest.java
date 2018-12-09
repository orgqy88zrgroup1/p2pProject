package com.aaa.sb.entity;

/**
 * className:UserTest
 * discription:
 * author:wzb
 * createTime:2018-11-30 20:03
 */

public class UserTest {

    private Integer userId;
    private String userName;
    private String passWord;

    private String userRole;

    public UserTest() {
    }

    public UserTest(Integer userId, String userName, String passWord, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.userRole = userRole;
    }

    public UserTest(Integer userId, String userName, String passWord) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
    }

    public UserTest(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
