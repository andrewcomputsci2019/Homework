import edu.miracosta.cs113.EfficientClue;
import model.AssistantJack;
import static org.junit.Assert.*;

import model.Theory;
import org.junit.Test;

import java.lang.reflect.Field;

public class TheoryTest {
    private final EfficientClue clue = new EfficientClue();
    @Test
    public void testSet1(){
        AssistantJack jack = new AssistantJack(1);
        Field theory;
        Theory correct = null;
        try { // used to get the correct theory without causing an increase in guess count
            theory = jack.getClass().getDeclaredField("correctTheory");
            theory.setAccessible(true);
            correct = (Theory) theory.get(jack);
        }catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
            fail("failed to get theory");
        }
        assertTrue(isEqual(correct,clue.getSolution(jack)));
        assertTrue("not less than 20",jack.getTimesAsked()<=20);
    }
    @Test
    public void testSet2(){
        AssistantJack jack = new AssistantJack(2);
        Field theory;
        Theory correct = null;
        try { // used to get the correct theory without causing an increase in guess count
            theory = jack.getClass().getDeclaredField("correctTheory");
            theory.setAccessible(true);
            correct = (Theory) theory.get(jack);
        }catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
            fail("failed to get theory");
        }
        assertTrue(isEqual(correct,clue.getSolution(jack)));
        assertTrue("not less than 20",jack.getTimesAsked()<=20);
    }
    @Test
    public void testRandom()  {
        AssistantJack jack = new AssistantJack(3);
        Field theory;
        Theory correct = null;
        try { // used to get the correct theory without causing an increase in guess count
            theory = jack.getClass().getDeclaredField("correctTheory");
            theory.setAccessible(true);
            correct = (Theory) theory.get(jack);
        }catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
            fail("failed to get theory");
        }
        assertTrue(isEqual(correct,clue.getSolution(jack)));
        assertTrue("not less than 20",jack.getTimesAsked()<=20);
    }

    private boolean isEqual(Theory first, Theory other){
        return first.getLocation()==other.getLocation() && first.getWeapon() == other.getWeapon() && first.getPerson() == other.getPerson();
    }
}
