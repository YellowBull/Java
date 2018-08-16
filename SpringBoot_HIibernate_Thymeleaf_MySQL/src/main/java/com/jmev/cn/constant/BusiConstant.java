package com.jmev.cn.constant;

/**
 * 常量类
 */
public interface BusiConstant {
	
	/**
	 * 登录账号解密公钥
	 */
	public static final String PRIVATE_KEY_STR =  "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBALVi84OCbISu0Jkh" + 
			"sS38jxlLqC4YNMx6WkVCe9eI9nOXfE0v0iSPmW5aQoPMmvkVemH7H1yEDEbEvVkm" + 
			"7XMEhCkeWm+ds/5ZDr+sdfC8MHgapDF5xVgY8WnYk8LiPY2R2kZkbnmSHegNkWs6" + 
			"Icpc5mhPYxV+iOnfA3ZcXhfJ8s2zAgMBAAECgYEAghFh03TsAj0lQhnB5tsLdjUl" + 
			"dWBQRCycnLuu+ICzUXJYZTUceLEscdBxj/dhTaoUJzyfYYUjFIAx00Mx1F9xsKGK" + 
			"lbf0P8Bfeneq+N1LtK2MWjtRnOv3a+DUS6wIYFzqIgjI+sVLTJiN46EVTKvpjPHs" + 
			"ED1vOzme6GZCMmHILFECQQDajUu5i1GIg4ozKdZIV2CUWm6a7e1PVzmRRV1dI72L" + 
			"+yI1vdPgWcbyv+uqNOO4W+3yZgY+nQfroIp+1zrJtdbrAkEA1HdoZQiK4okGfUK1" + 
			"icgTC6EOirwR55745MGwE5flSxxWaywb+NfgbyWPtr/TPOzJq0+eil+t+l6Gc04Q" + 
			"uVfCWQJBAKK7+TnEakaLI7mrGlqtbUWp/JeyODUOztfD3Zw/w6Das4NuwLhaQRB6" + 
			"JaGMVk/ta8VFgLrYtJAX72g5HRYbJ1cCQQCGERr0teE9dQ19OPzohjFOY0CT1nzN" + 
			"1QhlALskgfKT5Lu7QMgdy8q5F9CJlp2qkhfnW4RE+H8Fv2PDmnz/FxtxAkEAlllT" + 
			"59nNXC+HuW0bpHCyzdnYULabblYSHpMjxWqb6Qsix2b+Cda+VNgRGfKxOo3Mu4AY" + 
			"1Fif7mobRlTlSOyg0Q==";
	
	public static final int REPORT_EXPORT_COUNT=5000;  //报表导出文件默认导出的最大记录数量
	/**
	 * 成功
	 */
	public static final String STATUS_SUCCESS = "1";
	/**
	 * 失败
	 */
	public static final String STATUS_ERROR = "0";

	/**
	 * 默认初始页
	 */
	public static final Integer PAGE_INDEX = 1;

	/**
	 * 默认页面显示数
	 */
	public static final Integer PAGE_SIZE = 15;
	
	
	/**
	 * 黑名单待审批用户处理状态（0：待处理 1：审批通过 2：删除）
	 */
	public static final class BLACKLISTPENDING_PENDINGSTATUS {
		/**
		 * 待处理
		 */
		public static final String PENDING_TREATMENT = "0";

		/**
		 * 审批通过
		 */
		public static final String APPROVAL = "1";

		/**
		 * 删除
		 */
		public static final String DELETE = "2";
	}

	/**
	 * 黑名单类型
	 */
	public static final class BLACKLIST_TYPE {

		/**
		 * 个人
		 */
		public static final String PERSONAL = "1";

		/**
		 * 国家
		 */
		public static final String COUNTRY = "2";

		/**
		 * 组织
		 */
		public static final String ORGANIZATION = "3";
	}

	/**
	 * 黑名单来源
	 */
	public static final class BLACKLIST_SOURCE {

		/**
		 * 官方
		 */
		public static final String OFFICIAL = "1";

		/**
		 * 公司
		 */
		public static final String COMPANY = "2";

	}

