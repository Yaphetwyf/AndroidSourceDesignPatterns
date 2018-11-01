package 开闭原则;

import android.graphics.Bitmap;

public class DoubleCache {
    ImageCache imageCache = new ImageCache();
    DiskCache diskCache = new DiskCache();

    //添加缓存策略，首先在内存中获取图片，如果内存中不存在的话从SD卡中获取，如果都不存在再从网络中获取
    public Bitmap get(String url) {
        Bitmap bitmap = null;
        bitmap = imageCache.get(url);
        if (bitmap == null) {
            bitmap = diskCache.get(url);
        }
        return bitmap;
    }
    //图片获取之后，分别向内存和SD卡中分别缓存一份
    public void put(String url,Bitmap bitmap){
        imageCache.put(url,bitmap);
        diskCache.put(url,bitmap);
    }
}
