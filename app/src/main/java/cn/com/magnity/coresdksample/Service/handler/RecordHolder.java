package cn.com.magnity.coresdksample.Service.handler;

import android.graphics.Bitmap;

import org.apache.ftpserver.command.impl.PROT;

public class RecordHolder {
    private Bitmap bitmap;
    private String temp;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "RecordHolder{" +
                "bitmap=" + bitmap +
                ", temp=" + temp +
                '}';
    }
}