	/**
	 * 黑名单是否回溯
	 */
	public static final class BLACKLIST_IS_RECALL {

		/**
		 * 未回溯
		 */
		public static final String NO = "0";

		/**
		 * 已回溯
		 */
		public static final String YES = "1";

	}
	
	/**
	 * 是否启用
	 */
	public static final class IS_USED {

		/**
		 * 禁用
		 */
		public static final String NO = "2";

		/**
		 * 启用
		 */
		public static final String YES = "1";

	}
	
	/**
	 * 案例是否回溯(0,没有回溯;1:已经回溯)
	 */
	public static final class RECALLCASE {

		/**
		 * 未回溯
		 */
		public static final String NO = "0";

		/**
		 * 已回溯
		 */
		public static final String YES = "1";

	}
	/**
	 * 发布部门
	 */
	public static final String RELEASE_ORGAN_NAME = "恒大人寿";

	
	public static final class YES_OR_NO {
		/**
		 * 假（否）
		 */
		public static final String NO = "0";

		/**
		 * 真（是）
		 */
		public static final String YES = "1";
	}
	
	/**
	 * 数据是否上锁
	 */
	public static final class IS_LOCK {
		/**
		 * 否
		 */
		public static final String NO = "0";

		/**
		 * 是
		 */
		public static final String YES = "1";
	}
	/**
	 * 客户风险评级跑批任务状态
	 */
	public static final class TASK_STATUS{
		/**
		 * 未完成
		 */
		public static final String UN_FINISHED="0";
		
		/**
		 * 已完成
		 */
		public static final String FINISHED="1";
		
		/**
		 * 失败
		 */
		public static final String FAIL="2";
		
	}
	
	
	/**
	 * 客户风险评级跑批任务数量限制
	 */
	public static final String TASK_QUANTITATIVE_RESTRICTION="TASK_QUANTITATIVE_RESTRICTION";
	/**
	 * 客户风险等级跑批时每页记录数
	 */
	public static final String TASK_PAGE_SIZE="TASK_PAGE_SIZE";
	
	/**
	 * 客户风险评级跑批任务操作类型
	 */
	public static final class TASK_OPERATION_TYPE{
		/**
		 * 新增提交审核-0
		 */
		public static final String NEW_CUSTOMER_SUBMIT_AUDIT="0";
		
		/**
		 * 定审提交审核-1
		 */
		public static final String REGULER_CUSTOMER_SUBMIT_AUDIT="1";
		
		/**
		 * 审批通过-2
		 */
		public static final String APPROVAL="2";
		/**
		 * 审批退回-3
		 */
		public static final String APPROVAL_RETURNS="3";
		
	}
	
	
	
	
	/**
	 * 未审批客户风险等级类型
	 * @author lj
	 *
	 */
	public static final class NOT_APPROVE_CUSTOMER_LEVEL{
		/**
		 * 新增客户评级
		 */
		public static final String NEW_CUSTOMER_RISK_LEVEL="1";
		
		/**
		 * 定审客户评级
		 */
		public static final String REGULAR_INTERVALS_CHECK_CUSTOMER="2";
		
		/**
		 * 风险等级调整（其他）
		 */
	    public static final String OTHER_ADJUST_CUSTOMER="3";
	}
	/**
	 * 是否人工调整
	 * @ClassName: YES_OR_NO_MANUAL_ADJUSTMENT
	 */
	public static final class YES_OR_NO_MANUAL_ADJUSTMENT {
		/**
		 * 否
		 */
		public static final String NO = "0";

		/**
		 * 是
		 */
		public static final String YES = "1";
	}
	
	
	/**
	 * 未审批客户风险评级结果状态
	 */
	public static final class NOT_APPROVE_CUSTOMER_LEVEL_STATUS{
		/**
		 * 系统初评
		 */
		public static final String PRELIMINARY_ASSESSMENT="1";
		
		/**
		 * 待审批
		 */
		public static final String PENDING_APPROVAL="2";
		
		/**
		 * 审批退回
		 */
		public static final String APPROVAL_RETURNS="3";
		/**
		 * 审批通过
		 */
		public static final String APPROVAL="4";
	}
	
