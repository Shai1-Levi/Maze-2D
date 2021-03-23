package algorithms.search;

/**
 * we use hash for reduce the time complexity of the algorithms
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected int counter = 0; // count the NumberOfNodesEvaluated
    public abstract Solution solve(ISearchable domain); // return the solution for the problem
    public abstract String getName();
    public int getNumberOfNodesEvaluated()
    {
        return this.counter;
    } //  return the number of the node  that we explore their neighbors

}
