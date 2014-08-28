package com.jnt.post.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by arthur on 14-8-1.
 * <p/>
 * <p/>
 * CREATE TABLE `post` (
 * `id` bigint(20) NOT NULL,
 * `name` varchar(50) DEFAULT '',
 * `descs` varchar(100) DEFAULT '',
 * `abbr` varchar(10) DEFAULT NULL,
 * `level` int(11) NOT NULL DEFAULT '0',
 * `parent_id` bigint(20) DEFAULT NULL,
 * `salary_min` bigint(20) NOT NULL DEFAULT '0',
 * `salary_max` bigint(20) NOT NULL DEFAULT '0',
 * `worklife_str` varchar(20) DEFAULT '',
 * PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT
 */
@Entity
@Table(name = "post")
public class JntPost implements Serializable {

    private static final long serialVersionUID = -7203343128718525236L;

    private Long id; //主键

    private String name; //岗位名称

    private String descs; //岗位描述

    private String abbr; //岗位的缩写

    private Integer level; //表示岗位是第几层的

    private Long parentId;  //职位也是属性结构

    private Long salaryMin;   //年薪单位是万--起薪

    private Long salaryMax;   //年薪单位是万--最高值

    private String workLifeStr;  //工作年限的字符串  如 0 ~ 3 年

    public JntPost() {

    }

    @Id
    @Column(name = "id")

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "descs")
    public String getDesc() {
        return descs;
    }

    public void setDesc(String desc) {
        this.descs = desc;
    }

    @Column(name = "abbr")
    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Column(name = "parent_id")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Column(name = "salary_min")
    public Long getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Long salaryMin) {
        this.salaryMin = salaryMin;
    }

    @Column(name = "salary_max")
    public Long getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Long salaryMax) {
        this.salaryMax = salaryMax;
    }

    @Column(name = "worklife_str")
    public String getWorkLifeStr() {
        return workLifeStr;
    }

    public void setWorkLifeStr(String workLifeStr) {
        this.workLifeStr = workLifeStr;
    }

    @Override
    public String toString() {
        return "JntPost{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + descs + '\'' +
                ", abbr='" + abbr + '\'' +
                ", level=" + level +
                ", parentId=" + parentId +
                ", salaryMin=" + salaryMin +
                ", salaryMax=" + salaryMax +
                ", workLifeStr='" + workLifeStr + '\'' +
                '}';
    }

}
