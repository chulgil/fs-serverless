package com.barodream.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Builder
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PurchaseHistory {

    private String userId;
    private Long createdAt;
    private String userName;
    private Long goodsId;
    private String name;
    private String description;
    private Integer price;
    private String imageTitle;
    private String imageUrl;
    private Integer quantity;
    private Integer totalPrice;
    private Long updatedAt;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("user_id")
    public String getUserId() {
        return userId;
    }

    // 기존 Date 타입의 createdAt 필드는 그대로 두고, DynamoDB에 저장될 때 사용될 String 타입 변환 메서드를 추가합니다.
    @DynamoDbSortKey
    @DynamoDbAttribute("created_at")
    public Long getCreatedAt() {
        return createdAt;
    }

    @DynamoDbAttribute("user_name")
    public String getUserName() {
        return userName;
    }

    @DynamoDbAttribute("goods_id")
    public Long getGoodsId() {
        return goodsId;
    }

    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }

    @DynamoDbAttribute("description")
    public String getDescription() {
        return description;
    }

    @DynamoDbAttribute("price")
    public Integer getPrice() {
        return price;
    }

    @DynamoDbAttribute("image_title")
    public String getImageTitle() {
        return imageTitle;
    }

    @DynamoDbAttribute("image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    @DynamoDbAttribute("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    @DynamoDbAttribute("total_price")
    public Integer getTotalPrice() {
        return totalPrice;
    }

    @DynamoDbAttribute("updated_at")
    public Long getUpdatedAt() {
        return updatedAt;
    }
}
