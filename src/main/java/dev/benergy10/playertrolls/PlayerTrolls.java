package dev.benergy10.playertrolls;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import dev.benergy10.minecrafttools.MinecraftPlugin;
import dev.benergy10.minecrafttools.utils.Logging;
import dev.benergy10.playertrolls.trolls.CrazySwingHands;
import dev.benergy10.playertrolls.trolls.FireTrail;
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
        this.protocolManager = ProtocolLibrary.getProtocolManager();

        this.trollManager = new TrollManager(this);
        this.trollManager.register(new Freeze(this));
        this.trollManager.register(new LightingStrike(this));
        this.trollManager.register(new FireTrail(this));
        this.trollManager.register(new CrazySwingHands(this));
        this.trollManager.register(new InvisibleEnemy(this));

        CommandTools.setUp(this);
    }

    public TrollManager getTrollManager() {
        return trollManager;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
