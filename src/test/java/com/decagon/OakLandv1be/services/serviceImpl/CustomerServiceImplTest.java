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

    ProductCustResponseDto productCustResponseDto;

    Person person;
    Product product;
    Product product1;
    Customer customer;
    Set<Product> favorites;

//    List<Product> productList=new ArrayList<>();





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
    @Test
    void viewFavoriteByPagination(){


//        Page<Product> favouriteProduct= new PageImpl(productList, Pageable.ofSize(favorites.size()),4);
//        when(productRepository.findAll(PageRequest.of(1,2, Sort.Direction.DESC).
//                        withSort(Sort.by(field)))).thenReturn(favouriteProduct);
//        assertNotEquals(customerService.viewFavouritesByPagination(1,2,"name")
//                .getTotalElements(),4);



    }




}





