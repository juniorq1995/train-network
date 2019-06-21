package trains;

public class Node {
	private Node previous;
	private Node next;
	private Vertex vert;
	private int distance;
	
	public Node(Vertex v) {
		previous = null;
		next = null;
		this.vert = v;
		this.distance = v.get_distance();
	}
	// Copy contents over
	public Node(Vertex v, Node next, int distance) {
		this.vert = v;
		this.next = next;
		this.distance = distance;
	}
	
	public void set_prev(Node n) {
		this.previous = n;
	}
	public void set_next(Node n) {
		this.next = n;
	}
	public Node get_next() {
		return this.next;
	}
	public Node get_prev() {
		return this.previous;
	}
	public Vertex get_vert() {
		return this.vert;
	}
	public int get_distance() {
		return this.vert.get_distance();
	}
	public void set_distance(int dist) {
		this.distance = dist;
		this.vert.set_distance(dist);
	}
}