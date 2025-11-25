package project_Phase_2_CSC212;

import java.io.*;
import java.util.*;

public class Store {

	private DoubleLinkedList<Product> products;
	private DoubleLinkedList<Customer> customers;
	private DoubleLinkedList<Order> orders;
	
	
	public Store() {
		
		products = new DoubleLinkedList<Product>();
		customers = new DoubleLinkedList<Customer>();
		orders = new DoubleLinkedList<Order>();
		
	}
	
	public DoubleLinkedList<Product> getProducts() {
		return products;
	}


	public DoubleLinkedList<Customer> getCustomers() {
		return customers;
	}


	public DoubleLinkedList<Order> getOrders() {
		return orders;
	}
	
	
	public void addProduct(int productID, String name, double price, int stock) {
		Product newProduct = new Product(productID, name, price, stock);
		products.insert(newProduct);
		//System.out.println("The product has been added successfully");
	}

	public void removeProduct() {
		products.remove();
		//System.out.println("The product has been removed");
	}

	public void registerCustomer(int customerId, String name, String email) {
		Customer newCustomer = new Customer(name, email, customerId);
		customers.insert(newCustomer);
		//System.out.println("The customer hass been added successfully");
	}

	public void updateProduct(String name, double price, int stock) {

		Product updatedProduct = products.retrive();
		updatedProduct.setName(name);
		updatedProduct.setPrice(price);
		updatedProduct.setStock(stock);
		//System.out.println("The product has been updated successfully");

	}
	
	
	public boolean searchProduct(int productId) {
		
		products.findFirst();
		
		for (int i = 0; i < products.getLength(); ++i) {
			
			if (products.retrive().getProductId() == productId) {
				return true;
			}
			
			products.findNext();
		}
		
		return false;
	}
	
	
	public boolean searchProduct(String name) {
		
		products.findFirst();
		
		for (int i = 0; i < products.getLength(); ++i) {
			
			if (products.retrive().getName().equals(name)) {
				return true;
			}
			
			products.findNext();
		}
		
		return false;
	}
	
	
	public boolean searchCustomer(int customerId) {
		
		customers.findFirst();
		
		for (int i = 0; i < customers.getLength(); ++i) {
			
			if (customers.retrive().getCustomerId() == customerId) {
				return true;
			}
			
			customers.findNext();
		}
		
		return false;
	}
	
	
	public DoubleLinkedList<Product> outOfStockProducts() {
		
		DoubleLinkedList<Product> outOfStock = new DoubleLinkedList<Product>();
		
		products.findFirst();
		
		for (int i = 0; i < products.getLength(); ++i) {
			
			Product product = products.retrive();
			
			if (product.isOutOfStock()) {
				outOfStock.insert(product);
			}
			
			products.findNext();
		}
		
		products.findFirst();
		
		
		return outOfStock;
	}
	
	
	public boolean placeOrder(int orderId, int[] productIds, double totalPrice, Date orderDate, int customerId, String status) {
		
		Order order;
		
		DoubleLinkedList<Product> orderProd = new DoubleLinkedList<Product>();
		
		for (int i = 0; i < productIds.length; ++i) {
			
			if (!searchProduct(productIds[i])) {
				return false;
			}		
			orderProd.insert(products.retrive());
	
		}
		
		if (!searchCustomer(customerId)) {
			return false;
		}
		
		
		order = new Order(orderId, totalPrice, orderDate, status, customers.retrive(), orderProd);
		
		customers.retrive().addToOrderList(order);
		
		orders.insert(order);
		
		return true;
	}
	
	
	public void cancelOrder() {
		orders.retrive().setStatus("Cancelled");
	}
	
	
	public boolean searchOrder(int orderId) {
		
		orders.findFirst();
		
		for (int i = 0; i < orders.getLength(); ++i) {
			
			if (orders.retrive().getOrderId() == orderId) {
				return true;
			}
			
			orders.findNext();
		}
		
		return false;
	}
	
	
	public DoubleLinkedList<Review> getCustomerReviews() {
		
		int currentProdId = products.retrive().getProductId();
		Customer customer = customers.retrive();
		DoubleLinkedList<Review> customerReviews = new DoubleLinkedList<Review>();
		
		
		products.findFirst();
		
		for (int i = 0; i < products.getLength(); ++i) {
			
			Product product = products.retrive();
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
			
			products.findNext();
		}
		
		searchProduct(currentProdId);
		
		return customerReviews;
	}
	
