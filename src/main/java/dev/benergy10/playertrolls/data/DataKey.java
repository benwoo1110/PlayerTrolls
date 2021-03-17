package dev.benergy10.playertrolls.data;

import java.util.Objects;

public class DataKey<T> {

    public static <T> DataKey<T> create(Class<T> dataType) {
        return new DataKey<>(dataType, dataType.getName());
    }

    public static <T> DataKey<T> create(Class<T> dataType, String id) {
        return new DataKey<>(dataType, id);
    }

    private final Class<T> dataType;
    private final String id;

    public DataKey(Class<T> dataType, String id) {
        this.dataType = dataType;
        this.id = id;
    }

    public Class<T> getDataType() {
        return dataType;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataKey<?> dataKey = (DataKey<?>) o;
        return Objects.equals(dataType, dataKey.dataType) && Objects.equals(id, dataKey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataType, id);
    }
}
