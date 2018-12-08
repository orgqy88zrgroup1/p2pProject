package com.aaa.sb.entity;

import java.util.Date;

/**
 * className:Emp
 * discription:
 * author:wzb
 * createTime:2018-12-08 22:07
 */
public class Emp {

    private Integer id;
    private String userName;
    private String passWord;
    private Integer roleid;
    private String empname;
    private Integer deptno;
    private String sex;
    private Date hiredate;
    private double sal;
    private double comm;
    private String idcard;
    private String userRole;

    public Emp() {
    }

    public Emp(Integer id, String userName, String passWord, Integer roleid, String empname, Integer deptno, String sex, Date hiredate, double sal, double comm, String idcard, String userRole) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.roleid = roleid;
        this.empname = empname;
        this.deptno = deptno;
        this.sex = sex;
        this.hiredate = hiredate;
        this.sal = sal;
        this.comm = comm;
        this.idcard = idcard;
        this.userRole = userRole;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    public double getComm() {
        return comm;
    }

    public void setComm(double comm) {
        this.comm = comm;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
