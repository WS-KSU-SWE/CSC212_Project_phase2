package project_Phase_2_CSC212;

public class PQElement<T> {

	private T data;
	private int priority;
	
	
	public PQElement(T data, int priority) {
		this.data = data;
		this.priority = priority;
	}
	
	
	public T getData() {
		return data;
	}
	
	
	public int getPriority() {
		return priority;
	}
	
}
