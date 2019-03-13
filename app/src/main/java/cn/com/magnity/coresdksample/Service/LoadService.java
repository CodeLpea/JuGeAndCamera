package cn.com.magnity.coresdksample.Service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Environment;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.StringTokenizer;

import cn.com.magnity.coresdksample.MainActivity;
import cn.com.magnity.coresdksample.MyApplication;
import cn.com.magnity.coresdksample.utils.Config;
import cn.com.magnity.coresdksample.utils.PreferencesUtils;

import static cn.com.magnity.coresdksample.utils.Config.MSG4;
import static cn.com.magnity.coresdksample.utils.Config.SavaRootDirName;

/**
 * 开机后自动加载数据的服务
 */
public class LoadService extends IntentService {
    private static final String TAG="LoadService";
    public LoadService() {
        super("LoadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        HashMap keyValueMap;
        Message message=Message.obtain();
        //进行加载任务
        Log.i(TAG, "--------------------------------进行加载数据----------------------------------");
        if (intent != null&&isExistFlie()) {//如果文件夹存在才进行加载，否则为默认值
            try{
            keyValueMap=readKeyValueTxtToMap();
            Config.WifiName=keyValueMap.get("网络名称").toString();
            Config.WifiPassWord=keyValueMap.get("网络密码").toString();
            Config.currtentVoiceVolume=Integer.parseInt(keyValueMap.get("默认播报音量").toString());
            Config.normolTempVoiceVolume=Integer.parseInt(keyValueMap.get("体温正常播报音量").toString());
            Config.heightTempVoiceVolume=Integer.parseInt(keyValueMap.get("体温偏高播报音量").toString());
                message.what=MSG4;
                message.obj="读取配置文件成功";
                MainActivity.DelayStartHandler.sendMessageDelayed(message,7000);
            }catch (Exception e){
                e.printStackTrace();
                message.what=MSG4;
                message.obj="读取配置文件失败，请检查";
                MainActivity.DelayStartHandler.sendMessageDelayed(message,7000);
                //MyApplication.getInstance().ttsUtil.SpeechFlush("读取配置文件失败",8);
            }
        }else {//装载为默认值
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    /**
     * 检查是否存在文件
     * */
    private boolean isExistFlie(){
        boolean turn=false;
        //直接读取跟目录下面的配置文件Environment.getExternalStorageDirectory
        File file = Environment.getExternalStorageDirectory();
        if (null != file) {
            file = new File(file, "DdnProperties.txt");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    Log.i(TAG, "isExistFlie: ----------------------不存在DdnProperties.txt，正在创建-------------------");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i(TAG, "isExistFlie: ----------------------创建不存在DdnProperties.txt失败-------------------");
                }

                turn=false;
            }else {
                Log.i(TAG, "isExistFlie:------------------------ 存在DdnProperties-----------------------------");
                turn=true;
            }
        }
    return turn;
    }

/**
 * 读取本地txt
 * 存放各位hashmap键值对
 * */
    private HashMap readKeyValueTxtToMap() {
        //循环直至返回map
        final HashMap keyValueMap = new HashMap();//保存读取数据keyValueMap
        //每一个循环读取一组key=value
        while (true) {
            try {
               // final InputStream open = this.getAssets().open("key_value.txt");
                File f=new File(Environment.getExternalStorageDirectory().getPath()+"/"+Config.DdnProperties);
                final InputStream open =new FileInputStream(f);
                final byte[] readArray = new byte[open.available()];
                open.read(readArray);
                open.close();
                final StringTokenizer allLine = new StringTokenizer(new String(readArray, "GBK"), "\r\n");//以"\r\n"作为key=value的分解标志
                while (allLine.hasMoreTokens()) {
                    final StringTokenizer oneLine = new StringTokenizer(allLine.nextToken(), "=");//以"="作为分解标志
                    final String leftKey = oneLine.nextToken();//读取第一个字符串key
                    if (!oneLine.hasMoreTokens()) {
                        break;
                    }
                    final String rightValue = oneLine.nextToken();//读取第二个字符串value
                    keyValueMap.put(leftKey, rightValue);
                    Log.i(TAG, "Key:    "+leftKey);
                    Log.i(TAG, "Value:  --------------------------"+rightValue);
                }
                return keyValueMap;
            } catch (IOException e) {
                e.printStackTrace();
                return keyValueMap;
            }
        }

    }
}