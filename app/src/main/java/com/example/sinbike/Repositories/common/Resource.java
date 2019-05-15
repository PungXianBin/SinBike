package com.example.sinbike.Repositories.common;

/**
 * Dependencies
 */
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Resource class for the Repository
 * @param <T>
 */
@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public final class Resource<T> {

    /**
     * Declaration of variables needed for the class to work.
     */
    @Nullable
    private final T data;
    @Nullable
    private final Exception error;

    /**
     * Constructor
     * @param data
     */
    public Resource(@NonNull T data) {
        this(data, null);
    }

    /**
     * Parameterized Constructor
     * @param exception
     */
    public Resource(@NonNull Exception exception) {
        this(null, exception);
    }

    /**
     * Parameterized Constructor
     * @param value
     * @param error
     */
    private Resource(@Nullable T value, @Nullable Exception error) {
        this.data = value;
        this.error = error;
    }

    public boolean isSuccessful() {
        return data != null && error == null;
    }

    /**
     * Getting the object.
     * @return
     */
    @NonNull
    public T data() {
        if (error != null) {
            throw new IllegalStateException("error is not null. Call isSuccessful() first.");
        }
        return data;
    }

    /**
     * Getting the error.
     * @return
     */
    @NonNull
    public Exception error() {
        if (data != null) {
            throw new IllegalStateException("data is not null. Call isSuccessful() first.");
        }
        return error;
    }
}
