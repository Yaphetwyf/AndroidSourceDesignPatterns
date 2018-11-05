package 接口隔离原则;

import java.io.Closeable;
import java.io.IOException;
/**
 * final修饰的类无法被任何人继承的，子叶类
 * 关闭io接口类
 * //建立在最小抽象，只需要这个对象可关闭即可
 */
public final class CloseUtils {
    private CloseUtils() {
    }
    public static void closeQuiteiy(Closeable closeable){
        if (closeable!=null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
