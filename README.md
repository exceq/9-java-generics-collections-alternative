#* Альтернативная задача 9

Реализуйте обобщенный класс `Operator` для выполнения операций над коллекциями.

Необходимые функции:

`modify` - статический, принимает исходную коллекцию и оборачивает ее в Operator

`add` - добавляет переданный элемент в коллекцию

`add` - добавляет все элементы переданной коллекции в коллекцию

`remove` - удаляет элементы коллекции согласно переданному предикату

`sort` - сортирует элементы коллекции

`each` - передает все элементы коллекции потребителю

`copyTo` - копирует все элементы коллекции в новую

`convertTo` - преобразует каждый элемент коллекции и копирует результаты в новую

`get` - возвращает результат

Класс должен компилироваться без предупреждений "Unchecked cast" или аналогичных.


Код, использующий класс Operator:
```
ArrayList<String> list1 = new ArrayList<>(List.of("abc", "de", "f"));
ArrayList<String> result1 = Operator.modify(list1).add("1234").add(list1).add("zzzzz").get();
System.out.println(result1);
// [abc, de, f, 1234, abc, de, f, 1234, zzzzz]

LinkedHashSet<Integer> set1 = new LinkedHashSet<>(Set.of(12345, 23456, 34567, 45678, 56789));
Consumer<Object> print = System.out::print;
Comparator<Number> compareNumbers = Comparator.comparingDouble(Number::doubleValue);
Predicate<Number> greaterThan30000 = i -> compareNumbers.compare(i, 30000) > 0;
LinkedHashSet<Integer> result2 = Operator.modify(set1).remove(greaterThan30000).add(99999).get();
System.out.println(result2);
// [12345, 23456, 99999]

ArrayList<Integer> list2 = Operator.modify(set1).copyTo(ArrayList::new).get();
Operator.modify(list2).sort(compareNumbers.reversed()).convertTo(LinkedList::new, x -> x / 1000).each(print);
System.out.println();
// 992312

ArrayList<String> result3 = Operator.modify(result1).remove(s -> s.equals("f")).sort(Comparator.naturalOrder()).get();
System.out.println(result3);
// [1234, 1234, abc, abc, de, de, zzzzz]

TreeSet<Integer> result4 = Operator.modify(result3).convertTo(TreeSet::new, String::length).add(Set.of(888, 999)).get();
System.out.println(result4);
// [2, 3, 4, 5, 888, 999]

Set<Integer> s1 = new HashSet<>(Set.of(1, 2, 3));
Set<Integer> s2 = new HashSet<>(Set.of(2, 3, 4));
HashSet<Set<Integer>> sets = new HashSet<>(Set.of(s1, s2));
LinkedList<Set<Integer>> result5 = Operator.modify(sets).each(set -> set.remove(2)).copyTo(LinkedList::new).get();
System.out.println(result5);
// [[1, 3], [3, 4]]
```
Результат выполнения:
```
[abc, de, f, 1234, abc, de, f, 1234, zzzzz]
[12345, 23456, 99999]
992312
[1234, 1234, abc, abc, de, de, zzzzz]
[2, 3, 4, 5, 888, 999]
[[1, 3], [3, 4]]
```