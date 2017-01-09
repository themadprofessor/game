package uk.co.reillyfamily.game.lwjglwrapper.util.result;

import com.google.common.base.Preconditions;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An object which represents either a value or exception.
 */
public interface Result<V, E extends Exception> {
    /**
     * Creates a Result containing the given non-null value.
     * @param val The value of the Result.
     * @param <V> The class type of the value.
     * @param <E> The class type of the exception.
     * @return The Result containing the value.
     * @throws NullPointerException Thrown if the given value is null.
     */
    static <V, E extends Exception> Result<V, E> of(V val) {
        Preconditions.checkNotNull(val);
        return new Ok<>(val);
    }

    /**
     * Creates a Result containing the given non-null exception.
     * @param err The exception of the Result.
     * @param <V> The class type of the value.
     * @param <E> The class type of the exception.
     * @return The Result containing the exception.
     * @throws NullPointerException Thrown if the given exception is null.
     */
    static <V, E extends Exception> Result<V, E> err(E err) {
        Preconditions.checkNotNull(err);
        return new Err<>(err);
    }

    /**
     * Returns the value of the result, or throws an exception if the result is an exception.
     * @return The value of the result.
     * @throws IllegalStateException Thrown if the result contains an exception.
     */
    V ok();

    /**
     * Returns the value of the result, or the given value if the result is an exception.
     * @param or The value if the result is an exception.
     * @return The value of the result or the given value.
     */
    V okElse(V or);

    /**
     * Returns the value of the result, or the result of other if the result is an exception.
     * @param other A Supplier whose result is returned if the result is an exception.
     * @return The value of the result or the value of other if the result is an exception.
     */
    V okElseGet(Supplier<? extends V> other);

    /**
     * Returns the exception of the result, or throws an exception is the result is a value.
     * @return The exception of the result.
     * @throws IllegalStateException Thrown if the result is a value.
     */
    E err();

    /**
     * Return the exception of the result, or the given value if the result is a value.
     * @param or The exception if the result is a value.
     * @return The exception of the result or the given value.
     */
    E errElse(E or);

    /**
     * Returns the exception of the result, or the result of other if the result is a value.
     * @param other A Supplier whoes result is returned if the result is a value.
     * @return The exception of the result, or the result of other.
     */
    E errElseGet(Supplier<? extends E> other);

    /**
     * Returns the given result if this result is a value, otherwise returns this result.
     * @param and The result returned if this result is a value.
     * @param <U> The type of the returned result's value.
     * @return The given result if this result is a value, otherwise this result.
     */
    <U> Result<U, E> and(Result<U, E> and);

    /**
     * Returns the result of the given Supplier if this result is a value, otherwise returns this result.
     * @param supp A supplier whose return value is returned if this result is a value.
     * @param <U> The type of the returned result's value.
     * @return The return value of the given Supplier if this result is a value, otherwise this result.
     */
    <U> Result<U, E> andThen(Supplier<Result<U, E>> supp);

    /**
     * Returns the given result if this result is an exception, otherwise returns this result.
     * @param or The result returned if this result is an exception.
     * @param <U> The type of the returned result's exception.
     * @return The given result if this result is an exception, otherwise this result.
     */
    <U extends Exception> Result<V, U> or(Result<V, U> or);

    /**
     * Returns the result of the given Supplier if this result is an exception, otherwise returns this result.
     * @param supp A supplier whose return value is returned if this result is an exception.
     * @param <U> The type of the returned result's exception.
     * @return The return value of the given Supplier if this result is an exception, otherwise this result.
     */
    <U extends Exception> Result<V, U> orThen(Supplier<Result<V, U>> supp);

    /**
     * Returns true if this result is a value, otherwise false.
     * @return Returns true if this result is a value, otherwise false.
     */
    boolean isOk();

    /**
     * Returns true if this result is a exception, otherwise false.
     * @return Returns true if this result is a exception, otherwise false.
     */
    boolean isErr();

    /**
     * Calls the given Consumer if this result is a value.
     * @param consumer The consumer to be called if this result is a value.
     */
    void ifOk(Consumer<V> consumer);

    /**
     * Calls the given Consumer if this result is a exception.
     * @param consumer The consumer to be called if this result is a exception.
     */
    void ifErr(Consumer<E> consumer);

    /**
     * If this result is a value, apply the given mapping function to it and return a result with the resulting value.
     * Otherwise, return this result.
     * @param mapper The mapping function to be applied if the result is a value.
     * @param <U> The type of the result of the mapping function.
     * @return Returns a result of the value with the mapping function applied if this result is a value, otherwise this
     * result.
     */
    <U> Result<U, E> map(Function<V, ? extends U> mapper);

    /**
     * If this result if a value, apply the given mapping function and return its result. Otherwise, return this result.
     * @param mapper The mapping function to be applied if the result is a value.
     * @param <U> The type of the value of the result of the mapping function.
     * @return The result of the given mapping function if this result is a value, otherwise this result.
     */
    <U> Result<U, E> flatMap(Function<V, Result<U, E>> mapper);

    /**
     * If this result is an exception, apply the given mapping function to it and return a result with the resulting
     * exception. Otherwise, return this result.
     * @param mapper The mapping function to be applied if the result is an exception.
     * @param <U> The type of the result of the mapping function.
     * @return Returns a result of the value with the mapping function applied if this result is an exception, otherwise
     * this result.
     */
    <U extends Exception> Result<V, U> mapErr(Function<E, ? extends U> mapper);

    /**
     * If this result if an exception, apply the given mapping function and return its result. Otherwise, return this
     * result.
     * @param mapper The mapping function to be applied if the result is an exception.
     * @param <U> The type of the exception of the result of the mapping function.
     * @return The result of the given mapping function if this result is an exception, otherwise this result.
     */
    <U extends Exception> Result<V, U> flatMapErr(Function<E, Result<V, U>> mapper);
}
