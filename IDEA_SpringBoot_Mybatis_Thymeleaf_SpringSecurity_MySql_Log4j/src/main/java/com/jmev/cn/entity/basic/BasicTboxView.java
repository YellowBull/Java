package com.jmev.cn.entity.basic;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//@Entity
//@Table(name = "basic_tbox_view")
@Setter
@Getter
public class BasicTboxView {
    private String vinNum;

    private Long id;

    private Byte tboxFactory;

    private String tboxNum;

    private String iccidNum;

    private String bluetoothMac;

    private String tboxModel;

    private Date supplyDate;

    private Byte flowState;

    private Date activatedDate;

    private Date chargeDate;

    private String simNum;

    private Date createdTime;

    private Integer version;

    private Date lastAccess;
}