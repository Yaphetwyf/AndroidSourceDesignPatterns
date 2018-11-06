package 单例模式.静态内部类模式;

public class Singleton {
    private Singleton() {
    }

    public static Singleton getInstance() {
        return SingLetonHolder.singleton;
    }
    private static class SingLetonHolder{
        private static final Singleton singleton=new Singleton();
    }
}
