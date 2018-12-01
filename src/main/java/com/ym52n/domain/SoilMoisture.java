package com.ym52n.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class SoilMoisture extends Entitys implements Serializable {
        private static  final long serialVersionUID=1L;
        @Id
        @GeneratedValue(generator = "uid")
        @GenericGenerator(name = "uid", strategy = "com.ym52n.repository.impl.MakeUid")
        private String uid;
        @Column(nullable = false,unique =true)
        private String date;
        private String soilSaturatedWaterContent;
        private String waterHoldingField;
        private String coefficientImpotence;
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

    public String getSoilSaturatedWaterContent() {
        return soilSaturatedWaterContent;
    }

    public void setSoilSaturatedWaterContent(String soilSaturatedWaterContent) {
        this.soilSaturatedWaterContent = soilSaturatedWaterContent;
    }

    public String getWaterHoldingField() {
        return waterHoldingField;
    }

    public void setWaterHoldingField(String waterHoldingField) {
        this.waterHoldingField = waterHoldingField;
    }

    public String getCoefficientImpotence() {
        return coefficientImpotence;
    }

    public void setCoefficientImpotence(String coefficientImpotence) {
        this.coefficientImpotence = coefficientImpotence;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}



