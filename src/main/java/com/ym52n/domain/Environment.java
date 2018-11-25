package com.ym52n.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.persistence.Id;
import java.io.Serializable;
@Component("env")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Environment extends Entitys implements Serializable {
    private static  final long serialVersionUID=1L;
    @Id
    @GeneratedValue(generator = "uid")
    @GenericGenerator(name = "uid", strategy = "com.ym52n.repository.impl.MakeUid")
    private String uid;
    @Column(nullable = false,unique =true)
    private String date;
    private String temperature;
    private String dampness;
    private String lx;
    private String ppm;
    private String tuTemperature;
    private String water;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDampness() {
        return dampness;
    }

    public void setDampness(String dampness) {
        this.dampness = dampness;
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public String getPpm() {
        return ppm;
    }

    public void setPpm(String ppm) {
        this.ppm = ppm;
    }

    public String getTuTemperature() {
        return tuTemperature;
    }

    public void setTuTemperature(String tuTemperature) {
        this.tuTemperature = tuTemperature;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }
}
