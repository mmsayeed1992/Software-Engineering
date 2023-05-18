import java.util.*;

public class EdgeGraphAdapter implements EdgeGraph {

    private Graph g;

    public EdgeGraphAdapter(Graph g) { this.g = g; }

    public boolean addEdge(Edge e) {
        if (this.g.hasEdge(e.getSrc(), e.getDst())) {
            return false;
        }
        this.g.addNode(e.getSrc());
        this.g.addNode(e.getDst());
        this.g.addEdge(e.getSrc(), e.getDst());
        return true;
    }// good1

    public boolean hasNode(String n) {
        return g.hasNode(n);
    }// good1

    public boolean hasEdge(Edge e) {
        return g.hasEdge(e.getSrc(), e.getDst());
    }// good1

    public boolean removeEdge(Edge e) {
        if (!g.hasEdge(e.getSrc(), e.getDst())) {
            return false;
        }
        g.removeEdge(e.getSrc(), e.getDst());
        if (g.succ(e.getSrc()).size() == 0) { g.removeNode(e.getSrc());}
        if (g.succ(e.getDst()).size() == 0) { g.removeNode(e.getDst());}
        return true;
    }// good1

    public List<Edge> outEdges(String n) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        if (!g.hasNode(n)) {
            return edges;
        }
        for (String dest : g.succ(n)) {
            edges.add(new Edge(n, dest));
        }
        return edges;
    }// good1

    public List<Edge> inEdges(String n) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        if (!g.hasNode(n)) {
            return edges;
        }
        for (String src : g.pred(n)) {
            edges.add(new Edge(src, n));
        }
        return edges;
    }// good1

    public List<Edge> edges() {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (String src : g.nodes()) {
            for (String dest : g.succ(src)) {
                edges.add(new Edge(src, dest));
            }
        }
        return edges;
    }// good1

    public EdgeGraph union(EdgeGraph g) {
        EdgeGraph new_graph = new EdgeGraphAdapter(new ListGraph());
        for (Edge edge : g.edges()) {
            new_graph.addEdge(edge);
        }
        for (Edge edge : this.edges()) {
            new_graph.addEdge(edge);
        }
        return new_graph;
    }// good1

    public boolean hasPath(List<Edge> e) {
        for (Edge an_e : e) {
            if (!this.hasEdge(an_e)) {
                return false;
            }
        }
        for (int i = 0; i < e.size() - 1; i++) {
            if (!e.get(i).getDst().equals(e.get(i + 1).getSrc())) {
                throw new BadPath();
            }
        }
        return true;
    }// good1
}
