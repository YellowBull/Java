package com.jmev.cn.dto.basic;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EvTboxImport
{

    private Integer tboxFactory; //终端厂家

    private String tboxNum; //终端编号

    private String iccidNum; //ICCID号

    private String bluetoothMac; //蓝牙MAC地址

    private String simNum; //SIM卡号

    private String tboxModel; //终端型号

    private Date supplyDate; //发货时间

    private Date chargeDate; //续费时间
}
