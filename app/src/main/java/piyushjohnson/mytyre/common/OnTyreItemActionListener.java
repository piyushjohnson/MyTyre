package piyushjohnson.mytyre.common;

import piyushjohnson.mytyre.model.Tyre;

public interface OnTyreItemActionListener extends OnItemActionListener {
    void onTyreAddToCart(Tyre tyre);

    void onTyreBuyNow(Tyre tyre);
}
