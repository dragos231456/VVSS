package ssvv.example;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.*;
import service.Service;
import validation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class WhiteBoxTest {
    static TemaValidator assignmentValidator = new TemaValidator();
    static TemaXMLRepository assignmentRepository = new TemaXMLRepository(assignmentValidator, "wbt_repo.xml");
    static Service service = new Service(null, assignmentRepository, null);

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
}
