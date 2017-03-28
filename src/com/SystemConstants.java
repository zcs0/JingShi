package com;
/**
 * @ClassName:     HttpUrl.java
 * @author         zcs
 * @version        V1.0  
 * @Date           2017年3月9日 下午4:05:00 
 * @Description:   TODO(用一句话描述该文件做什么) 
 */
public class SystemConstants {
	public static int handlerMessageBasis = 10;//handler what基本值++
	private static final String host="192.168.0.138:8001/";
	private static final String http="http://";
	//事实货物震动信息
	public static final String vibrate_url = "EmfcRciWeb/MobileCtrl/vibrateDemo";
	//事实货物车辆信息
	public static final String car_position = "EmfcRciWeb/MobileCtrl/findRealtimeData";
	//用户登录接口
	public static final String userLogin = "/userLogin";
	//极光推送ID注册
	private static final  String JpushID = "/registerJpushID";
	//手机验证接口
	public static final String MobileVerificationCode ="/getMobileVerificationCode";
	//通过手机验证码重置密码接口
	public static final String resetPassByMobile = "/resetPassByMobile";
	//通过邮箱找回密码接口
	public static final String resetPassByEmail = "/resetPassByEmail";
	//修改密码接口
	public static final String modifyPass = "/modifyPass";
	//修改邮箱接口
	public static final String modifyEmail = "/modifyEmail";
	//获得项目列表接口
	public static final String getProjectList = "/getProjectList";
	//扫描货物条码接口
	public static final String getCommodityIdByBarCode = "/getCommodityIdByBarCode";
	//扫描设备条码接口
	public static final String getDeviceIdByBarCode = "/getDeviceIdByBarCode";
	//绑定货物接口
	public static final String bindCommodity = "/bindCommodity";
	//绑定设备接口
	public static final String bindDevice= "/bindDevice";
	//绑定设备接口2
	public static final String bindDeviceByBarcode = "/bindDeviceByBarcode";
	//项目搜索接口
	public static final String searchProject = "/searchProject";
	//天津工控物联有限公司
	//获得货物列表接口
	public static final String getCommodityList = "/getCommodityList";
	//获得设备列表接口
	public static final String getDeviceList = "/getDeviceList";
	//远程唤醒设备接口
	public static final String wakeupDevice = "/wakeupDevice";
	//搜索设备列表接口
	public static final String searchDevice = "/searchDevice";
	//获得设备曲线数据列表接口
	public static final String getDeviceCurveDataList = "/getDeviceCurveDataList";
	//获得已发送通知消息接口
	public static final String getMessageList ="/getMessageList";
	//用户登出接口
	public static final String userLogout = "/userLogout";
	//设备解绑
	public static final String unbindDevice = "/unbindDevice";
	//上传用户地理位置
	public static final String setUserPostion = "/setUserPostion";
	
	public static String getUrl(boolean isdebug,String url){
		if(isdebug){//如果是测试版
			//http://192.168.0.141:8001/EmfcRciWeb/MobileCtrl/vibrateDemo
			return http + host + url;
		}else{
			
		}
		return null;
		
	}
	
	
}
