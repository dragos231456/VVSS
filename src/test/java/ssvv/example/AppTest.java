package ssvv.example;

import domain.Nota;
import domain.Pair;
import domain.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.*;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.ValidationException;
import validation.Validator;

import java.io.File;

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
    public void tc_1_ValidStudent() {
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
    public void tc_2_InvalidStudent_EmptyId() {
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
    public void tc_3_InvalidStudent_NullId() {
        Student student = new Student(null, "Nuna", 935);
        try {
            studentValidator.validate(student);
            fail();
        }
        catch (ValidationException ex) {
            assertEquals("ID invalid! \n", ex.getMessage());
        }
    }

    @Test
    public void tc_4_InvalidStudent_NullName() {
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
    public void tc_5_InvalidStudent_EmptyName() {
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
    public void tc_6_InvalidStudent_GroupUnder110() {
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
    public void tc_7_InvalidStudent_GroupOver938() {
        Student student = new Student("3", "Nuna", 938);
        try {
            studentValidator.validate(student);
            fail();
        }
        catch (ValidationException ex) {
            assertEquals("Grupa invalida! \n", ex.getMessage());
        }
    }

    @Test
    public void tc_8_Repo_AddStudent_Invalid() {
        Student student = new Student("3", "Nuna", 100);
        Student addedStudent = studentRepository.save(student);
        assertNull(addedStudent);
    }

    @Test
    public void tc_9_Repo_AddStudent_Valid() {
        StudentXMLRepository studentRepository1 = new StudentXMLRepository(studentValidator, "st_repo1.xml");

        Student student = new Student("3", "Nuna", 935);

        Student addedStudent = studentRepository1.save(student);

        assertNull(addedStudent);

        File repo_file = new File("st_repo1.xml");
        repo_file.delete();
    }

    @Test
    public void tc_10_Repo_AddStudent_Duplicate() {
        StudentXMLRepository studentRepository2 = new StudentXMLRepository(studentValidator, "st_repo2.xml");

        Student student = new Student("3", "Nuna", 935);
        Student addedStudent = studentRepository2.save(student);
        Student duplicateStudent = studentRepository2.save(student);
        assertEquals(student, duplicateStudent);

        File repo_file = new File("st_repo2.xml");
        repo_file.delete();
    }
}
