package piyushjohnson.mytyre.ui.cart;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.repo.CartRepository;

public class CartViewModel extends ViewModel {

    private final CartRepository cartRepository;
    private LiveData<List<Tyre>> cartItems;

    @Inject
    CartViewModel(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
        cartItems = cartRepository.getCartTyres();
    }

    public LiveData<List<Tyre>> getCartItems() {
        return cartItems;
    }

    public void addCartItem(Tyre cartItem) {
        cartRepository.addCartTyre(cartItem);
    }

    public void removeCartItem(Tyre cartItem) {
        cartRepository.removeCartTyre(cartItem);
    }

    public CartRepository getCartRepository() {
        return cartRepository;
    }
}
