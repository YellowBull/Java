package com.jmev.cn.dto.basic;

import lombok.Getter;
import lombok.Setter;

// ev_tbox 查询 dto
@Getter
@Setter
public class QueryEvTboxDto
{
    private String isUse;// 是否使用 1 = 是, 0 = 否 默认是 -1
    private Integer tboxFactory;// 终端品牌  1-北京飞驰;2-杭州云动;3-其它 默认是 -1
    private String tboxNum;// T-BOX号
    private String iccidNum;// ICCID
    private String tboxModel;// 终端型号
    private String supplier;// 0 = 移动, 1 = 联通 默认是 -1
    private Integer flowState;// 流量卡状态 //1-可测试;2-库存;3-已激活;4-已停用;5-其它 默认是 -1
    
    /** 每页记录数 */
    private int pageSize;

    /** 当前页索引 */
    private int pageIndex;
}
