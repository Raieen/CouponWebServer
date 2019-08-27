package xyz.raieen.couponwebserver;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CouponsRepository extends MongoRepository<Coupon, String> {
}
