package com.example.cedarxpressliveprojectjava010.repository;

import com.example.cedarxpressliveprojectjava010.entity.Order;
import com.example.cedarxpressliveprojectjava010.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByCustomer(User customer);
    @Query(value = "select * from Order o WHERE o.id = :id and o.customer = :customer", nativeQuery = true)
    Optional<Order> findOrderByCustomerAndId(User customer, long id);
}
