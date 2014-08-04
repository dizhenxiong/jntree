package com.lenovo.vctl.apps.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: kangyang1
 * Date: 14-5-15
 * Time: 下午3:05
 * To change this template use File | Settings | File Templates.
 */
  //create table material (id bigint primary key auto_increment,title varchar(50) default "",sname varchar(50) default "",semail varchar(50) default "",semailcc varchar(50) default "",scompany varchar(50) default "",length varchar(50) default "",firsttime varchar(50) default "",deadline varchar(50) default "",type varchar(50) default "",ccommnet varchar(50) default "",area varchar(50) default "",status int default 1,cont int default 1) engine=innodb;
@Entity
@Table(name = "material")
public class Material implements Serializable {

    private static final long serialVersionUID = -7203343128718524236L;

    private Long id;

    private String title; //标题
    private String sName; //提交者的姓名
    private String sEmail; //提交者的邮箱
    private String sEmailcc; //抄送的邮箱
    private String sCompany; //提交者的公司
    private String length;  //持续时长
    private String firstTime; //开始时间
    private String deadline;  //截止时间
    private String type;
    private String cCommnet;  //审核者的意见
    private String area;  //shell的大区
    private int status; //当前的审核状态  1.审核中  2.审核通过  3.审核不通过
    private int cont;//常量
    private String url;


    public Material() {
    }

    public Material(Long id, String title, String sName, String sEmail, String sEmailcc, String sCompany, String length, String firstTime, String deadline, String type, String cCommnet, String area, int status, int cont) {
        this.id = id;
        this.title = title;
        this.sName = sName;
        this.sEmail = sEmail;
        this.sEmailcc = sEmailcc;
        this.sCompany = sCompany;
        this.length = length;
        this.firstTime = firstTime;
        this.deadline = deadline;
        this.type = type;
        this.cCommnet = cCommnet;
        this.area = area;
        this.status = status;
        this.cont = cont;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;

    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "sname")
    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    @Column(name = "semail")
    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    @Column(name = "semailcc")
    public String getsEmailcc() {
        return sEmailcc;
    }

    public void setsEmailcc(String sEmailcc) {
        this.sEmailcc = sEmailcc;
    }

    @Column(name = "scompany")
    public String getsCompany() {
        return sCompany;
    }

    public void setsCompany(String sCompany) {
        this.sCompany = sCompany;
    }

    @Column(name = "length")
    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    @Column(name = "firsttime")
    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    @Column(name = "deadline")
    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "ccommnet")
    public String getcCommnet() {
        return cCommnet;
    }

    public void setcCommnet(String cCommnet) {
        this.cCommnet = cCommnet;
    }

    @Column(name = "area")
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    @Column(name = "cont")
    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", sName='" + sName + '\'' +
                ", sEmail='" + sEmail + '\'' +
                ", sEmailcc='" + sEmailcc + '\'' +
                ", sCompany='" + sCompany + '\'' +
                ", length='" + length + '\'' +
                ", firstTime='" + firstTime + '\'' +
                ", deadline='" + deadline + '\'' +
                ", type='" + type + '\'' +
                ", cCommnet='" + cCommnet + '\'' +
                ", area='" + area + '\'' +
                ", status=" + status +
                ", cont=" + cont +
                '}';
    }

}
