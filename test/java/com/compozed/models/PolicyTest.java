package com.compozed.models;

import com.compozed.enums.Gender;
import com.compozed.util.Mysql;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.TimeZone;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by localadmin on 8/11/16.
 */
public class PolicyTest {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private int currentYear = LocalDateTime.now().getYear();

    @Before
    public void setUp() throws Exception {
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Session session = Mysql.getSession();
        session.beginTransaction();
        session.createNativeQuery("set FOREIGN_KEY_CHECKS = 0").executeUpdate();
        session.createNativeQuery("truncate table policies_clients").executeUpdate();
        session.createNativeQuery("truncate table policies").executeUpdate();
        session.createNativeQuery("truncate table clients").executeUpdate();
        session.createNativeQuery("truncate table rates").executeUpdate();
        session.createNativeQuery("set FOREIGN_KEY_CHECKS = 1").executeUpdate();
        Client c = new Client("jennifer", format.parse((currentYear - 20) + "-03-18"), Gender.F);

        Rate rate = new Rate(100, "", "base");
        Rate rate1 = new Rate(50, "M", "gender");
        Rate rate4 = new Rate(45, "F", "gender");
        Rate rate2 = new Rate(50, "25", "age");
        Rate rate3 = new Rate(55, "1", "claim");

        session.save(rate);
        session.save(rate1);
        session.save(rate2);
        session.save(rate3);
        session.save(rate4);

        session.save(c);
        session.getTransaction().commit();
        session.close();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldCreateNewPolicyAndSave() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();

        Client c = new Client("John", format.parse((currentYear - 30) + "-03-18"), Gender.M);
        Policy pol = new Policy(c);
        c.getPolicies().add(pol);
        session.save(pol);

        session.getTransaction().commit();
        session.close();

        assertEquals(1, pol.getId());
        assertThat(pol.getPremium(), is(100f + 50f + 50f));
    }

    @Test
    public void shouldCreateNewPolicyAndSave2() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();

        Client c = session.get(Client.class, 1);
        Policy pol = new Policy(c);
        session.save(pol);

        session.getTransaction().commit();
        session.close();

        assertEquals(1, pol.getId());
        assertThat(pol.getPremium(), is(100f + 45f));
    }

    @Test
    public void shouldCreateNewPolicyAndSave3() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();

        Client c = new Client("John", format.parse("1979-03-18"), Gender.M);
        Client c2 = new Client("Roberta", format.parse("1879-03-18"), Gender.F);
        Policy pol = new Policy(c);
        pol.getDrivers().add(c2);
        c.getPolicies().add(pol);
        session.save(pol);

        session.getTransaction().commit();
        session.close();

        assertEquals(1, pol.getId());
    }
}