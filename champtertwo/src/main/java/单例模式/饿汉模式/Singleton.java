package 单例模式.饿汉模式;

public class Singleton {
    private static  final Singleton singletonInstance=new Singleton();
    private Singleton() {
    }
    //在类加载时就完成了初始化，所以类加载较慢，但获取对象的速度快
    public static Singleton getSingletonInstance(){
        return singletonInstance;
    }
}
