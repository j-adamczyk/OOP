import io.DefaultOutputBuilder;
import model.Entry;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class testDefaultOutputBuilder
{
    @Test
    public void testEmptyInput()
    {
        List<Entry> input = new ArrayList<>();
        DefaultOutputBuilder o = new DefaultOutputBuilder();
        String result = o.buildOutput(input,"");

        Assert.assertEquals("\n",result);
    }

    @Test(expected = NullPointerException.class)
    public void testNullInput()
    {
        DefaultOutputBuilder o = new DefaultOutputBuilder();
        String result = o.buildOutput(null,"");
    }
}
