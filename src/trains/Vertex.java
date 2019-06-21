package trains;

public class Vertex {
	private String id;
	// array of arrays
	private Edge[] adjacentNodes;
	private int distance;
	private boolean visited;
	private Vertex previous;
	private int numNeighbors;
	
	
	public Vertex(String node) {
		this.id = node;
		adjacentNodes = new Edge[10];
		this.distance = Integer.MAX_VALUE;
		this.visited = false;
		this.previous = null;
		this.numNeighbors = 0;
	}

	private Edge[] expand_array(Edge[] array) {
		Edge[] temp = new Edge[this.numNeighbors * 2];
		for(int i= 0; i < array.length; i++) {
			temp[i] = array[i];
		}
		return array;
	}

	public void add_neighbor(Vertex neighbor, int weight) {
		// If there is no more room in array, make new array with room
		if(this.adjacentNodes.length < this.numNeighbors) {
			this.adjacentNodes = this.expand_array(this.adjacentNodes);
		}
		else {
			this.adjacentNodes[numNeighbors] = new Edge(neighbor,weight);
			this.numNeighbors++;
		}
	}

	public void modify_neighbor(Vertex oldNeighbor, Vertex newNeighbor) {
		for(int i = 0; i < this.numNeighbors;i++) {
			if(this.adjacentNodes[i].get_neighbor().get_id().equals(oldNeighbor.get_id())) {
				this.adjacentNodes[i].modify_neighbor(newNeighbor);
			}
		}
	}

	public Edge[] get_connections() {
		return this.adjacentNodes;
	}

	public String get_id() {
		return this.id;
	}

	public int get_weight(Vertex neighbor) {
		int w = 0;
		for(int i = 0; i < numNeighbors; i++) {
			if(neighbor.get_id().equals(this.adjacentNodes[i].get_neighbor().get_id())) {
				w = this.adjacentNodes[i].get_weight();
				return w;
			}
		}
		return 0;
	}
	
	public int get_num_neighbors() {
		return this.numNeighbors;
	}

	public void set_distance(int dist) {
		this.distance = dist;
	}

	public int get_distance() {
		return this.distance;
	}

	public void set_previous(Vertex prev) {
		this.previous = prev;
	}
	public Vertex get_previous() {
		return this.previous;
	}

	public void set_visited(boolean val) {
		this.visited = val;
		
	}
	public boolean visited() {
		return this.visited;
	}

}