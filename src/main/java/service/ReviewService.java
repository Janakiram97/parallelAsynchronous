package service;

import domain.Review;

import static util.CommonTimeUtil.delay;

public class ReviewService {
    public Review retriveReview(String productId)
    {
        delay(500);
     return new Review(200,4.5);
    }
}
