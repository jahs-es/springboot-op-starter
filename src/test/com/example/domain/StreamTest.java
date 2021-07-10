package com.example.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;

@RunWith(SpringRunner.class)
public class StreamTest {
    private Person person;
    private Person person2;

    @Before
    public void init() {
        person = new Person("Pepe", "Lucas", 21);
        person2 = new Person("Juan", "Mtnez", 85);
    }

    @Test
    public void printAllPersonsNamesToConsole()  throws Exception {
        List<Person> list = new ArrayList<>(Arrays.asList(person, person2));
        Stream<Person> personsStream = list.stream();

        String writtenToConsole = tapSystemOut(() -> {
            personsStream.forEach(person -> person.sayName());
        });

        Assert.assertEquals("Pepe\nJuan", writtenToConsole.trim());
    }

    @Test
    public void filterPersonsOlderThanAnAge()  throws Exception {
        List<Person> list = new ArrayList<>(Arrays.asList(person, person2));
        Stream<Person> personsStream = list.stream();

        Stream<Person> olderPersons = personsStream
                .filter(p -> p.getAge() > 21);

        List<Person> olderList = olderPersons.collect(Collectors.toList());

        Assert.assertTrue(olderList.size() == 1);
    }

    @Test
    public void printAllSeniorPersonsNamesToConsole()  throws Exception {
        List<Person> list = new ArrayList<>(Arrays.asList(person, person2));
        Stream<Person> personsStream = list.stream();

        String writtenToConsole = tapSystemOut(() -> {
            personsStream
                    .filter(p -> p.isSenior())
                    .forEach(p -> p.sayName());
        });

        Assert.assertEquals("Juan", writtenToConsole.trim());
    }

    @Test
    public void getAllJobAssignedToPersons()  throws Exception {
        List<Person> list = new ArrayList<>(Arrays.asList(person, person2));
        Stream<Person> personsStream = list.stream();

        Stream<Integer> personAgesStream = personsStream.map(Person::getAge);
        List<Integer> personAgesList = personAgesStream.collect(Collectors.toList());

        Assert.assertTrue(personAgesList.get(0).equals(21));
    }

    @Test
    public void getSumOfAges()  throws Exception {
        List<Person> list = new ArrayList<>(Arrays.asList(person, person2));
        Stream<Person> personsStream = list.stream();

        int suma = personsStream
                .map(Person::getAge)
                .reduce(0, (age1, age2) -> age1 + age2);

        Assert.assertTrue(suma == 106);
    }
}
