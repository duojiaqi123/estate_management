package com.djq.estate_management.Common;

import com.djq.estate_management.Domain.Vehicle;

/**
 * @Auth: DUOJIAQI
 * @Desc: 返回结果消息提示常量类
 */
public class MessageConstant {
    //---------------------------流水（TradingStream）操作消息提示信息---------------------------------------

    public static final String TRADING_STREAM_SEARCH_SUCCESS="查询流水列表信息成功！";
    public static final String TRADING_STREAM_ADD_SUCCESS="新增流水信息成功！";
    public static final String TRADING_STREAM_UPDATE_SUCCESS="修改流水信息成功！";
    public static final String TRADING_STREAM_DELETE_SUCCESS="删除流水信息成功！";
    public static final String TRADING_STREAM_FIND_BY_ID_SUCCESS = "根据主键获取流水对象成功！";

    //---------------------------活动（Activity）操作消息提示信息---------------------------------------
    public static final String ACTIVITY_SEARCH_SUCCESS="查询活动列表信息成功！";
    public static final String ACTIVITY_ADD_SUCCESS="新增活动信息成功！";
    public static final String ACTIVITY_UPDATE_SUCCESS="修改活动信息成功！";
    public static final String ACTIVITY_DELETE_SUCCESS="删除活动信息成功！";
    public static final String ACTIVITY_FIND_BY_ID_SUCCESS = "根据主键获取活动对象成功！";
    public static final String ACTIVITY_UPDATE_STATUS_SUCCESS = "活动状态信息更新成功！";
    //---------------------------管理员（Admin）操作消息提示信息---------------------------------------
    public static final String ADMIN_SEARCH_SUCCESS="查询管理员列表信息成功！";
    public static final String ADMIN_ADD_SUCCESS="新增管理员信息成功！";
    public static final String ADMIN_UPDATE_SUCCESS="修改管理员信息成功！";
    public static final String ADMIN_DELETE_SUCCESS="删除管理员信息成功！";
    public static final String ADMIN_FIND_BY_ID_SUCCESS = "根据主键获取管理员对象成功！";
    //---------------------------维修工（Worker）操作消息提示信息---------------------------------------
    public static final String WORKER_SEARCH_SUCCESS="查询维修工列表信息成功！";
    public static final String WORKER_ADD_SUCCESS="新增维修工信息成功！";
    public static final String WORKER_UPDATE_SUCCESS="修改维修工信息成功！";
    public static final String WORKER_DELETE_SUCCESS="删除维修工信息成功！";
    public static final String WORKER_FIND_BY_ID_SUCCESS = "根据主键获取维修工对象成功！";
    //---------------------------缴费（Charge）操作消息提示信息---------------------------------------
    public static final String CHARGE_SEARCH_SUCCESS="查询缴费列表信息成功！";
    public static final String CHARGE_ADD_SUCCESS="新增缴费信息成功！";
    public static final String CHARGE_UPDATE_SUCCESS="修改缴费信息成功！";
    public static final String CHARGE_DELETE_SUCCESS="删除缴费信息成功！";
    public static final String CHARGE_FIND_BY_ID_SUCCESS = "根据主键获取缴费对象成功！";
    public static final String CHARGE_UPDATE_STATUS_SUCCESS = "缴费状态信息更新成功！";
    //---------------------------投诉（Complaint）操作消息提示信息---------------------------------------
    public static final String COMPLAINT_SEARCH_SUCCESS="查询投诉列表信息成功！";
    public static final String COMPLAINT_ADD_SUCCESS="新增投诉信息成功！";
    public static final String COMPLAINT_UPDATE_SUCCESS="修改投诉信息成功！";
    public static final String COMPLAINT_DELETE_SUCCESS="删除投诉信息成功！";
    public static final String COMPLAINT_FIND_BY_ID_SUCCESS = "根据主键获取投诉对象成功！";
    public static final String COMPLAINT_UPDATE_STATUS_SUCCESS = "投诉状态信息更新成功！";
    //---------------------------楼栋（Building）操作消息提示信息---------------------------------------
    public static final String BUILDING_SEARCH_SUCCESS="查询楼栋列表信息成功！";
    public static final String BUILDING_ADD_SUCCESS="新增楼栋信息成功！";
    public static final String BUILDING_UPDATE_SUCCESS="修改楼栋信息成功！";
    public static final String BUILDING_DELETE_SUCCESS="删除楼栋信息成功！";
    public static final String BUILDING_FIND_BY_ID_SUCCESS = "根据主键获取楼栋对象成功！";
    //---------------------------房产（Home）操作消息提示信息---------------------------------------
    public static final String HOME_SEARCH_SUCCESS="查询房产列表信息成功！";
    public static final String HOME_ADD_SUCCESS="新增房产信息成功！";
    public static final String HOME_UPDATE_SUCCESS="修改房产信息成功！";
    public static final String HOME_DELETE_SUCCESS="删除房产信息成功！";
    public static final String HOME_FIND_BY_ID_SUCCESS = "根据主键获取房产对象成功！";
    //---------------------------停车位（Parking）操作消息提示信息---------------------------------------
    public static final String PARKING_SEARCH_SUCCESS="查询停车位列表信息成功！";
    public static final String PARKING_ADD_SUCCESS="新增停车位信息成功！";
    public static final String PARKING_UPDATE_SUCCESS="修改停车位信息成功！";
    public static final String PARKING_DELETE_SUCCESS="删除停车位信息成功！";
    public static final String PARKING_FIND_BY_ID_SUCCESS = "根据主键获取停车位对象成功！";
    public static final String PARKING_UPDATE_STATUS_SUCCESS = "停车位状态信息更新成功！";
    //---------------------------超级管理员（Root）操作消息提示信息---------------------------------------
    public static final String ROOT_SEARCH_SUCCESS="查询超级管理员列表信息成功！";
    public static final String ROOT_ADD_SUCCESS="新增超级管理员信息成功！";
    public static final String ROOT_UPDATE_SUCCESS="修改超级管理员信息成功！";
    public static final String ROOT_DELETE_SUCCESS="删除超级管理员信息成功！";
    public static final String ROOT_FIND_BY_ID_SUCCESS = "根据主键获取超级管理员对象成功！";
    //---------------------------业主（Personnel）操作消息提示信息---------------------------------------
    public static final String PERSONNEL_SEARCH_SUCCESS="查询业主列表信息成功！";
    public static final String PERSONNEL_ADD_SUCCESS="新增业主信息成功！";
    public static final String PERSONNEL_UPDATE_SUCCESS="修改业主信息成功！";
    public static final String PERSONNEL_DELETE_SUCCESS="删除业主信息成功！";
    public static final String PERSONNEL_FIND_BY_ID_SUCCESS = "根据主键获取业主对象成功！";
    //---------------------------报修（Repair）操作消息提示信息---------------------------------------
    public static final String REPAIR_SEARCH_SUCCESS="查询报修列表信息成功！";
    public static final String REPAIR_ADD_SUCCESS="新增报修信息成功！";
    public static final String REPAIR_UPDATE_SUCCESS="修改报修信息成功！";
    public static final String REPAIR_DELETE_SUCCESS="删除报修信息成功！";
    public static final String REPAIR_FIND_BY_ID_SUCCESS = "根据主键获取报修对象成功！";
    public static final String REPAIR_UPDATE_STATUS_SUCCESS = "报修状态信息更新成功！";

