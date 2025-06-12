import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");

        Collection<Person> persons = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(random.nextInt(names.size())),
                    families.get(random.nextInt(families.size())),
                    random.nextInt(100),
                    Sex.values()[random.nextInt(Sex.values().length)],
                    Education.values()[random.nextInt(Education.values().length)])
            );
        }

        // 1. Количество несовершеннолетних
        long under18 = persons.stream()
                .filter(p -> p.getAge() < 18)
                .count();
        System.out.println("Несовершеннолетние: " + under18);

        // 2. Список фамилий призывников (мужчины 18-27 лет)
        List<String> recruits = persons.stream()
                .filter(p -> p.getSex() == Sex.MAN)
                .filter(p -> p.getAge() >= 18 && p.getAge() <= 27)
                .map(Person::getFamily)
                .distinct()
                .limit(10) // Ограничим вывод
                .collect(Collectors.toList());
        System.out.println("Призывники: " + recruits);

        // 3. Люди с высшим образованием трудоспособного возраста
        List<Person> workable = persons.stream()
                .filter(p -> p.getEducation() == Education.HIGHER)
                .filter(p -> {
                    if (p.getSex() == Sex.WOMAN) return p.getAge() >= 18 && p.getAge() <= 60;
                    else return p.getAge() >= 18 && p.getAge() <= 65;
                })
                .sorted(Comparator.comparing(Person::getFamily))
                .limit(10) // Ограничим вывод
                .collect(Collectors.toList());

        System.out.println("Работоспособные с высшим образованием:");
        workable.forEach(System.out::println);
    }
}
