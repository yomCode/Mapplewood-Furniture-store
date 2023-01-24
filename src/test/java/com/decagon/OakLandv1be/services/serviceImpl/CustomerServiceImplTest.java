package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import com.decagon.OakLandv1be.utils.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.*;
import com.decagon.OakLandv1be.dto.CustomerProfileDto;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.exceptions.UserNotFoundException;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @InjectMocks
    ProductServiceImpl productService;
    @Mock
    PersonRepository personRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    Authentication authentication;
    @Mock
    SecurityContext securityContext;
   @InjectMocks
    CustomerServiceImpl customerService;
    
    private Person person;
    private Person person2;
    private Product product;
    private Product product1;
    private Customer customer;
    private Customer customer2;
    private Set<Product> favorites;
    private CustomerProfileDto customerProfileDto;
    private List<Person> personList;
    ProductCustResponseDto productCustResponseDto;

    Person person;
    Product product;
    Product product1;
    Customer customer;
    Set<Product> favorites;
    Integer pageNumber = 0;
    Integer pageSize = 2;
    String sortBy = "lastName";

    List<Product> productList=new ArrayList<>();


    String field ="name";



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setName("Phone");

        product1 = new Product();
        product1.setId(2L);
        product1.setName("Beans");

        customer = new Customer();
        customer.setId(1L);

        person = new Person();
        person.setEmail("email.com");
        person.setCustomer(customer);
        person2 = Person.builder().email("good.com").firstName("big").lastName("small").customer(customer2).build();
        favorites = new HashSet<>();
        customer.setFavorites(favorites);
        when(authentication.getName()).thenReturn("email.com");
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(personRepository.findByEmail(anyString())).thenReturn(Optional.of(person));
        customerProfileDto = CustomerProfileDto.builder().firstName("John").lastName("Doe").email("email.com")
                .phone("1234567890").address("123 Main St").date_of_birth("01-01-2000").gender(Gender.MALE).verificationStatus(true).build();
        personList = Arrays.asList(person, person2);
    }

    @Test
    void addProductToFavorites() {
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(product));
        when(customerRepository.save(customer))
                .thenReturn(customer);
        when(authentication.getName())
                .thenReturn("email.com");
        customerService.addProductToFavorites(2L);
        verify(customerRepository, times(1)).save(person.getCustomer());
        assertTrue(favorites.contains(product));
    }
    @Test
    void viewFavoriteByPagination(){


//        Page<Product> favouriteProduct= new PageImpl(productList, Pageable.ofSize(favorites.size()),4);
//        when(productRepository.findAll(PageRequest.of(1,2, Sort.Direction.DESC).
//                        withSort(Sort.by(field)))).thenReturn(favouriteProduct);
//        assertNotEquals(customerService.viewFavouritesByPagination(1,2,"name")
//                .getTotalElements(),4);


    }

}

    @Test
    void viewProfile() {
        person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("email.com");
        person.setGender(Gender.MALE);
        person.setDate_of_birth("01-01-2000");
        person.setPhone("1234567890");
        person.setVerificationStatus(true);
        person.setAddress("123 Main St");
        when(personRepository.findByEmail("email.com")).thenReturn(Optional.of(person));
        Assertions.assertEquals(customerProfileDto, customerService.viewProfile());
        when(personRepository.findByEmail("email.com")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> customerService.viewProfile());
    }


    @Test
    void viewAllCustomersProfileWithPaginationSorting() {
        Page<Person> personPage = new PageImpl<>(personList, Pageable.ofSize(pageSize), 2);
        when(personRepository.findAll(any(Pageable.class))).thenReturn(personPage);
        Page<CustomerProfileDto> customerProfileDtoPage =
                customerService.viewAllCustomersProfileWithPaginationSorting(pageNumber, pageSize, sortBy);
        assertEquals(2, customerProfileDtoPage.getTotalElements());
        verify(personRepository, times(1)).findAll(any(Pageable.class));
    }
}
