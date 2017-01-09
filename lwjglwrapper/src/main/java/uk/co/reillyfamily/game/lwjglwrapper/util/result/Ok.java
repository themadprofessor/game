package uk.co.reillyfamily.game.lwjglwrapper.util.result;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by stuart on 08/01/17.
 */
public class Ok<V, E extends Exception> implements Result<V, E> {
    private final V val;

    Ok(V val) {
        this.val = val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V ok() {
        return val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V okElse(V or) {
        return val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V okElseGet(Supplier<? extends V> other) {
        return val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E err() {
        throw new IllegalStateException("Result is a value not an exception!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E errElse(E or) {
        return or;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E errElseGet(Supplier<? extends E> other) {
        return other.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> Result<U, E> and(Result<U, E> and) {
        return and;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> Result<U, E> andThen(Supplier<Result<U, E>> supp) {
        return supp.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U extends Exception> Result<V, U> or(Result<V, U> or) {
        return Result.of(val);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U extends Exception> Result<V, U> orThen(Supplier<Result<V, U>> supp) {
        return Result.of(val);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOk() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isErr() {
        return false;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void ifOk(Consumer<V> consumer) {
        consumer.accept(val);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void ifErr(Consumer<E> consumer) {}

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> Result<U, E> map(Function<V, ? extends U> mapper) {
        return Result.of(mapper.apply(val));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> Result<U, E> flatMap(Function<V, Result<U, E>> mapper) {
        return mapper.apply(val);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U extends Exception> Result<V, U> mapErr(Function<E, ? extends U> mapper) {
        return (Result<V, U>) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U extends Exception> Result<V, U> flatMapErr(Function<E, Result<V, U>> mapper) {
        return (Result<V, U>) this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ok<?, ?> ok = (Ok<?, ?>) o;
        return Objects.equals(val, ok.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public String toString() {
        return "Value Result: " + val;
    }
}
