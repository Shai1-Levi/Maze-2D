package algorithms.search;

public interface ISearchingAlgorithm {

    public String getName();
    public int getNumberOfNodesEvaluated();  //  return the number of the node  that we explore their neighbors
    public Solution solve(ISearchable domain);
}
