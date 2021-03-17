package dev.benergy10.playertrolls.data;

import java.util.HashMap;
import java.util.Map;

public class DataContainer {

    private final Map<DataKey<?>, Object> dataStorage;

    public DataContainer() {
        this.dataStorage = new HashMap<>();
    }

    public <T> DataContainer set(DataKey<T> key, T value) {
        this.dataStorage.put(key, value);
        return this;
    }

    public <T> T get(DataKey<T> key) {
        return (T) this.dataStorage.get(key);
    }
}
