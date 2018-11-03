package 里氏替换原则;

public abstract class View {
    public abstract void draw();
    public void measure(int width,int hight){
        //测量视图的宽高
    }
}
