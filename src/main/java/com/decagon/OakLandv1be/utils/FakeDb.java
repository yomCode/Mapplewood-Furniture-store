package com.decagon.OakLandv1be.utils;

import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.BaseCurrency;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.exceptions.NotAvailableException;
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

    private final StateRepository stateRepository;
    private final PickupRepository pickupRepository;

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
                        .admin(admin)
                        .address("No Address")
                        .role(Role.ADMIN)
                        .password(passwordEncoder.encode("password123453"))
                        .isActive(true)
                        .build();

                Person adminPerson = personRepository.save(person);
                admin.setPerson(adminPerson);
                Admin savedAdmin = adminRepository.save(admin);

                Customer customer = new Customer();
                Person customerPerson = Person.builder()
                        .firstName("Joe")
                        .lastName("Lennon")
                        .email("bennyson2@gmail.com")
                        .gender(Gender.MALE)
                        .date_of_birth("1996-03-12")
                        .phone("9859595959")
                        .isActive(true)
                        .verificationStatus(true)
                        .role(Role.CUSTOMER)
                        .customer(customer)
                        .address("No Address")
                        .password(passwordEncoder.encode("password123453"))
                        .isActive(true)

                        .build();
                Person savedPerson = personRepository.save(customerPerson);
                customer.setPerson(savedPerson);
                customerRepository.save(customer);

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

            if(!stateRepository.existsById(1L)) {
                State lagos = State.builder()
                        .name("Lagos".toUpperCase())
                        .build();
                State kwara = State.builder()
                        .name("Kwara".toUpperCase())
                        .build();
                State anambra = State.builder()
                        .name("Anambra".toUpperCase())
                        .build();
                State osun = State.builder()
                        .name("Osun".toUpperCase())
                        .build();
                State akwaibom = State.builder()
                        .name("AkwaIbom".toUpperCase())
                        .build();
                State kano = State.builder()
                        .name("Kano".toUpperCase())
                        .build();
                List<State> states = List.of(lagos, kwara, anambra, osun, akwaibom, kano);
                stateRepository.saveAll(states);
            }

            if(!pickupRepository.existsById(1L)) {
                State lagos = stateRepository.findById(1L).orElseThrow(() -> new NotAvailableException("State not found"));
                State kwara = stateRepository.findById(2L).orElseThrow(() -> new NotAvailableException("State not found"));
                State anambra = stateRepository.findById(3L).orElseThrow(() -> new NotAvailableException("State not found"));
                State osun = stateRepository.findById(4L).orElseThrow(() -> new NotAvailableException("State not found"));
                State akwaibom = stateRepository.findById(5L).orElseThrow(() -> new NotAvailableException("State not found"));
                State kano = stateRepository.findById(6L).orElseThrow(() -> new NotAvailableException("State not found"));

                PickupCenter lagosPickup = PickupCenter.builder()
                        .name("Lagos Pickup Center")
                        .state(lagos)
                        .email("lagospickup@oakland.com")
                        .phone("09033444123")
                        .address("12 Ibom Street, Allen, Ikeja, Lagos")
                        .build();

                PickupCenter lagosPickup2 = PickupCenter.builder()
                        .name("2nd Lagos Pickup Center")
                        .state(lagos)
                        .email("lagospickuptwo@oakland.com")
                        .phone("09033444111")
                        .address("1 Aka Street, Allen, Oshodi, Lagos")
                        .build();

                PickupCenter kwaraPickup = PickupCenter.builder()
                        .name("Kwara Pickup Center")
                        .state(kwara)
                        .email("kwarapickup@oakland.com")
                        .phone("08022444123")
                        .address("13 Ibom Street, Allen, Kwara")
                        .build();

                PickupCenter anambraPickup = PickupCenter.builder()
                        .name("Anambra Pickup Center")
                        .state(anambra)
                        .email("anambrapickup@oakland.com")
                        .phone("09011444123")
                        .address("14 Ibom Street, Allen, Nnewi, Anmabra")
                        .build();

                PickupCenter osunPickup = PickupCenter.builder()
                        .name("Osun Pickup Center")
                        .state(osun)
                        .email("osunpickup@oakland.com")
                        .phone("08000444123")
                        .address("15 Ibom Street, Oshogbo, Osun")
                        .build();

                PickupCenter akwaIbomPickup = PickupCenter.builder()
                        .name("AkwaIbom Pickup Center")
                        .state(akwaibom)
                        .email("akwaibompickup@oakland.com")
                        .phone("09055444123")
                        .address("16 Ibom Street, AkwaIbom")
                        .build();

                PickupCenter kanoPickup = PickupCenter.builder()
                        .name("Kano Pickup Center")
                        .state(kano)
                        .email("kanopickup@oakland.com")
                        .phone("08133444123")
                        .address("12 Ibom Street, kano, Kano")
                        .build();

                List<PickupCenter> pickupCenters = List.of(lagosPickup, lagosPickup2, kwaraPickup, anambraPickup, osunPickup, akwaIbomPickup, kanoPickup);
                pickupRepository.saveAll(pickupCenters);
            }

            if (!categoryRepository.existsById(1L)) {

                Category table = Category.builder()
                        .name("Table")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/RBSJBF@2x-300x300.jpg")
                        .build();
                Category chair = Category.builder()
                        .name("Chair")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-WAX664T@2x-300x300.jpg")
                        .build();
                Category cupboard = Category.builder()
                        .name("Cupboard")
                        .imageUrl("https://th.bing.com/th/id/R.309800b9a251e7f8d14b8b468cbe60c4?rik=b2DGwVd%2ff1xqUQ&pid=ImgRaw&r=0")
                        .build();
                Category sofa = Category.builder()
                        .name("Sofa")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/4PHLD2@2x-300x300.jpg")
                        .build();
                Category dresser = Category.builder()
                        .name("Dresser")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/YMN7ZV@2x-300x300.jpg")
                        .build();
                Category modern = Category.builder()
                        .name("Modern")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Group-1@2x-300x300.jpg")
                        .build();
                Category lamps = Category.builder()
                        .name("Lamps")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/NAM2CS@2x-300x300.jpg")
                        .build();
                Category wooden = Category.builder()
                        .name("Wooden")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/92DNEPD@2x-300x300.jpg")
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
                        .name("Deco Lamp")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/XFSNSK@2x-300x300.jpg")
                        .category(lamps)
                        .build();
                SubCategory lamp1 = SubCategory.builder()
                        .name("Table Lamp")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-NAM2CS@2x-300x300.jpg")
                        .category(lamps)
                        .build();

                SubCategory table4 = SubCategory.builder()
                        .name("Coffee Table")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/J7ZW2XK@2x-300x300.jpg")
                        .category(table)
                        .build();
                SubCategory table3 = SubCategory.builder()
                        .name("End Table")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-RBSJBF@2x-300x300.jpg")
                        .category(table)
                        .build();
                SubCategory table2 = SubCategory.builder()
                        .name("Modern Table")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Group-1@2x-300x300.jpg")
                        .category(table)
                        .build();
                SubCategory table1 = SubCategory.builder()
                        .name("Table Wood")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/3N8FQJ@2x-300x300.jpg")
                        .category(table)
                        .build();
                SubCategory chair2 = SubCategory.builder()
                        .name("Recliner Chair")
                        .imageUrl("https://th.bing.com/th/id/R.5bdd3851db3752d798d5d9debb46a9a7?rik=FqdmQr%2b8UzxR2g&pid=ImgRaw&r=0")
                        .category(chair)
                        .build();
                SubCategory chair1 = SubCategory.builder()
                        .name("Arm Chair")
                        .imageUrl("https://th.bing.com/th/id/OIP.yYV6XzWVGdCVZI2h2uXlGAHaDt?pid=ImgDet&rs=1")
                        .category(chair)
                        .build();
                SubCategory wooden2 = SubCategory.builder()
                        .name("Shelves Wood")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-U5BW8PS@2x-300x300.jpg")
                        .category(wooden)
                        .build();
                SubCategory wooden3 = SubCategory.builder()
                        .name("Table Wood")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/3N8FQJ@2x-300x300.jpg")
                        .category(wooden)
                        .build();
                SubCategory wooden1 = SubCategory.builder()
                        .name("Wooden Rack")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-92DNEPD@2x-300x300.jpg")
                        .category(wooden)
                        .build();
                SubCategory modern2 = SubCategory.builder()
                        .name("New Age Chair")
                        .imageUrl("https://th.bing.com/th/id/OIP.IK6LM7F-kXcngeThmlB5KgAAAA?pid=ImgDet&w=300&h=300&rs=1")
                        .category(modern)
                        .build();
                SubCategory modern1 = SubCategory.builder()
                        .name("Modern Chair")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Image-WAX664T@2x-300x300.jpg")
                        .category(modern)
                        .build();
                SubCategory modern4 = SubCategory.builder()
                        .name("Modern Table")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/Group-1@2x-300x300.jpg")
                        .category(modern)
                        .build();
                SubCategory sofa2 = SubCategory.builder()
                        .name("Lounge Sofa")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/JD46ETY@2x-300x300.jpg")
                        .category(sofa)
                        .build();
                SubCategory sofa1 = SubCategory.builder()
                        .name("Luxury Sofa")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/4PHLD2@2x-300x300.jpg")
                        .category(sofa)
                        .build();
                SubCategory cupboard1 = SubCategory.builder()
                        .name("Arc Cupboard")
                        .imageUrl("https://th.bing.com/th/id/OIP.PoITRe2lfX9fz-U35ixkggAAAA?pid=ImgDet&rs=1")
                        .category(cupboard)
                        .build();
                SubCategory cupboard2 = SubCategory.builder()
                        .name("Kitchen Cupboard")
                        .imageUrl("https://th.bing.com/th/id/OIP.uf0BRnMEJEOxk3tx2mHGMgHaHa?pid=ImgDet&w=500&h=500&rs=1")
                        .category(cupboard)
                        .build();
                SubCategory dresser1 = SubCategory.builder()
                        .name("Scandinavia Dresser")
                        .imageUrl("https://templatekit.jegtheme.com/funiture/wp-content/uploads/sites/18/2020/11/YMN7ZV@2x-300x300.jpg")
                        .category(dresser)
                        .build();
                List<SubCategory> subCategories = List.of(
                        table1, table2, table3, table4,
                        wooden2, wooden1, wooden3,
                        sofa1, sofa2,
                        cupboard1, cupboard2,
                        chair1, chair2,
                        modern1, modern2, modern4,
                        dresser1,
                        lamp1, lamp2
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