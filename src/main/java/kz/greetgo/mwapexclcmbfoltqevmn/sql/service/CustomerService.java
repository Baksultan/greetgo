package kz.greetgo.mwapexclcmbfoltqevmn.sql.service;

import kz.greetgo.mwapexclcmbfoltqevmn.sql.model.dto.CustomerDto;
import kz.greetgo.mwapexclcmbfoltqevmn.sql.model.dto.Filter;
import kz.greetgo.mwapexclcmbfoltqevmn.sql.model.dto.UpdateCustomerDto;
import kz.greetgo.mwapexclcmbfoltqevmn.sql.model.entity.Customer;
import kz.greetgo.mwapexclcmbfoltqevmn.sql.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {


    private final CustomerRepository customerRepository;
    private final CustomerMapperService customerMapper;

    public ResponseEntity<CustomerDto> getCustomerByPhoneNumber(String phone) {
        Customer customer = customerRepository.getCustomerByPhoneNumberOrSecondPhoneNumber(phone);
        if (customer != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(customerMapper.toDto(customer));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public ResponseEntity<CustomerDto> getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body(customerMapper.toDto(customer));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public void deleteCustomerByPhoneNumber(String phone) {
        Customer customer = customerRepository.getCustomerByPhoneNumberOrSecondPhoneNumber(phone);
        if (customer != null) {
            customerRepository.delete(customer);
        } else {
            log.info("Customer with this phone number not found.");
        }
    }

    public void deleteCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customerRepository.deleteById(id);
        } else {
            log.info("Customer with this ID not found.");
        }
    }

//    public ResponseEntity<List<CustomerDto>> getCustomers(int limit, int offset) {
//        Pageable pageable = PageRequest.of(offset, limit);
//        Page<Customer> customerPage = customerRepository.findAll(pageable);
//        if (!customerPage.getContent().isEmpty()) {
//            return ResponseEntity.status(HttpStatus.OK).body(customerMapper.toDtoList(customerPage.getContent()));
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<CustomerDto>());
//    }

    public ResponseEntity<List<CustomerDto>> getCustomers(Filter filter) {
        Pageable pageable = PageRequest.of(filter.getOffset(), filter.getLimit());
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        if (!customerPage.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(customerMapper.toDtoList(customerPage.getContent()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<CustomerDto>());
    }


    public ResponseEntity<CustomerDto> updateCustomerByPhoneNum(String phone, UpdateCustomerDto responseCustomer) {
        Customer customer = customerRepository.getCustomerByPhoneNumberOrSecondPhoneNumber(phone);
        if (customer != null) {
            Customer updatedCustomer = Customer.builder()
                    .id(customer.getId())
                    .fullName(responseCustomer.getFullName())
                    .birthYear(responseCustomer.getBirthYear())
                    .phoneNumber(responseCustomer.getPhoneNumber())
                    .secondPhoneNumber(responseCustomer.getSecondPhoneNumber())
                    .creationDate(customer.getCreationDate())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(customerMapper.toDto(customerRepository.save(updatedCustomer)));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


    public ResponseEntity<CustomerDto> updateCustomerById(Long id, UpdateCustomerDto responseCustomer) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            Customer updatedCustomer = Customer.builder()
                    .id(customer.getId())
                    .fullName(responseCustomer.getFullName())
                    .birthYear(responseCustomer.getBirthYear())
                    .phoneNumber(responseCustomer.getPhoneNumber())
                    .secondPhoneNumber(responseCustomer.getSecondPhoneNumber())
                    .creationDate(customer.getCreationDate())
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(customerMapper.toDto(customerRepository.save(updatedCustomer)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
