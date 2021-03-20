package dev.benergy10.playertrolls.utils;

public class Alternator<T> {

    public static <T> Alternator<T> of(T thisT, T thatT) {
        return new Alternator<>(thisT, thatT);
    }

    private T thisT;
    private T thatT;
    boolean state = false;

    public Alternator(T thisT, T thatT) {
        this.thisT = thisT;
        this.thatT = thatT;
    }

    public T get() {
        return (state ^= true) ? thisT : thatT;
    }

    public T getThis() {
        return thisT;
    }

    public void setThis(T thisT) {
        this.thisT = thisT;
    }

    public T getThat() {
        return thatT;
    }

    public void setThat(T thatT) {
        this.thatT = thatT;
    }
}
