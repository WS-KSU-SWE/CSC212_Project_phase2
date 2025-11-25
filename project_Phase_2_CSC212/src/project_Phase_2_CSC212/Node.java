package project_Phase_2_CSC212;

public class Node<T> {

	public T data;
	
	public Node<T> next;
	public Node<T> prevoius;
	
	
	public Node(T data) {
		this.data = data;
	}
	public T getData() {
		return data;
	}
	
}
