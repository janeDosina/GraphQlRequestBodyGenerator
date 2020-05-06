package com.github.vladislavsevruk.generator.test.data;

import com.github.vladislavsevruk.generator.annotation.GqlDelegate;
import com.github.vladislavsevruk.generator.annotation.GqlField;
import com.github.vladislavsevruk.generator.annotation.GqlIgnore;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class GenericTestModelWithAnnotations<T> {

    @GqlField(withSelectionSet = true)
    private Collection<T> collectionEntity;
    @GqlField
    private Collection<T> collectionField;
    @GqlDelegate
    private T fieldWithDelegateAnnotation;
    @GqlField(withSelectionSet = true)
    private T fieldWithEntityAnnotation;
    @GqlField
    private Long fieldWithFieldAnnotation;
    @GqlIgnore
    private Long fieldWithIgnoreAnnotation;
    private Long fieldWithoutAnnotations;
    @GqlField(name = "idField")
    private Long id;
    @GqlField(name = "id")
    private Long idField;
    @GqlField(withSelectionSet = true)
    private List<T> listEntity;
    @GqlField(name = "customNamedEntity", withSelectionSet = true)
    private T namedEntity;
    @GqlField(name = "customNamedField")
    private Long namedField;
    @GqlField(name = "customNamedNonNullableEntity", nonNullable = true, withSelectionSet = true)
    private T namedNonNullableEntity;
    @GqlField(name = "customNamedNonNullableField", nonNullable = true)
    private Long namedNonNullableField;
    @GqlField(nonNullable = true, withSelectionSet = true)
    private T nonNullableEntity;
    @GqlField(nonNullable = true)
    private Long nonNullableField;
    @GqlField(withSelectionSet = true)
    private Queue<T> queueEntity;
    @GqlField(withSelectionSet = true)
    private Set<T> setEntity;
}
