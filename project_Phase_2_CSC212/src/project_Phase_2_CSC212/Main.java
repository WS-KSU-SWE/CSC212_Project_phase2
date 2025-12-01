package project_Phase_2_CSC212;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Scanner in = new Scanner(System.in);
		Store store = new Store();
		int choice = 0;
		
		store.readCSV();
		
		System.out.println("Welcome to the store");
		
		
		while (choice != 15) {
			System.out.println("Enter your choice:");
			System.out.println("1- Add Product   2-Update Product   3-Search For Product");
			System.out.println("4-Range Query by Price   5-Add Customer  6-Search For Customer");
			System.out.println("7-View Customer Order History   8- Find All Orders Between Two Dates  9-Show the Top 3 Highest Rated Products");
			System.out.println("10-List All Customers Sorted Alphabetically   11-View Customers That Reviewed a Product");
			System.out.println("12-Print Products   13-Print Customers    14-Print Orders");
			System.out.println("15-exit");
			
			
			System.out.print("Please enter your choice: "); 
			choice = in.nextInt();
	
			switch (choice) {
			
				case 1:
					System.out.print("Enter the product ID: ");
					int pID = in.nextInt();
					in.nextLine();
					
					System.out.print("Enter the product name: ");
					String pName = in.nextLine();
					
					System.out.print("Enter the product price: ");
					double pPrice = in.nextDouble();
					
					System.out.print("Enter the stock: ");
					int stock = in.nextInt();
					
					if (store.addProduct(pID, pName, pPrice, stock)) {
						System.out.println("Product has been added successfully");
						
					}
					else {
						System.out.println("Error, product with the provided product id already exists");
					}
					
					break;
					
				case 2:
					System.out.print("Enter the product ID: ");
					int UpID = in.nextInt();
					in.nextLine();
					
					System.out.print("Enter the new product name: ");
					String upName = in.nextLine();
					
					System.out.print("Enter the new product price: ");
					double UpPrice = in.nextDouble();
					
					System.out.print("Enter the new stock: ");
					int upStock = in.nextInt();
					
					if (store.updateProduct(UpID, upName, UpPrice, upStock)) {
						System.out.println("Product has been updated successfully");
					}
					else {
						System.out.println("Error, product with the provided product id does not exist");
					}
					
					
					break;
		
				case 3:
					System.out.print("Enter the product ID: ");
					int searchP = in.nextInt();
					Product prod = store.searchProduct(searchP);
					
					if (prod != null) {
						System.out.println("Product found and is now selected.");
						System.out.println(prod);
					}
					else {
						System.out.println("Sorry, we could not find the product with the product id of " + searchP);
					}
					
					
					break;
		
				case 4:
					
					double minP = 0;
					double maxP = 0;
					
					do {
						
						System.out.print("Enter the minimum price: ");
						minP = in.nextDouble();
						
						if (minP < 0) {
							System.out.println("Value cannot be negative.");
						}
						
					} while (minP < 0);
					
					do {
						
						System.out.print("Enter the maximum price: ");
						maxP = in.nextDouble();
						
						if (maxP < 0) {
							System.out.println("Value cannot be negative.");
						}
						if (maxP < minP) {
							System.out.println("Max price cannot be less than minimum price.");
						}
						
					} while (maxP < 0 || maxP < minP);
					
					
					store.getProductsBetweenPrices(minP,maxP).printInorder();
					break;
		
				case 5:
					System.out.print("Enter the customer ID: ");
					int cID = in.nextInt();
					in.nextLine();
					
					System.out.print("Enter the customer name: ");
					String cName = in.nextLine();
					
					System.out.print("Enter the customer e-mail: ");
					String cEmail = in.next();
					
					
					
					if (store.registerCustomer(cID, cName, cEmail)) {
						System.out.println("Customer has been added successfully");
						
					}
					else {
						System.out.println("Error, customer with the provided customer id already exists");
					}
					
					break;
					
				case 6:
					
					System.out.print("Enter the customer ID: ");
					int searchC = in.nextInt();
					Customer customer = store.searchCustomer(searchC);
					
					if (customer != null) {
						System.out.println("Customer found and is now selected.");
						System.out.println(customer);
					}
					else {
						System.out.println("Sorry, we could not find the customer with the customer id of " + searchC);
					}
					
					break;
						
				case 7:
					System.out.print("Enter the customer ID: ");
					int coID = in.nextInt();
						
					AVLTree<Order> orders = store.getCustomerOrders(coID);
						
					if (orders != null) {
						orders.printInorder();
					}
					else {
						System.out.println("Sorry, we could not find the customer with the customer id of " + coID);
					}
						
					break;
						
				case 8:
					System.out.print("Enter the day , month and year of start date (seperate by space): ");
					Date start = new Date(in.nextInt(), in.nextInt(), in.nextInt());
					
					System.out.print("Enter the day , month and year of end date (seperate by space): ");
					Date end = new Date(in.nextInt(), in.nextInt(), in.nextInt());
					
					store.getOrderBetweenDates(start, end).printInorder();;
					break;
	
				case 9:
					store.getTop3Products().printList();
					break;

				case 10:
					store.listCustomersAlphabetically();
					break;
					
				case 11:
					System.out.print("Enter the product ID: ");
					int prID = in.nextInt();
					
					DoubleLinkedList<Customer> customersReviwed = store.getCustomersReviewed(prID);
					
					if (customersReviwed != null) {
						customersReviwed.printList();
					}
					else {
						System.out.println("Error, product with the provided product id does not exist");
					}
					
					break;
				
				case 12:
					store.printProducts();
					break;
				
				case 13:
					store.printCustomers();
					break;
					
				case 14:
					store.printOrders();
					break;
					
				default:
					break;
			}
			
			
		}
	}
	
}
