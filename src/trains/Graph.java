package trains;

public class Graph {
	private Vertex[] vert_array;
	private int num_vertices;
	
	public Graph() {
		vert_array = new Vertex[10];
		num_vertices = 0;
	}
	
	public int size() {
		return this.num_vertices;
	}
	
	public Vertex[] get_vertices() {
		return this.vert_array;
	}


	public void reset() {
		for(int i =0; i < this.num_vertices; i++) {
			vert_array[i].set_distance(Integer.MAX_VALUE);
			vert_array[i].set_previous(null);
			vert_array[i].set_visited(false);
		}
	}

	public void node_split(Vertex node) {
		String miniMe = node.get_id().toLowerCase();
		this.add_vertex(miniMe);
		// For all vertices in graph
		for(int i = 0; i < this.num_vertices; i++) {
			// For all neighbors
			Vertex v = this.vert_array[i];
			for(int j = 0; j < v.get_num_neighbors();j++) {
				Vertex w = v.get_connections()[j].get_neighbor();
				// If neighbor is node we want to split
				if(w.get_id().equals(node.get_id())) {
					v.modify_neighbor(w, this.get_vertex(miniMe));
				}
			}
		}
	}

	public void merge_nodes(Vertex original, Vertex copy) {
		for(int i = 0; i < this.num_vertices; i++) {
			for(int j = 0; j < this.vert_array[i].get_num_neighbors();j++) {
				Vertex w = this.vert_array[i].get_connections()[j].get_neighbor();
				if(copy.get_id().equals(w.get_id())) {
					vert_array[i].modify_neighbor(w, original);
				}
			}
		}
		original.set_distance(copy.get_distance());
	}

	public void add_vertex(String node) {
		Vertex new_vertex;
		if(num_vertices == 0) {
			new_vertex = new Vertex(node);
			this.vert_array[num_vertices] = new_vertex;
			this.num_vertices++;
			return;
		}
		for(int i = 0; i < num_vertices; i++) {
			// Check if node is already present in Graph
			if(this.vert_array[i].get_id().equals(node)) {
				return;
			}
		}
		new_vertex = new Vertex(node);
		this.vert_array[num_vertices] = new_vertex;
		this.num_vertices++;
		return;
		
	}

	public void remove_vertex(String node) {
		for(int i = 0; i < num_vertices; i++) {
			// Check if node is already present in Graph
			if(this.vert_array[i].get_id().equals(node)) {
				this.vert_array[i] = null;
				this.num_vertices--;
				return;
			}
		}
		
	}

	public Vertex get_vertex(String node) {
		for(int i = 0; i < num_vertices; i++) {
			if(this.vert_array[i].get_id().equals(node)) {
				return vert_array[i];
			}
		}
		return null;
	}

	public void add_edge(String from, String to,int cost) {
		int i = 0;
		int j = 0;
		for(;i < this.num_vertices; i++) {
			if(this.vert_array[i].get_id().equals(from)) {
				break;
			}
			if(i == num_vertices-1) {
				this.add_vertex(from);
			}
		}
		for(;j < this.num_vertices; j++) {
			if(this.vert_array[j].get_id().equals(to)) {
				break;
			}
			if(j == num_vertices-1) {
				this.add_vertex(to);
			}
		}
		this.vert_array[i].add_neighbor(this.vert_array[j],cost);
	}
	

}