package kz.greetgo.mwapexclcmbfoltqevmn.sql.service;

import kz.greetgo.mwapexclcmbfoltqevmn.sql.model.dto.CustomerDto;
import kz.greetgo.mwapexclcmbfoltqevmn.sql.model.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerMapperService {

    public CustomerDto toDto(Customer customer) {
        CustomerDto customerDto = CustomerDto.builder()
                .fullName(customer.getFullName())
                .birthYear(customer.getBirthYear())
                .phoneNumber(customer.getPhoneNumber())
                .secondPhoneNumber(customer.getSecondPhoneNumber())
                .creationDate(customer.getCreationDate())
                .build();
        return customerDto;
    }

    public List<CustomerDto> toDtoList(List<Customer> customerList) {
        return customerList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
