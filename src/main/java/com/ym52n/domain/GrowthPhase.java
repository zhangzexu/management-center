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
 * @ClassName GrowthPhase
 * @Desription 农作物生长阶段 生成阶段:{播种;施肥;开花;结果;收获  to sow; fertilize; blossom; result; harvest.}
 * @Author yangxiaoxiao
 * @Date 2018/12/1 16:53
 * @Version V1.0
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class GrowthPhase extends Entitys implements Serializable {

    private static  final long serialVersionUID=1L;
    @Id
    @GeneratedValue(generator = "uid")
    @GenericGenerator(name = "uid", strategy = "com.ym52n.repository.impl.MakeUid")
    private String uid;
    @Column(nullable = false,columnDefinition ="varchar(100) COMMENT '播种时间'")
    private String sow;
    @Column(nullable = false,columnDefinition ="varchar(100) COMMENT '施肥阶段和频次'")
    private String fertilize;
    @Column(nullable = false,columnDefinition ="varchar(100) COMMENT '开花时间'")
    private String blossom;
    @Column(nullable = false,columnDefinition ="varchar(100) COMMENT '结果时间'")
    private String outcome;
    @Column(nullable = false,columnDefinition ="varchar(50) COMMENT '收获时间，每年7-9月份'")
    private String harvest;

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





}
