package ssvv.example;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static ssvv.example.BigBangIntegrationTest.cleanup;

public class IncrementalTest {
    static StudentValidator studentValidator = new StudentValidator();
    static TemaValidator assignmentValidator = new TemaValidator();
    static NotaValidator notaValidator = new NotaValidator();

    @Test
    public void tc_1_incrementalIntegration_addStudent() throws FileNotFoundException, UnsupportedEncodingException {
        StudentXMLRepository studentXMLRepository = new StudentXMLRepository(studentValidator, "td_1_st_repo.xml");
        Service service = new Service(studentXMLRepository, null, null);
        int actual = service.saveStudent("integration", "Nuna", 935);
        cleanup("td_1_st_repo.xml");
        assertEquals(1, actual);
    }

    @Test
    public void tc_2_incrementalIntegration_addStudent_addAssignment() throws FileNotFoundException, UnsupportedEncodingException {
        StudentXMLRepository studentXMLRepository = new StudentXMLRepository(studentValidator, "td_2_st_repo.xml");
        TemaXMLRepository assignmentXMLRepository = new TemaXMLRepository(assignmentValidator, "td_2_as_repo.xml");
        Service service = new Service(studentXMLRepository, assignmentXMLRepository, null);

        int studentActual = service.saveStudent("integration", "Nuna", 935);
        assertEquals(1, studentActual);

        int assignmentAcutal = service.saveTema("integration", "Description", 10, 1);
        assertEquals(1, assignmentAcutal);

        cleanup("td_2_st_repo.xml");
        cleanup("td_2_as_repo.xml");
    }

    @Test
    public void tc_3_incrementalIntegration_addStudent_addAssignment_addGrade() throws FileNotFoundException, UnsupportedEncodingException {
        StudentXMLRepository studentXMLRepository = new StudentXMLRepository(studentValidator, "td_3_st_repo.xml");
        TemaXMLRepository assignmentXMLRepository = new TemaXMLRepository(assignmentValidator, "td_3_as_repo.xml");
        NotaXMLRepository notaXMLRepository = new NotaXMLRepository(notaValidator, "td_3_gd_repo.xml");
        Service service = new Service(studentXMLRepository, assignmentXMLRepository, notaXMLRepository);

        int studentActual = service.saveStudent("integration", "Nuna", 935);
        assertEquals(1, studentActual);

        int assignmentAcutal = service.saveTema("integration", "Description", 10, 1);
        assertEquals(1, assignmentAcutal);

        int gradeActual = service.saveNota("integration", "integration", 6, 10, "bine");
        assertEquals(1, gradeActual);

        cleanup("td_3_st_repo.xml");
        cleanup("td_3_as_repo.xml");
        cleanup("td_3_gd_repo.xml");
    }

}
