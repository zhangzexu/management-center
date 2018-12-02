package com.ym52n.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Title
 * @ClassName Crops
 * @Desription 中文名称:
 *    英文名称:
 *    种子图片:
 *    种子价格:
 *    果实售价:
 *    是否为有机作物:
 *    收获次数:
 *    成长阶段:{播种;施肥;开花;结果;收获}
 *    作物种类：https://baike.baidu.com/item/%E5%86%9C%E4%BD%9C%E7%89%A9/2868861?fr=aladdin
 * @Author yangxiaoxiao
 * @Date 2018/12/1 16:39
 * @Version V1.0
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Crops extends Entitys implements Serializable {
    private static  final long serialVersionUID=1L;
    @Id
    @GeneratedValue(generator = "uid")
    @GenericGenerator(name = "uid", strategy = "com.ym52n.repository.impl.MakeUid")
    private String uid;
    @Column(nullable = false,unique = true,columnDefinition ="varchar(30) COMMENT '种子的中文名称'")
    private String nameCn;
    @Column(nullable = false,unique = true,columnDefinition ="varchar(30) COMMENT '种子的英文名称'")
    private String nameEn;
    @Column(unique = true,columnDefinition ="varchar(20) COMMENT '种子的展示图片'")
    private String cropsIcon;
    @Column(nullable = false,columnDefinition ="varchar(30) COMMENT '种子价格'")
    private String cropssPrice;
    @Column(nullable = false,columnDefinition ="varchar(30) COMMENT '是否为有机种子默认为0不是有机种子'")
    private Integer isOrganic = 0;
    @Column(nullable = false,columnDefinition ="varchar(30) COMMENT '种子种类'")
    private String cropsType;
    @Column(nullable = false,columnDefinition ="int(10) COMMENT '果实收获次数有：可收获果实三次或者3-10次'")
    private Integer harvestTimes;
    @Column(nullable = false,columnDefinition ="varchar(30) COMMENT '种子的收获频率:有15天一次，一个月一次'")
    private String harvestFrequency;//收获频率
    //成长阶段
    @OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="growthPhase_uid")//注释本表中指向另一个表的外键。
    private GrowthPhase growthPhase;

    @Column(length = 65535,columnDefinition ="Text COMMENT '种子简介'")
    private String describes;

    private Integer available = 0; //0不可用，1是可用  是否可用,如果不可用将不会用于种植
    @Column(length = 65535,columnDefinition="Text")
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

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCropsIcon() {
        return cropsIcon;
    }

    public void setCropsIcon(String cropsIcon) {
        this.cropsIcon = cropsIcon;
    }

    public String getCropssPrice() {
        return cropssPrice;
    }

    public void setCropssPrice(String cropssPrice) {
        this.cropssPrice = cropssPrice;
    }

    public Integer getIsOrganic() {
        return isOrganic;
    }

    public void setIsOrganic(Integer isOrganic) {
        this.isOrganic = isOrganic;
    }

    public String getCropsType() {
        return cropsType;
    }

    public void setCropsType(String cropsType) {
        this.cropsType = cropsType;
    }

    public Integer getHarvestTimes() {
        return harvestTimes;
    }

    public void setHarvestTimes(Integer harvestTimes) {
        this.harvestTimes = harvestTimes;
    }

    public String getHarvestFrequency() {
        return harvestFrequency;
    }

    public void setHarvestFrequency(String harvestFrequency) {
        this.harvestFrequency = harvestFrequency;
    }

    public GrowthPhase getGrowthPhase() {
        return growthPhase;
    }

    public void setGrowthPhase(GrowthPhase growthPhase) {
        this.growthPhase = growthPhase;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
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
}
