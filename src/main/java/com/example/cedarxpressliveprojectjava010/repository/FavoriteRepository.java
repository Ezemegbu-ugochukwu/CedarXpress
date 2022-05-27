package com.example.cedarxpressliveprojectjava010.repository;

import com.example.cedarxpressliveprojectjava010.entity.Favorite;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.User;
import net.bytebuddy.matcher.ElementMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite,Long> {

//    Favorite findProductId( Long productId);
//    Boolean existsByUserAndProduct (User user, Product product);
//    Optional<Favorite> findFavoritesByProduct_ProductIdAndUser_UserId (Long productId);


}
