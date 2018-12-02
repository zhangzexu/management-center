package com.ym52n.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
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
 *    生成阶段:{播种;施肥;开花;结果;收获}
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

    private String nameCn;
    private String nameEn;
    private String cropsIcon;
    private String cropssPrice;
    private Integer isOrganic;
    private Integer harvestTimes;
    private String harvestFrequency;//收获频率
//    private String

}
