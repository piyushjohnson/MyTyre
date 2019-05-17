package piyushjohnson.mytyre.common;

import piyushjohnson.mytyre.model.Tyre;

public interface OnCartTyreItemActionListener extends OnItemActionListener {
    void onCartTyreQuantityUp(Tyre tyre);

    void onCartTyreQuantityDown(Tyre tyre);
}
