package trains;

public class Queue {
	private Node firstNode;
	private int queueLength;
	private Node smallestNode;
	
	public Queue(Vertex[] vert_array, int length) {
		// Double the size since there will be pushing and popping ops
		this.queueLength = length;
		this.firstNode = new Node(vert_array[0]);
		this.smallestNode = firstNode;
		Node newNode;
		for(int i =1; i < queueLength; i++) {
			newNode = new Node(vert_array[i]);
			newNode.set_next(firstNode);
			firstNode.set_prev(newNode);
			firstNode = newNode;
			
			
		}
		this.smallestNode = this.findSmallest();
	}
	public int get_length() {
		return this.queueLength;
	}
	// Only works if ONLY ONE node is out of place
	public Node findSmallest() {
		Node iterator = firstNode;
		Node smallestNode = firstNode;
		while(iterator != null) {
			if(iterator.get_distance() < smallestNode.get_distance()) {
				smallestNode = iterator;
			}
			iterator = iterator.get_next();
		}
		return smallestNode;
	}
	public Node pop() {
		this.queueLength--;
		// IF its the last node in queue
		if(this.queueLength == 0) {
			Node temp = this.firstNode;
			this.firstNode = null;
			return temp;
		}
		// If smallest is first node
		Node temp = this.smallestNode;
		if(this.smallestNode.get_prev() == null) {
			this.smallestNode.get_next().set_prev(null);
			this.firstNode = this.smallestNode.get_next();
		}
		// if smallest is last node
		else if(this.smallestNode.get_next() == null) {
			this.smallestNode.get_prev().set_next(null);
		}
		else {
			this.smallestNode.get_next().set_prev(this.smallestNode.get_prev());
			this.smallestNode.get_prev().set_next(this.smallestNode.get_next());
		}
		this.smallestNode = this.findSmallest();
		temp.set_next(null);
		temp.set_prev(null);
		return temp;
	}
	public void push(Node n) {
		// Add to queue, then sort
		Node iterator = this.firstNode;
		// Go to end of node list
		while(iterator != null){
			if(iterator.get_next() == null) {
				break;
			}
			iterator = iterator.get_next();
		}
		iterator.set_next(n);
		n.set_next(null);
		n.set_prev(iterator);
		this.queueLength++;
		if(n.get_distance() < smallestNode.get_distance()) {
			this.smallestNode = n;
		}
	}
	public int length() {
		return this.queueLength;
	}

}