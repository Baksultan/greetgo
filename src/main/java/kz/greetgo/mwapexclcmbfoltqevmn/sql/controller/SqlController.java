package kz.greetgo.mwapexclcmbfoltqevmn.sql.controller;

import kz.greetgo.mwapexclcmbfoltqevmn.sql.model.dto.CustomerDto;
import kz.greetgo.mwapexclcmbfoltqevmn.sql.model.dto.Filter;
import kz.greetgo.mwapexclcmbfoltqevmn.sql.model.dto.UpdateCustomerDto;
import kz.greetgo.mwapexclcmbfoltqevmn.sql.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class SqlController {

    private final CustomerService customerService;

    @GetMapping("/getCustomerById/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable(name = "id") Long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/getCustomerByPhoneNumber/{phone}")
    public ResponseEntity<CustomerDto> getCustomerByPhoneNumber(@PathVariable(name = "phone") String phone) {
        return customerService.getCustomerByPhoneNumber(phone);
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<List<CustomerDto>> getCustomers(@RequestBody Filter filter) {
        return customerService.getCustomers(filter);
    }

    @PutMapping("/updateCustomerByPhoneNum/{phone}")
    public ResponseEntity<CustomerDto> updateCustomerByPhoneNum(@RequestBody UpdateCustomerDto updateCustomerDto,
                                                                @PathVariable(name = "phone") String phone) {
        return customerService.updateCustomerByPhoneNum(phone, updateCustomerDto);
    }

    @PutMapping("/updateCustomerById/{id}")
    public ResponseEntity<CustomerDto> updateCustomerById(@RequestBody UpdateCustomerDto updateCustomerDto,
                                                          @PathVariable(name = "id") Long id) {
        return customerService.updateCustomerById(id, updateCustomerDto);
    }

    @DeleteMapping("/deleteCustomerByPhoneNumber/{phone}")
    public void deleteCustomerByPhoneNumber(@PathVariable(name = "phone") String phone) {
        customerService.deleteCustomerByPhoneNumber(phone);
    }

    @DeleteMapping("/deleteCustomerById/{id}")
    public void deleteCustomerById(@PathVariable(name = "id") Long id) {
        customerService.deleteCustomerById(id);
    }
}
