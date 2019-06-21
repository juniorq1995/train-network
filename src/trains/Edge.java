package trains;

public class Edge {
	private Vertex node;
	private int weight;
	
	public Edge(Vertex neighbor, int weight) {
		this.node = neighbor;
		this.weight = weight;
	}
	public Vertex get_neighbor() {
		return this.node;
	}
	public void modify_neighbor(Vertex newNeighbor) {
		this.node = newNeighbor;
	}
	public int get_weight() {
		return this.weight;
	}
}