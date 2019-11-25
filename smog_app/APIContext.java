/**
 * Context class, part of Strategy design pattern used to choose API to get information from. Contains information about currently used API
 * and methods to get and set it.
 */
public class APIContext
{
    private IAPIStrategy usedAPI;

    APIContext() { this.usedAPI = new API_GIOS(); }

    /**
     * Strategy setter.
     * @param targetStrategy API to be used
     */
    public void setStrategy(IAPIStrategy targetStrategy)
    {
        if (targetStrategy != null)
            this.usedAPI = targetStrategy;
    }

    /**
     * Strategy getter.
     * @return currently used API
     */
    public IAPIStrategy getStrategy () { return this.usedAPI; }
}
