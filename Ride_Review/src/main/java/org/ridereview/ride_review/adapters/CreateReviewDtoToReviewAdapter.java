package org.ridereview.ride_review.adapters;

import org.ridereview.ride_review.dtos.CreateReviewDto;
import org.ridereview.ride_review.models.Review;

public interface CreateReviewDtoToReviewAdapter {

    public Review convertDto(CreateReviewDto createReviewDto);
}