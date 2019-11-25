import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class testAPIContext
{
    private APIContext apiContext;

    @Before
    public void initialize()
    {
        this.apiContext = new APIContext();
    }

    @Test
    public void testDefaultAPI()
    {
        API_GIOS api_gios = new API_GIOS();
        Assert.assertEquals(this.apiContext.getStrategy().getClass(),api_gios.getClass());
    }

    @Test
    public void testSetNullAPI()
    {
        API_GIOS api_gios = new API_GIOS();
        this.apiContext.setStrategy(null);
        Assert.assertNotNull(this.apiContext.getStrategy());
        Assert.assertEquals(this.apiContext.getStrategy().getClass(),api_gios.getClass());
    }
}
