package project_Phase_2_CSC212;

import java.io.*;
import java.util.*;


public class Store {

	private AVLTree<Product> products;
	private AVLTree<Customer> customers;
	private AVLTree<Order> orders;
	
	
	public Store() {
		
		products = new AVLTree<Product>();
		customers = new AVLTree<Customer>();
		orders = new AVLTree<Order>();
		
	}
	
	public AVLTree<Product> getProducts() {
		return products;
	}


	public AVLTree<Customer> getCustomers() {
		return customers;
	}


	public AVLTree<Order> getOrders() {
		return orders;
	}
	
	// log n
	public boolean addProduct(int productID, String name, double price, int stock) {
		
		Product newProduct = new Product(productID, name, price, stock);
		boolean added = products.insert(newProduct, productID);
		
		return added;
	}

	// O(log n)
	public boolean removeProduct(int productId) {
		
		boolean removed = products.removeKey(productId);
		
		return removed;
	}

	// O(log n)
	public boolean updateProduct(int productId, String name, double price, int stock) {

		Product updatedProduct;
		
		boolean exists = products.findKey(productId); // O(log n)
		
		if (!exists) {
			return false;
		}
		
		updatedProduct = products.retrieve();
		
		updatedProduct.setName(name);
		updatedProduct.setPrice(price);
		updatedProduct.setStock(stock);
		
		return true;
	}
	
	// O(log n)
	public Product searchProduct(int productId) {
		
		boolean exists = products.findKey(productId);
		
		if (!exists) {
			return null;
		}
		
		return products.retrieve();
	}
	
	// O(log n)
	public boolean registerCustomer(int customerId, String name, String email) {
		
		Customer newCustomer = new Customer(name, email, customerId);
		boolean registered = customers.insert(newCustomer, customerId);

		return registered;
	}

	// O(log n)
	public Customer searchCustomer(int customerId) {
		
		boolean exists = customers.findKey(customerId);
		
		if (!exists) {
			return null;
		}
		
		return customers.retrieve();
	}
	
	// O (n log n) This method can be done in O(n) if we linearize the products tree. This was done recursively to demonstrate that we can traverse the tree in order as a user of the ADT 
	public DoubleLinkedList<Product> outOfStockProducts() {
		
		DoubleLinkedList<Product> outOfStock = new DoubleLinkedList<Product>();
		
		if (!products.findNode(Relative.Root)) { // if products is empty
			return null;
		}
		
		outOfStockProductsRec(outOfStock); // O(n log n)
		
		products.findNode(Relative.Root);
		
		return outOfStock;
	}
	
	// this will traverse the tree in-order 
	private void outOfStockProductsRec(DoubleLinkedList<Product> outOfStock) { // O(n log n)
		
		Product parentProduct = products.retrieve();
		
		// if there is a right child, go to it
		if (products.findNode(Relative.RightChild)) {
			outOfStockProductsRec(outOfStock);
		}
		
		// we find the parent after going right all the way
		products.findKey(parentProduct.getProductId());
		
		if (parentProduct.isOutOfStock()) {
			outOfStock.insert(parentProduct);
		}
		
		// if there is a left child, go to it
		if (products.findNode(Relative.LeftChild)) {
			outOfStockProductsRec(outOfStock);
		}
		
	}
	
	// O(n log n)
	public boolean placeOrder(int orderId, int[] productIds, double totalPrice, Date orderDate, int customerId, String status) {
		
		Order order;
		
		DoubleLinkedList<Product> orderProd = new DoubleLinkedList<Product>();
		
		for (int i = 0; i < productIds.length; ++i) {
			
			if (!products.findKey(productIds[i])) { // O (log n)
				return false;
			}		
			orderProd.insert(products.retrieve());
	
		}
		
		if (!customers.findKey(customerId)) {
			return false;
		}
		
		
		order = new Order(orderId, totalPrice, orderDate, status, customers.retrieve(), orderProd);
		
		customers.retrieve().addToOrderList(order);
		
		orders.insert(order, orderId);
		
		return true;
	}
	
	// O(log n)
	public boolean cancelOrder(int orderId) {
		
		boolean exists = orders.findKey(orderId);
		
		if (!exists) {
			return false;
		}
		
		orders.retrieve().setStatus("Cancelled");
		
		return true;
	}
	
