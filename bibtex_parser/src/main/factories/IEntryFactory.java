package factories;

import model.Entry;

import java.util.List;

/**
 * Main part of Abstract Factory design pattern. This is an interface for Factories producing empty entries of given type.
 * Classes implementing this interface should have names like: RecordtypeFactory, e. g. factories.BookFactory. It's required, because later reflection
 * is used to determine which Factory should be used.
 * Methods:<br>
 * - create(): creates empty model.Entry object of type specified in Factory name, with adequate recordType attribute value and attributes' lists<br>
 * - getRequiredFields(): makes list of required fields for certain object type<br>
 * - getOptionalFields(): makes list of optional fields for certain object type<br>
 * If there is choice between two fields (e. g. there can be either author or editor), Factory should create attribute list with both types
 * since it's uncertain at the moment of creation of empty object which one will be used.
 */
public interface IEntryFactory
{
    Entry create();
    List<String> getRequiredFields();
    List<String> getOptionalFields();
}