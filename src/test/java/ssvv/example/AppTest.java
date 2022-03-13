package ssvv.example;

import domain.Nota;
import domain.Pair;
import domain.Student;
import org.junit.Test;
import repository.*;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.ValidationException;
import validation.Validator;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    Validator<Student> studentValidator = new StudentValidator();
    StudentXMLRepository studentRepository = new StudentXMLRepository(studentValidator, "st_repo.xml");
    Service service = new Service(studentRepository, null, null);

    @Test
    public void tc_1_InvalidStudent_InvalidId() {
        Student student = new Student("", "Nuna", 935);
        try {
            studentValidator.validate(student);
            fail();
        }
        catch (ValidationException ex) {
            assertEquals("ID invalid! \n", ex.getMessage());
        }
    }

    @Test
    public void tc_2_InvalidStudent_InvalidName() {
        Student student = new Student("3", null, 935);
        try {
            studentValidator.validate(student);
            fail();
        }
        catch (ValidationException ex) {
            assertEquals("Nume invalid! \n", ex.getMessage());
        }
    }

    @Test
    public void tc_3_InvalidStudent_InvalidGroup() {
        Student student = new Student("3", "Nuna", 100);
        try {
            studentValidator.validate(student);
            fail();
        }
        catch (ValidationException ex) {
            assertEquals("Grupa invalida! \n", ex.getMessage());
        }
    }

    @Test
    public void tc_4_ValidStudent() {
        Student student = new Student("3", "Nuna", 935);
        try {
            studentValidator.validate(student);
            assertTrue(true);
        }
        catch (ValidationException ex) {
            fail();
        }
    }

    @Test
    public void tc_5_Repo_AddStudent_Invalid() {
        Student student = new Student("3", "Nuna", 100);
        Student addedStudent = studentRepository.save(student);
        assertNull(addedStudent);
    }

    @Test
    public void tc_6_Repo_AddStudent_Valid() {
        Student student = new Student("3", "Nuna", 935);
        Student addedStudent = studentRepository.save(student);
        assertEquals(student, addedStudent);
    }

    @Test
    public void tc_7_Service_AddStudent_Invalid() {
        int returnValue = service.saveStudent("3", "Nuna", 100);
        assertEquals(1, returnValue);
    }

    @Test
    public void tc_8_Service_AddStudent_Valid() {
        int returnValue = service.saveStudent("3", "Nuna", 935);
        assertEquals(0, returnValue);
    }
}
