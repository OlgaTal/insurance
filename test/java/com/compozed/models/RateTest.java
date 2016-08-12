package com.compozed.models;

import com.compozed.enums.Gender;
import com.compozed.util.Mysql;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by localadmin on 8/11/16.
 */
public class RateTest {

    @Before
    public void setUp() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();
        session.createNativeQuery("set FOREIGN_KEY_CHECKS = 0").executeUpdate();
        session.createNativeQuery("truncate table rates").executeUpdate();
        session.createNativeQuery("set FOREIGN_KEY_CHECKS = 1").executeUpdate();

        Rate rate = new Rate(100, "", "base");
        Rate rate1 = new Rate(50, "M", "gender");
        Rate rate2 = new Rate(50, "25", "age");
        Rate rate3 = new Rate(55, "1", "claim");
        Rate rate4 = new Rate(45, "F", "gender");

        session.save(rate);
        session.save(rate1);
        session.save(rate2);
        session.save(rate3);
        session.save(rate4);

        session.getTransaction().commit();
        session.close();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldAddNewRate() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();
        Rate rate = new Rate(100, "", "garbage");
        session.save(rate);
        session.getTransaction().commit();
        session.close();
        assertEquals(5, rate.getId());
    }

    @Test
    public void shouldUpdateExistingRate() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();
        Rate rate = session.get(Rate.class, 1);
        rate.setField("base2");
        session.getTransaction().commit();
        session.refresh(rate);
        session.close();
        assertEquals("base2", rate.getField());
    }

}