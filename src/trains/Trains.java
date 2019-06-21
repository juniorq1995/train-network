package trains;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Trains {

	public static int custom_path_distance(Graph agraph, String[] path) {
		int  distance = 0;
		boolean containsNeighbor = false;
		for(int i = 0; i < path.length-1; i ++) {
			// reset checker
			containsNeighbor = false;
			// Get next vertex from list
			Vertex vert = agraph.get_vertex(path[i]);
			for(int j = 0; j < vert.get_num_neighbors(); j++) {
				
				Vertex w = vert.get_connections()[j].get_neighbor();
				// Get each edge
				// Check edge
				if(w.get_id().equals(path[i+1])) {
					distance = distance + vert.get_weight(w);
					if (i != path.length-1 && i != 0) {
						distance = distance + 2;
					}
					containsNeighbor = true;
					break;
				}

				
			}
			if(!containsNeighbor) {
				return 0;
			}
		}
		return distance;
	}

	public static int count_paths(Graph agraph, Vertex start, Vertex target, int constraint, int mode) {
		int count = 0;
		// Mode for Max Distance Not Included
		if(mode == 0) {
			count = count_paths_max_distance(start,target,constraint,0,0);
		}
		// Mode for Exact Stops
		if(mode == 1) {
			count = count_paths_exact_stops(start,target,constraint,0,0);
		}
		// Mode for Max Stops Included
		if(mode == 2) {
	        count = count_paths_max_stops(start,target,constraint,0,0);
		}
		return count;
	}

	public static int count_paths_max_distance(Vertex start, Vertex target, int maxDist, int currDist, int pathCount) {
		// If current vertex is same as destination, then inc count
		if(start.get_id().equals(target.get_id()) && currDist > 0) {
			pathCount++;
		}
		for(int i = 0; i < start.get_num_neighbors(); i++) {
			Vertex w = start.get_connections()[i].get_neighbor();
			if(currDist + start.get_weight(w) < maxDist) {
				pathCount = count_paths_max_distance(w,target,maxDist,currDist + start.get_weight(w),pathCount);
			}
		}
		return pathCount;
	}

	public static int count_paths_exact_stops(Vertex start, Vertex target, int exactStops, int currStops, int pathCount) {
		// If current vertex is same as destination, then inc count
		if(start.get_id().equals(target.get_id()) && currStops > 0 && currStops == exactStops) {
			pathCount++;
		}
		for(int i = 0; i < start.get_num_neighbors(); i++) {
			Vertex w = start.get_connections()[i].get_neighbor();
			if(currStops < exactStops) {
				pathCount = count_paths_exact_stops(w,target,exactStops,currStops + 1,pathCount);
			}
		}
		return pathCount;
	}

	public static int count_paths_max_stops(Vertex start, Vertex target, int maxStops, int currStops, int pathCount) {
		// If current vertex is same as destination, then inc count
		if(start.get_id().equals(target.get_id()) && currStops > 0) {
			pathCount++;
		}
		for(int i = 0; i < start.get_num_neighbors(); i++) {
			Vertex w = start.get_connections()[i].get_neighbor();
			if(currStops < maxStops) {
				pathCount = count_paths_max_stops(w,target,maxStops,currStops + 1,pathCount);
			}
		}
		return pathCount;
	}
	
	public static void dijkstra_shortest_path(Graph g,Vertex start, Vertex target) {
		// print '''Dijkstra's shortest path'''
		// Set the distance for the start node to zero
		start.set_distance(0);
		// If start and target are same nodes
		// Split the node into two
		boolean needsMerging = false;
	    if(start.get_id().equals(target.get_id())) {
	    	// Splits the node
	        // creating a clone at the ends of edges
	        g.node_split(start);
	        target = g.get_vertex(target.get_id().toLowerCase());
	        needsMerging = true;
	    }
	    // Put tuple pair into the priority queue
	     Queue unvisited_queue = new Queue(g.get_vertices(),g.size());
	     while(unvisited_queue.get_length() > 0) {
	    	 // Pops a vertex with the smallest distance 
	    	 Node uv = unvisited_queue.pop();
	    	 Vertex current = uv.get_vert();

	    	 if(current.get_distance() >= Integer.MAX_VALUE && !current.visited()) {
	    		 current.set_visited(true);
	    		 unvisited_queue.push(uv);
	    		 continue;
	    	 }
	    	 else {
	    		 current.set_visited(true);
	    	 }
	    	 // For next in v.adjancet
	    	 Edge[] adjacentNodes = current.get_connections();
	    	 Vertex next;
	    	 for(int i = 0; i < current.get_num_neighbors();i++) {
	    		 next = adjacentNodes[i].get_neighbor();
	
	    		 int new_dist;
	    		 if(current.get_distance() == Integer.MAX_VALUE) {
	    			 new_dist = Integer.MAX_VALUE;
	    		 }
	    		 else {
	    			 new_dist = current.get_distance() + current.get_weight(next);
	    		 }
    			 if(new_dist < next.get_distance()) {
    				 next.set_distance(new_dist);
    				 next.set_previous(current);
    			 }
	    		 
	    	 }

	    	 
	     }
		if(needsMerging) {
			g.merge_nodes(start, target);
		}
	}

	public static Graph import_graph(String filename) {
		try {
			Graph g = new Graph();
			File file = new File(filename);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String lines = bufferedReader.readLine();
			stringBuffer.append(lines);
			fileReader.close();
			String line = stringBuffer.toString();
			
			String[] edges = line.split(",");
			// Add vertices
			for(int i = 0; i < edges.length; i++) {
				String[] info = edges[i].split("");
				g.add_vertex(info[0]);
				g.add_vertex(info[1]);
			}
			for(int i = 0; i < edges.length; i++) {
				String[] info = edges[i].split("");
				String stringWeight = "";
				for(int j = 2; j < info.length;j++) {
					stringWeight = stringWeight + info[j];
				}
				
				int weight = Integer.parseInt(stringWeight);
				g.add_edge(info[0],info[1],weight);
			}
			// add edges
			
			return g;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.print("Enter a file name: ");
		String filename = reader.nextLine(); // Scans the next token of the input as a string
		reader.close();
	    Graph g = import_graph(filename);
	   
	    String distance;
	    // print 'Graph data:'

	    distance = Integer.toString(custom_path_distance(g, new String[]{"A","B","C"}));
	    if(distance.equals("0")) {
	    	distance = "NO SUCH PATH EXISTS";
	    }
	    System.out.println("Output #1: " + distance); // Should be 9
	    
	    distance = Integer.toString(custom_path_distance(g, new String[]{"A","D"}));
	    if(distance.equals("0")) {
	    	distance = "NO SUCH PATH EXISTS";
	    }
	    System.out.println("Output #2: " + distance); // Should be 5
	    
	    distance = Integer.toString(custom_path_distance(g, new String[]{"A","D","C"}));
	    if(distance.equals("0")) {
	    	distance = "NO SUCH PATH EXISTS";
	    }
	    System.out.println("Output #3: " + distance); // Should be 13
	    
	    distance = Integer.toString(custom_path_distance(g, new String[]{"A","E","B","C","D"}));
	    if(distance.equals("0")) {
	    	distance = "NO SUCH PATH EXISTS";
	    }
	    System.out.println("Output #4: " + distance); // Should be 22
	    
	    distance = Integer.toString(custom_path_distance(g, new String[]{"A","E","D"}));
	    if(distance.equals("0")) {
	    	distance = "NO SUCH PATH EXISTS";
	    }
	    System.out.println("Output #5: " + distance); // Should be NO SUCH ROUTE
	    
	    distance = Integer.toString(count_paths(g,g.get_vertex("C"),g.get_vertex("C"),3,2));
	    if(distance.equals("0")) {
	    	distance = "NO SUCH PATH EXISTS";
	    }
	    System.out.println("Output #6: " + distance); // Should be 2
	    
	    distance = Integer.toString(count_paths(g,g.get_vertex("A"),g.get_vertex("C"),4,1));
	    System.out.println("Output #7: " + distance); // Should be 3

	    dijkstra_shortest_path(g,g.get_vertex("A"), g.get_vertex("C"));
	    distance = Integer.toString(g.get_vertex("C").get_distance());
	    if(distance.equals("0")) {
	    	distance = "NO SUCH PATH EXISTS";
	    }
	    System.out.println("Output #8: " + distance); // Should be 9

	    g.reset();

	    dijkstra_shortest_path(g,g.get_vertex("B"), g.get_vertex("B"));
	    distance = Integer.toString(g.get_vertex("B").get_distance());
	    if(distance.equals("0")) {
	    	distance = "NO SUCH PATH EXISTS";
	    }
	    System.out.println("Output #9: " + distance); // Should be 9
	    
	    g.reset();
	    
	    distance = Integer.toString(count_paths(g,g.get_vertex("C"),g.get_vertex("C"),30,0));
	    if(distance.equals("0")) {
	    	distance = "NO SUCH PATH EXISTS";
	    }
	    System.out.println("Output #10: " + distance); // Should be 7
    }
}