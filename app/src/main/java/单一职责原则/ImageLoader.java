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
    private ImageCache imageCache;
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());

    public ImageLoader() {
        imageCache=new ImageCache();
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
