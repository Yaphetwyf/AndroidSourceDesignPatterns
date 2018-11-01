package 开闭原则.开闭原则实现;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import 单一职责原则.ImageCache;
import 开闭原则.DiskCache;
import 开闭原则.DoubleCache;

public class ImageLoader {
    private 开闭原则.开闭原则实现.ImageCache imageCache = new MemoryCache();//默认为内存缓存
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
    //依赖注入
    public void setImageCache(开闭原则.开闭原则实现.ImageCache cache) {
        imageCache=cache;
    }

    public void disPlayImage(final String url, final ImageView imageView) {
        Bitmap bitmap=null;
        bitmap=imageCache.get(url);
        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);
            return;
        }
        //没有缓存，则下载
        submitLoadRequest(url, imageView);
    }

    //下载图片
    private void submitLoadRequest(final String url, final ImageView imageView) {
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
                imageCache.put(url,bitmap);
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
        Bitmap bitmap = null;
        try {
            URL realURL = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) realURL.openConnection();
            bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
