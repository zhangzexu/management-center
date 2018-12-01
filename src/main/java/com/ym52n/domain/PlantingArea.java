package com.ym52n.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Title 种植区域 基本属性名称，
 * 	是否为温棚，
 * 	灌溉方式，
 * 	可种植农作物，
 *  现种植作物
 *  区域介绍
 * @ClassName PlantingArea
 * @Desription
 * @Author yangxiaoxiao
 * @Date 2018/12/1 15:52
 * @Version V1.0
 */
public class PlantingArea extends Entitys implements Serializable {
    private static  final long serialVersionUID=1L;
    @Id
    @GeneratedValue(generator = "uid")
    @GenericGenerator(name = "uid", strategy = "com.ym52n.repository.impl.MakeUid")
    private String uid;
    @Column(nullable = false,unique = true,columnDefinition ="varchar(100) COMMENT '种植区域名称'")
    private String name;//区域名称
    @Column(nullable = false,unique = true,columnDefinition ="varchar(100) COMMENT '种植区域类型:可选值有温棚、空地、其他'")
    private String areaType;
    @Column(nullable = false,unique = true,columnDefinition ="varchar(100) COMMENT '种植区域类型:可选值有喷灌、。。、其他'")
    private String irrigationType;
    private String




}
