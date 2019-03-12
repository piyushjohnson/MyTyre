package piyushjohnson.mytyre.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public final class Resource<T> {

    @Nullable
    private final T data;

    @Nullable
    private final Exception error;

    public Resource(@NonNull T data) {
        this(data, null);
    }

    public Resource(@NonNull Exception error) {
        this(null, error);
    }

    public Resource(@Nullable T data, @Nullable Exception error) {
        this.data = data;
        this.error = error;
    }

    public boolean isSuccessful() {
        return data != null && error == null;
    }

    public T data() {
        if (error != null) {
            throw new IllegalStateException("Error is not null, call isSuccessful first");
        }
        return data;
    }

    public Exception error() {
        if (data != null) {
            throw new IllegalStateException("Data is not null, call isSuccessful first");
        }
        return error;
    }

}
