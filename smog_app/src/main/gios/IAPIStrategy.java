package gios;

import model.Entry;

import javax.naming.CommunicationException;
import java.util.*;

/**
 * Interface for strategies of choosing API to get info from.
 * Any class implementing this interface can be chosen as source of air quality information.
 * Every strategy should use different API. Default version using GIOS API is provided.
 * No matter what API is used it should pack the information in model.Entry types and return them as list.
 */
public interface IAPIStrategy
{
    /**
     * Gets all information from given API.
     * @return list of entries containing all information from API
     * @throws CommunicationException if there was a problem connecting to the Internet or API site
     */
    List<Entry> getInfo() throws CommunicationException;
}
