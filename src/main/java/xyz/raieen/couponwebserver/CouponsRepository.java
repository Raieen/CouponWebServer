package xyz.raieen.couponwebserver;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Represents the mongo repository specified in application.properties
 */
public interface CouponsRepository extends MongoRepository<Coupon, String> {
}
