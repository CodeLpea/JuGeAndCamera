package cn.com.magnity.coresdksample.ddnwebserver.model;

import com.alibaba.fastjson.annotation.JSONField;

import static cn.com.magnity.coresdksample.ddnwebserver.WebConfig.CAMERA_EXPLORE;

/**
 * 摄像头信息
 * 曝光值
 * */
public class CameraData {
    @JSONField(name = CAMERA_EXPLORE)
    private int explorer;

    public int getExplorer() {
        return explorer;
    }

    public void setExplorer(int explorer) {
        this.explorer = explorer;
    }

    @Override
    public String toString() {
        return "CameraData{" +
                "explorer='" + explorer + '\'' +
                '}';
    }
}
