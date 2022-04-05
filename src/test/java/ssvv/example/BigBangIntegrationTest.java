package ssvv.example;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BigBangIntegrationTest {
    static TemaValidator assignmentValidator = new TemaValidator();
    static TemaXMLRepository assignmentXMLRepository = new TemaXMLRepository(assignmentValidator, "intg_as_repo.xml");

    static StudentValidator studentValidator = new StudentValidator();
    static StudentXMLRepository studentXMLRepository = new StudentXMLRepository(studentValidator, "intg_st_repo.xml");

    static NotaValidator notaValidator = new NotaValidator();
    static NotaXMLRepository notaXMLRepository = new NotaXMLRepository(notaValidator, "intg_gd_repo.xml");

    static Service service = new Service(studentXMLRepository, assignmentXMLRepository, notaXMLRepository);

    public void cleanup(String repoFile) throws FileNotFoundException, UnsupportedEncodingException {
        File repo = new File(repoFile);
        repo.delete();
        PrintWriter writer = new PrintWriter(repoFile, "UTF-8");
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
        writer.close();
    }

    @Test
    public void tc_1_bigBangIntegration_addAssignment() throws FileNotFoundException, UnsupportedEncodingException {
        Service tempService = new Service(studentXMLRepository, new TemaXMLRepository(assignmentValidator, "intg_as1_repo.xml"), notaXMLRepository);
        int actual = tempService.saveTema("integration", "Description", 10, 1);
        cleanup("intg_as1_repo.xml");
        assertEquals(1, actual);
    }

    @Test
    public void tc_2_bigBangIntegration_addStudent() throws FileNotFoundException, UnsupportedEncodingException {
        Service tempService = new Service(new StudentXMLRepository(studentValidator, "intg_st1_repo.xml"), assignmentXMLRepository, notaXMLRepository);
        int actual = tempService.saveStudent("integration", "Nuna", 935);
        cleanup("intg_st1_repo.xml");
        assertEquals(1, actual);
    }

    @Test
    public void tc_3_bigBangIntegration_addGrade() throws FileNotFoundException, UnsupportedEncodingException {
        Service tempService = new Service(new StudentXMLRepository(studentValidator, "studenti.xml"), new TemaXMLRepository(assignmentValidator, "teme.xml"), notaXMLRepository);
        int actual = tempService.saveNota("1", "1", 10, 6, "bine");
        cleanup("intg_gd_repo.xml");
        assertEquals(1, actual);
    }

    @Test
    public void tc_4_bigBangIntegration_addAssignment_Student_and_Grade() throws FileNotFoundException, UnsupportedEncodingException {
        service.saveTema("integration", "Description", 10, 1);
        service.saveStudent("integration", "Nuna", 935);
        int actual = service.saveNota("integration", "integration", 6, 10, "bine");
        cleanup("intg_gd_repo.xml");
        cleanup("intg_st_repo.xml");
        cleanup("intg_as_repo.xml");
        assertEquals(1, actual);
    }
}
