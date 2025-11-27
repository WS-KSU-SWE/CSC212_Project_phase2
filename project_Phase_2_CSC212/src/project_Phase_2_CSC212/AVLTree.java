package project_Phase_2_CSC212;

public class AVLTree<T> {
	
	private AVLNode<T> root;
	private AVLNode<T> current;
	
	
	public AVLTree() {
		
	}
	
	
	public boolean empty() {
		return root == null;
	}
	
	
	public boolean full() {
		return false;
	}
	
	
	public T retrieve() {
		return current.data;
	}
	
	
	public boolean findKey(int key) {
		
		AVLNode<T> current = root;
		AVLNode<T> previous = null;
		
		while (current != null) {
			
			previous = current;
			
			if (current.key == key) {
				
				this.current = current;
				
				return true;
			}
			
			if (key > current.key) {
				current = current.right;
			}
			else {
				current = current.left;
			}
		
		}
		
		this.current = previous;
		
		return false;
	}
	
	
	public void printPreorder() {
		printPreorderRec(root);
		System.out.println();
	}
	
	private void printPreorderRec(AVLNode<T> start) {
		
		if (start == null) {
			return;
		}
		
		
		System.out.print(start.data + " ");
		
		if (start.left != null) {
			printPreorderRec(start.left);
		}
		
		if (start.right != null) {
			printPreorderRec(start.right);
		}
		
	}
	
	
	public void printInorder() {
		printInorderRec(root);
		System.out.println();
	}
	
	private void printInorderRec(AVLNode<T> start) {
		
		if (start == null) {
			return;
		}
		
		
		if (start.left != null) {
			printInorderRec(start.left);
		}
		
		System.out.print(start.data + " ");
		
		if (start.right != null) {
			printInorderRec(start.right);
		}
		
	}
	
	
	public void printPostorder() {
		printPostorderRec(root);
		System.out.println();
	}
	