	/**
	 * 调整等级上传文件类型
	 */
	public static final String GRADE_ADJUSTMENT_DOCUMENT="GA";

	
	/**
	 * 客户风险等级
	 */
	public static final class RISK_LEVEL{
		/**
		 * 低风险
		 */
		public static final String LOW_RISK="1001";
		
		/**
		 * 较低风险
		 */
		public static final String LOWER_RISK="1002";
		
		/**
		 * 中风险
		 */
		public static final String MEDIUM_RISK="1003";
		/**
		 * 较高风险
		 */
		public static final String HIGHER_RISK="1004";
		/**
		 * 高风险
		 */
		public static final String HIGH_RISK="1005";
	}
	/**
	 * 是否超期
	 * @ClassName: IS_OVERDUE 
	 */
	public static final class IS_OVERDUE{
		public static final String YES="YES";
		public static final String NO="NO";
	}
	/**
	 * 计算复核截止日的时间间隔（天数）
	 */
	public static final Integer OVERDUE_DAY_NUM=5;
	
	/**
	 * 评级周期大类
	 */
	public static final String RATING_CYCLE="RATING_CYCLE";
	
	
	
	/**
	 * 审批状态（0=不通过 1=通过）
	 * @ClassName: AUDIT_TYPE
	 */
	public static final class AUDIT_TYPE {
		/**
		 * 不通过
		 */
		public static final String NO = "0";

		/**
		 * 通过
		 */
		public static final String YES = "1";
	}
	
	/**
	 * 2号令报送相关定义
	 */
	public interface Law2 {
		
		/**
		 * 大额报告类型
		 */
		String DE_TYPE = "IH";
		
		/**
		 * 可疑报告类型
		 */
		String KY_TYPE = "IS";
		
		/**
		 * 正常ZIP包
		 */
		String ZIP_N = "N";
		
		/**
		 * 异常ZIP包
		 */
		String ZIP_S = "S";
		
		/**
		 * 普通报文
		 */
		String XML_N = "N";
		
		/**
		 * 重发报文
		 */
		String XML_R = "R";
		
		/**
		 * 补报报文
		 */
		String XML_A = "A";
		
		/**
		 * 纠错报文
		 */
		String XML_C = "C";
		
		/**
		 * 补正报文
		 */
		String XML_I = "I";
		
		/**
		 * 删除报文
		 */
		String XML_D = "D";
		
	}
	
	
	//恒大人寿报送机构代码（人行分配，固定）
    public static final String REPORT_ORG_CODE="030015001034316";
    
    //恒大人寿报送机构名称
    public static final String REPORT_ORG_NAME="恒大人寿保险有限公司";
    
    //报送机构行政区划代码
    public static final String REPORT_ORG_AREA_CODE="500000";
    
    //可疑交易报文中的金融机构代码类型
    public static final String REPORT_ORG_CODE_TYPE="00";
    
    
    /**
     * 性别，大类
     */
    String GENDER = "GENDER";
    	
	/**
	 * 黑名单类型-大类
	 */
    String BLACKLIST_TYPE = "BLACKLIST_TYPE";
    
    /**
     * 黑名单来源-大类
     */
    String BLACKLIST_SOURCE = "BLACKLIST_SOURCE";
    	
    /**
     * 反洗钱审批流程-大类
     */
    String APPROVE_FLOW = "APPROVE_FLOW";
    
    /**
     * 	知识库-知识类别
     */
    String WIKI_TYPE = "WIKI_TYPE";
    
    /**
     * 	客户风险等级-风险等级
     */
    String CUSTOMER_RISK_LEVEL = "CUSTOMER_RISK_LEVEL";
    
    /**
     * 	客户风险等级-评级结果状态
     */
    String CUSTOMER_LEVEL_RESULT_STATUS = "CUSTOMER_LEVEL_RESULT_STATUS";
    
    /**
     * 	客户风险等级-评级类型
     */
    String CUSTOMER_RISK_TYPE = "CUSTOMER_RISK_TYPE";
    
    /**
     * 	客户风险等级-客户类型
     */
    String CUSTOMER_TYPE = "CUSTOMER_TYPE";
    /**
     * 客户证件类型-大类
     */
    String CARD_TYPE="CARD_TYPE";
    