	// O(log n)
	public Order searchOrder(int orderId) {
		
		boolean exists = orders.findKey(orderId);
		
		if (!exists) {
			return null;
		}
		
		return orders.retrieve();
	}
	
	// O (n^2)
	public DoubleLinkedList<Review> extractReviews(int customerId) {
		
		Customer customer = searchCustomer(customerId);
		DoubleLinkedList<Review> customerReviews = new DoubleLinkedList<Review>();
		
		DoubleLinkedList<Product> linearizedProducts = products.linearizeInOrder(); // this turns the tree of products to a list of products sorted in order O(n)
		
		linearizedProducts.findFirst();
		
		for (int i = 0; i < linearizedProducts.getLength(); ++i) {
			
			Product product = linearizedProducts.retrieve();
			CustomPriorityQueue<Review> reviewList = product.getReviewList();
			
			reviewList.findFirst();
			
			boolean foundReviewer = false;
			
			for (int j = 0; j < reviewList.length(); ++j) {
				
				Customer reviewer = reviewList.retrive().getReviewer();
				
				if (reviewer.equals(customer)) {
					foundReviewer = true;
					customerReviews.insert(reviewList.retrive());
				}
				// if we already found the reviewer and the current reviewer is not the customer, then we terminate. We can do this because the reviews are ordered by customer id.
				else if (foundReviewer) {
					break;
				}
				
				reviewList.findNext();
			}
			
			linearizedProducts.findNext();
		}
		
		
		return customerReviews;
	}
	
	// O(log n)
	public AVLTree<Order> getCustomerOrders(int customerId) {
		
		boolean exists = customers.findKey(customerId);
		
		if (!exists) {
			return null;
		}
		
		
		return customers.retrieve().getOrderTree();
	}
	
	// O (n)
	public DoubleLinkedList<Customer> getCustomersReviewed(int productId) {
		
		Product product = searchProduct(productId);
		DoubleLinkedList<Customer> customersReviewed = new DoubleLinkedList<Customer>();
		
		CustomPriorityQueue<Review> reviewList;
		
		
		if (product == null) {
			return null;
		}
		
		reviewList = product.getReviewList();
		
		// the review list is already sorted based on customer id
		reviewList.findFirst();
		
		for (int i = 0; i < reviewList.length(); ++i) {
			customersReviewed.insertFirst(reviewList.retrive().getReviewer());
			
			reviewList.findNext();
		}
		
		reviewList.findFirst();
		
		return customersReviewed;
	}
	
	// O(n log n)
	public AVLTree<Order> getOrderBetweenDates(Date startDate, Date endDate) {
		
		AVLTree<Order> ordersBetween = new AVLTree<Order>();
		
		if (!orders.findNode(Relative.Root)) { // if orders is empty
			return null;
		}
		
		getOrderBetweenDatesRec(startDate, endDate, ordersBetween);
		
		
		orders.findNode(Relative.Root);
		
		return ordersBetween;
	}
	
