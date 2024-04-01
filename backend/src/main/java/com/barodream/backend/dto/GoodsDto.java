package com.barodream.backend.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GoodsDto {
    private String name;
    private String description;
    private Integer price;
    private Integer stock;
    private String imageTitle;
    private String imageExtension;
    private String imageSize;
    private String imageUri;
}
