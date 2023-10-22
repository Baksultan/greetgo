package kz.greetgo.mwapexclcmbfoltqevmn.noSql.service;

import kz.greetgo.mwapexclcmbfoltqevmn.noSql.model.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MongoDBDataInitializer {

    private final CustomerMongoService service;

    public void initializeData() {

        Customer customer1 = Customer.builder()
                .fullName("Анна Иванова")
                .birthYear(1990)
                .phoneNumber("111-111-1111")
                .secondPhoneNumber("222-222-2222")
                .creationDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .fullName("Максим Смирнов")
                .birthYear(1995)
                .phoneNumber("333-333-3333")
                .secondPhoneNumber("444-444-4444")
                .creationDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .fullName("Екатерина Петрова")
                .birthYear(2000)
                .phoneNumber("555-555-5555")
                .secondPhoneNumber("666-666-6666")
                .creationDate(LocalDateTime.now())
                .build();

        Customer customer4 = Customer.builder()
                .fullName("Алексей Козлов")
                .birthYear(2002)
                .phoneNumber("777-777-7777")
                .secondPhoneNumber("888-888-8888")
                .creationDate(LocalDateTime.now())
                .build();

        Customer customer5 = Customer.builder()
                .fullName("Ольга Соколова")
                .birthYear(2005)
                .phoneNumber("999-999-9999")
                .secondPhoneNumber("000-000-0000")
                .creationDate(LocalDateTime.now())
                .build();

        service.addNewCustomer(customer1);
        service.addNewCustomer(customer2);
        service.addNewCustomer(customer3);
        service.addNewCustomer(customer4);
        service.addNewCustomer(customer5);
    }
}
