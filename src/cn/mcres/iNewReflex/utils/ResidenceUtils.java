package cn.mcres.iNewReflex.utils;

import com.bekvon.bukkit.residence.api.ResidenceApi;
import org.bukkit.Location;

public class ResidenceUtils {
    public static boolean isInResidence(Location location) {
        return ResidenceApi.getResidenceManager().getByLoc(location) != null;
    }
}
