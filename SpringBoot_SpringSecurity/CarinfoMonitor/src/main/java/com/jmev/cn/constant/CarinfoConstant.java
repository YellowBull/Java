package com.jmev.cn.constant;

/**
 * 常量类
 */
public interface CarinfoConstant
{

    /**
     * 登录账号解密公钥
     */
    public static final String PRIVATE_KEY_STR = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBALVi84OCbISu0Jkh"
            + "sS38jxlLqC4YNMx6WkVCe9eI9nOXfE0v0iSPmW5aQoPMmvkVemH7H1yEDEbEvVkm"
            + "7XMEhCkeWm+ds/5ZDr+sdfC8MHgapDF5xVgY8WnYk8LiPY2R2kZkbnmSHegNkWs6"
            + "Icpc5mhPYxV+iOnfA3ZcXhfJ8s2zAgMBAAECgYEAghFh03TsAj0lQhnB5tsLdjUl"
            + "dWBQRCycnLuu+ICzUXJYZTUceLEscdBxj/dhTaoUJzyfYYUjFIAx00Mx1F9xsKGK"
            + "lbf0P8Bfeneq+N1LtK2MWjtRnOv3a+DUS6wIYFzqIgjI+sVLTJiN46EVTKvpjPHs"
            + "ED1vOzme6GZCMmHILFECQQDajUu5i1GIg4ozKdZIV2CUWm6a7e1PVzmRRV1dI72L"
            + "+yI1vdPgWcbyv+uqNOO4W+3yZgY+nQfroIp+1zrJtdbrAkEA1HdoZQiK4okGfUK1"
            + "icgTC6EOirwR55745MGwE5flSxxWaywb+NfgbyWPtr/TPOzJq0+eil+t+l6Gc04Q"
            + "uVfCWQJBAKK7+TnEakaLI7mrGlqtbUWp/JeyODUOztfD3Zw/w6Das4NuwLhaQRB6"
            + "JaGMVk/ta8VFgLrYtJAX72g5HRYbJ1cCQQCGERr0teE9dQ19OPzohjFOY0CT1nzN"
            + "1QhlALskgfKT5Lu7QMgdy8q5F9CJlp2qkhfnW4RE+H8Fv2PDmnz/FxtxAkEAlllT"
            + "59nNXC+HuW0bpHCyzdnYULabblYSHpMjxWqb6Qsix2b+Cda+VNgRGfKxOo3Mu4AY" + "1Fif7mobRlTlSOyg0Q==";

    public static final int REPORT_EXPORT_COUNT = 5000; //报表导出文件默认导出的最大记录数量
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
     * 是否启用
     */
    public static final class IS_USED
    {

        /**
         * 禁用
         */
        public static final String NO = "2";

        /**
         * 启用
         */
        public static final String YES = "1";

    }

    public static final class YES_OR_NO
    {
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
    public static final class IS_LOCK
    {
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
     * 查询条件 isDeleted 查询所有
     */
    Object HAD_DEL = 3;

}
