package ssvv.example;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import repository.*;
import service.Service;
import validation.*;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class WhiteBoxTest {
    TemaValidator assignmentValidator = new TemaValidator();
    TemaXMLRepository assignmentRepository = new TemaXMLRepository(assignmentValidator, "wbt_repo.xml");
    Service service = new Service(null, assignmentRepository, null);

    @BeforeAll
    static void before() throws IOException {
        File repo_file = new File("wbt_repo.xml");
        repo_file.createNewFile();
    }

    @AfterAll
    static void after() {
        File repo_file = new File("wbt_repo.xml");
        repo_file.delete();
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
}
