package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

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

    Person person;
    Product product;
    Product product1;
    Customer customer;
    Set<Product> favorites;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setName("Phone");
        product.setId(1L);

        product1 = new Product();
        product1.setId(2L);
        product1.setName("Beans");

        customer = new Customer();
        customer.setId(1L);

        person = new Person();
        person.setEmail("email.com");
        person.setCustomer(customer);

        favorites = new HashSet<>();

        customer.setFavorites(favorites);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void addProductToFavorites() {
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(product));
        when(personRepository.findByEmail(anyString()))
                .thenReturn(Optional.ofNullable(person));
        when(customerRepository.save(customer))
                .thenReturn(customer);
        when(authentication.getName())
                .thenReturn("email.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        customerService.addProductToFavorites(2L);
        verify(customerRepository, times(1)).save(person.getCustomer());
        assertTrue(favorites.contains(product));
    }
}