package ssvv.example;

import domain.Nota;
import domain.Pair;
import org.junit.Test;
import validation.NotaValidator;
import validation.ValidationException;
import validation.Validator;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void tc_1_ValidGrade() {
        Nota nota = new Nota(new Pair<>("2", "3"), 4.5, 12, "se putea si mai rau");
        Validator<Nota> gradeValidator = new NotaValidator();

        try {
        gradeValidator.validate(nota);
        }
        catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void tc_2_InvalidGrade() {
        Nota nota = new Nota(new Pair<>(null, "3"), 4.5, 12, "se putea si mai rau");
        Validator<Nota> gradeValidator = new NotaValidator();

        try {
            gradeValidator.validate(nota);
            fail();
        } catch (ValidationException ex) {
            assertEquals("ID Student invalid! \n", ex.getMessage());
        }
    }
}
