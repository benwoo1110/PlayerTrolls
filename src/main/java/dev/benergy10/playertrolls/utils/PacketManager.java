package dev.benergy10.playertrolls.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;


public class PacketManager {

    private final ProtocolManager manager;

    public PacketManager(@NotNull ProtocolManager manager) {
        this.manager = manager;
    }

    public @NotNull PacketContainer createAnimationPacket(@NotNull Entity entity, int type) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ANIMATION);
        packet.getIntegers().write(0, entity.getEntityId());
        packet.getIntegers().write(1, type);
        return packet;
    }

    public boolean sendPacket(@NotNull Player player, @NotNull PacketContainer packet) {
        try {
            this.manager.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public @NotNull ProtocolManager getManager() {
        return manager;
    }
}
