package cn.mcres.iNewReflex.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathTool {
    /**
     * @param string
     * @return 是否为非零正整数数字
     */
    public static boolean isIntNumber(String string) {
        if (!string.isEmpty()) {
            if (!string.equals("0")) {
                Pattern pattern = Pattern.compile("^\\+?[1-9][0-9]*$");
                Matcher matcher = pattern.matcher(string);
                return matcher.matches();
            }
        }
        return false;
    }

    /**
     * @param text
     * @param target
     * @return 获取指定字符的前面的字符串
     */
    public static String getTargetBefore(String text, String target) {
        int index = text.indexOf(target);
        return text.substring(0, index);
    }

    /**
     * @param text
     * @param target
     * @return 获取指定字符的后面的字符串
     */
    public static String getTargetAfter(String text, String target) {
        int index = text.indexOf(target);
        return text.substring(index + 1);
    }

    /**
     *
     * @param text
     * @param target
     * @return 两数之间的随机数
     */
    public static double getRandomDouble(String text, String target) {
        if (text.contains(target)) {
            double min = Double.parseDouble(getTargetBefore(text, target));
            double max = Double.parseDouble(getTargetAfter(text, target));
            int scl =  2;
            int pow = (int) Math.pow(10, scl);
            return Math.floor((Math.random() * (max - min) + min) * pow) / pow;
        }
        return 0;
    }

    /**
     * @param min
     * @param max
     * @return 两数之间的随机数，保留两位小数点
     */
    public static double getRandomDouble(double min, double max) {
        int scl =  2;
        int pow = (int) Math.pow(10, scl);
        return Math.floor((Math.random() * (max - min) + min) * pow) / pow;
    }

    /**
     * @param db
     * @return 四舍五入 double转int
     */
    public static int rounding(double db) {
        return Integer.parseInt(new java.text.DecimalFormat("0").format(db));
    }
}
