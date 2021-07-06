package com.example.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PersonTest {
    private Person person;
    private Person person2;

    @Before
    public void init() {
        person = new Person("Pepe", "Lucas", 21);
        person2 = new Person("Pepe", "Mtnez", 25);
    }

    @Test
    public void assertNameIsTheSame() {
        Assert.assertEquals(person2.getName(), person.getName());
    }

    @Test
    public void assertPersonsAreDistinct() {
        Assert.assertFalse(person.equals(person2));
    }

    @Test
    public void assertIsSenior() {
        Assert.assertFalse(person.isSenior());
    }

    @Test(expected = Exception.class)
    public void assertExceptionDueAge() throws Exception {
        Job job = new Job("Pepe", 30);

        person.addJob(job);
    }

    @Test
    public void assertJobIsAssigned() throws Exception {
        Job job = new Job("Pepe", 10);
        person.addJob(job);

        Assert.assertTrue(person.getAssignedJobs().size() > 0);
    }
}
