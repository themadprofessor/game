package uk.co.reillyfamily.game.lwjglwrapper.result;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by stuart on 08/01/17.
 */
class Err<V, E extends Exception> implements Result<V, E> {
    private final E err;

    Err(E err) {
        this.err = err;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V ok() {
        throw new IllegalStateException("Result is an exception, not a value!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V okElse(V or) {
        return or;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V okElseGet(Supplier<? extends V> other) {
        return other.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E err() {
        return err;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E errElse(E or) {
        return err;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E errElseGet(Supplier<? extends E> other) {
        return err;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> Result<U, E> and(Result<U, E> and) {
        return Result.err(err);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> Result<U, E> andThen(Supplier<Result<U, E>> supp) {
        return Result.err(err);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U extends Exception> Result<V, U> or(Result<V, U> or) {
        return or;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U extends Exception> Result<V, U> orThen(Supplier<Result<V, U>> supp) {
        return supp.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOk() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isErr() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void ifOk(Consumer<V> consumer) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void ifErr(Consumer<E> consumer) {
        consumer.accept(err);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> Result<U, E> map(Function<V, ? extends U> mapper) {
        return (Result<U, E>) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> Result<U, E> flatMap(Function<V, Result<U, E>> mapper) {
        return (Result<U, E>) this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U extends Exception> Result<V, U> mapErr(Function<E, ? extends U> mapper) {
        return Result.err(mapper.apply(err));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U extends Exception> Result<V, U> flatMapErr(Function<E, Result<V, U>> mapper) {
        return mapper.apply(err);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Err<?, ?> err1 = (Err<?, ?>) o;
        return Objects.equals(err, err1.err);
    }

    @Override
    public int hashCode() {
        return Objects.hash(err);
    }

    @Override
    public String toString() {
        return "Excepion Result: " + err;
    }
}
