package org.ta4j.core.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MapUtils {

    public static <K, V, C extends Collection<V>, M extends Map<K, C>> M getMap(List<V> list,
                                                                                Function<? super V, ? extends K> classifier, Supplier<M> mapSupplier, Supplier<C> collectionSupplier) {
        return list.stream().collect(
                Collectors.groupingBy(classifier, mapSupplier, Collectors.toCollection(collectionSupplier))
        );
    }
}
