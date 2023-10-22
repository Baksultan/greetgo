package kz.greetgo.mwapexclcmbfoltqevmn;

import kz.greetgo.mwapexclcmbfoltqevmn.sql.model.dto.CustomerDto;
import kz.greetgo.mwapexclcmbfoltqevmn.sql.model.dto.Filter;
import kz.greetgo.mwapexclcmbfoltqevmn.sql.model.dto.UpdateCustomerDto;
import kz.greetgo.mwapexclcmbfoltqevmn.sql.model.entity.Customer;
import kz.greetgo.mwapexclcmbfoltqevmn.sql.repository.CustomerRepository;
import kz.greetgo.mwapexclcmbfoltqevmn.sql.service.CustomerMapperService;
import kz.greetgo.mwapexclcmbfoltqevmn.sql.service.CustomerService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class SqlCustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapperService customerMapper;

    @BeforeClass
    public void init() {
        openMocks(this);
    }

    @BeforeMethod
    public void resetMocks() {
        reset(customerRepository, customerMapper);
    }

    @Test
    public void testGetCustomerByPhoneNumberFound() {
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Baksultan Amangeldi")
                .birthYear(2002)
                .phoneNumber("111-111-1111")
                .secondPhoneNumber("222-222-2222")
                .creationDate(LocalDateTime.now())
                .build();
        when(customerRepository.getCustomerByPhoneNumberOrSecondPhoneNumber(anyString())).thenReturn(customer);

        when(customerMapper.toDto(customer)).thenReturn(CustomerDto.builder()
                .fullName("Baksultan Amangeldi")
                .birthYear(2002)
                .phoneNumber("111-111-1111")
                .secondPhoneNumber("222-222-2222")
                .creationDate(LocalDateTime.now())
                .build());

        ResponseEntity<CustomerDto> response = customerService.getCustomerByPhoneNumber("111-111-1111");

        assertEquals(response.getStatusCode(), HttpStatus.FOUND);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetCustomerByPhoneNumberNotFound() {
        when(customerRepository.getCustomerByPhoneNumberOrSecondPhoneNumber(anyString())).thenReturn(null);

        ResponseEntity<CustomerDto> response = customerService.getCustomerByPhoneNumber("123-123-1234");

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertNull(response.getBody());
    }

    @Test
    public void testGetCustomerByIdFound() {
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Baksultan Amangeldi")
                .birthYear(2002)
                .phoneNumber("111-111-1111")
                .secondPhoneNumber("222-222-2222")
                .creationDate(LocalDateTime.now())
                .build();
        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(customer));

        when(customerMapper.toDto(customer)).thenReturn(CustomerDto.builder()
                        .fullName("Baksultan Amangeldi")
                        .birthYear(2002)
                        .phoneNumber("111-111-1111")
                        .secondPhoneNumber("222-222-2222")
                        .creationDate(LocalDateTime.now())
                        .build());

        ResponseEntity<CustomerDto> response = customerService.getCustomerById(1L);

        assertEquals(response.getStatusCode(), HttpStatus.FOUND);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetCustomerByIdNotFound() {
        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        ResponseEntity<CustomerDto> response = customerService.getCustomerById(1L);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteCustomerByPhoneNumberFound() {
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Baksultan Amangeldi")
                .birthYear(2002)
                .phoneNumber("111-111-1111")
                .secondPhoneNumber("222-222-2222")
                .creationDate(LocalDateTime.now())
                .build();
        when(customerRepository.getCustomerByPhoneNumberOrSecondPhoneNumber(anyString())).thenReturn(customer);

        customerService.deleteCustomerByPhoneNumber("111-111-1111");

        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    public void testDeleteCustomerByPhoneNumberNotFound() {
        when(customerRepository.getCustomerByPhoneNumberOrSecondPhoneNumber(anyString())).thenReturn(null);

        customerService.deleteCustomerByPhoneNumber("123-123-1234");

        verify(customerRepository, never()).delete(any());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetCustomersNotEmpty() {
        Filter filter = new Filter();
        filter.setLimit(10);
        filter.setOffset(0);
        Page<Customer> customerPage = mock(Page.class);
        when(customerRepository.findAll(any(Pageable.class))).thenReturn(customerPage);
        when(customerPage.getContent()).thenReturn(new ArrayList<>());

        ResponseEntity<List<CustomerDto>> response = customerService.getCustomers(filter);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetCustomersEmpty() {
        Filter filter = new Filter();
        filter.setLimit(10);
        filter.setOffset(0);

        Page<Customer> customerPage = mock(Page.class);

        when(customerRepository.findAll(any(Pageable.class))).thenReturn(customerPage);
        when(customerPage.getContent()).thenReturn(Collections.emptyList());

        ResponseEntity<List<CustomerDto>> response = customerService.getCustomers(filter);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateCustomerByPhoneNumFound() {
        String phone = "111-111-1111";
        UpdateCustomerDto responseCustomer = UpdateCustomerDto.builder()
                .fullName("Baqsultan Amangeldi")
                .birthYear(2003)
                .phoneNumber("111-111-1110")
                .secondPhoneNumber("222-222-2220")
                .build();
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Baksultan Amangeldi")
                .birthYear(2002)
                .phoneNumber("111-111-1111")
                .secondPhoneNumber("222-222-2222")
                .creationDate(LocalDateTime.now())
                .build();
        when(customerRepository.getCustomerByPhoneNumberOrSecondPhoneNumber(phone)).thenReturn(customer);

        ResponseEntity<CustomerDto> response = customerService.updateCustomerByPhoneNum(phone, responseCustomer);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateCustomerByPhoneNumNotFound() {
        String phone = "111-111-1111";
        UpdateCustomerDto responseCustomer = UpdateCustomerDto.builder()
                .fullName("Baqsultan Amangeldi")
                .birthYear(2003)
                .phoneNumber("111-111-1110")
                .secondPhoneNumber("222-222-2220")
                .build();
        when(customerRepository.getCustomerByPhoneNumberOrSecondPhoneNumber(phone)).thenReturn(null);

        ResponseEntity<CustomerDto> response = customerService.updateCustomerByPhoneNum(phone, responseCustomer);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateCustomerByIdFound() {
        Long id = 1L;
        UpdateCustomerDto responseCustomer = UpdateCustomerDto.builder()
                .fullName("Baqsultan Amangeldi")
                .birthYear(2003)
                .phoneNumber("111-111-1110")
                .secondPhoneNumber("222-222-2220")
                .build();
        Customer customer = Customer.builder()
                .id(1L)
                .fullName("Baksultan Amangeldi")
                .birthYear(2002)
                .phoneNumber("111-111-1111")
                .secondPhoneNumber("222-222-2222")
                .creationDate(LocalDateTime.now())
                .build();
        when(customerRepository.findById(id)).thenReturn(java.util.Optional.of(customer));

        ResponseEntity<CustomerDto> response = customerService.updateCustomerById(id, responseCustomer);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateCustomerByIdNotFound() {
        Long id = 1L;
        UpdateCustomerDto responseCustomer = new UpdateCustomerDto(/* заполните данные */);
        when(customerRepository.findById(id)).thenReturn(java.util.Optional.empty());

        ResponseEntity<CustomerDto> response = customerService.updateCustomerById(id, responseCustomer);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNull(response.getBody());
    }


}