	// traverse in order
	private void getOrderBetweenDatesRec(Date startDate, Date endDate, AVLTree<Order> ordersBetween) { // O(n log n)
		
		Order currentOrder = orders.retrieve();
		Date date = currentOrder.getOrderDate();
		
		// go right
		if (orders.findNode(Relative.RightChild)) {
			getOrderBetweenDatesRec(startDate, endDate, ordersBetween);
		}
		
		// go back to current order and process it
		
		orders.findKey(currentOrder.getOrderId());
		
		if (date.getIntDate() >= startDate.getIntDate() && date.getIntDate() <= endDate.getIntDate()) {
			ordersBetween.insert(currentOrder, currentOrder.getOrderId()); // O(log n)
		}
		
		// go left
		if (orders.findNode(Relative.LeftChild)) {
			getOrderBetweenDatesRec(startDate, endDate, ordersBetween);
		}
		
	}
	
	
	// get common products between 2 customers with an average rating of above 4
	DoubleLinkedList<Product> commonProducts(int customerId1, int customerId2) { // O(n^2)
		
		DoubleLinkedList<Product> products1 = new DoubleLinkedList<Product>();
		DoubleLinkedList<Product> products2 = new DoubleLinkedList<Product>();
		DoubleLinkedList<Product> commonProducts = new DoubleLinkedList<Product>();
		
		DoubleLinkedList<Order> orderList;
		
		
		// get all products related to customer 1
		if (!customers.findKey(customerId1)) {
			
			return commonProducts; // empty linked list
		}
		
		orderList = customers.retrieve().getOrderTree().linearizeInOrder();
		
		orderList.findFirst();
		for (int i = 0; i < orderList.getLength(); ++i) {
			
			DoubleLinkedList<Product> products = orderList.retrieve().getProductList();
			
			products.findFirst();
			
			for (int j = 0; j < products.getLength(); ++j) {
				
				products1.insertFirst(products.retrieve());
				products.findNext();
			}
			
			orderList.findNext();
		}
		
		// get all products related to customer 2
		if (!customers.findKey(customerId2)) {
			
			return commonProducts; // empty linked list
		}
		
		
		orderList = customers.retrieve().getOrderTree().linearizeInOrder();
		
		orderList.findFirst();
		for (int i = 0; i < orderList.getLength(); ++i) {
			
			DoubleLinkedList<Product> products = orderList.retrieve().getProductList();
			
			products.findFirst();
			
			for (int j = 0; j < products.getLength(); ++j) {
				
				products2.insertFirst(products.retrieve());
				products.findNext();
			}
			
			orderList.findNext();
		}
		
		
		// finding common products with an average rating greater than 4
		
		// no need to do findFirst for product1
		
		for (int i = 0; i < products1.getLength(); ++i) {
			
			products2.findFirst();
			
			for (int j = 0; j < products2.getLength(); ++j) {
				
				Product prod1 = products1.retrieve();
				Product prod2 = products2.retrieve();
				
				//System.out.println(prod1 + " " + prod1.getAverageRating());
				//System.out.println(prod2 + " " + prod2.getAverageRating());
				
				
				if (prod1.equals(prod2) && prod1.getAverageRating() > 4) {
					commonProducts.insert(prod1);
				}
				
				products2.findNext();
			}
			
			products1.findNext();
		}
		
		
		return commonProducts;
	}
	
	// O(n^2)
	public DoubleLinkedList<Product> getTop3Products() {
		
		DoubleLinkedList<Product> top3 = new DoubleLinkedList<Product>();
		
		CustomPriorityQueue<Product> temp = new CustomPriorityQueue<Product>();
		
		
		DoubleLinkedList<Product> linearizedProducts = products.linearizeInOrder();
		
		linearizedProducts.findFirst();
		
		for (int i = 0; i < linearizedProducts.getLength(); ++i) {
			
			Product product = linearizedProducts.retrieve();
			
			int ratting = (int) Math.ceil(product.getAverageRating() * 100); // get a better scale of the avg rating: 4.5 > 4 ==> 4 = 4 | 450.33 > 400.2 ==> 450 > 400  
			
			temp.enqueue(product, ratting);
		
			linearizedProducts.findNext();
		}
		
		// this is done to avoid issues when products has less than 3 elements
		for (int i = 0; i < linearizedProducts.getLength(); ++i) {
			
			if (i >= 3) {
				break;
			}
			
			PQElement<Product> topProduct = temp.serve();
			
			top3.insert(topProduct.getData());
			
		}
		
		
		return top3;
	}
	
	// O(n log n) 
	public AVLTree<Product> getProductsBetweenPrices(double minPrice, double maxPrice) {
		
		if (minPrice < 0 || maxPrice < 0) {
			throw new NegativeValueException("Price cannot be negative.");
		}
		
		AVLTree<Product> productsBetween = new AVLTree<Product>();
		
		if (!products.findNode(Relative.Root)) { // if products is empty
			return null;
		}
		
		getProductsBetweenPricesRec(minPrice, maxPrice, productsBetween); // O(n log n)
		
		products.findNode(Relative.Root);
		
		
		return productsBetween;
	}
	
	
	// traverse in order
	private void getProductsBetweenPricesRec(double minPrice, double maxPrice, AVLTree<Product> productsBetween) { // O(n log n)
			
		Product currentProduct = products.retrieve();
			
		// go right
		if (products.findNode(Relative.RightChild)) {
			getProductsBetweenPricesRec(minPrice, maxPrice, productsBetween);
		}
			
		// go back to current order and process it
			
		products.findKey(currentProduct.getProductId());
		
		if (currentProduct.getPrice() >= minPrice && currentProduct.getPrice() <= maxPrice) {
			productsBetween.insert(currentProduct, currentProduct.getProductId()); // O(log n)
		}
			
		// go left
		if (products.findNode(Relative.LeftChild)) {
			getProductsBetweenPricesRec(minPrice, maxPrice, productsBetween);
		}
			
	}
	
	
	// O(n^2 log n)
	public void readCSV() throws IOException {
		readProductsCSV();
		readCustomersCSV();
		readOrdersCSV();
		readReviewsCSV();
		
	}
	