    /**
     * 案例类型-大类
     */
    String CASE_TYPE="CASE_TYPE";
    
    /**
     * 案例状态-大类
     */
    String APP_STATE_CD="APP_STATE_CD";
    
    /**
     * 保险种类 -- 大类
     */
    String INSURANCE_TYPE = "INSURANCE_TYPE";
    
    /**
     * 业务类型-大类
     */
    String BUSINESS_TYPE = "BUSINESS_TYPE";
    
    
    /**
     * 币种-大类
     */
    String CURRENCY_TYPE = "CURRENCY_TYPE";
    
    String ORGAN_SOURCE = "ORGAN_SOURCE";
    
    /**
     * 	产品风险等级-产品风险等级
     */
    String PRODUCT_RISK_LEVEL = "PRODUCT_RISK_LEVEL";
    
    /**
     * 	客户风险等级-附件存放路径
     */
    String 	GRADE_ADJUSTMENT_FILE_PATH = "GRADE_ADJUSTMENT_FILE_PATH";
    
    /**
     * 	知识库-文件存放根路径
     */
    String WIKI_FILE_PATH = "WIKI_FILE_PATH";
    
    /**
     * 工作流key
     * @Package com.hdrs.aml.constant
     * @ClassName: WorkFlow
     */
    interface WorkFlow {
    	/**
    	 * 可疑案例工作流
    	 */
    	String kyFlow = "kyFlow";
    	
    	/**
    	 * 大额案例工作流
    	 */
    	String deFlow = "deFlow";
    	
    	/**
    	 * 客户风险等级工作流
    	 */
    	String riskFlow = "riskFlow";
    	/**
    	 * 
    	 */
    	String reportFlow = "reportFlow";
    }
    
    /**
     * 查询条件 isDeleted 查询所有
     */
    Object HAD_DEL = 3;
    
    /**
     *证件类型
     *
     */
    interface CARD_TYPE{
    	/**
    	 * 身份证
    	 */
    	String IDENTITY_CARD = "11";
    	
    	/**
    	 * 港澳通行证/台胞证
    	 */
    	String GANG_AO_TAI = "13";
    	
    	/**
    	 * 护照
    	 */
    	String PASSPORT = "14";
    	
    	/**
    	 * 其他
    	 */
    	String OTHERS = "19";
    	
    }
    
    /**
     * 本地系统中的性别
     *
     */
    interface GENDER{
    	
    	/**
    	 * 男
    	 */
    	String MALE = "1";
    	/**
    	 * 女
    	 */
    	String FEMALE = "2";
    	
    	/**
    	 * 未知
    	 */
    	String X = "3";
    }
    
    /**
     * 黑名单接口，xml文件中的的性别
     *
     */
    interface GENDER_XML{
    	
    	/**
    	 * 男
    	 */
    	String MALE = "MALE";
    	/**
    	 * 女
    	 */
    	String FEMALE = "FEMALE";
    	
    }
    
	/**
	 * 季报审核/审批结果状态
	 */
	public static final class QUARTERLY_REPORT_APPROVE_STATUS{
		/**
		 * 编辑中
		 */
		public static final String IN_EDITING = "1";
		
		/**
		 * 审核中
		 */
		public static final String IN_AUDIT = "2";
		
		/**
		 * 审批中
		 */
		public static final String IN_APPROVAL = "3";
		
		/**
		 *  审批完成
		 */
		public static final String END_APPROVAL = "4";
		
		/**
		 *  审核退回
		 */
		public static final String RETURN_AUDIT = "5";
		
		/**
		 * 审批退回
		 */
		public static final String RETURN_APPROVAL = "6";
	}
    
	/**
	 * 年度报表类型
	 */
	public static final class REPORT_YEAR_TYPE{
		/**
		 * 报告机构基本情况年报
		 */
		public static final String ORGANIZATION = "ORGANIZATION";
		
		/**
		 * 内控制度年报
		 */
		public static final String INCONTROL = "INCONTROL";
		
		/**
		 * 岗位人员年报
		 */
		public static final String STAFF = "STAFF";
		
