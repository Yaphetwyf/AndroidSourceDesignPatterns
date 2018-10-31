package 单一职责原则;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.util.LruCache;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {
    LruCache<String, Bitmap> bitmapLruCache;
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
    public ImageLoader() {
        initIamgeCache();
    }
    //初始化ImageCache
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

    public void disPlayImage(final String url, final ImageView imageView) {
        imageView.setTag(url);//使其一一对应
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downLoadImage(url);
                if (bitmap == null) {
                    return;
                }
                if (imageView.getTag().equals(url)) {
                    updateImageView(imageView, bitmap);
                }
                bitmapLruCache.put(url,bitmap);
            }
        });
    }
    //在主线程中显示图片
    private void updateImageView(final ImageView imageView, final Bitmap bitmap) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }
    //图片下载
    private Bitmap downLoadImage(String url) {
        Bitmap bitmap=null;
        try {
            URL realURL=new URL(url);
            HttpURLConnection urlConnection =(HttpURLConnection)realURL.openConnection();
            bitmap= BitmapFactory.decodeStream(urlConnection.getInputStream());
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
