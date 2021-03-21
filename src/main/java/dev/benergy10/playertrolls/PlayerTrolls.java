package dev.benergy10.playertrolls;

import com.comphenix.protocol.ProtocolLibrary;
import dev.benergy10.minecrafttools.MinecraftPlugin;
import dev.benergy10.minecrafttools.configs.CommentedYamlFile;
import dev.benergy10.minecrafttools.configs.YamlFile;
import dev.benergy10.minecrafttools.utils.Logging;
import dev.benergy10.minecrafttools.utils.ReflectHelper;
import dev.benergy10.playertrolls.trolls.CrazySwingHands;
import dev.benergy10.playertrolls.trolls.CreepyCreeper;
import dev.benergy10.playertrolls.trolls.FireTrail;
import dev.benergy10.playertrolls.trolls.ForbidChest;
import dev.benergy10.playertrolls.trolls.Freeze;
import dev.benergy10.playertrolls.trolls.InvisibleEnemy;
import dev.benergy10.playertrolls.trolls.LightingStrike;
import dev.benergy10.playertrolls.trolls.Transfiguration;
import dev.benergy10.playertrolls.utils.CommandTools;
import dev.benergy10.playertrolls.utils.PacketManager;
import dev.benergy10.playertrolls.utils.TrollOptions;
import me.libraryaddict.disguise.LibsDisguises;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PlayerTrolls extends MinecraftPlugin {

    private TrollManager trollManager;
    private PacketManager packetManager;
    private YamlFile config;

    @Override
    public void enable() {
        Logging.doDebugLog(true);

        this.config = new CommentedYamlFile(this.getConfigFile(), TrollOptions.getOptions(), TrollOptions.header());

        this.trollManager = new TrollManager(this)
                .register(new Freeze(this))
                .register(new LightingStrike(this))
                .register(new FireTrail(this))
                .register(new CrazySwingHands(this))
                .register(new InvisibleEnemy(this))
                .register(new ForbidChest(this))
                .register(new CreepyCreeper(this))
                .register(new Transfiguration(this));

        CommandTools.setUp(this);
    }

    public TrollManager getTrollManager() {
        return this.trollManager;
    }

    public @NotNull PacketManager getPacketManager() {
        if (this.packetManager == null) {
            this.packetManager = new PacketManager(ProtocolLibrary.getProtocolManager());
        }
        return packetManager;
    }

    @NotNull
    public YamlFile getTrollConfig() {
        return config;
    }
}
