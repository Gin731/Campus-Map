package graphs;

import minpq.DoubleMapMinPQ;
import minpq.ExtrinsicMinPQ;

import java.util.*;

/**
 * Topological sorting implementation of the {@link ShortestPathSolver} interface for <b>directed acyclic graphs</b>.
 *
 * @param <V> the type of vertices.
 * @see ShortestPathSolver
 */
public class ToposortDAGSolver<V> implements ShortestPathSolver<V> {
    private final Map<V, Edge<V>> edgeTo;
    private final Map<V, Double> distTo;

    /**
     * Constructs a new instance by executing the toposort-DAG-shortest-paths algorithm on the graph from the start.
     *
     * @param graph the input graph.
     * @param start the start vertex.
     */
    public ToposortDAGSolver(Graph<V> graph, V start) {
        // 0.initialize the data structure
        this.edgeTo = new HashMap<>();
        this.distTo = new HashMap<>();
        // add the root element to the data structure
        edgeTo.put(start, null);
        distTo.put(start, 0.0);
        // TODO: Replace with your code
        List<V> topologyOrder = new ArrayList<>();
        Set<V> visited = new HashSet<>();
        // 1.find the topology order and reverse it
        dfsPostOrder(graph, start, visited, topologyOrder);
        Collections.reverse(topologyOrder);
        // 2.use the topology order to correctly compute the shortest path tree
        for(V from : topologyOrder){
            for(Edge<V> edge : graph.neighbors(from)){
                V to = edge.to;
                double oldDist = distTo.getOrDefault(to, Double.POSITIVE_INFINITY);
                double newDist = distTo.get(from) + edge.weight;

                if(newDist < oldDist){
                    // Since put will add new node if Map does not contain the node, replace the old node if already have it.
                    edgeTo.put(to, edge);
                    distTo.put(to, newDist);
                }
            }
        }
    }

    /**
     * Recursively adds nodes from the graph to the result in DFS postorder from the start vertex.
     *
     * @param graph   the input graph.
     * @param start   the start vertex.
     * @param visited the set of visited vertices.
     * @param result  the destination for adding nodes.
     */
    private void dfsPostOrder(Graph<V> graph, V start, Set<V> visited, List<V> result) {
        // TODO: Replace with your code
        // using DFS to traverse all nodes and added to the result in topology order
        visited.add(start);
        for(Edge<V> e : graph.neighbors(start)){
            if(!visited.contains(e.to)){
                dfsPostOrder(graph, e.to, visited, result);
            }
        }
        result.add(start);
    }

    @Override
    public List<V> solution(V goal) {
        List<V> path = new ArrayList<>();
        V curr = goal;
        path.add(curr);
        while (edgeTo.get(curr) != null) {
            curr = edgeTo.get(curr).from;
            path.add(curr);
        }
        Collections.reverse(path);
        return path;
    }
}
