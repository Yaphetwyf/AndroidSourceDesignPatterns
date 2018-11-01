package 开闭原则.开闭原则实现;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * SD卡缓存
 */
public class DiskCache implements ImageCache {
    static final String cacheDir="sdcard/cache/";
    @Override
    public Bitmap get(String url) {
        return BitmapFactory.decodeFile(cacheDir+url);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=new FileOutputStream(cacheDir+url);//按f2提醒参数
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
