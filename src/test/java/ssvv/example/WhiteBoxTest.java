package ssvv.example;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.*;
import service.Service;
import validation.*;

import java.io.*;

import static org.junit.Assert.*;

public class WhiteBoxTest {
    static TemaValidator assignmentValidator = new TemaValidator();
    static TemaXMLRepository assignmentRepository = new TemaXMLRepository(assignmentValidator, "wbt_repo.xml");
    static Service service = new Service(null, assignmentRepository, null);

    public void after() throws FileNotFoundException, UnsupportedEncodingException {
        File repo = new File("wbt_repo.xml");
        repo.delete();
        PrintWriter writer = new PrintWriter("wbt_repo.xml", "UTF-8");
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
        writer.println("<Entitati>");
        writer.println("</Entitati>");
        writer.close();
    }

    @Test
    public void tc_1_wbt_addAssignment_NullId() {
        int actual = service.saveTema(null, "Description", 2, 1);
        assertEquals(1, actual);
    }

    @Test
    public void tc_2_wbt_addAssignment_NullDescription() {
        int actual = service.saveTema("2", null, 2, 1);
        assertEquals(1, actual);
    }

    @Test
    public void tc_2_wbt_addAssignment_NegativeDeadline() {
        int actual = service.saveTema("2", "Description", -2, 1);
        assertEquals(1, actual);
    }

    @Test
    public void tc_2_wbt_addAssignment_NegativeStartLine() {
        int actual = service.saveTema("2", "Description", 2, -1);
        assertEquals(1, actual);
    }

    @Test
    public void tc_2_wbt_addAssignment_ValidAssignment() throws FileNotFoundException, UnsupportedEncodingException {
        int actual = service.saveTema("2", "Description", 2, 1);
        assertEquals(0, actual);
        after();
    }
}
