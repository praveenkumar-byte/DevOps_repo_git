package com.nagarro.javaMiniAssignment2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageInfo {
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private Long total;
}