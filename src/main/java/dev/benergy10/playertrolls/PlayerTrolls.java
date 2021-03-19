package dev.benergy10.playertrolls;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import dev.benergy10.minecrafttools.MinecraftPlugin;
import dev.benergy10.minecrafttools.utils.Logging;
import dev.benergy10.playertrolls.trolls.CrazySwingHands;
import dev.benergy10.playertrolls.trolls.FireTrail;
import dev.benergy10.playertrolls.trolls.ForbidChest;
import dev.benergy10.playertrolls.trolls.Freeze;
import dev.benergy10.playertrolls.trolls.InvisibleEnemy;
import dev.benergy10.playertrolls.trolls.LightingStrike;
import dev.benergy10.playertrolls.utils.CommandTools;

public final class PlayerTrolls extends MinecraftPlugin {

    private TrollManager trollManager;
    private ProtocolManager protocolManager;

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

    public ProtocolManager getProtocolManager() {
        if (this.protocolManager == null) {
            this.protocolManager = ProtocolLibrary.getProtocolManager();
        }
        return protocolManager;
    }
}
