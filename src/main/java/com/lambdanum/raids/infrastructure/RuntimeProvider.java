package com.lambdanum.raids.infrastructure;

public interface RuntimeProvider<T> {

    T get();
}
