package project_Phase_2_CSC212;

public class CustomPriorityQueue <T> {

	private PQNode<T> head;
	private PQNode<T> current;
	
	private int length;
	
	public CustomPriorityQueue() {
		
	}
	
	
	public PQElement<T> enquiry() {
		
		PQElement<T> elem = new PQElement<T>(head.data, head.priority);
		
		return elem;
	}
	
	
	public boolean full() {
		return false;
	}
	
	
	public int length() {
		return length;
	}
	
	
	public boolean first() {
		return current == head;
	}
	
	
	public boolean last() {
		return current.next == null;
	}
	
	
	public void findFirst() {
		current = head;
	}
	
	
	public void findNext() {
		current = current.next;
	}
	
	
	public T retrive() {
		return current.data;
	}
	
	
	public void update(T data) {
		current.data = data;
	}
	
	
	public void enqueue(T data, int priority) {
		
		PQNode<T> newNode = new PQNode<T>(data, priority);
		PQNode<T> current = head;
		PQNode<T> previous = null;
		
		if (head == null || head.priority < priority) {
			newNode.next = head;
			this.current = head = newNode;
			
			length++;
			
			return;
		}
		
		// search for the correct location based on priority
		while ((current != null) && (current.priority >= priority)) {
			
			previous = current;
			current = current.next;
			
		}
		
		newNode.next = current;
		previous.next = newNode;
		this.current = newNode;
		length++;
	}
	
	
	public PQElement<T> serve() {
		
		PQElement<T> data = new PQElement<T>(head.data, head.priority);
		
		head = head.next;
		length--;
		
		current = head;
		
		return data;
	}
	
	
	public void printPQ() {
		
		PQNode<T> temp = head;
		
		while (temp != null) {
			System.out.println(temp.data);
			
			temp = temp.next;
		}
		
		System.out.println();
	}
	
}
