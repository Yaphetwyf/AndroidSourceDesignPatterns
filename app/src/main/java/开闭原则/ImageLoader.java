package 开闭原则;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import 单一职责原则.ImageCache;
public class ImageLoader {
    private ImageCache imageCache;//内存缓存
    private DiskCache diskCache;//sd卡缓存
    private DoubleCache doubleCache;
    private boolean isUseSDcache;
    private boolean isUseDoubleCache;
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
    public ImageLoader() {
        imageCache = new ImageCache();
        diskCache = new DiskCache();
        doubleCache=new DoubleCache();
    }
    public void setUseSDcache(boolean useSDcache){
        this.isUseSDcache=useSDcache;
    }
    public void setUseDoubleCache(boolean useDoublecache){
        this.isUseDoubleCache=useDoublecache;
    }
    public void disPlayImage(final String url, final ImageView imageView) {
        //判断是那种缓存
       /* Bitmap bitmap=isUseSDcache ? imageCache.get(url):diskCache.get(url);//三目运算符代替if、else
        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);
            return;//结束这个方法
        }*/
       Bitmap bitmap=null;
       if (isUseDoubleCache){
           bitmap=doubleCache.get(url);
       }else if (isUseSDcache){
           bitmap=diskCache.get(url);
       }else{
           bitmap=imageCache.get(url);
       }
        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);
            return;//结束这个方法
        }
        //下载
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
                imageCache.put(url, bitmap);
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
