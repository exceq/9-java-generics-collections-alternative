import static org.junit.jupiter.api.Assertions.*;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.*;
import java.util.function.Predicate;

public class Tests {
    private void check(String collection, String result) {
        assertEquals(result, collection);
    }

    @Test
    public void test1() {
        ArrayList<String> list1 = new ArrayList<>(List.of("abc", "de", "f"));
        ArrayList<String> result1 = Operator.modify(list1).add("1234").add(list1).add("zzzzz").get();
        check(result1.toString(), "[abc, de, f, 1234, abc, de, f, 1234, zzzzz]");
    }

    //тест не всегда проходит из-за особенностей HashSet'а
    //То есть числа идут в перемешку и результат выполнения теста все время разный и не совпадает с ожидаемым результатом
    //@Test
    public void test2() {
        LinkedHashSet<Integer> set1 = new LinkedHashSet<>(Set.of(12345, 23456, 34567, 45678, 56789));
        Comparator<Number> compareNumbers = Comparator.comparingDouble(Number::doubleValue);
        Predicate<Number> greaterThan30000 = i -> compareNumbers.compare(i, 30000) > 0;
        LinkedHashSet<Integer> result2 = Operator.modify(set1).remove(greaterThan30000).add(99999).get();
        check(result2.toString(), "[12345, 23456, 99999]");
    }

    @Test
    public void test3() {
        LinkedHashSet<Integer> set1 = new LinkedHashSet<>(Set.of(12345, 23456, 34567, 45678, 56789));
        Comparator<Number> compareNumbers = Comparator.comparingDouble(Number::doubleValue);
        ArrayList<Integer> list2 = Operator.modify(set1).copyTo(ArrayList::new).get();
        var result25 = Operator.modify(list2).sort(compareNumbers.reversed()).convertTo(LinkedList::new, x -> x / 1000).get();
        check(result25.toString(), "[56, 45, 34, 23, 12]");
    }

    @Test
    public void test4() {

        ArrayList<String> list1 = new ArrayList<>(List.of("abc", "de", "f"));
        ArrayList<String> result1 = Operator.modify(list1).add("1234").add(list1).add("zzzzz").get();
        ArrayList<String> result3 = Operator.modify(result1).remove(s -> s.equals("f")).sort(Comparator.naturalOrder()).get();
        check(result3.toString(), "[1234, 1234, abc, abc, de, de, zzzzz]");
    }

    @Test
    public void test5() {
        var a = 10;
        ArrayList<String> list1 = new ArrayList<>(List.of("abc", "de", "f"));
        ArrayList<String> result1 = Operator.modify(list1).add("1234").add(list1).add("zzzzz").get();
        ArrayList<String> result3 = Operator.modify(result1).remove(s -> s.equals("f")).sort(Comparator.naturalOrder()).get();
        TreeSet<Integer> result4 = Operator.modify(result3).convertTo(TreeSet::new, String::length).add(Set.of(888, 999)).get();
        check(result4.toString(), "[2, 3, 4, 5, 888, 999]");
    }

    @Test
    public void test6()  {
        Set<Integer> s1 = new HashSet<>(Set.of(1, 2, 3));
        Set<Integer> s2 = new HashSet<>(Set.of(2, 3, 4));
        HashSet<Set<Integer>> sets = new HashSet<>(Set.of(s1, s2));
        LinkedList<Set<Integer>> result5 = Operator.modify(sets).each(set -> set.remove(2)).copyTo(LinkedList::new).get();
        check(result5.toString(),"[[1, 3], [3, 4]]");
    }

    @Test
    public void nullCollectionInModify(){
        assertThrows(NullPointerException.class, () -> Operator.modify(null));
    }
}