package kz.greetgo.mwapexclcmbfoltqevmn.noSql.service;

import kz.greetgo.mwapexclcmbfoltqevmn.noSql.model.dto.CustomerDto;
import kz.greetgo.mwapexclcmbfoltqevmn.noSql.model.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerMongoMapperService {

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
