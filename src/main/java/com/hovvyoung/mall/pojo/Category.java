package com.hovvyoung.mall.pojo;

import lombok.Data;

import java.util.Date;
/*
* po (persistent object)
* pojo (plain ordinary java object)
* */

// we use Lombok to configure getter and setter etc... automatically;
@Data
public class Category {

    private Integer id;
    private Integer parentId;
    private String name;
    private Integer status;
    private Integer sortOrder;
    private Date createTime;
    private Date updateTime;

}
