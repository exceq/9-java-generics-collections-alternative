import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;


public class Operator<T, C extends Collection<T>> {
    private final C storage;

    private Operator(C collection) {
        if (collection==null)
          throw new NullPointerException("Collection is null!");
        storage = collection;
    }

    public static <T, C extends Collection<T>> Operator<T, C> modify(C collection){
        return new Operator<>(collection);

    }

    public Operator<T, C> add(T value){
        storage.add(value);
        return this;
    }

    public Operator<T, C> add(Collection<T> values){
        storage.addAll(values);
        return this;
    }

    public Operator<T, C> remove(Predicate<? super T> predicate){
        storage.removeIf(predicate);
        return this;
    }

    public Operator<T, C> sort(Comparator<? super T> comparator){
        if (storage instanceof List) {
            var a = storage.stream().sorted(comparator).collect(Collectors.toList());
            storage.clear();
            storage.addAll(a);
        }
        return this;
    }

    public Operator<T, C> each(Consumer<? super T> action){
        storage.forEach(action);
        return this;
    }

    public <P extends Collection<T>> Operator<T, P> copyTo(Supplier<P> supplier){
        return Operator.modify(storage.stream().collect(Collectors.toCollection(supplier)));
    }

    public <K, P extends Collection<K>> Operator<K, P> convertTo(Supplier<P> generator, Function<T,K> modifier){
        return Operator.modify(storage.stream().map(modifier).collect(Collectors.toCollection(generator)));
    }

    public C get() {
        return storage;
    }
}