		/**
		 *  客户身份识别及风险等级分类年报
		 */
		public static final String IDCARDRISK = "IDCARDRISK";
		
		/**
		 *  大额和可疑交易年报
		 */
		public static final String LARSUS = "LARSUS";
		
		/**
		 * 宣传情况年报
		 */
		public static final String REPORTAD = "REPORTAD";
		
		/**
		 * 培训情况年报
		 */
		public static final String TRAINING = "TRAINING";
		
		/**
		 * 内部检查情况年报
		 */
		public static final String INSPECTION = "INSPECTION";
		
		/**
		 * 配合行政调查年报
		 */
		public static final String ADMINISTRATIVE = "ADMINISTRATIVE";
		
		/**
		 * 接受现场检查年报
		 */
		public static final String SITECHECK = "SITECHECK";
		
		/**
		 * 风险防控成果表
		 */
		public static final String RISKCONTROL = "RISKCONTROL";
		
		/**
		 * 境外分支机构表
		 */
		public static final String OVERSEASBRANCH = "OVERSEASBRANCH";
	}
	
    
    // 机构名称（全称）默认值
    public static final String REPORT_ORGANIZATION_NAME = "恒大人寿保险有限公司";
    
    // 行业类型默认值
    public static final String CAREER_TYPE = "保险业";
    
    // 设立时间默认值
    public static final String FOUND_TIME = "2006-5-11";
    
	// 是否外资机构默认值
    public static final String IS_FOREIGN = "0";
	
	/**
	 * 报表状态
	 */
	public static final class REPORT_STATE{
		/**
		 * 未保存
		 */
		public static final String UNSAVED = "0";
		
		/**
		 * 未确认
		 */
		public static final String UNCONFIRMED = "1";
		
		/**
		 * 已确认
		 */
		public static final String CONFIRMED = "2";
	}
	
	// 机构代码：总公司
    public static final String REPORT_ORGAN_CODE = "86";
    
    /**
     * 发送短信配置
     * @ClassName: SHORT_MSG
     */
    String SHORT_MSG = "SHORT_MSG";
    
    /**
     * 数据同步失败短信通知，多人逗号隔开
     */
    String SYN_DATA_MSG_TOS = "SYN_DATA_MSG_TOS";
    
    /**
     * 发送邮件相关配置大类
     */
    String EMAIL_MSG = "EMAIL_MSG";
    
    /**
	 * 发送邮件相关配置
	 */
	interface EMAIL_MSG{
		/**
		 * 邮箱服务器地址
		 */
		String HOST = "HOST";
		
		/**
		 * 发送者邮箱地址
		 */
		String FROM = "FROM";
		
		/**
		 * 发送用户名对应的密码（qq邮箱要用授权码）
		 */
		String PASSWORD = "PASSWORD";
		
		/**
		 * 设置登录服务器校验用户
		 */
		String USERNAME = "USERNAME";
		
	}
	
	/**
	 * 数据同步失败邮件通知，收件人，多人逗号隔开
	 */
	String SYN_DATA_EMAIL_TOS = "SYN_DATA_EMAIL_TOS";
	
	/**
	 * 发送统计信息邮件
	 */
	String RULE_INVOKE_EMAIL_TOS = "RULE_INVOKE_EMAIL_TOS";
	
	/**
	 * 上报人行枚举标记
	 * @ClassName: UP_TYPE
	 */
	interface UP_TYPE {
		/**
		 * 大额类
		 */
		String DE = "DE";
		
		/**
		 * 可疑类
		 */
	    String KY = "KY";
	}
	
	/**
	 * 案例种类
	 */
	String CASE_KIND = "CASE_KIND";
	
	/**
	 * 报文类型  --大类
	 */
	String LAW2_UP_TYPE  = "LAW2_UP_TYPE";
	
	/**
	 * 回执类型  --大类
	 */
	String LAW2_FD_TYPE  = "LAW2_FD_TYPE";
	
	/**
	 * 黑名单FTP接口信息 -- 大类
	 */
	String BLACKLIST_FTP = "BLACKLIST_FTP";
    
	/**
	 * 是：1/否：0
	 * @ClassName: UP_TYPE
	 */
	interface WHETHER {
		/**
		 * 是
		 */
		String YES = "1";
		
