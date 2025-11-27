package project_Phase_2_CSC212;

public class AVLNode<T> {
	
	
	public T data;
	int key;
	
	public AVLNode<T> left;
	public AVLNode<T> right;
	
	public int height;
	
	
	public AVLNode(T data, int key) {
		
		this.data = data;
		this.key = key;
		
	}
	
	
	public AVLNode(AVLNode<T> node) {
		
		data = node.data;
		key = node.key;
		left = node.left;
		right = node.right;
		
	}
	
}
