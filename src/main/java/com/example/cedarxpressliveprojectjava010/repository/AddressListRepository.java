package com.example.cedarxpressliveprojectjava010.repository;

import com.example.cedarxpressliveprojectjava010.entity.AddressList;
import com.example.cedarxpressliveprojectjava010.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressListRepository extends JpaRepository<AddressList,Long> {
    Optional<AddressList> findAddressListByCustomer(User customer);
}
