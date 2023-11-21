package ait.citizens.dao;

import ait.citizens.interfaces.Citizens;
import ait.citizens.model.Person;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class CitizensSetImpl implements Citizens {

    private TreeSet<Person> idSet;
    private TreeSet<Person> lastNameSet;
    private TreeSet<Person> ageSet;
    private static Comparator<Person> lastNameComparator = (p1, p2) -> {
        int res = p1.getLastName().compareTo(p2.getLastName());
        return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
    };
    private static Comparator<Person> ageComparator = (p1, p2) -> {
        int res = Integer.compare(p1.getAge(), p2.getAge());
        return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
    };

    public CitizensSetImpl() {
        idSet = new TreeSet<>();
        lastNameSet = new TreeSet<>(lastNameComparator);
        ageSet = new TreeSet<>(ageComparator);
    }

    public CitizensSetImpl(Set<Person> citizens) {
        this();
        citizens.forEach(p -> add(p));
    }

    @Override
    public boolean add(Person person) {
        if (person == null) {
            return false;
        }
        boolean added = idSet.add(person);
        if (added) {
            ageSet.add(person);
            lastNameSet.add(person);
        }
        return added;
    }

    @Override
    public boolean remove(int id) {
        Person victim = find(id);
        if (victim == null) {
            return false;
        }
        idSet.remove(victim);
        ageSet.remove(victim);
        lastNameSet.remove(victim);
        return true;
    }

    @Override
    public Person find(int id) {
        Person pattern = new Person(id, null, null, null);
        return idSet.contains(pattern) ? idSet.stream().filter(p -> p.equals(pattern)).findFirst().orElse(null) : null;
    }

    @Override
    public Iterable<Person> find(int minAge, int maxAge) {
        LocalDate now = LocalDate.now();
        Person patternMin = new Person(Integer.MIN_VALUE, null, null, now.minusYears(minAge));
        Person patternMax = new Person(Integer.MAX_VALUE, null, null, now.minusYears(maxAge));
        return ageSet.subSet(patternMin, patternMax);
    }

    @Override
    public Iterable<Person> find(String lastName) {
        Person patternMin = new Person(Integer.MIN_VALUE, null, lastName, null);
        Person patternMax = new Person(Integer.MAX_VALUE, null, lastName, null);
        return lastNameSet.subSet(patternMin, patternMax);
    }

    @Override
    public Iterable<Person> getAllPersonSortedById() {
        return idSet;
    }

    @Override
    public Iterable<Person> getAllPersonSortedByLastName() {
        return lastNameSet;
    }

    @Override
    public Iterable<Person> getAllPersonSortedByAge() {
        return ageSet;
    }

    @Override
    public int size() {
        return idSet.size();
    }

}
