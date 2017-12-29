package com.lambdanum.raids.infrastructure.injection;

public interface RuntimeProvider<T> {

    T get();
}
