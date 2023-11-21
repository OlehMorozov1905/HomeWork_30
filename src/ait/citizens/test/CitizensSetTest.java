package ait.citizens.test;

import ait.citizens.dao.CitizensSetImpl;
import ait.citizens.interfaces.Citizens;
import ait.citizens.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CitizensSetTest {
    Citizens citizens;
    static final LocalDate now = LocalDate.now();

    @BeforeEach
    void setUp() {
        citizens = new CitizensSetImpl(Set.of(
                        new Person(1, "Peter", "Jackson", now.minusYears(23)),
                        new Person(2, "John", "Smith", now.minusYears(20)),
                        new Person(3, "Mary", "Jackson", now.minusYears(20)),
                        new Person(4, "Rabindranate", "Anand", now.minusYears(25))));
    }

    @Test
    void testCitizensImplListOfPerson() {
        citizens = new CitizensSetImpl(new LinkedHashSet<>(
                List.of(new Person(1, "Peter", "Jackson", now.minusYears(23)),
                        new Person(2, "John", "Smith", now.minusYears(20)),
                        new Person(1, "Peter", "Jackson", now.minusYears(23)))));
        assertEquals(2, citizens.size());
    }

    @Test
    void add() {
        assertFalse(citizens.add(null));
        assertFalse(citizens.add(new Person(2, "John", "Smith", now.minusYears(20))));
        assertEquals(4, citizens.size());
        assertTrue(citizens.add(new Person(5, "John", "Smith", now.minusYears(20))));
        assertEquals(5, citizens.size());
    }

    @Test
    void remove() {
        assertFalse(citizens.remove(5));
        assertEquals(4, citizens.size());
        assertTrue(citizens.remove(2));
        assertEquals(3, citizens.size());
    }

    @Test
    void findById() {
        Person person = citizens.find(1);
        assertEquals(1, person.getId());
        assertEquals("Peter", person.getFirstName());
        assertEquals("Jackson", person.getLastName());
        assertEquals(23, person.getAge());
        assertNull(citizens.find(5));
    }

    @Test
    void testFindByAges() {
        Iterable<Person> res = citizens.find(20, 23);
        List<Person> actual = new ArrayList<>();
        res.forEach(p -> actual.add(p));
        Collections.sort(actual);
        List<Person> expected = List.of(
                new Person(1, "Peter", "Jackson", now.minusYears(23)),
                new Person(2, "John", "Smith", now.minusYears(20)),
                new Person(3, "Mary", "Jackson", now.minusYears(20)));
        assertIterableEquals(expected, actual);
    }

    @Test
    void testFindByLastName() {
        Iterable<Person> res = citizens.find("Jackson");
        List<Person> actual = new ArrayList<>();
        res.forEach(p -> actual.add(p));
        Collections.sort(actual);
        Iterable<Person> expected = Set.of(
                new Person(1, "Peter", "Jackson", now.minusYears(23)),
                new Person(3, "Mary", "Jackson", now.minusYears(20)));
        assertIterableEquals(expected, actual);
    }

    @Test
    void getAllPersonSortedById() {
        Iterable<Person> actual = citizens.getAllPersonSortedById();
        Iterable<Person> expected = List.of(
                new Person(1, "Peter", "Jackson", now.minusYears(23)),
                new Person(2, "John", "Smith", now.minusYears(20)),
                new Person(3, "Mary", "Jackson", now.minusYears(20)),
                new Person(4, "Rabindranate", "Anand", now.minusYears(25)));
        assertIterableEquals(expected, actual);
    }

    @Test
    void getAllPersonSortedByLastName() {
        Iterable<Person> actual = citizens.getAllPersonSortedByLastName();
        String name = "";
        for (Person person : actual) {
            assertTrue(person.getLastName().compareTo(name) >= 0);
            name = person.getLastName();
        }
    }

    @Test
    void getAllPersonSortedByAge() {
        Iterable<Person> res = citizens.getAllPersonSortedByAge();
        int age = -1;
        for (Person person : res) {
            assertTrue(person.getAge() >= age);
            age = person.getAge();
        }
    }

    @Test
    void size() {
        assertEquals(4, citizens.size());
    }
}
