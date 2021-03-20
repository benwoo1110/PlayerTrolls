package dev.benergy10.playertrolls;

import com.comphenix.protocol.ProtocolLibrary;
import dev.benergy10.minecrafttools.MinecraftPlugin;
import dev.benergy10.minecrafttools.utils.Logging;
import dev.benergy10.minecrafttools.utils.ReflectHelper;
import dev.benergy10.playertrolls.trolls.CrazySwingHands;
import dev.benergy10.playertrolls.trolls.FireTrail;
import dev.benergy10.playertrolls.trolls.ForbidChest;
import dev.benergy10.playertrolls.trolls.Freeze;
import dev.benergy10.playertrolls.trolls.InvisibleEnemy;
import dev.benergy10.playertrolls.trolls.LightingStrike;
import dev.benergy10.playertrolls.utils.CommandTools;
import dev.benergy10.playertrolls.utils.PacketManager;
import org.jetbrains.annotations.Nullable;

public final class PlayerTrolls extends MinecraftPlugin {

    private TrollManager trollManager;
    private PacketManager packetManager;

    @Override
    public void enable() {
        Logging.doDebugLog(true);

        this.trollManager = new TrollManager(this)
                .register(new Freeze(this))
                .register(new LightingStrike(this))
                .register(new FireTrail(this))
                .register(new CrazySwingHands(this))
                .register(new InvisibleEnemy(this))
                .register(new ForbidChest(this));

        CommandTools.setUp(this);
    }

    public TrollManager getTrollManager() {
        return this.trollManager;
    }

    public @Nullable PacketManager getPacketManager() {
        if (this.packetManager == null) {
            if (!ReflectHelper.hasClass("com.comphenix.protocol.ProtocolLibrary")) {
                Logging.warning("ProtocolLib not detected. Trolls that require this will not work!");
                return null;
            }
            this.packetManager = PacketManager.of(ProtocolLibrary.getProtocolManager());
        }
        return packetManager;
    }

    public boolean hasPacketManager() {
        return this.getPacketManager() != null;
    }
}
