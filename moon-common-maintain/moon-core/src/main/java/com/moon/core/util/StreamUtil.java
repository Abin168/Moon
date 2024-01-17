package com.moon.core.util;

import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream util
 */
public class StreamUtil {

    /**
     * list group by key
     *
     * @param data
     * @param keyMapper
     * @param <K>
     * @param <T>
     * @return
     */
    public static <K, T> Map<K, List<T>> group(Collection<T> data, Function<T, K> keyMapper) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>(0);
        }
        return data.stream().collect(Collectors.groupingBy(keyMapper));
    }

    /**
     * list group by key and value
     *
     * @param data
     * @param keyMapper
     * @param valueMapper
     * @param <K>
     * @param <T>
     * @param <U>
     * @return
     */
    public static <K, T, U> Map<K, List<U>> group(Collection<T> data, Function<T, K> keyMapper, Function<T, U> valueMapper) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>(0);
        }
        return data.stream().collect(Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList())));
    }

    /**
     * list group then count
     *
     * @param data
     * @param keyMapper
     * @param <K>
     * @param <T>
     * @return
     */
    public static <K, T> Map<K, Long> groupCount(List<T> data, Function<T, K> keyMapper) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>(0);
        }
        return data.stream().collect(Collectors.groupingBy(keyMapper, Collectors.counting()));
    }

    /**
     * group
     *
     * @param data        data
     * @param keyMapper   keyMapper
     * @param valueMapper valueMapper
     * @param <K>         K
     * @param <T>         T
     * @param <U>         U
     * @return r
     */
    public static <K, T, U> Map<K, List<U>> group(List<T> data, Function<T, K> keyMapper, Function<T, U> valueMapper) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>(0);
        }
        return data.stream().collect(Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList())));
    }

    public static <K, T> Map<K, Long> groupLongSum(List<T> data, Function<T, K> keyMapper, ToLongFunction<T> valueMapper) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>(0);
        }
        return data.stream().collect(Collectors.groupingBy(keyMapper, Collectors.summingLong(valueMapper)));
    }

    public static <K, T> Map<K, Double> groupDoubleSum(List<T> data, Function<T, K> keyMapper, ToDoubleFunction<T> valueMapper) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>(0);
        }
        return data.stream().collect(Collectors.groupingBy(keyMapper, Collectors.summingDouble(valueMapper)));
    }

    public static class IndexItem<T> {
        @Getter
        private Integer index;
        @Getter
        private T item;
        private Iterator<T> iterator;
        public void remove() {
            iterator.remove();
        }
    }

    /**
     * for each with condition
     *
     * @param list
     * @param filter
     * @param breaker
     * @param consumer
     * @param <T>
     */
    public static <T> void foreach(Collection<T> list,
                                   Function<IndexItem<T>, Boolean> filter,
                                   Function<IndexItem<T>, Boolean> breaker,
                                   Consumer<IndexItem<T>> consumer) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        int i = 0;
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            IndexItem<T> pair = new IndexItem<>();
            pair.index = i;
            pair.item = iterator.next();
            pair.iterator = iterator;
            if (Objects.nonNull(filter) && !filter.apply(pair)) {
                continue;
            }
            if (Objects.nonNull(breaker) && breaker.apply(pair)) {
                return;
            }
            consumer.accept(pair);
            i++;
        }
    }

    /**
     * random index
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Pair<Integer, T> randomIndex(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        Collections.shuffle(list);
        int index = RandomUtils.nextInt(0, list.size());
        return Pair.of(index, list.get(index));
    }

    public static <T> T random(Collection<T> list) {
        return StreamUtil.map(randomIndex(new ArrayList<>(list)), Pair::getValue);
    }

    public static <T, U> U map(T t, Function<T, U> mapFunction) {
        if (Objects.isNull(t)) {
            return null;
        }
        return mapFunction.apply(t);
    }

    /**
     * covert t
     *
     * @param t
     * @param mapFunction
     * @param defaultValue
     * @param <T>
     * @param <U>
     * @return
     */
    public static <T, U> U map(T t, Function<T, U> mapFunction, U defaultValue) {
        if (Objects.isNull(t)) {
            return defaultValue;
        }
        U value = mapFunction.apply(t);
        return Objects.isNull(value) ? defaultValue : value;
    }


    public static <T> Long sumLong(List<T> data, Function<T, Long> valueMapper) {
        return sumLong(data, Objects::nonNull, valueMapper);
    }

    public static <T> Double sumDouble(List<T> data, Function<T, Double> valueMapper) {
        if (CollectionUtils.isEmpty(data)) {
            return 0.0;
        }
        return data.stream().mapToDouble(o -> {
            Double result = valueMapper.apply(o);
            return Objects.nonNull(result) ? result : 0L;
        }).sum();
    }

    public static <T> Long sumLong(List<T> data, Predicate<T> predicate, Function<T, Long> valueMapper) {
        if (CollectionUtils.isEmpty(data)) {
            return 0L;
        }
        return sumLong(data.stream().filter(Objects::nonNull).filter(predicate), valueMapper);
    }

    public static <T> Integer sumInt(List<T> data, Function<T, Integer> valueMapper) {
        if (CollectionUtils.isEmpty(data)) {
            return 0;
        }
        return sumInt(data.stream(), valueMapper);
    }

    public static <T> Long sumLong(Stream<T> stream, Function<T, Long> valueMapper) {
        return stream.mapToLong(o -> {
            Long result = valueMapper.apply(o);
            return Objects.nonNull(result) ? result : 0L;
        }).sum();
    }

    public static <T> Integer sumInt(Stream<T> stream, Function<T, Integer> valueMapper) {
        return stream.mapToInt(o -> {
            Integer result = valueMapper.apply(o);
            return Objects.nonNull(result) ? result : 0;
        }).sum();
    }

    /**
     * list to distinct list
     *
     * @param list
     * @param keyMapper
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> List<K> toDistinctList(Collection<T> list, Function<T, K> keyMapper) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>(0);
        }
        return list.stream().map(keyMapper).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    /**
     * list to set
     *
     * @param list
     * @param keyMapper
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> Set<K> toSet(Collection<T> list, Function<T, K> keyMapper) {
        if (CollectionUtils.isEmpty(list)) {
            return new HashSet<>(0);
        }
        return list.stream().map(keyMapper).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * list get
     *
     * @param list
     * @param index
     * @param <T>
     * @return
     */
    public static <T> T get(List<T> list, int index) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        if (index >= list.size()) {
            return null;
        }
        return list.get(index);
    }

    /**
     * list get or default
     *
     * @param list
     * @param index
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T getOrDefault(List<T> list, int index, T defaultValue) {
        T value = get(list, index);
        return Objects.isNull(value) ? defaultValue : value;
    }

    /**
     * to list
     *
     * @param list
     * @param keyMapper
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> List<K> toList(Collection<T> list, Function<T, K> keyMapper) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>(0);
        }
        return list.stream().map(keyMapper).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * filter list
     *
     * @param data
     * @param predicate
     * @param valueMapper
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> List<K> filterList(Collection<T> data, Predicate<T> predicate, Function<T, K> valueMapper) {
        if (CollectionUtils.isEmpty(data)) {
            return new ArrayList<>(0);
        }
        return data.stream().filter(predicate).filter(Objects::nonNull).map(valueMapper).collect(Collectors.toList());
    }

    public static <T> List<T> filterList(Collection<T> data, Predicate<T> predicate) {
        if (CollectionUtils.isEmpty(data)) {
            return new ArrayList<>(0);
        }
        return data.stream().filter(predicate).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static <T> List<T> filter(Collection<T> data, Predicate<T> predicate) {
        if (CollectionUtils.isEmpty(data)) {
            return new ArrayList<>(0);
        }
        return data.stream().filter(predicate).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * list to map
     *
     * @param list
     * @param keyMapper
     * @param <K>
     * @param <T>
     * @return
     */
    public static <K, T> Map<K, T> toMap(Collection<T> list, Function<T, K> keyMapper) {
        return toMap(list, keyMapper, Function.identity(), false);
    }

    public static <K, T, U> Map<K, U> toMap(Collection<T> list, Function<T, K> keyMapper, Function<T, U> valueMapper) {
        return toMap(list, keyMapper, valueMapper, false);
    }

    public static <K, T, U> Map<K, U> toMap(Collection<T> list, Function<T, K> keyMapper, Function<T, U> valueMapper, boolean overrideKey) {
        if (CollectionUtils.isEmpty(list)) {
            return new HashMap<>(0);
        }
        return list.stream().filter(o -> o != null && valueMapper.apply(o) != null)
                .collect(Collectors.toMap(keyMapper, valueMapper, (k1, k2) -> overrideKey ? k2 : k1));
    }


    /**
     * list exec Supplier
     *
     * @param list
     * @param emptySupplier
     * @param notEmptySupplier
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R execSupplier(List<T> list, Supplier<R> emptySupplier, Supplier<R> notEmptySupplier) {
        if (CollectionUtils.isEmpty(list)) {
            return Objects.nonNull(emptySupplier) ? emptySupplier.get() : null;
        } else {
            return Objects.nonNull(notEmptySupplier) ? notEmptySupplier.get() : null;
        }
    }

    /**
     * list exec Function
     *
     * @param list
     * @param emptyFunction
     * @param notEmptyFunction
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R execFunction(List<T> list,
                                        Function<List<T>, R> emptyFunction,
                                        Function<List<T>, R> notEmptyFunction) {
        if (CollectionUtils.isEmpty(list)) {
            return Objects.nonNull(emptyFunction) ? emptyFunction.apply(list) : null;
        } else {
            return Objects.nonNull(notEmptyFunction) ? notEmptyFunction.apply(list) : null;
        }
    }

    /**
     * list exec Consumer
     *
     * @param list
     * @param emptyConsumer
     * @param notEmptyConsumer
     * @param <T>
     */
    public static <T> void execConsumer(List<T> list, Consumer<List<T>> emptyConsumer, Consumer<List<T>> notEmptyConsumer) {
        if (CollectionUtils.isEmpty(list)) {
            if (Objects.nonNull(emptyConsumer)) {
                emptyConsumer.accept(list);
            }
        } else {
            if (Objects.nonNull(notEmptyConsumer)) {
                notEmptyConsumer.accept(list);
            }
        }
    }

    /**
     * Collection is empty
     *
     * @param collection
     * @param <T>
     * @return
     */
    public static <T> boolean empty(Collection<T> collection) {
        return size(collection) == 0;
    }

    /**
     * Map is empty
     *
     * @param map
     * @param <T>
     * @return
     */
    public static <T> boolean empty(Map<String, T> map) {
        return size(map) == 0;
    }

    /**
     * collection size
     *
     * @param collection
     * @param <T>
     * @return
     */
    public static <T> int size(Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return 0;
        }
        return collection.size();
    }


    /**
     * map size
     *
     * @param map
     * @param <T>
     * @return
     */
    public static <T> int size(Map<String, T> map) {
        if (MapUtils.isEmpty(map)) {
            return 0;
        }
        return map.size();
    }

    /**
     * Get first item
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T first(Collection<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.iterator().next();
    }

    /**
     * Get last item
     *
     * @param list
     * @param <T>
     * @return
     */

    public static <T> T last(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(list.size() - 1);
    }
}
