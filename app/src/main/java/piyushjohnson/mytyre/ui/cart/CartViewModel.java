package piyushjohnson.mytyre.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import piyushjohnson.mytyre.model.Tyre;
import piyushjohnson.mytyre.repo.CartRepository;

public class CartViewModel extends ViewModel {

    private static final String TAG = "CartViewModel";

    private final CartRepository cartRepository;
    private LiveData<List<Tyre>> cartItems;

    public int getTotalItemCount() {
        return cartItems.getValue().size();
    }

    @Inject
    CartViewModel(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
        cartItems = cartRepository.getCartTyres();
    }

    public LiveData<List<Tyre>> getCartItems() {
        return cartItems;
    }

    public void setCartItemCount(Tyre cartItem, CartAction cartAction) {
        if (cartItems.getValue().contains(cartItem)) ;
        cartRepository.setCartItemCount(cartItem, cartAction);
    }

    public enum CartAction {
        QUANTITY_UP, QUANTITY_DOWN
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
