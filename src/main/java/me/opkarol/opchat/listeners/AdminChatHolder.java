package me.opkarol.opchat.listeners;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AdminChatHolder {

    private static final Set<UUID> activeAC = new HashSet<>();

    public static void setActiveAC(UUID uuid) {
        activeAC.add(uuid);
    }

    public static void removeActiveAC(UUID uuid) {
        activeAC.remove(uuid);
    }

    public static boolean isActiveAC(UUID uuid) {
        return activeAC.contains(uuid);
    }

    public static Set<UUID> getActiveACList() {
        return new HashSet<>(activeAC);
    }
}
