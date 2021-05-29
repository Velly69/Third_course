package second.lab.graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable<Vertex> {
    private final int i;
    private final Point point;
    private List<Vertex> in;
    private List<Vertex> out;
    private int inWeight;
    private int outWeight;

    public Vertex(int i, Point point) {
        this.i = i;
        this.point = point;
        this.in = new ArrayList<>();
        this.out = new ArrayList<>();
        this.inWeight = 1;
        this.outWeight = 1;
    }

    public void addIn(Vertex vertex) {
        in.add(vertex);
    }

    public void addOut(Vertex vertex) {
        out.add(vertex);
    }

    public float getX() {
        return point.getX();
    }

    public float getY() {
        return point.getY();
    }

    public int getInWeight() {
        return this.inWeight;
    }

    public void setInWeight(int weight) {
        this.inWeight = weight;
    }

    public int getOutWeight() {
        return this.outWeight;
    }

    public void setOutWeight(int weight) {
        this.outWeight = weight;
    }

    public List<Vertex> getIn() {
        return in;
    }

    public List<Vertex> getOut() {
        return out;
    }

    public int getI() {
        return this.i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return point.equals(vertex.point);
    }

    @Override
    public int compareTo(Vertex vertex) {
        if (this.equals(vertex)) {
            return 0;
        }
        if (Float.compare(getY(), vertex.getY()) < 0) {
            return -1;
        }
        if (Float.compare(getY(), vertex.getY()) == 0 && Float.compare(getX(), vertex.getX()) > 0) {
            return -1;
        }
        return 1;
    }

    @Override
    public String toString() {
        String s = "Vertex{" +
                "i=" + i +
                ", point=" + point.toString() +
                ", in=[";
        for (Vertex v : in) {
            s += v.i + " ";
        }
        s += "], out=[";
        for (Vertex v : out) {
            s += v.i + " ";
        }
        s += "]}";
        return s;
    }
}
