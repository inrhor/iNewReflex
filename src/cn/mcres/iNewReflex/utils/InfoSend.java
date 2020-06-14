package cn.mcres.iNewReflex.utils;

import com.google.common.base.Strings;

public class InfoSend {
    /**
     * 百分比状态
     * @param current
     * @param max
     * @param totalBars
     * @param symbol
     * @param completedColor
     * @param notCompletedColor
     * @return
     */
    public static String getProgressBar(int current, int max, int totalBars, String  symbol, String  completedColor,
                                        String  notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }
}
