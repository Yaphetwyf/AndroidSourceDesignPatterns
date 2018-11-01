package 开闭原则.开闭原则实现;

import android.graphics.Bitmap;

import 开闭原则.DiskCache;

/**
 * 双重缓存机制
 */
public class DoubleCache implements ImageCache {
    开闭原则.ImageCache imageCache = new 开闭原则.ImageCache();
    开闭原则.DiskCache diskCache = new DiskCache();

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = null;
        bitmap = imageCache.get(url);
        if (bitmap == null) {
            bitmap = diskCache.get(url);
        }
        return bitmap;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        imageCache.put(url,bitmap);
        diskCache.put(url,bitmap);
    }
}