		/**
		 * 否
		 */
	    String NO = "0";
	}
	
	/**
	 * 数据同步-个险相关
	 * @Package com.hdrs.aml.constant
	 * @ClassName: ODS_P
	 */
	interface ODS_P {
		/**
		 * 核心被保人表
		 */
		String T47_CONTRACT_B = "T47_CONTRACT_B";
		
		/**
		 * 核心合同表
		 */
		String T47_CONTRACT = "T47_CONTRACT";
		
		/**
		 * 核心受益人
		 */
		String T47_CONTRACT_S = "T47_CONTRACT_S";
		
		/**
		 * 核心个人账户信息
		 */
		String T47_ID_DEPOSIT = "T47_ID_DEPOSIT";
		
		/**
		 * 核心影像信息
		 */
		String T47_IMAGE = "T47_IMAGE";
		
		/**
		 * 核心个人信息表
		 */
		String T47_INDIVIDUAL = "T47_INDIVIDUAL";
		
		/**
		 * 核心机构信息
		 */
		String T47_ORGAN = "T47_ORGAN";
		
		/**
		 *核心客户信息
		 */
		String T47_PARTY = "T47_PARTY";
		
		/**
		 * 核心产品信息
		 */
		String T47_PRODUCT = "T47_PRODUCT";
		
		/**
		 * 核心交易信息
		 */
		String T47_TRANSACTION = "T47_TRANSACTION";
		
		/**
		 * 保全信息表
		 */
		String T47_POSINFO = "T47_POSINFO";
		
		
		
	}
	
	/**
	 * 数据同步-团险相关
	 * @Package com.hdrs.aml.constant
	 * @ClassName: ODS_G
	 */
	interface ODS_G {
		/**
		 * 核心被保人表
		 */
		String T47_CONTRACT_B_G = "T47_CONTRACT_B_G";
		
		/**
		 * 核心合同表
		 */
		String T47_CONTRACT_G = "T47_CONTRACT_G";
		
		/**
		 * 核心受益人
		 */
		String T47_CONTRACT_S_G = "T47_CONTRACT_S_G";
		
		/**
		 * 核心公司信息表
		 */
		String T47_CORPORATION_G = "T47_CORPORATION_G";
		
		/**
		 * 核心公司账户信息表
		 */
		String T47_CP_DEPOSIT_G = "T47_CP_DEPOSIT_G";
		
		/**
		 * 核心个人账户信息
		 */
		String T47_ID_DEPOSIT_G = "T47_ID_DEPOSIT_G";
		
		/**
		 * 核心影像信息
		 */
		String T47_IMAGE_G = "T47_IMAGE_G";
		
		/**
		 * 核心个人信息表
		 */
		String T47_INDIVIDUAL_G = "T47_INDIVIDUAL_G";
		
		/**
		 * 核心机构信息
		 */
		String T47_ORGAN_G = "T47_ORGAN_G";
		
		/**
		 *核心客户信息
		 */
		String T47_PARTY_G = "T47_PARTY_G";
		
		/**
		 * 核心产品信息
		 */
		String T47_PRODUCT_G = "T47_PRODUCT_G";
		
		/**
		 * 核心交易信息
		 */
		String T47_TRANSACTION_G = "T47_TRANSACTION_G";
	}
	
	/**
	 * 数据合并处理标记
	 * @ClassName: MERGE_TAG
	 */
	interface MERGE_TAG {
		/**
		 * 个险数据合并完成标记
		 */
		String ODS_P_MERGE_FLAG = "ODS_P_MERGED";
		
		/**
		 * 团险数据合并完成标记
		 */
		String ODS_G_MERGE_FLAG = "ODS_G_MERGED";
	}
	
	/**
	 * 要下载的文件不存在提示页面
	 */
	String FileNotExistPage = "fileNotExist";
	
	/**
	 * 限制上传的文件类型
	 */
	String UPFILE_DEBAR_TYPE = "UPFILE_DEBAR_TYPE";
	
	/**
	 * 系统开关配置大类代码
	 */
	String SWITCH_LIST = "SWITCH_LIST";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
