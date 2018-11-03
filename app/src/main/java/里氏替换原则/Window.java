package 里氏替换原则;
public class Window {
    public void show(View child){//依赖于抽象而不依赖于具体，在父类出现的地方，可以替换成子类
        child.draw();
    }
}
