import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;


public class Operator<T> {
    private final Collection<T> storage;
    //private final Class<? extends Collection> type;

    private Operator(Collection<T> collection) {
        if (collection==null)
          throw new NullPointerException("Collection is null!");
        storage = collection;
        //type = collection.getClass();

    }

    public static <T> Operator<T> modify(Collection<T> collection){
        return new Operator<>(collection);

    }

    public Operator<T> add(T value){
        storage.add(value);
        return this;
    }
    public Operator<T> add(Collection<T> values){
        storage.addAll(values);
        return this;
    }

    public Operator<T> remove(Predicate<? super T> predicate){
        storage.removeIf(predicate);
        return this;
    }

    public Operator<T> sort(Comparator<? super T> comparator){
        if (storage instanceof List)
            ((List<T>) storage).sort(comparator);
        return this;
    }

    public Operator<T> each(Consumer<? super T> action){
        storage.forEach(action);
        return this;
    }

    public <C extends Collection<T>> Operator<T> copyTo(Supplier<C> supplier){
        return Operator.modify(storage.stream().collect(Collectors.toCollection(supplier)));
    }

    public <C extends Collection<K>, K> Operator<K> convertTo(Supplier<C> generator, Function<T,K> modifier){
        return Operator.modify(storage.stream().map(modifier).collect(Collectors.toCollection(generator)));
    }

    @SuppressWarnings("unchecked")
    public <C extends Collection<T>> C  get() {

        return (C) storage;
    }
}
