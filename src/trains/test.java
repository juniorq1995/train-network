package trains;

import static org.junit.Assert.*;

import org.junit.Test;

public class test {

	@Test
	public void test() {
		Trains trains = new Trains();
		Graph g = trains.import_graph("input.txt");
		
		assertEquals(9,trains.custom_path_distance(g, new String[]{"A","B","C"}));
		assertEquals(5,trains.custom_path_distance(g, new String[]{"A","D"}));
		assertEquals(13,trains.custom_path_distance(g, new String[]{"A","D","C"}));
		assertEquals(22,trains.custom_path_distance(g, new String[]{"A","E","B","C","D"}));
		assertEquals(0,trains.custom_path_distance(g, new String[]{"A","E","D"}));

		assertEquals(2,trains.count_paths(g,g.get_vertex("C"),g.get_vertex("C"),3,2));
		assertEquals(3,trains.count_paths(g,g.get_vertex("A"),g.get_vertex("C"),4,1));
		assertEquals(7,trains.count_paths(g,g.get_vertex("C"),g.get_vertex("C"),30,0));

	    //trains.dijkstra_shortest_path(g,g.get_vertex("A"), g.get_vertex("C"));
	    //assertEquals(9,g.get_vertex("C").get_distance());
	    //g.reset();

	    trains.dijkstra_shortest_path(g,g.get_vertex("B"), g.get_vertex("B"));
	    assertEquals(9,g.get_vertex("B").get_distance());
	    g.reset();

		
	}

}
