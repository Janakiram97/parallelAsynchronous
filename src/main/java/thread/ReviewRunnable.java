package thread;

import domain.Review;
import lombok.Getter;
import service.ReviewService;

public class ReviewRunnable implements Runnable {
    @Getter
    private Review review;
    private String productId;
    public ReviewRunnable(String productId) {
        this.productId=productId;
    }


    @Override
    public void run() {
        ReviewService reviewService=new ReviewService();
        review=reviewService.retriveReview(productId);
    }
}
