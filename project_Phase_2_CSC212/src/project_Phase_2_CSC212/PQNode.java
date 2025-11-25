package project_Phase_2_CSC212;

public class PQNode<T> {

	public T data;
	
	public PQNode<T> next;
	public int priority;
	
	
	public PQNode(T data, int priority) {
		
		this.data = data;
		this.priority = priority;
		
	}
	
}
