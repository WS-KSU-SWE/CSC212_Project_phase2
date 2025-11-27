package project_Phase_2_CSC212;

public class DoubleLinkedList<T> {

	private Node<T> head;
	private Node<T> current;
	private int length;
	
	DoubleLinkedList() {
		
	}
	
	
	public boolean full() {
		return false;
	}
	
	
	public boolean empty() {
		return head == null;
	}
	
	
	public boolean first() {
		return current == head;
	}
	
	
	public boolean last() {
		return current.next == null;
	}
	
	
	public int getLength() {
		return length;
	}
	
	
	public void findFirst() {
		current = head;
	}
	
	
	public void findNext() {
		current = current.next;
	}
	
	
	public void findPrevious() {
		current = current.prevoius;
	}
	
	
	public T retrieve() {
		return current.data;
	}
	
	
	public void update(T data) {
		current.data = data;
	}
	
	
	// inserts AFTER current
	public void insert(T data) {
		
		Node<T> newNode = new Node<T>(data);
		
		if (empty()) {
			head = current = newNode;
			length++;
			
			return;
		}
		
		newNode.prevoius = current;
		newNode.next = current.next;
		
		if (current.next != null) {
			current.next.prevoius = newNode;
		}
		
		current.next = newNode;
		current = current.next;
		
		length++;
	}
	
	
	public void insertFirst(T data) {
		
		Node<T> newNode = new Node<T>(data);
		
		if (empty()) {
			current = head = newNode;
			length++;
			
			return;
		}
		
		newNode.next = head;
		head.prevoius = newNode;
		
		head = current = newNode;
		
		length++;
	}
	
	
	// removes current node
	public void remove() {
		
		if (current == head) {
			head = head.next;
		}
		else {
			current.prevoius.next = current.next;
		}
		
		if (current.next != null) {
			
			current.next.prevoius = current.prevoius;
			current = current.next;
			
		}
		else {
			current = head;
		}
		
		length--;
	}
	
	
	public void printList() {
		
		Node<T> temp = head;
		
		while (temp != null) {
			
			System.out.println(temp.data);
			
			temp = temp.next;
		}
		
	}
	
}
