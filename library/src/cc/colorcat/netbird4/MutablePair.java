//package cc.colorcat.netbird4;
//
//import java.util.*;
//
///**
// * Created by cxx on 18-1-24.
// * xx.ch@outlook.com
// */
//class MutablePair extends Pair implements PairWriter {
//    MutablePair(List<String> names, List<String> values, Comparator<String> comparator) {
//        super(names, values, comparator);
//    }
//
//    @Override
//    public final Iterator<NameAndValue> iterator() {
//        return new MutablePairIterator();
//    }
//
//    public final Pair toPair() {
//        return new Pair(new ArrayList<>(names), new ArrayList<>(values), comparator);
//    }
//
//
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        MutablePair pair = (MutablePair) o;
//        return Objects.equals(names, pair.names) &&
//                Objects.equals(values, pair.values) &&
//                Objects.equals(comparator, pair.comparator);
//    }
//
//    @Override
//    public int hashCode() {
//        return 17 * Objects.hash(names, values, comparator);
//    }
//}
