package cn.com.magnity.coresdksample.utils;

import android.util.Log;

import cn.com.magnity.coresdksample.Config;

/**
 * GPIO控制信号灯
 * */
public class lampUtil {
    private static final String TAG="lampUtil";
    public static int which;
    public static int time;

    /**
     * @param which 灯的颜色1：绿，2：红
     * @param time  闪烁间隔时间 ms为单位
     * @param delay 持续时间 ms为单位，大于零这会恢复默认状态
     * @return
     */
    public static void setlamp(final int which, final int time,final int delay) {

        if(lampUtil.which!=which||lampUtil.time!=time){//有变化才进入
            lampUtil.which=which;
            lampUtil.time=time;
        if(delay>0){//如果延时大于0，则延时之后恢复到系统当前状态（根据Lamp来定，系统有误为2红，正常为l绿色）
        new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    Thread.sleep(delay);
                }catch (Exception e){
                }
                lampUtil.which= Config.LAMP;
                lampUtil.time=Config.TIME;
            }
        }).start();

        }else {//否则，不做延时恢复，直接永久改变。
            Log.i(TAG, "系统灯光设置: ");
            Config.LAMP=which;//设置默认状态
            Config.TIME=time;

        }
        }
    }

    /**
     * @param which 灯的颜色1：绿，2：红
     * @param time  闪烁间隔时间 ms为单位
     */public lampUtil(int which, int time) {
        this.which = which;
        this.time = time;
        new lampThread().start();
    }
int count=1;
private class lampThread  extends Thread{
    public lampThread() { }
    @Override
    public void run() {
        Log.i(TAG, "开启灯控制线程: ");
        while(true){
            switch (which){
                case 2://亮红灯
                    ShellUtils.execCommand("echo 0 > /sys/class/backlight/rk28_bl/blue", false);
                    ShellUtils.execCommand("echo 0 > /sys/class/backlight/rk28_bl/red", false);
                    try{
                        Thread.sleep(time);
                    }catch (Exception e){

                    }
                    ShellUtils.execCommand("echo 1 > /sys/class/backlight/rk28_bl/red", false);
                    try{
                        Thread.sleep(time);
                    }catch (Exception e){

                    }
                    break;
                case 1://亮绿灯
                    ShellUtils.execCommand("echo 0 > /sys/class/backlight/rk28_bl/blue", false);
                    ShellUtils.execCommand("echo 0 > /sys/class/backlight/rk28_bl/red", false);
                    try{
                        Thread.sleep(time);
                    }catch (Exception e){

                    }
                    ShellUtils.execCommand("echo 1 > /sys/class/backlight/rk28_bl/blue", false);
                    try{
                        Thread.sleep(time);
                    }catch (Exception e){

                    }
                    break;
            }


        }
    }
}
}

/*
*
*   try{
                     count++;
                        Log.i(TAG, "count "+count);
                    if(count<=time){
                    ShellUtils.execCommand("echo 0 > /sys/class/backlight/rk28_bl/blue", false);
                    ShellUtils.execCommand("echo 0 > /sys/class/backlight/rk28_bl/red", false);
                    Thread.sleep(1);//保持暗time
                    }else if(time<count&&count<(2*time)){
                        if(count % 2== 0){
                            ShellUtils.execCommand("echo 1 > /sys/class/backlight/rk28_bl/blue", false);
                        }else {
                            ShellUtils.execCommand("echo 0 > /sys/class/backlight/rk28_bl/blue", false);
                        }
                        Thread.sleep(1);//保持暗time
                    }else if(count>=2*time){
                        count=1;
                    }
                    }catch (Exception e){

                    }


java.util.Timer timer = new java.util.Timer(true);
TimerTask task = new TimerTask() {
   public void run() {
   //每次需要执行的代码放到这里面。
   }
};
//以下是几种调度task的方法：
//time为Date类型：在指定时间执行一次。
timer.schedule(task, time);

//firstTime为Date类型,period为long，表示从firstTime时刻开始，每隔period毫秒执行一次。
timer.schedule(task, firstTime, period);

//delay 为long类型：从现在起过delay毫秒执行一次。
timer.schedule(task, delay);

//delay为long,period为long：从现在起过delay毫秒以后，每隔period毫秒执行一次。
timer.schedule(task, delay, period);
* */