	private void printPostorderRec(AVLNode<T> start) {
		
		if (start == null) {
			return;
		}
		
		
		
		
		if (start.left != null) {
			printPostorderRec(start.left);
		}
		
		
		if (start.right != null) {
			printPostorderRec(start.right);
		}
		
		System.out.print(start.data + " ");
		
	}
	
	
	public void traverse(TraverseOrder ord) {
		
		switch (ord) {
		
			case preorder:
				printPreorder();
				break;
				
			case inorder:
				printInorder();
				break;
				
			case postorder:
				printPostorder();
				break;
		}
		
	}
	
	
	private int height(AVLNode<T> node) {
		
		if (node == null) {
			return -1;
		}
		
		return node.height;
	}
	
	
	private AVLNode<T> rotate_RR(AVLNode<T> pivot) {
		
		// swap nodes
		AVLNode<T> tempRight = pivot.right;
		pivot.right = tempRight.left;
		tempRight.left = pivot;
		
		// correct height
		pivot.height = Math.max(height(pivot.left), height(pivot.right)) + 1;
		tempRight.height = Math.max(height(tempRight.left), height(tempRight.right)) + 1;
		
		
		return tempRight;
	}
	
	
	private AVLNode<T> rotate_LL(AVLNode<T> pivot) {
		
		AVLNode<T> tempLeft = pivot.left;
		pivot.left = tempLeft.right;
		tempLeft.right = pivot;
		
		pivot.height = Math.max(height(pivot.left), height(pivot.right)) + 1;
		tempLeft.height = Math.max(height(tempLeft.left), height(tempLeft.right)) + 1;
		
		return tempLeft;
	}
	
	
	private AVLNode<T> rotate_RL(AVLNode<T> pivot) {
		
		pivot.right = rotate_LL(pivot.right);
	
		return rotate_RR(pivot);
	}
	
	
	private AVLNode<T> rotate_LR(AVLNode<T> pivot) {
		
		pivot.left = rotate_RR(pivot.left);
		
		
		return rotate_LL(pivot);
	}
	
	
	public BooleanWrapper insert(T data, int key) {
		
		AVLNode<T> newNode = new AVLNode<T>(data, key);
		BooleanWrapper isInserted = new BooleanWrapper(true);
		
		root = rec_insert(newNode, root, isInserted);
		
		
		return isInserted;
	}
	
	
	public int getBalance(AVLNode<T> node) {
		
		if (node == null) {
			return 0;
		}
		
		return height(node.right) - height(node.left); 
	}
	
	
	private AVLNode<T> rec_insert(AVLNode<T> newNode, AVLNode<T> current, BooleanWrapper inserted) {
		
		int balance;

		
		// insert as usual
		if (current == null) {
			current = new AVLNode<T>(newNode);
		}
		
		else if (newNode.key > current.key) {
			current.right = rec_insert(newNode, current.right, inserted);
		}
		
		else if (newNode.key < current.key) {
			current.left = rec_insert(newNode, current.left, inserted);
		}
		// the node is already in the tree
		else {
			inserted.set(false);
		}
		
		current.height = 1 + Math.max(height(current.left), height(current.right));
		
		balance = getBalance(current);
		
		// rotate if needed
		
		// RR (Rotate left)
		if (balance > 1 && newNode.key > current.right.key) {
			current = rotate_RR(current);
		}
		// RL (Rotate LL then RR)
		else if (balance > 1 && newNode.key < current.right.key) {
			current = rotate_RL(current);
		}
		
		// LL (Rotate right)
		else if (balance < -1 && newNode.key < current.left.key) {
			current = rotate_LL(current);
		}
		// LR (Rotate RR then LL)
		else if (balance < -1 && newNode.key > current.left.key) {
			current = rotate_LR(current);
		}
		
		return current;
	}
	
	
	// finds the minimum key in a subtree
	private AVLNode<T> find_min(AVLNode<T> start) {
		
		if (start == null) {
			return null;
		}
		
		
		while (start.left != null) {
			start = start.left;
		}
		
		return start;
	}
	
	
	public boolean removeKey(int key) {
		
		BooleanWrapper removed = new BooleanWrapper(false);
		
		current = root = deleteNode(key, root, removed);
		
		return removed.get();
	}
	
	
	private AVLNode<T> deleteNode(int key, AVLNode<T> current, BooleanWrapper flag) {
		
		AVLNode<T> min = null;
		AVLNode<T> child = null;
		
		int balance;
		
		// remove as usual
		if (current == null) {
			return null;
		}
		
		if (key < current.key) {
			current.left = deleteNode(key, current.left, flag);
		}
		else if (key > current.key) {
			current.right = deleteNode(key, current.right, flag);
		} 
		// found
		else {
			
			flag.set(true);
			
			// 2 children
			if (current.left != null && current.right != null) {
				
				min = find_min(current.right);
				
				// copy data from min
				current.data = min.data;
				current.key = min.key;
				
				// remove min
				current.right = deleteNode(min.key, current.right, flag);
			}
			// one child (also works for no child)
			else {
				
				if (current.left == null) {
					child = current.right;
				}
				else if (current.right == null) {
					child = current.left;
				}

				return child;
			}

		}
		
		
		current.height = 1 + Math.max(height(current.left), height(current.right));
		
		balance = getBalance(current);
		
		// rotate if needed
		
		// RR (Rotate left)
		if (balance > 1 && getBalance(current.right) >= 0) {
			current = rotate_RR(current);
		}
		// RL (Rotate LL then RR)
		else if (balance > 1 && getBalance(current.right) < 0) {
			current = rotate_RL(current);
		}
		
		// LL (Rotate right)
		else if (balance < -1 && getBalance(current.left) <= 0) {
			current = rotate_LL(current);
		}
		// LR (Rotate RR then LL)
		else if (balance < -1 && getBalance(current.left) > 0) {
			current = rotate_LR(current);
		}
		
		return current;
		
	}
	
}
