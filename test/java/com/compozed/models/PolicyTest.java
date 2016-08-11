package com.compozed.models;

import com.compozed.enums.Gender;
import com.compozed.util.Mysql;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Created by localadmin on 8/11/16.
 */
public class PolicyTest {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setUp() throws Exception {
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Session session = Mysql.getSession();
        session.beginTransaction();
        session.createNativeQuery("set FOREIGN_KEY_CHECKS = 0").executeUpdate();
        session.createNativeQuery("truncate table policies_clients").executeUpdate();
        session.createNativeQuery("truncate table policies").executeUpdate();
        session.createNativeQuery("truncate table clients").executeUpdate();
        session.createNativeQuery("set FOREIGN_KEY_CHECKS = 1").executeUpdate();
        Client c = new Client("jennifer", format.parse("2000-03-18"), Gender.M);
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

        Client c = new Client("John", format.parse("1999-03-18"), Gender.M);
        Policy pol = new Policy(c);
        c.getPolicies().add(pol);
        session.save(pol);

        session.getTransaction().commit();
        session.close();

        assertEquals(1, pol.getId());
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