package com.ym52n.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
@Entity
@EntityListeners(AuditingEntityListener.class)
public class PlantingArea extends Entitys implements Serializable {
    private static  final long serialVersionUID=1L;
    @Id
    @GeneratedValue(generator = "uid")
    @GenericGenerator(name = "uid", strategy = "com.ym52n.repository.impl.MakeUid")
    private String uid;
    @Column(nullable = false,unique = true,columnDefinition ="varchar(100) COMMENT '种植区域名称'")
    private String name;//区域名称
    @Column(nullable = false,columnDefinition ="varchar(30) COMMENT '种植区域类型:可选值有温棚、空地、其他'")
    private String areaType;
    @Column(nullable = false,columnDefinition ="varchar(30) COMMENT '灌溉类型:可选值有喷灌、。。、其他'")
    private String irrigationType;
    @OneToMany(fetch= FetchType.EAGER)
    @JoinColumn(name="plantingArea_uid")
    private List<Crops> optionalCrops;

    @Column(nullable = false,columnDefinition ="varchar(2) COMMENT '现在是否种植作物:可选值有 0 没有种植，1种植'")
    private String isPlant="0";
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="plantingCrops_uid")//注释本表中指向另一个表的外键。
    private Crops plantingCrops;
    @Column(columnDefinition ="varchar(30) COMMENT '种植时间'")
    private String plantDate;
    @Column(columnDefinition ="varchar(100) COMMENT '作物现在达到的阶段'")
    private String plantStage;

    @Column(length = 65535,columnDefinition ="Text COMMENT '区域简介'")
    private String describes;

    private String available = "0"; // 是否可用,如果不可用将不会用于种植
    @Column(length = 65535,columnDefinition="Text COMMENT '备注'")
    private String descInfo;
    @CreatedDate
    @Column(nullable = false)
    private String createDate;
    @CreatedBy
    @Column(nullable = false)
    private String createBy;
    @LastModifiedDate
    @Column(nullable = false)
    private String lastModifiedDate;
    @LastModifiedBy
    @Column(nullable = false)
    private String lastModifiedBy;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getIrrigationType() {
        return irrigationType;
    }

    public void setIrrigationType(String irrigationType) {
        this.irrigationType = irrigationType;
    }

    public String getIsPlant() {
        return isPlant;
    }

    public void setIsPlant(String isPlant) {
        this.isPlant = isPlant;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public List<Crops> getOptionalCrops() {
        return optionalCrops;
    }

    public void setOptionalCrops(List<Crops> optionalCrops) {
        this.optionalCrops = optionalCrops;
    }

    public Crops getPlantingCrops() {
        return plantingCrops;
    }

    public void setPlantingCrops(Crops plantingCrops) {
        this.plantingCrops = plantingCrops;
    }

    public String getPlantDate() {
        return plantDate;
    }

    public void setPlantDate(String plantDate) {
        this.plantDate = plantDate;
    }

    public String getPlantStage() {
        return plantStage;
    }

    public void setPlantStage(String plantStage) {
        this.plantStage = plantStage;
    }

}
