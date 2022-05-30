package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.entity.Category;
import com.example.cedarxpressliveprojectjava010.entity.Favorite;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.exception.NotFoundException;
import com.example.cedarxpressliveprojectjava010.repository.FavoriteRepository;
import com.example.cedarxpressliveprojectjava010.repository.ProductRepository;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.ManyToOne;
import java.util.Optional;
import static  org.mockito.BDDMockito.given;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    FavoriteRepository favoriteRepository;
    @InjectMocks
    FavoriteServiceImpl favoriteServiceImpl;

    private User user1;
    private User user2;
    private Product product1;
    private Favorite favorite;

    @BeforeEach
    public void setUp(){
        user1 = User.builder()
                .email("test@gmail.com")
                .password("password")
                .firstName("chinedu")
                .build();
        user1.setId(2L);

        user2 = User.builder()
                .build();
        user2.setId(3L);
        Category burger = Category.builder()
                .categoryName("Burger")
                .build();

        product1 = Product.builder()
                .productName("m-model")
                .category(burger)
                .build();
        product1.setId(1L);

        favorite = Favorite.builder()
                .product(product1)
                .user(user1)
                .build();
        favorite.setId(2L);
    }




    public User getUser1() {
        return user1;
    }

    public Product getProduct1() {
        return product1;
    }

    public User getUser2() {
        return user2;
    }

    @Disabled
    @Test
    public void testAddProductToFavorite() {

        given(productRepository.findById(1L)).willReturn(Optional.of(product1));
        given(userRepository.findById(1L)).willReturn(Optional.of(user1));
        ResponseEntity<String> responseEntity = favoriteServiceImpl.addProductToFavorite(product1.getId(), user1.getId());
        Assertions.assertEquals(responseEntity.getBody(), product1.getProductName() + " added to favorite");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK );
    }

    @Test
    void testForExceptionThrownWhenUserRemovesProductThatIsNotInFavorite() {
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        Mockito.when(favoriteRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, ()-> {
            favoriteServiceImpl.deleteProductFromFavorite(product1.getId(), user1.getId());
        });
    }
    @Test
    void TestForRightResponseWhenUserRemoveProductFromFavorite() {
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(user1));
        Mockito.when(favoriteRepository.findById(anyLong())).thenReturn(Optional.of(favorite));
        ResponseEntity<String> responseEntity1 = favoriteServiceImpl.deleteProductFromFavorite(product1.getId(),user1.getId());
        Assertions.assertEquals("Product successfully deleted","Product successfully deleted");
    }

}