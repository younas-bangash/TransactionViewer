package com.example.transaction.model;
import java.io.Serializable;
import java.nio.ByteBuffer;
public final class Pair implements Comparable<Pair>, Serializable {
    public String oldCurrency;
    public double priceGbp;
    public String name;

    @Override
    public int compareTo(Pair another) {
        return ByteBuffer.wrap(oldCurrency.getBytes()).getInt() -
                ByteBuffer.wrap(another.oldCurrency.getBytes()).getInt();
    }

    @Override
    public boolean equals(Object o) {
        return ((Pair) o).name == this.name;
    }

    @Override
    public int hashCode() {
        return ByteBuffer.wrap(name.getBytes()).getInt();
    }

}
