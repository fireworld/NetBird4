package cc.colorcat.netbird4;

import java.util.Objects;

/**
 * Created by cxx on 18-1-24.
 * xx.ch@outlook.com
 */
public final class NameAndValue {
    public final String name;
    public final String value;

    public NameAndValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NameAndValue that = (NameAndValue) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return "NameAndValue{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