	// O(n)
	private void readProductsCSV() throws IOException {
		
		Scanner input = new Scanner(new File("products.csv"));
		String row; 
		
		int productId;
		double price;
		int stock;
		String name;
		
		
		input.nextLine(); // ignore the first row.
		
		while (input.hasNext()) {
			
			row = input.nextLine();
			
			int comma = row.indexOf(',');
			
			productId = Integer.parseInt(row.substring(0, comma));
			
			row = row.substring(comma + 1);
			comma = row.indexOf(',');
			
			name = row.substring(0, comma);
			
			row = row.substring(comma + 1);
			comma = row.indexOf(',');
			
			price = Double.parseDouble(row.substring(0, comma));
			
			row = row.substring(comma + 1);
			// no more commas.
			
			stock = Integer.parseInt(row.substring(0));
			
			Product product = new Product(productId, name, price, stock);
			
			products.insert(product, productId);
			
		}
		
		input.close();
	}
	
	// O(n)
	private void readCustomersCSV() throws IOException {
		
		Scanner input = new Scanner(new File("customers.csv"));
		String row; 
		
		int customerId;
		String name;
		String email;
		
		input.nextLine(); // ignore the first row.
		
		while (input.hasNext()) {
			
			row = input.nextLine();
			
			int comma = row.indexOf(',');
			
			customerId = Integer.parseInt(row.substring(0, comma));
			
			row = row.substring(comma + 1);
			comma = row.indexOf(',');
			
			name = row.substring(0, comma);
			
			row = row.substring(comma + 1);
			// no more commas.
			
			email = row.substring(0);
			
			Customer customer = new Customer(name, email, customerId);
			
			customers.insert(customer, customerId);
			
		}
		
		input.close();
	}
	
	// O(n^2 log n)
	private void readOrdersCSV() throws IOException {
		
		Scanner input = new Scanner(new File("orders.csv"));
		String row; 
		
		int orderId;
		int customerId;
		
		double totalPrice;
		String orderDate;
		int year;
		int month;
		int day;
		String status;
		
		
		input.nextLine(); // ignore the first row.
		
		while (input.hasNext()) {
			
			DoubleLinkedList<Integer> intProductIds = new DoubleLinkedList<Integer>();
			
			row = input.nextLine();
			
			// order id
			int comma = row.indexOf(',');
			
			orderId = Integer.parseInt(row.substring(0, comma));
			
			// customer id
			row = row.substring(comma + 1);
			comma = row.indexOf(',');
			
			customerId = Integer.parseInt(row.substring(0, comma));
			
			// product ids
			row = row.substring(comma + 1);
			comma = row.indexOf(',');
			
			String productIds = row.substring(0, comma);
			
			productIds = productIds.substring(1, productIds.length() - 1); // remove ""
			
			
			int simicolon;
			
			// split the product ids into individual integers
			while (productIds.indexOf(';') != -1) {
				
				simicolon = productIds.indexOf(';');
				
				int id = Integer.parseInt(productIds.substring(0, simicolon));
				
				productIds = productIds.substring(simicolon + 1); 
				
				intProductIds.insert(id);
			}
			
			int id = Integer.parseInt(productIds.substring(0));
			
			intProductIds.insert(id);
			
			// insert into an array
			
			int[] productIdsArr = new int[intProductIds.getLength()];
			
			
			intProductIds.findFirst();
			for (int i = 0; i < intProductIds.getLength(); ++i) {
				
				productIdsArr[i] = intProductIds.retrieve();
				intProductIds.findNext();
			}
			
		
			// total price
			row = row.substring(comma + 1);
			comma = row.indexOf(',');
			
			totalPrice = Double.parseDouble(row.substring(0, comma));
			
			
			// date
			row = row.substring(comma + 1);
			comma = row.indexOf(',');
			
			orderDate = row.substring(0, comma);
			
			
			
			year = Integer.parseInt(orderDate.substring(0, 4));
			month = Integer.parseInt(orderDate.substring(5, 7));
			day = Integer.parseInt(orderDate.substring(8, 10));
			
			Date date = new Date(day, month, year);
			
			
			// status
			row = row.substring(comma + 1);
			// no more commas.
			
			status = row.substring(0);
			
			placeOrder(orderId, productIdsArr, totalPrice, date, customerId, status);
			
		}
		
		input.close();
	}
	