	public DoubleLinkedList<Order> getOrderBetweenDates(Date startDate, Date endDate) {
		
		System.out.println(startDate.getIntDate());
		System.out.println(endDate.getIntDate());
		
		DoubleLinkedList<Order> ordersBetween = new DoubleLinkedList<Order>();
		
		orders.findFirst();
		
		for (int i = 0; i < orders.getLength(); ++i) {
			
			Order order = orders.retrive();
			Date date = order.getOrderDate();
			
			
			if (date.getIntDate() >= startDate.getIntDate() && date.getIntDate() <= endDate.getIntDate()) {
				ordersBetween.insertFirst(order);
			}
				
			orders.findNext();
		}
		
		return ordersBetween;
	}
	
	
	DoubleLinkedList<Product> commonProducts(int customerId1, int customerId2) {
		
		DoubleLinkedList<Product> products1 = new DoubleLinkedList<Product>();
		DoubleLinkedList<Product> products2 = new DoubleLinkedList<Product>();
		DoubleLinkedList<Product> commonProducts = new DoubleLinkedList<Product>();
		
		DoubleLinkedList<Order> orderList;
		
		
		// get all products related to customer 1
		if (!searchCustomer(customerId1)) {
			
			return commonProducts;
		}
		
		orderList = customers.retrive().getOrderList();
		
		orderList.findFirst();
		for (int i = 0; i < orderList.getLength(); ++i) {
			
			DoubleLinkedList<Product> products = orderList.retrive().getProductList();
			
			products.findFirst();
			
			for (int j = 0; j < products.getLength(); ++j) {
				
				products1.insertFirst(products.retrive());
				products.findNext();
			}
			
			orderList.findNext();
		}
		
		//products1.printList();
		
		// get all products related to customer 2
		if (!searchCustomer(customerId2)) {
			
			return commonProducts;
		}
		
		orderList = customers.retrive().getOrderList();
		
		orderList.findFirst();
		for (int i = 0; i < orderList.getLength(); ++i) {
			
			DoubleLinkedList<Product> products = orderList.retrive().getProductList();
			
			products.findFirst();
			
			for (int j = 0; j < products.getLength(); ++j) {
				
				products2.insertFirst(products.retrive());
				products.findNext();
			}
			
			orderList.findNext();
		}
		
		
		// finding common products with an average rating greater than 4
		
		// no need to do find first for product1
		
		for (int i = 0; i < products1.getLength(); ++i) {
			
			products2.findFirst();
			
			for (int j = 0; j < products2.getLength(); ++j) {
				
				Product prod1 = products1.retrive();
				Product prod2 = products2.retrive();
				
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
	
	
	public DoubleLinkedList<Product> getTop3Products() {
		
		DoubleLinkedList<Product> top3 = new DoubleLinkedList<Product>();
		
		CustomPriorityQueue<Product> temp = new CustomPriorityQueue<Product>();
		
		
		products.findFirst();
		
		for (int i = 0; i < products.getLength(); ++i) {
			
			Product product = products.retrive();
			
			int ratting = (int) Math.ceil(product.getAverageRating() * 100); // get a better scale of the avg rating: 4.5 > 4 ==> 4 = 4 450.33 > 400.2 ==> 450 > 400  
			
			temp.enqueue(product, ratting);
		
			products.findNext();
		}
		
		// this is done to avoid issues when products has less than 3 elements
		for (int i = 0; i < products.getLength(); ++i) {
			
			if (i >= 3) {
				break;
			}
			
			PQElement<Product> topProduct = temp.serve();
			
			top3.insert(topProduct.getData());
			
		}
		
		
		return top3;
	}
	
	
	public void readCSV() throws IOException {
		readProductsCSV();
		readCustomersCSV();
		readOrdersCSV();
		readReviewsCSV();
		
	}
	
	
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
			
			products.insert(product);
			
		}
		
		input.close();
	}
	
	
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
			
			customers.insert(customer);
			
		}
		
		input.close();
	}
	
	
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
				productIdsArr[i] = intProductIds.retrive();
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
			
			searchProduct(productId);
			searchCustomer(customerId);
			
			products.retrive().addReview(rating, comment, customers.retrive());
			
		}
		
		customers.findFirst();
		products.findFirst();
		
		input.close();
	}
	
	
	public void printProducts() {
		
		int id = products.retrive().getProductId();
		
		products.findFirst();
		for (int i = 0; i < products.getLength(); ++i) {
			System.out.println(products.retrive());
			products.findNext();
		}
		
		searchProduct(id);
	}
	
	
	public void printCustomers() {
		
		int id = customers.retrive().getCustomerId();
		
		customers.findFirst();
		for (int i = 0; i < customers.getLength(); ++i) {
			System.out.println(customers.retrive());
			customers.findNext();
		}

		searchCustomer(id);
	}
	
	
	public void printOrders() {
		
		int id = orders.retrive().getOrderId();
		
		orders.findFirst();
		for (int i = 0; i < orders.getLength(); ++i) {
			System.out.println(orders.retrive());
			orders.findNext();
		}

		searchOrder(id);
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
		
	}
	
}
