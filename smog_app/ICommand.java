/**
 * Interface for commands implementing various program functions.
 * The only method is getResult() which formats results, which are computed by void execute() method. Execute itself cannot be part of interface,
 * since every command requires different set of arguments. Results of execute() methods should be stored in private fields, which are used by
 * getResult(). Execute should always be used before getResult() for that reason.
 */
public interface ICommand
{
    //void execute (arguments);
    String getResult();
}