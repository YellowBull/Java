package com.jmev.cn.entity.basic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
//@Entity
//@Table(name = "ev_tbox")
public class EvTbox {
    private Long id;// 终端供货id

    private Integer tboxFactory; // 终端品牌

    private String tboxNum;// 终端编号

    private String iccidNum;// ICCID号

    private String bluetoothMac;// 蓝牙MAC地址

    private String tboxModel;// 终端型号

    private Date supplyDate;// 发货日期

    private Integer flowState;// 流量卡状态

    private Date activatedDate;// 流量卡激活日期

    private Date chargeDate;// 流量卡续费时间

    private String simNum;// SIM卡号

    private Date createdTime;// 创建时间

    private Integer version;// 版本号

    private Date lastAccess;// 最后更新时间
}