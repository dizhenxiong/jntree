package com.jnt.post.core;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by arthur on 14-8-1.
 */
@Entity
@Table(name = "post")
public class JntPost implements Serializable {

    private static final long serialVersionUID = -7203343128718525236L;

    private Long id; //主键

    private String name; //岗位名称

    private String desc; //岗位描述


}
