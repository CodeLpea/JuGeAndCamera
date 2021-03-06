package cn.com.magnity.coresdksample.Service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.com.magnity.coresdksample.MyApplication;
import cn.com.magnity.coresdksample.Service.update.SoftWareUpgradeService;
import cn.com.magnity.coresdksample.ddnwebserver.WebCoreService;
import cn.com.magnity.coresdksample.websocket.service.WebSocketService;


/**
 * 通用的服务管理
 * lp
 * 2019/05/28
 */

public class ServiceManager {
    private static final String TAG = "ServiceManager";
    private Context mContext = null;
    
    private static ServiceManager instance;
    // 单例模式中获取唯一的ServiceManager实例
    public static ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager(MyApplication.getInstance());
        }
        return instance;
    }
    public ServiceManager(Context context) {
        mContext = context;
    }
    public void startServices(){
        if(mContext == null){
            Log.i(TAG, "=======startServices============>mConetxt = " + mContext);
            return;
        }
        Log.i(TAG, "startServices: ");
        //版本控制服务
        if(!SoftWareUpgradeService.isServiceRunning()) {
            Intent SoftWareIntent = new Intent(mContext, SoftWareUpgradeService.class);
            mContext.startService(SoftWareIntent);
        }

        //开启WebSorket服务
        if(!WebSocketService.isServiceRunning()) {
            Intent websorketIntent = new Intent(mContext, WebSocketService.class);
            mContext.startService(websorketIntent);
        }
        //开启Web配置界面服务
        if(!WebCoreService.isServiceRunning()) {
            mContext.startService(new Intent(mContext, WebCoreService.class));
        }
        //开启网络连接服务
        if(!NetService.isServiceRunning()) {
            mContext.startService(new Intent(mContext, NetService.class));
        }
        //开启网络连接服务
        if(!FtpService.isServiceRunning()) {
            mContext.startService(new Intent(mContext, FtpService.class));
        }


    }

    public void stopServices(){
        if(mContext == null){
            Log.i(TAG, "=======stopServices============>mConetxt = " + mContext);
            return;
        }
        Log.i(TAG, "stopServices: ");
        // 停止版本控制服务
        if(SoftWareUpgradeService.isServiceRunning()) {
            Intent SoftWareIntent = new Intent(mContext, SoftWareUpgradeService.class);
            mContext.stopService(SoftWareIntent);
        }

        //关闭WebSorket服务
        if(WebSocketService.isServiceRunning()) {
            Intent websorketIntent = new Intent(mContext, WebSocketService.class);
            mContext.stopService(websorketIntent);
        }
        //关闭Web配置界面服务
        if(WebCoreService.isServiceRunning()) {
            mContext.stopService(new Intent(mContext, WebCoreService.class));
        }
        //关闭网络连接服务
        if(NetService.isServiceRunning()) {
            mContext.stopService(new Intent(mContext, NetService.class));
        }
        //关闭网络连接服务
        if(FtpService.isServiceRunning()) {
            mContext.stopService(new Intent(mContext, FtpService.class));
        }


    }
}
