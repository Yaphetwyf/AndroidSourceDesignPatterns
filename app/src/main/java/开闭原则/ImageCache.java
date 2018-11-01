package 开闭原则;

import android.graphics.Bitmap;
import android.util.LruCache;

public class ImageCache {
    LruCache<String, Bitmap> bitmapLruCache;
    public ImageCache() {
        initIamgeCache();
    }
    private void initIamgeCache() {
        //计算可使用的最大内存
        final int MaxSize = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = MaxSize / 4;
        bitmapLruCache = new LruCache<String, Bitmap>(cacheSize) {

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }
    //共有方法添加缓存
    public void put(String url,Bitmap bitmap){
        bitmapLruCache.put(url,bitmap);
    }
    public Bitmap get(String url){
       return bitmapLruCache.get(url);
    }
}