	// O(n)
	private void readReviewsCSV() throws IOException {
		
		Scanner input = new Scanner(new File("reviews.csv"));
		String row; 
		
		int productId;
		int customerId;
		int rating;
		String comment;
		
		
		input.nextLine(); // ignore the first row.
		
		while (input.hasNext()) {
			
			row = input.nextLine();
			
			int comma = row.indexOf(',');
			
			// skip the first column
			
			
			// product id
			row = row.substring(comma + 1);
			comma = row.indexOf(',');
			
			productId = Integer.parseInt(row.substring(0, comma));
			
			
			// customer id
			row = row.substring(comma + 1);
			comma = row.indexOf(',');
			
			customerId = Integer.parseInt(row.substring(0, comma));
			
			
			// rating
			row = row.substring(comma + 1);
			comma = row.indexOf(',');
						
			rating = Integer.parseInt(row.substring(0, comma));
						
			// comment
			row = row.substring(comma + 1);
			// no more commas.
			
			comment = row.substring(0);
			
			comment = comment.substring(1, comment.length() - 1); // remove ""
			
			Product product = searchProduct(productId);
			Customer reviewer = searchCustomer(customerId);
			
			product.addReview(rating, comment, reviewer);
			
		}
		
		
		input.close();
	}
	
	
	// we will use bubble sort. This algo is O(n^3) since compareToIgnoreCase is at worst O(n) (We will assume that the length of the string equals the length of customers)
	private void sortCustomersAlphabetically(Customer[] customers) {
			
		boolean isSorted = true;
			
		for (int i = 0; i < customers.length - 1; ++i) {
				
			for (int j = 0; j < customers.length - 1 - i; ++j) {
					
				String currentName = customers[j].getName();
				String nextName = customers[j + 1].getName();
					
					// a letter in currentName comes after the corresponding letter in nextName
				if (currentName.compareToIgnoreCase(nextName) > 0) {
						
					Customer temp = customers[j];
					customers[j] = customers[j + 1];
					customers[j + 1] = temp;
						
					isSorted = false;
						
				}
					
			}
				
			if (isSorted) {
				break;
			}
				
			isSorted = true;
				
		}
			
	}
	
	// O(n)
	public void printProducts() {
		products.printInorder();
	}
	
	// O(n)
	public void printCustomers() {
		customers.printInorder();
	}
	
	// O(n)
	public void printOrders() {
		orders.printInorder();
	}
	
	// O(n^3)
	public void listCustomersAlphabetically() {
		
		DoubleLinkedList<Customer> customerList = customers.linearizeInOrder();
		Customer[] customerArr = new Customer[customerList.getLength()];
		
		customerList.findFirst();
		
		for (int i = 0; i < customerArr.length; ++i) {
			
			customerArr[i] = customerList.retrieve();
			customerList.findNext();
			
		}
		
		sortCustomersAlphabetically(customerArr);
		
		for (int i = 0; i < customerArr.length; ++i) {
			System.out.println(customerArr[i]);
		}
		
		System.out.println();
		
	}
	
	
	// test main method for debugging
	public static void main(String[] args) throws IOException {
		
		Store store = new Store();
		
		store.readCSV();
		//store.printProducts();
		//store.printOrders();
		
		//System.out.println(store.products.retrive());
		
		//store.products.retrive().getReviewList().printPQ();
		
		//store.commonProducts(201, 202).printList();
		
		//store.products.retrive().getReviewList().printPQ();
		
		//store.products.findFirst();
		
		//store.products.retrive().getReviewList().printPQ();
		
		//System.out.println(store.products.retrive().getReviewList().retrive());
		
		//store.getOrderBetweenDates(new Date(2, 3, 2025), new Date(2, 12, 2025)).printList();
		
		//store.printProducts();
		
		//store.printOrders();
		
		//store.printCustomers();
		
		//store.listCustomersAlphabetically();
		
		//store.getOrderBetweenDates(new Date(1, 2, 2025), new Date(1, 3, 2025)).printInorder();
		//store.getProductsBetweenPrices(10, 30).printInorder();
		
		//store.getCustomersReviewed(103).printList();
		
	}
	
}
