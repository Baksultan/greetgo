package kz.greetgo.mwapexclcmbfoltqevmn.noSql.repository;

import kz.greetgo.mwapexclcmbfoltqevmn.noSql.model.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerMongoRepository extends MongoRepository<Customer, String> {
}
