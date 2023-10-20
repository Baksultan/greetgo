package kz.greetgo.mwapexclcmbfoltqevmn.sql.repository;

import kz.greetgo.mwapexclcmbfoltqevmn.sql.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.phoneNumber = :number OR c.secondPhoneNumber = :number")
    Customer getCustomerByPhoneNumberOrSecondPhoneNumber(@Param("number") String number);
}
