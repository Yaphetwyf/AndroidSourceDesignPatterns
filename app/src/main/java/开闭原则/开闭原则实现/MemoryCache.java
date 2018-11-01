package 开闭原则.开闭原则实现;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 内存缓存
 */
public class MemoryCache implements ImageCache {
    LruCache<String, Bitmap> bitmapLruCache;

    public MemoryCache() {
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
    @Override
    public Bitmap get(String url) {
        return bitmapLruCache.get(url);
    }
    @Override
    public void put(String url, Bitmap bitmap) {
        bitmapLruCache.put(url,bitmap);
    }
}
