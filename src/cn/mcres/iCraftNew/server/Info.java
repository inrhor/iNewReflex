package cn.mcres.iCraftNew.server;

import java.util.Arrays;
import java.util.List;

public class Info {
    private String serverVersion, serverName;
    private List<String> oldVersion = Arrays.asList("v1_9_R1", "v1_9_R2", "v1_10_R1", "v1_11_R1", "v1_12_R1", "v1_13_R1", "v1_13_R2");

    public Info(String ServerVersion, String serverName) {
        this.serverVersion = ServerVersion;
        this.serverName = serverName;
    }

    public String getServerVersion() {
        return this.serverVersion;
    }

    public String getServerName() {
        return serverName;
    }

    public boolean isOldVersion() {
        for (String s : this.oldVersion) {
            if (this.serverVersion.equals(s)) {
                return true;
            }
        }
        return false;
    }
}