    //---------------------------车辆（Vehicle）操作消息提示信息---------------------------------------
    public static final String VEHICLE_SEARCH_SUCCESS="查询车辆列表信息成功！";
    public static final String VEHICLE_ADD_SUCCESS="新增车辆信息成功！";
    public static final String VEHICLE_UPDATE_SUCCESS="修改车辆信息成功！";
    public static final String VEHICLE_DELETE_SUCCESS="删除车辆信息成功！";
    public static final String VEHICLE_FIND_BY_ID_SUCCESS = "根据主键获取车辆对象成功！";
    //---------------------------系统提示信息----------------------------------------------------------
    public static final String SYSTEM_BUSY = "系统繁忙，请求稍后重试！";
    //---------------------------文件上传提示信息-------------------------------------------------------
    public static final String NO_FILE_SELECTED = "未选择上传的文件,请求选择后上传!";
    public static final String NO_WRITE_PERMISSION = "上传目录没有写权限!";
    public static final String INCORRECT_DIRECTORY_NAME = "目录名不正确!";
    public static final String SIZE_EXCEEDS__LIMIT = "上传文件大小超过限制!";
    public static final String FILE_TYPE_ERROR = "文件类型错误，只允许上传JPG/PNG/JPEG/GIF等图片类型的文件!";
    public static final String FILE_PIC_UPLOAD_SUCCESS="缩略图上传成功！";
    public static final String FILE_PIC_DEL_SUCCESS = "缩略图删除成功！";
}
