package project_Phase_2_CSC212;

public class Order {

	private Customer customerReference;
    private DoubleLinkedList<Product> productList;
    private Date orderDate;
	private int orderId;
    private double totalPrice;
    private String status;
    

    public Order(int orderId, double totalPrice, Date orderDate, String status, Customer customerReference, DoubleLinkedList<Product> productList) {
    	
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
        this.customerReference = customerReference;
        this.productList = productList;
        
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(Customer customerReference) {
        this.customerReference = customerReference;
    }

    public DoubleLinkedList<Product> getProductList() {
        return productList;
    }

    public void setProductList(DoubleLinkedList<Product> productList) {
        this.productList = productList;
    }

	public String toString() {
		return "ID: " + orderId + ", status: " + status + ", total price: " + totalPrice + ", order date: " + orderDate;
	}
    
}
