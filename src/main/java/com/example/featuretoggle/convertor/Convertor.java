package com.example.featuretoggle.convertor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface Convertor<E, M> {

    M convertToModel(E entity);

    E convertToEntity(M model);

    default Collection<M> convertToModel(Collection<E> entity) {
        return convertToModel(entity, ArrayList::new);
    }

    default Collection<E> convertToEntity(Collection<M> model) {
        return convertToEntity(model, ArrayList::new);
    }

    default <C extends Collection<M>> C convertToModel(Collection<E> entity, Supplier<C> supplier) {
        return entity.stream().map(this::convertToModel).collect(Collectors.toCollection(supplier));
    }

    default <C extends Collection<E>> C convertToEntity(Collection<M> model, Supplier<C> supplier) {
        return model.stream().map(this::convertToEntity).collect(Collectors.toCollection(supplier));
    }

}
