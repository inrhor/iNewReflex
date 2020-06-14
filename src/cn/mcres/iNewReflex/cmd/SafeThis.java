package cn.mcres.iNewReflex.cmd;

public class SafeThis {
    // 安全地获取数组的值
    public static String get(String[] array, int index, String def) {
        if (array != null) {
            if (index > -1) {
                if (index < array.length) {
                    return array[index];
                }
            }
        }
        return def;
    }
}

