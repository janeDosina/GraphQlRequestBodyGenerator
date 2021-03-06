/*
 * MIT License
 *
 * Copyright (c) 2020 Uladzislau Seuruk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.vladislavsevruk.generator.util;

import com.github.vladislavsevruk.generator.annotation.GqlField;
import com.github.vladislavsevruk.generator.annotation.GqlFieldArgument;
import com.github.vladislavsevruk.generator.annotation.GqlUnionType;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Picks name for GraphQL items.
 */
public final class GqlNamePicker {

    private GqlNamePicker() {
    }

    /**
     * Gets name for GraphQL operation from {@link GqlField} annotation if present or using field name.
     *
     * @param field <code>Field</code> to get name for.
     * @return <code>String</code> with field name.
     */
    public static String getFieldName(Field field) {
        GqlField fieldAnnotation = field.getAnnotation(GqlField.class);
        if (fieldAnnotation != null && !fieldAnnotation.name().isEmpty()) {
            return fieldAnnotation.name();
        }
        return field.getName();
    }

    /**
     * Gets name for GraphQL operation from {@link GqlField} annotation with field alias and arguments if present or
     * using field name.
     *
     * @param field <code>Field</code> to get name, alias and arguments for.
     * @return <code>String</code> with field name, alias and arguments.
     */
    public static String getFieldNameWithArgumentsAndAlias(Field field) {
        String fieldName = getFieldName(field);
        GqlField fieldAnnotation = field.getAnnotation(GqlField.class);
        if (fieldAnnotation != null) {
            fieldName = addAliasIfPresent(fieldName, fieldAnnotation);
            fieldName = addArgumentsIfPresent(fieldName, fieldAnnotation);
        }
        return fieldName;
    }

    /**
     * Gets name for GraphQL union type from {@link GqlUnionType} annotation.
     *
     * @param unionType <code>GqlUnionType</code> to get name from.
     * @return <code>String</code> with union type name.
     */
    public static String getUnionName(GqlUnionType unionType) {
        if (!unionType.name().isEmpty()) {
            return unionType.name();
        }
        return unionType.value().getSimpleName();
    }

    private static String addAliasIfPresent(String fieldName, GqlField fieldAnnotation) {
        String alias = fieldAnnotation.alias();
        return alias.isEmpty() ? fieldName : alias + ":" + fieldName;
    }

    private static String addArgumentsIfPresent(String fieldName, GqlField fieldAnnotation) {
        GqlFieldArgument[] arguments = fieldAnnotation.arguments();
        if (arguments.length != 0) {
            String argumentValues = Arrays.stream(arguments).map(GqlNamePicker::generateArgumentValue)
                    .collect(Collectors.joining(","));
            fieldName += "(" + argumentValues + ")";
        }
        return fieldName;
    }

    private static String generateArgumentValue(GqlFieldArgument argument) {
        return argument.name() + ":" + argument.value();
    }
}
