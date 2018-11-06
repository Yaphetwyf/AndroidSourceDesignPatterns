package 单例模式.懒汉模式;

public class Singleton {
    private static Singleton singleton = null;

    private Singleton() {
    }
    //比较懒，在类加载时，不创建实例，因此类加载速度快，但运行时获取对象的速度慢
    //缺点是：第一次加载时需要实例化，反应稍慢，最大的问题是每次调用getSingleton方法时都会进行同步，造成不必要的同步开销
    public static synchronized Singleton getSingleton() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }
}
