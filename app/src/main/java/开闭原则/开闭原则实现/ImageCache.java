package 开闭原则.开闭原则实现;

import android.graphics.Bitmap;

public interface ImageCache {
    public Bitmap get(String url);
    public void put(String url,Bitmap bitmap);
}
