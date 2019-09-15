package com.netisov.tim.commandes.dto;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;

import java.util.LinkedList;
import java.util.List;

/**
 * La classe pour extendre pour les requettes de recherche.
 */
@SuppressWarnings("unused")
@Getter
@Setter
public abstract class PagingAndSortingRequest<T> {
    @ApiModelProperty(value = "Parameters of sorting", position = 10001)
    protected String sort;
    @ApiModelProperty(value = "Page number",example = "1", position = 10002)
    protected Integer page;
    @ApiModelProperty(value = "Page size", example = "10", position = 10003)
    protected Integer size;


    /**
     * Returns search criteria
     *
     * @see Predicate
     */
    public abstract Predicate predicate();

    /**
     * Returns parameters of paging
     *
     * @see Pageable
     */
    public Pageable pageable() {
        return new QPageRequest(
                page != null ? page : 0,
                size != null ? size : Integer.MAX_VALUE / ((page != null && page != 0)? page : 1), // Защита от переполнения int при вычислении offset
                sort()
        );
    }

    /**
     * Returns parameters of sorting
     *
     * @see Sort
     */
    public QSort sort() {
        if (sort == null) {
            return null;
        }
        List<OrderSpecifier<?>> specifiers = new LinkedList<>();
        for (String field : sort.split(",")) {
            Order order = Order.ASC;
            if (field.startsWith("+")) {
                field = field.substring(1);
            } else if (field.startsWith("-")) {
                field = field.substring(1);
                order = Order.DESC;
            }
            OrderSpecifier specifier = orderBy(order, field);
            if (specifier != null) {
                specifiers.add(specifier);
            }
        }
        return !specifiers.isEmpty() ? new QSort(specifiers) : null;
    }

    protected abstract OrderSpecifier<?> orderBy(Order order, String field);
}
