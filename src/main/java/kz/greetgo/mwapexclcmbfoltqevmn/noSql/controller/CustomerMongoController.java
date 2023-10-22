package kz.greetgo.mwapexclcmbfoltqevmn.noSql.controller;

import kz.greetgo.mwapexclcmbfoltqevmn.noSql.model.dto.CustomerDto;
import kz.greetgo.mwapexclcmbfoltqevmn.noSql.model.dto.Filter;
import kz.greetgo.mwapexclcmbfoltqevmn.noSql.model.dto.UpdateCustomerDto;
import kz.greetgo.mwapexclcmbfoltqevmn.noSql.service.CustomerMongoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v2")
public class CustomerMongoController {

    private final CustomerMongoService service;

    @GetMapping("/getCustomerById/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable(name = "id") String id) {
        return service.getCustomerById(id);
    }

    @GetMapping("/getCustomerByPhoneNumber/{phone}")
    public ResponseEntity<CustomerDto> getCustomerByPhoneNumber(@PathVariable(name = "phone") String phone) {
        return service.getCustomerByPhoneNumber(phone);
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<List<CustomerDto>> getCustomers(@RequestBody Filter filter) {
        return service.getCustomers(filter);
    }

    @PutMapping("/updateCustomerByPhoneNum/{phone}")
    public ResponseEntity<CustomerDto> updateCustomerByPhoneNum(@RequestBody UpdateCustomerDto updateCustomerDto,
                                                                @PathVariable(name = "phone") String phone) {
        return service.updateCustomerByPhoneNum(phone, updateCustomerDto);
    }

    @PutMapping("/updateCustomerById/{id}")
    public ResponseEntity<CustomerDto> updateCustomerById(@RequestBody UpdateCustomerDto updateCustomerDto,
                                                          @PathVariable(name = "id") String id) {
        return service.updateCustomerById(id, updateCustomerDto);
    }

    @DeleteMapping("/deleteCustomerByPhoneNumber/{phone}")
    public void deleteCustomerByPhoneNumber(@PathVariable(name = "phone") String phone) {
        service.deleteCustomerByPhoneNumber(phone);
    }

    @DeleteMapping("/deleteCustomerById/{id}")
    public void deleteCustomerById(@PathVariable(name = "id") String id) {
        service.deleteCustomerById(id);
    }
}
