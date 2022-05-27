package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Favorite;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.exception.NotFoundException;
import com.example.cedarxpressliveprojectjava010.exception.ProductNotFoundException;
import com.example.cedarxpressliveprojectjava010.repository.FavoriteRepository;
import com.example.cedarxpressliveprojectjava010.repository.ProductRepository;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.service.FavoriteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;



    @Override
    public ResponseEntity<String> addProductToFavorite(Long productId, Long userId) {
        User user1 = userRepository.findById(userId)
              .orElseThrow(() -> new UsernameNotFoundException("User with ID" + " " + userId + " does not exist"));

        Product product1 = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("product with ID " + productId + " Does not Exist"));


        Favorite favorite = new Favorite();

        favorite.setUser(user1);
        favorite.setProduct(product1);
        favoriteRepository.save(favorite);


        return new ResponseEntity<>(product1.getProductName() + " added to favorite", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteProductFromFavorite(Long productId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UsernameNotFoundException("User with this" + " " + userId +" does not exist " ));

        Favorite addedFavoriteProduct = favoriteRepository.findById(productId)
                .orElseThrow(()-> new NotFoundException("Resources does not exist"));
        favoriteRepository.delete(addedFavoriteProduct);



        return new ResponseEntity<String>("Product successfully deleted",HttpStatus.OK);
    }
}
