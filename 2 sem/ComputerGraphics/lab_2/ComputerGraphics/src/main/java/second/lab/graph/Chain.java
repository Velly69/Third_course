package second.lab.graph;

import java.util.ArrayList;
import java.util.List;

public class Chain {
    private List<Edge> chain;

    public Chain() {
        chain = new ArrayList<>();
    }

    public List<Edge> getChain() {
        return chain;
    }

    public void addEdge(Edge e) {
        chain.add(e);
    }

    public Edge getEdge(int i) {
        return chain.get(i);
    }

    public int getSize() {
        return chain.size();
    }

    @Override
    public String toString() {
        String s = "Chain{";
        for (Edge e : chain) {
            s += "(" + (e.getA().getI() + 1) + "," + (e.getB().getI() + 1) + ") ";
        }
        s += "}";
        return s;
    }
}
