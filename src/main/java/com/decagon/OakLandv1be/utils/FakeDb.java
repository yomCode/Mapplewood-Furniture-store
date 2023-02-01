package com.decagon.OakLandv1be.utils;

import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.BaseCurrency;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.exceptions.ProductNotFoundException;
import com.decagon.OakLandv1be.repositries.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FakeDb {

    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;


    @Bean
    @Qualifier("MyOtherCommand")
    public CommandLineRunner myCommandLineRunner(PersonRepository personRepository,

                                                 ProductRepository productRepository,
                                                 CustomerRepository customerRepository,
                                                 SubCategoryRepository subCategoryRepository,
                                                 CategoryRepository categoryRepository) {
        return argument -> {
            if (!personRepository.existsByEmail("bennyson1@gmail.com")) {
                Admin admin = new Admin();
                Person person = Person.builder()
                        .firstName("Benson")
                        .lastName("Malik")
                        .email("bennyson1@gmail.com")
                        .gender(Gender.MALE)
                        .date_of_birth("13-08-1990")
                        .phone("9859595959")
                        .isActive(true)
                        .admin(admin)
                        .verificationStatus(true)
                        .address("No Address")
                        .role(Role.ADMIN)
                        .password(passwordEncoder.encode("password123453"))
                        .isActive(true)

                        .build();
                Person savedperson = personRepository.save(person);

                admin.setPerson(person);
                adminRepository.save(admin);


                Customer customer1 = new Customer();
                Person person1 = Person.builder()
                        .firstName("Alex")
                        .lastName("Cole")
                        .email("indigo@gmail.com")
                        .gender(Gender.MALE)
                        .date_of_birth("13-08-1990")
                        .phone("9859595959")
                        .isActive(true)
                        .customer(customer1)
                        .verificationStatus(true)
                        .address("No Address")
                        .role(Role.ADMIN)
                        .password(passwordEncoder.encode("password123453"))
                        .isActive(true)

                        .build();
                Person savedperson1 = personRepository.save(person1);

                customer1.setPerson(person1);

                Wallet wallet = Wallet.builder()
                        .baseCurrency(BaseCurrency.NAIRA)
                        .accountBalance(BigDecimal.valueOf(400000))
                        .build();

                customer1.setPerson(savedperson1);
                customer1.setWallet(wallet);
                customerRepository.save(customer1);


                Customer customer2 = new Customer();
                Person person2 = Person.builder()
                        .firstName("Natalie")
                        .lastName("Feshman")
                        .email("oppenheimer@gmail.com")
                        .gender(Gender.MALE)
                        .date_of_birth("13-08-1990")
                        .phone("9859595959")
                        .isActive(true)
                        .customer(customer2)
                        .verificationStatus(true)
                        .address("No Address")
                        .role(Role.ADMIN)
                        .password(passwordEncoder.encode("password123453"))
                        .isActive(true)

                        .build();
                Person savedperson2 = personRepository.save(person2);

                customer1.setPerson(person2);

                Wallet wallet1 = Wallet.builder()
                        .baseCurrency(BaseCurrency.NAIRA)
                        .accountBalance(BigDecimal.valueOf(400000))
                        .build();

                customer2.setPerson(savedperson2);
                customer2.setWallet(wallet1);
                customerRepository.save(customer2);


                Customer customer3 = new Customer();
                Person person3 = Person.builder()
                        .firstName("Natalie")
                        .lastName("Feshman")
                        .email("natialieFreshman@gmail.com")
                        .gender(Gender.MALE)
                        .date_of_birth("13-08-1990")
                        .phone("9859595959")
                        .isActive(true)
                        .customer(customer3)
                        .verificationStatus(true)
                        .address("No Address")
                        .role(Role.ADMIN)
                        .password(passwordEncoder.encode("password123453"))
                        .isActive(true)

                        .build();
                Person savedperson3 = personRepository.save(person3);

                customer3.setPerson(person3);

                Wallet wallet2 = Wallet.builder()
                        .baseCurrency(BaseCurrency.NAIRA)
                        .accountBalance(BigDecimal.valueOf(400000))
                        .build();

                customer3.setPerson(savedperson3);
                customer3.setWallet(wallet2);
                customerRepository.save(customer3);




                Customer customer4 = new Customer();
                Person person4 = Person.builder()
                        .firstName("King")
                        .lastName("Kong")
                        .email("kingKong@gmail.com")
                        .gender(Gender.MALE)
                        .date_of_birth("13-08-1990")
                        .phone("9859595959")
                        .isActive(true)
                        .customer(customer4)
                        .verificationStatus(true)
                        .address("No Address")
                        .role(Role.ADMIN)
                        .password(passwordEncoder.encode("password123453"))
                        .isActive(true)

                        .build();
                Person savedperson4 = personRepository.save(person4);

                customer1.setPerson(person4);

                Wallet wallet3 = Wallet.builder()
                        .baseCurrency(BaseCurrency.NAIRA)
                        .accountBalance(BigDecimal.valueOf(400000))
                        .build();

                customer4.setPerson(savedperson4);
                customer4.setWallet(wallet3);
                customerRepository.save(customer4);



            }

            if (!categoryRepository.existsById(1L)) {

                Category table = Category.builder()
                        .name("Table")
                        .build();
                Category chair = Category.builder()
                        .name("Chair")
                        .build();
                Category cupboard = Category.builder()
                        .name("Cupboard")
                        .build();
                Category sofa = Category.builder()
                        .name("Sofa")
                        .build();
                Category dresser = Category.builder()
                        .name("Dresser")
                        .build();
                Category modern = Category.builder()
                        .name("Modern")
                        .build();
                Category lamps = Category.builder()
                        .name("Lamps")
                        .build();
                Category wooden = Category.builder()
                        .name("Wooden")
                        .build();
                List<Category> categories = List.of(table, sofa, cupboard, chair, dresser, modern, lamps, wooden);
                categoryRepository.saveAll(categories);
            }

            if (!subCategoryRepository.existsById(1L)) {
                Category table = categoryRepository.findById(1L).orElseThrow(() -> new ProductNotFoundException("Not found!"));
                Category sofa = categoryRepository.findById(2L).orElseThrow(() -> new ProductNotFoundException("Not found!"));
                Category cupboard = categoryRepository.findById(3L).orElseThrow(() -> new ProductNotFoundException("Not found!"));
                Category chair = categoryRepository.findById(4L).orElseThrow(() -> new ProductNotFoundException("Not found!"));
                Category dresser = categoryRepository.findById(5L).orElseThrow(() -> new ProductNotFoundException("Not found!"));
                Category modern = categoryRepository.findById(6L).orElseThrow(() -> new ProductNotFoundException("Not found!"));
                Category lamps = categoryRepository.findById(7L).orElseThrow(() -> new ProductNotFoundException("Not found!"));
                Category wooden = categoryRepository.findById(8L).orElseThrow(() -> new ProductNotFoundException("Not found!"));

                SubCategory lamp2 = SubCategory.builder()
                        .name("Coffee Lamp")
                        .category(lamps)
                        .build();
                SubCategory lamp1 = SubCategory.builder()
                        .name("Nighty Lamp")
                        .category(lamps)
                        .build();
                SubCategory lamp3 = SubCategory.builder()
                        .name("Work Lamp")
                        .category(lamps)
                        .build();

                SubCategory table4 = SubCategory.builder()
                        .name("Oak Table")
                        .category(table)
                        .build();
                SubCategory table3 = SubCategory.builder()
                        .name("Dining Table")
                        .category(table)
                        .build();
                SubCategory table2 = SubCategory.builder()
                        .name("Work Table")
                        .category(table)
                        .build();
                SubCategory table1 = SubCategory.builder()
                        .name("Chief Table")
                        .category(table)
                        .build();
                SubCategory chair2 = SubCategory.builder()
                        .name("Work Chair")
                        .category(chair)
                        .build();
                SubCategory chair1 = SubCategory.builder()
                        .name("Sofa Chair")
                        .category(chair)
                        .build();
                SubCategory wooden2 = SubCategory.builder()
                        .name("Sheriff Wood")
                        .category(wooden)
                        .build();
                SubCategory wooden3 = SubCategory.builder()
                        .name("Table Wood")
                        .category(wooden)
                        .build();
                SubCategory wooden1 = SubCategory.builder()
                        .name("Wooden Rack")
                        .category(wooden)
                        .build();
                SubCategory modern2 = SubCategory.builder()
                        .name("Upperside Chair")
                        .category(modern)
                        .build();
                SubCategory modern1 = SubCategory.builder()
                        .name("Minty Fresh Chair")
                        .category(modern)
                        .build();
                SubCategory modern4 = SubCategory.builder()
                        .name("Top Table")
                        .category(modern)
                        .build();
                SubCategory modern3 = SubCategory.builder()
                        .name("High Lifting Cupboard")
                        .category(modern)
                        .build();
                SubCategory sofa3 = SubCategory.builder()
                        .name("Home Sofa")
                        .category(sofa)
                        .build();
                SubCategory sofa2 = SubCategory.builder()
                        .name("Work Sofa")
                        .category(sofa)
                        .build();
                SubCategory sofa1 = SubCategory.builder()
                        .name("Living Room Sofa")
                        .category(sofa)
                        .build();
                SubCategory cupboard1 = SubCategory.builder()
                        .name("Dresser")
                        .category(cupboard)
                        .build();
                SubCategory cupboard2 = SubCategory.builder()
                        .name("6ft Cupboard")
                        .category(cupboard)
                        .build();
                SubCategory dresser4 = SubCategory.builder()
                        .name("BK Dresser")
                        .category(dresser)
                        .build();
                SubCategory dresser3 = SubCategory.builder()
                        .name("Upperside Dresser")
                        .category(dresser)
                        .build();
                SubCategory dresser2 = SubCategory.builder()
                        .name("Minty Fresh Dresser")
                        .category(dresser)
                        .build();
                SubCategory dresser1 = SubCategory.builder()
                        .name("Top Dresser")
                        .category(dresser)
                        .build();
                List<SubCategory> subCategories = List.of(
                        table1, table2, table3, table4,
                        wooden2, wooden1, wooden3,
                        sofa1, sofa2, sofa3,
                        cupboard1, cupboard2,
                        chair1, chair2,
                        modern1, modern2, modern3, modern4,
                        dresser1, dresser2, dresser3, dresser4,
                        lamp1, lamp2, lamp3
                );


                subCategoryRepository.saveAll(subCategories);


            }

            if (!productRepository.existsById(1L)) {

                SubCategory table1 = subCategoryRepository.findById(1L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory table2 = subCategoryRepository.findById(2L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory table3 = subCategoryRepository.findById(3L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory table4 = subCategoryRepository.findById(4L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory wooden2 = subCategoryRepository.findById(5L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory wooden1 = subCategoryRepository.findById(6L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));

                SubCategory wooden3 = subCategoryRepository.findById(7L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory sofa1 = subCategoryRepository.findById(8L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));

                SubCategory sofa2 = subCategoryRepository.findById(9L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory sofa3 = subCategoryRepository.findById(10L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory cupboard1 = subCategoryRepository.findById(11L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));

                SubCategory cupboard2 = subCategoryRepository.findById(12L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory chair1 = subCategoryRepository.findById(13L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));

                SubCategory chair2 = subCategoryRepository.findById(14L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory modern1 = subCategoryRepository.findById(15L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));

                SubCategory modern2 = subCategoryRepository.findById(16L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));

                SubCategory modern3 = subCategoryRepository.findById(17L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory modern4 = subCategoryRepository.findById(18L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory dresser1 = subCategoryRepository.findById(19L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory dresser2 = subCategoryRepository.findById(20L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));

                SubCategory dresser3 = subCategoryRepository.findById(21L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory dresser4 = subCategoryRepository.findById(22L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory lamp1 = subCategoryRepository.findById(23L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory lamp2 = subCategoryRepository.findById(24L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));
                SubCategory lamp3 = subCategoryRepository.findById(25L).orElseThrow(() -> new ProductNotFoundException("Category not found!"));


                List<Product> products = List.of(
                        Product.builder()
                                .name("Oppola")
                                .price(40000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(lamp1)
                                .description("lovely fur")
                                .build(),


                        Product.builder()
                                .name("Table")
                                .price(10000.00)
                                .availableQty(20)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("brown")
                                .subCategory(lamp2)
                                .description("lovely furnished Lorem Ipsum")
                                .build(),
                        Product.builder()
                                .name("Coffee Lamp")
                                .price(50000.00)
                                .availableQty(100)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("sunset yellow")
                                .subCategory(lamp3)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Jummy")
                                .price(20000.00)
                                .availableQty(440)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("gray")
                                .subCategory(modern1)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Cupboard")
                                .price(40000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(modern2)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola15")
                                .price(41000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(modern3)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola16")
                                .price(42000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(chair1)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola17")
                                .price(43000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(chair2)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola18")
                                .price(44000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(dresser4)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola19")
                                .price(45000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(table4)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola20")
                                .price(46000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(table3)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola21")
                                .price(47000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(modern1)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola22")
                                .price(48000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(sofa1)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola23")
                                .price(49000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(sofa2)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola24")
                                .price(50000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(sofa3)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola25")
                                .price(51000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(cupboard1)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola26")
                                .price(52000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(cupboard2)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola27")
                                .price(53000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(dresser2)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola28")
                                .price(54000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(dresser3)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola29")
                                .price(55000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(modern4)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola30")
                                .price(56000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(modern1)
                                .description("lovely fur")
                                .build(),

                        Product.builder()
                                .name("Oppola31")
                                .price(43000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(modern1)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola32")
                                .price(44000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(sofa1)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola33")
                                .price(45000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(chair1)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola34")
                                .price(46000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(chair2)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola35")
                                .price(47000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(cupboard2)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola36")
                                .price(48000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(cupboard1)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola38")
                                .price(49000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(table4)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola39")
                                .price(50000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(modern4)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola40")
                                .price(51000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(modern3)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola41")
                                .price(52000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(modern2)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola42")
                                .price(53000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(modern1)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola43")
                                .price(54000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(wooden3)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola44")
                                .price(55000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(wooden2)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola45")
                                .price(56000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(wooden1)
                                .description("lovely fur")
                                .build(),

                        Product.builder()
                                .name("Oppola46")
                                .price(43000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(sofa3)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola47")
                                .price(44000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(sofa3)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola48")
                                .price(45000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(sofa2)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola49")
                                .price(46000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(sofa1)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola50")
                                .price(47000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(chair2)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola51")
                                .price(48000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(chair1)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola52")
                                .price(49000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(table4)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola53")
                                .price(50000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(table3)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola54")
                                .price(51000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(table2)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola55")
                                .price(52000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(table1)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola56")
                                .price(53000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(dresser1)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola57")
                                .price(54000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(dresser3)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola58")
                                .price(55000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(dresser2)
                                .description("lovely fur")
                                .build(),
                        Product.builder()
                                .name("Oppola59")
                                .price(56000.00)
                                .availableQty(400)
                                .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                                .color("yellow")
                                .subCategory(dresser1)
                                .description("lovely fur")
                                .build()
                );


                productRepository.saveAll(products);
            }

        };
    }
}