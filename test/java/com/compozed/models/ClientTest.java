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
 * Created by localadmin on 8/10/16.
 */
public class ClientTest {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setUp() throws Exception {
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Session session = Mysql.getSession();
        session.beginTransaction();
        session.createNativeQuery("set FOREIGN_KEY_CHECKS = 0").executeUpdate();
        session.createNativeQuery("truncate table clients").executeUpdate();
        session.createNativeQuery("set FOREIGN_KEY_CHECKS = 1").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldCreateNewClientAndSave() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();
        Client c = new Client("jennifer", format.parse("2000-03-18"), Gender.M);
        session.save(c);
        session.getTransaction().commit();
        session.close();

        assertEquals(1, c.getId());
    }

    @Test(expected = org.hibernate.exception.DataException.class)
    public void shouldNotSaveDuetoNameTooLong() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();
        Client client = new Client("jenniferasffffffffffffffffffffffjenniferasffffffffffffffffffffffjenniferasffffffffffffffffffffffjenniferasffffffffffffffffffffffjenniferasffffffffffffffffffffff", format.parse("1997-04-20"), Gender.F);
        try {
            session.save(client);
            session.getTransaction().commit();

        } finally {
            session.close();
        }
    }

}