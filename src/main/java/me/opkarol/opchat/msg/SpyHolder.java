package me.opkarol.opchat.msg;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SpyHolder{
    private static final Set<UUID> activeSpy = new HashSet<>();

    public static void setActiveSpy(UUID uuid) {
        activeSpy.add(uuid);
    }

    public static void removeActiveSpy(UUID uuid) {
        activeSpy.remove(uuid);
    }

    public static boolean isActiveSpy(UUID uuid) {
        return activeSpy.contains(uuid);
    }

    public static Set<UUID> getActiveSpyList() {
        return new HashSet<>(activeSpy);
    }
}
