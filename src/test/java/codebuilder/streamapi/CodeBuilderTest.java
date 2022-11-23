package codebuilder.streamapi;

import codebuilder.streamapi.models.Order;
import codebuilder.streamapi.models.Product;
import codebuilder.streamapi.repos.OrderRepo;
import codebuilder.streamapi.repos.ProductRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * https://blog.devgenius.io/15-practical-exercises-help-you-master-java-stream-api-3f9c86b1cf82
 */

@DataJpaTest
public class CodeBuilderTest {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrderRepo orderRepo;

    /**
     * Exercises 1 : Obtain a list of products belongs to category “Books” with price > 100
     * Exercises 1 : Obtain a list of order with products belong to category “Baby”
     * Exercises 1 :
     * Exercises 1 : Obtain a list of products ordered by customer of tier 2 between 01-Feb-2021 and 01-Apr-2021
     * Exercises 1 : Get the cheapest products of “Books” category
     * Exercises 1 :
     * Exercises 1 : Calculate order average payment placed on 14-Mar-2021
     * Exercises 1 : Get the 3 most recent placed order
     * Exercises 1 :
     * Exercises 1 : Get a list of orders which were ordered on 15-Mar-2021, log the order records to the console and " +
     * "then return its product list
     * Exercises 1 :
     * Exercises 1 : Obtain a list of product with category = “Toys” and then apply 10% discount
     * Exercises 1 : Calculate total lump sum of all orders placed in Feb 2021
     * Exercises 1 :
     */


    @Test
    @DisplayName("Obtain a list of products belongs to category “Books” with price > 100")
    public void exercises1() {

        List<Product> products = productRepo.findAll().stream()
                .filter(product -> product.getCategory().equals("Books"))
                .filter(product -> product.getPrice() > 100)
                .collect(Collectors.toList());
        products.forEach(System.out::println);
    }

    @Test
    @DisplayName("Obtain a list of order with products belong to category “Baby”")
    public void exercises2() {
        List<Order> orders = orderRepo.findAll().stream()
                .filter(order -> order.getProducts().stream().anyMatch(product -> product.getCategory().equals("Baby")))
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName("Obtain a list of product with category = “Toys” and then apply 10% discount")
    public void exercises3() {
        List<Product> products = productRepo.findAll().stream()
                .filter(p -> p.getCategory().equals("Toys"))
                .map(p -> p.withPrice(p.getPrice() * 0.9))
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName("Obtain a list of products ordered by customer of tier 2 between 01-Feb-2021 and 01-Apr-2021")
    public void exercises4() {
        List<Product> products = orderRepo.findAll().stream()
                .filter(o -> o.getCustomer().getTier() == 2)
                .filter(o -> o.getOrderDate().compareTo(LocalDate.of(2021, 2, 1)) >= 0)
                .filter(o -> o.getOrderDate().compareTo(LocalDate.of(2021, 4, 1)) <= 0)
                .flatMap(o -> o.getProducts().stream())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("==========================");
        products.forEach(System.out::println);
        System.out.println("==========================");
    }

    @Test
    @DisplayName("Get the cheapest products of “Books” category")
    public void exercises5() {
        Optional<Product> cheapestProduct = productRepo.findAll().stream()
                .filter(p -> p.getCategory().equals("Book"))
                .min(Comparator.comparing(Product::getPrice));
    }

    @Test
    @DisplayName("Get the 3 most recent placed order")
    public void exercises6() {
        List<Order> orders = orderRepo.findAll().stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .collect(Collectors.toList());
        orders.forEach(System.out::println);
    }

    @Test
    @DisplayName(" Get a list of orders which were ordered on 15-Mar-2021, log the order records to the console and " +
            "then return its product list")
    public void exercises7() {
        List<Product> results = orderRepo.findAll().stream()
                .filter(o -> o.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .peek(System.out::println)
                .flatMap(o -> o.getProducts().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName("Calculate total lump sum of all orders placed in Feb 2021")
    public void exercises8() {
        Double result = orderRepo.findAll().stream()
                .filter(order -> order.getOrderDate().compareTo(LocalDate.of(2021, 2, 1)) >= 0 &&
                        order.getOrderDate().compareTo(LocalDate.of(2021, 3, 1)) < 0)
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(product -> product.getPrice())
                .sum();
        System.out.println(result);
    }

    @Test
    @DisplayName("Calculate order average payment placed on 14-Mar-2021")
    public void exercises9() {
        Double result = orderRepo.findAll().stream()
                .filter(order -> order.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .average()
                .getAsDouble();
        System.out.println(result);

    }


}
