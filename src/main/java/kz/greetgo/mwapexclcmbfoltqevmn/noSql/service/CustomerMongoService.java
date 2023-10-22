package kz.greetgo.mwapexclcmbfoltqevmn.noSql.service;

import kz.greetgo.mwapexclcmbfoltqevmn.noSql.model.dto.CustomerDto;
import kz.greetgo.mwapexclcmbfoltqevmn.noSql.model.dto.Filter;
import kz.greetgo.mwapexclcmbfoltqevmn.noSql.model.dto.UpdateCustomerDto;
import kz.greetgo.mwapexclcmbfoltqevmn.noSql.model.entity.Customer;
import kz.greetgo.mwapexclcmbfoltqevmn.noSql.repository.CustomerMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerMongoService {

    private final CustomerMongoRepository repository;
    private final MongoTemplate mongoTemplate;
    private final CustomerMongoMapperService mapperService;

    public ResponseEntity<CustomerDto> addNewCustomer(Customer customer) {
        Query query = new Query();
        query.addCriteria(Criteria.where("phoneNumber").is(customer.getPhoneNumber()));
        query.addCriteria(Criteria.where("secondPhoneNumber").is(customer.getSecondPhoneNumber()));

        List<Customer> customers = mongoTemplate.find(query, Customer.class);

        if (customers.size() > 1) {
            throw new IllegalStateException("found may students with phone number " + customer.getPhoneNumber());
        }

        if (customers.isEmpty()) {
            log.info("Inserting customer " + customer);
            CustomerDto customerDto = mapperService.toDto(repository.insert(customer));
            return ResponseEntity.status(HttpStatus.OK).body(customerDto);
        } else {
            log.info(customer + " already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseEntity<CustomerDto> getCustomerByPhoneNumber(String phoneNumber) {
        Query query = new Query();
        query.addCriteria(
                new Criteria().orOperator(
                        Criteria.where("phoneNumber").is(phoneNumber),
                        Criteria.where("secondPhoneNumber").is(phoneNumber)
                )
        );

        Customer customer = mongoTemplate.findOne(query, Customer.class);

        if (customer != null) {
            CustomerDto customerDto = mapperService.toDto(customer);
            return ResponseEntity.status(HttpStatus.FOUND).body(customerDto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    }

    public ResponseEntity<CustomerDto> getCustomerById(String id) {
        Optional<Customer> customer = repository.findById(id);

        if (customer.isPresent()) {

            CustomerDto customerDto = mapperService.toDto(customer.get());
            return ResponseEntity.status(HttpStatus.FOUND).body(customerDto);

        } else {
            return ResponseEntity.status(HttpStatus.FOUND).body(null);
        }

    }


    public void deleteCustomerByPhoneNumber(String phoneNumber) {
        Query query = new Query();
        query.addCriteria(
                new Criteria().orOperator(
                        Criteria.where("phoneNumber").is(phoneNumber),
                        Criteria.where("secondPhoneNumber").is(phoneNumber)
                )
        );

        List<Customer> customersToDelete = mongoTemplate.find(query, Customer.class);

        if (!customersToDelete.isEmpty()) {
            for (Customer customer : customersToDelete) {
                repository.deleteById(customer.getId());
            }
        }

    }


    public void deleteCustomerById(String id) {
        Optional<Customer> customerToDelete = repository.findById(id);

        if (customerToDelete.isPresent()) {
            repository.deleteById(id);
        }

    }

    public ResponseEntity<List<CustomerDto>> getCustomers(Filter filter) {
        int limit = filter.getLimit();
        int offset = filter.getOffset();

        Pageable pageable = PageRequest.of(offset, limit, Sort.by("fullName").ascending());

        Page<Customer> customerPage = repository.findAll(pageable);

        List<Customer> customers = customerPage.getContent();

        return ResponseEntity.status(HttpStatus.FOUND).body(mapperService.toDtoList(customers));
    }

    public ResponseEntity<CustomerDto> updateCustomerByPhoneNum(String phone, UpdateCustomerDto responseCustomer) {
        Query query = new Query(Criteria.where("phoneNumber").is(phone));

        Update update = new Update();

        if (responseCustomer.getFullName() != null) {
            update.set("fullName", responseCustomer.getFullName());
        }
        if (responseCustomer.getBirthYear() != 0) {
            update.set("birthYear", responseCustomer.getBirthYear());
        }
        if (responseCustomer.getPhoneNumber() != null) {
            update.set("phoneNumber", responseCustomer.getPhoneNumber());
        }
        if (responseCustomer.getSecondPhoneNumber() != null) {
            update.set("secondPhoneNumber", responseCustomer.getSecondPhoneNumber());
        }
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

        Customer updatedCustomer = mongoTemplate.findAndModify(query, update, options, Customer.class);

        if (updatedCustomer!=null) {
            CustomerDto customerDto = (mapperService.toDto(updatedCustomer));
            return ResponseEntity.status(HttpStatus.OK).body(customerDto);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


    public ResponseEntity<CustomerDto> updateCustomerById(String id, UpdateCustomerDto responseCustomer) {
        Query query = new Query(Criteria.where("_id").is(id));

        Update update = new Update();

        if (responseCustomer.getFullName() != null) {
            update.set("fullName", responseCustomer.getFullName());
        }
        if (responseCustomer.getBirthYear() != 0) {
            update.set("birthYear", responseCustomer.getBirthYear());
        }
        if (responseCustomer.getPhoneNumber() != null) {
            update.set("phoneNumber", responseCustomer.getPhoneNumber());
        }
        if (responseCustomer.getSecondPhoneNumber() != null) {
            update.set("secondPhoneNumber", responseCustomer.getSecondPhoneNumber());
        }

        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);

        Customer updatedCustomer = mongoTemplate.findAndModify(query, update, options, Customer.class);

        if (updatedCustomer != null) {
            CustomerDto customerDto = mapperService.toDto(updatedCustomer);
            return ResponseEntity.status(HttpStatus.OK).body(customerDto);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
