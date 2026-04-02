package com.training.pet.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String categoryColor;
    private String icon;
    private boolean isDefault;
}