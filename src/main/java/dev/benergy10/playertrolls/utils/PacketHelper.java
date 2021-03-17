package dev.benergy10.playertrolls.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Entity;


public class PacketHelper {

    public static PacketContainer createAnimationPacket(Entity entity, int type) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.ANIMATION);
        packet.getIntegers().write(0, entity.getEntityId());
        packet.getIntegers().write(1, type);
        return packet;
    }
}
