package com.example.administrator.newsdf.utils;

/**
 * 作者：winelx
 * 时间：2017/11/22 0022:下午 14:33
 * 说明：网络请求端口
 */
public class Request {
    //  public static final String  = networks + "";
    //公共头  192.168.20.38

    //public static final String networks = "http://192.168.20.40:8080/baseframe/";
      public static final String networks = "http://117.187.27.78:8081/baseframe/";
    //   public static final String networks = "http://120.79.142.15/pzgc/";
    /**
     * 用户登陆
     */

    public static final String Login = networks + "admin/login";
    /**
     * 项目成员
     */


    public static final String Members = networks + "iface/mobile/user/staffList";
    /**
     * 修改密码
     */


    public static final String AlterPwd = networks + "iface/mobile/user/alterPwd";
    /**
     * 消息首页
     */


    public static final String TaskMain = networks + "iface/mobile/taskmsg/findWbsTaskMsgByOrg";
    /**
     * wbs树
     */


    public static final String WBSTress = networks + "iface/mobile/taskmain/findWbsTree";
    /**
     * wbsid查询任务维护列表
     */


    public static final String Mission = networks + "iface/mobile/taskmain/findTaskMainList";
    /**
     * 退出登录
     */


    public static final String BackTo = networks + "iface/mobile/logout";
    /**
     * 根据wbs书选择检查项
     */


    public static final String WbsTaskGroup = networks + "iface/mobile/taskmain/findWbsTaskGroup";
    /**
     * 查询任务维护列表
     */


    public static final String WbsTaskMain = networks + "iface/mobile/taskmain/findWbsTaskMain";
    /**
     * 任务详情
     */


    public static final String Detail = networks + "iface/mobile/taskmain/wbsTaskDetail";
    /**
     * 图纸查看
     */

    public static final String Photolist = networks + "iface/mobile/drawing/findDrawingList";
    /**
     * 上传资料
     */

    public static final String Uploade = networks + "iface/mobile/taskmain/addWbsTask";
    /**
     * 发表评论
     */

    public static final String commentaries = networks + "iface/mobile/taskmain/commentWbsTask";
    /**
     * 推送配置列表查看
     */

    public static final String PUSHList = networks + "iface/mobile/taskmain/findWbsCascadePoint";
    /**
     * 根据组织查询消息列表
     */

    public static final String CascadeList = networks + "iface/mobile/taskmsg/findWbsTaskMsgByWbs";
    /**
     * 推送消息
     */

    public static final String pushOKgo = networks + "iface/mobile/taskmain/startWbsTaskByIds";
    /**
     * 新增推送
     */

    public static final String newPush = networks + "iface/mobile/taskmain/addNewTask";
    /**
     * 查询可切换组织
     */

    public static final String Swatchmakeup = networks + "iface/mobile/user/getUserOrg";
    /**
     * 切换组织
     */

    public static final String Swatch = networks + "iface/mobile/user/changeUserOrg";
    /**
     * 根据ID查人员信息
     */

    public static final String Personal = networks + "iface/mobile/user/staff";
    /**
     * 图纸列表
     */

    public static final String PhotoList = networks + "iface/mobile/drawing/findDrawingTree";
    /**
     * 饼状图
     */

    public static final String PieChart = networks + "iface/mobile/taskmain/findHomePieJson";
    /**
     *
     */
    public static final String Photo_ce = networks + "iface/mobile/drawing/findDrawingByGroup";
    /**
     * 任务配置
     */

    public static final String WbsTaskConfig = networks + "iface/mobile/taskmain/wbsTaskConfig";
    /**
     * 根据ID查询wbs的信息
     */

    public static final String Wbsdetails = networks + "iface/mobile/taskmsg/findWbsInfoById";
    /**
     * 查询联系人
     */

    public static final String UserList = networks + "iface/mobile/user/getStaffList";
    /**
     * 版本更新
     */

    public static final String UpLoading = networks + "iface/mobile/appversion/findAppVersion";


}