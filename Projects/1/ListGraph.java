import java.util.*;

public class ListGraph implements Graph {
    private HashMap<String, LinkedList<String>> nodes = new HashMap<>();

    public boolean addNode(String n) {
        if (nodes.containsKey(n)){
            return false;
        }
        LinkedList<String> l= new LinkedList<>();
        nodes.put(n,l);
        return true;
    } //good1

    public boolean addEdge(String n1, String n2) {
        if ((nodes.containsKey(n1) == false || nodes.containsKey(n2) == false)) {
            throw new NoSuchElementException();
        }
        for (String n : nodes.get(n1)) {
            if (n == n2) {
                return false;
            }
        }
        LinkedList<String> temp = nodes.get(n1);
        temp.add(n2);
        nodes.put(n1, temp);
        return true;
    }//good1

    public boolean hasNode(String n) {
        return nodes.containsKey(n);
    }//good1

    public boolean hasEdge(String n1, String n2) {
        LinkedList<String> tempEdges = nodes.get(n1);
        if (tempEdges == null) { return false; }
        for (String n : nodes.get(n1)) {
            if (n == n2) {
                return true;
            }
        }
        return false;
    }//good1

    public boolean removeNode(String n) {
        if (!nodes.containsKey(n)){
            return false;
        }
        for (String name:nodes.keySet()) {
            nodes.get(name).remove(n);
        }
        nodes.remove(n);
        return true;
    }//good1

    public boolean removeEdge(String n1, String n2) {
        if ((nodes.containsKey(n1) == false || nodes.containsKey(n2) == false)) {
            throw new NoSuchElementException();
        }
        LinkedList<String> temp = nodes.get(n1);
        int temp_size = temp.size();
        temp.removeFirstOccurrence(n2);
        if (temp.size() == temp_size-1) {
            nodes.put(n1, temp);
            return true;
        }
        return false;
    }// good1

    public List<String> nodes() {
        List<String> AllNodes = new ArrayList<String>();
        for (String name : nodes.keySet()) {
            AllNodes.add(name);
        }
        return AllNodes;
    }//good1

    public List<String> succ(String n) {
        if (!nodes.containsKey(n)) {
            throw new NoSuchElementException(); }
        return nodes.get(n);
    } //good1

    public List<String> pred(String n) {
        if (nodes.containsKey(n)){
            List<String> PredNodes = new ArrayList<String>();
            for (String key : nodes.keySet()){
                for(String val :  nodes.get(key)){
                    if (val.equals(n)){
                        PredNodes.add(key);
                        break;
                    }
                }
            }
            return PredNodes;
        }
        throw new NoSuchElementException();
    } //good1

    public Graph union(Graph g) {
        Graph NewGraph = new ListGraph();
        if (this.nodes.isEmpty() &&
                g.nodes().isEmpty()){
            return NewGraph;
        }
        else if (this.nodes.isEmpty() &&
                !(g.nodes().isEmpty())){
            return g;
        }
        else if (!(this.nodes.isEmpty()) &&
                g.nodes().isEmpty()){
            return this;
        }
        for (String key : this.nodes()) {
            NewGraph.addNode(key);
        }
        for (String key : g.nodes()) {
            NewGraph.addNode(key);
        }
        for (String key : this.nodes()) {
            List<String> successors = this.succ(key);
            if (!successors.isEmpty()) {
                for (String a_key : successors) {
                    NewGraph.addEdge(key, a_key);
                }
            }
        }
        for (String key : g.nodes()) {
            List<String> successors = g.succ(key);
            if (!successors.isEmpty()) {
                for (String a_key : successors) {
                    NewGraph.addEdge(key, a_key);
                }
            }
        }
        return NewGraph;
    } //good1

    public Graph subGraph(Set<String> nodes) {
        Graph NewGraph = new ListGraph();
        if (nodes.isEmpty()) {
            return NewGraph;
        }
        if (this.nodes().isEmpty()){
            return this;
        }
        for (String a_node : nodes){
            if (this.nodes().contains(a_node)){
                NewGraph.addNode(a_node);
            }
        }
        for (String a_node:NewGraph.nodes()){
            List<String> temp = this.succ(a_node);
            for (String element : temp){
                if (NewGraph.nodes().contains(element)){
                    NewGraph.addEdge(a_node, element);
                }
            }
        }
        return NewGraph;
    } //good1

    public boolean connected(String n1, String n2) {
        if ((nodes.containsKey(n1) == false || nodes.containsKey(n2) == false)) {
            throw new NoSuchElementException();
        }
        Queue<String> q = new LinkedList<>();
        q.add(n1);
        while (!q.isEmpty()) {
            for (String edge : this.succ(q.poll())) {
                if (edge == n2) {
                    return true;
                }
                q.add(edge);
            }
        }
        return false;
    }//good1
}
