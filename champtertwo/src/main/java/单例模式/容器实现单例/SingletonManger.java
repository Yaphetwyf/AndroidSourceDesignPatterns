package 单例模式.容器实现单例;

import java.util.HashMap;
import java.util.Map;

/**
 * 隐藏了具体实现，降低了耦合度
 */
public class SingletonManger {
    private static Map<String,Object>Maps=new HashMap<>();
    private SingletonManger() {
    }
    public static void registerService(String key,Object instance){
        if (!Maps.containsKey(key)){
            Maps.put(key,instance);
        }
    }

    public static Object getMaps(String key) {
        return Maps.get(key);
    }
}
