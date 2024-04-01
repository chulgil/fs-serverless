package com.barodream.backend.controller;

import com.barodream.backend.dto.PurchaseGoodsDto;
import com.barodream.backend.entity.Goods;
import com.barodream.backend.entity.PurchaseHistory;
import com.barodream.backend.entity.User;
import com.barodream.backend.repository.GoodsRepository;
import com.barodream.backend.repository.UserRepository;
import com.barodream.backend.service.DDBService;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final DDBService ddbService;
    private final GoodsRepository goodsRepository;
    private final UserRepository userRepository;

    @GetMapping(value = "/{userId}/{createdAt}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseHistory getPurchaseHistory(@PathVariable("userId") String userId,
                                              @PathVariable("createdAt") Long createdAt) {
        return ddbService.getItem(userId, createdAt);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PurchaseHistory> getPurchaseHistories(@PathVariable("userId") String userId) {
        return ddbService.getItems(userId);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseHistory purchaseGoods(@RequestBody PurchaseGoodsDto purchaseGoodsDto) {
        Goods goods = goodsRepository.findById(purchaseGoodsDto.getGoodsId())
                                     .orElseThrow(() -> new IllegalArgumentException("Goods not found"));

        if (goods.getStock() < purchaseGoodsDto.getQuantity()) {
            throw new IllegalArgumentException("Out of stock");
        }

        goods.updateStock(purchaseGoodsDto.getQuantity());
        goodsRepository.save(goods);

        User user = userRepository.findById(purchaseGoodsDto.getUserId())
                                  .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PurchaseHistory purchaseHistory = PurchaseHistory.builder()
                .userId(user.getId().toString())
                .createdAt(new Date().getTime())
                .userName(user.getUsername())
                .goodsId(goods.getId())
                .name(goods.getName())
                .description(goods.getDescription())
                .price(goods.getPrice())
                .imageTitle(goods.getImageTitle())
                .imageUrl(goods.getImageUrl())
                .quantity(purchaseGoodsDto.getQuantity())
                .totalPrice(goods.getPrice() * purchaseGoodsDto.getQuantity())
                .updatedAt(new Date().getTime())
                .build();

        // 구매 이력 저장 로직 (가정)
        ddbService.putItem(purchaseHistory);

        return purchaseHistory;
    }
}
