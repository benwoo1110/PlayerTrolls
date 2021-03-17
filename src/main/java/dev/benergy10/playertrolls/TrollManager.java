package dev.benergy10.playertrolls;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TrollManager {

    private final Map<String, Troll> trollMap;

    public TrollManager() {
        this.trollMap = new HashMap<>();
    }

    public void register(Troll troll) {
        String trollName = troll.getName().toLowerCase();
        if (this.trollMap.containsKey(trollName)) {
            throw new IllegalArgumentException("Duplicate troll name: " + trollName);
        }
        this.trollMap.put(trollName, troll);
    }

    public Troll getTroll(String name) {
        return this.trollMap.get(name.toLowerCase());
    }

    public Collection<Troll> getTrolls() {
        return this.trollMap.values();
    }

    public Collection<String> getTrollNames() {
        return this.trollMap.keySet();
    }
}
