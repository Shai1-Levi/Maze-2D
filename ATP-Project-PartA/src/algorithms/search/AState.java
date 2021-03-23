package algorithms.search;

import algorithms.mazeGenerators.Position;

/**
 * Astate is abstract class that force the inherited class to implement methods that need to solve the problem
 */

public abstract class AState{

    private String state;
    private int cost;

    public abstract Position getCurrState();

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public abstract void setCameFrom(AState s);  // cameFrom = parent in graph

    public abstract AState getCameFrom();

    @Override
    public boolean equals(Object obj){
        AState state1 = (AState)obj;
        return this.getCurrState().equals(state1.getCurrState());
    }

    @Override
    public String toString() {
        return this.getCurrState().toString();
    }
}
