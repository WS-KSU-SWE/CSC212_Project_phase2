package project_Phase_2_CSC212;

public class Review {

	private int rating;
    private String comment;
    private Customer reviewer;

    public Review(int rating, String comment, Customer reviewer) {
    	this.rating = rating;
        this.comment = comment;
        this.reviewer = reviewer;
    }
    
    
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Customer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Customer reviewer) {
        this.reviewer = reviewer;
    }
	
    public String toString() {
    	return "reviewer Id: " + reviewer.getCustomerId() + ", comment: " + comment + ", rating: " + rating;
    }
    
}
