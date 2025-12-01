package project_Phase_2_CSC212;

public class Customer {

	private int customerId;
	private String name;
	private String email;

	private AVLTree<Order> orderTree = new AVLTree<Order>();

	
	public Customer(String name, String email, int customerId) {

		this.customerId = customerId;
		this.email = email;
		this.name = name;
		
		orderTree = new AVLTree<Order>();

	}

	public void addToOrderTree(Order order) {
		this.orderTree.insert(order, order.getOrderId());
	}

	public void viewOrderHistory() {
		
		if (orderTree.empty()) {
			System.out.println("There are no orders ");
			return;
		}

		orderTree.printInorder();
		System.out.println();
	}

	public void makeReview(int rating, String comment, Product product) {
		product.addReview(rating, comment, this);
	}

	// edits the current review if it belongs to the customer
	public boolean editReview(int reviewId, int rating, String comment, Product product) {
		
		Review review = product.getReviewList().retrive();
		
		if (review.getReviewer().equals(this)) {
			
			product.editReview(rating, comment, this);
			
			return true;
		}
		
		
		return false;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AVLTree<Order> getOrderTree() {
		return orderTree;
	}

	
	public String toString() {
		return "ID: " + customerId + ", name: " + name + ", email: " + email;
	}
	
}
