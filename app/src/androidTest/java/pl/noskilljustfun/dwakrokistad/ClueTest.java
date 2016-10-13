package pl.noskilljustfun.dwakrokistad;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.noskilljustfun.dwakrokistad.model.Clue;

/**
 * Created by Bartosz on 22.06.2016.
 */
public class ClueTest {


        private Clue clue;

    @Before
    public void setUp(){
        clue = new Clue();

       setClueNameTest();
    }

    @Test
    public void setClueNameTest()
    {
        clue.setName("Test");
        Assert.assertSame(new String[]{"Test"},clue.getName());

    }

}
