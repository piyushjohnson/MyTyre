package piyushjohnson.mytyre.common;

import com.google.firebase.firestore.Source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public final class Resource<T> {

    @Nullable
    private final T data;

    @Nullable
    private final Source source;

    @Nullable
    private final Exception error;

    public Resource(@NonNull T data, @NonNull Source source) {
        this(data, null, source);
    }

    public Resource(@NonNull Exception error, @NonNull Source source) {
        this(null, error, source);
    }

    public Resource(@Nullable T data, @Nullable Exception error, Source source) {
        this.data = data;
        this.error = error;
        this.source = source;
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

    public boolean isStale() {
        return isSuccessful() && source == Source.CACHE;
    }

    public Exception error() {
        if (data != null) {
            throw new IllegalStateException("Data is not null, call isSuccessful first");
        }
        return error;
    }

}
