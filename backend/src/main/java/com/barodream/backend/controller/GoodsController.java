package com.barodream.backend.controller;

import com.barodream.backend.dto.GoodsDto;
import com.barodream.backend.entity.Goods;
import com.barodream.backend.repository.GoodsRepository;
import com.barodream.backend.repository.UserRepository;
import com.barodream.backend.service.DDBService;
import com.barodream.backend.service.S3Service;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final S3Service s3Service;
    private final GoodsRepository goodsRepository;
    private final UserRepository userRepository;
    private final DDBService ddbService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String registerItem(@RequestPart("file") MultipartFile file,
        @RequestPart GoodsDto goodsDto) throws IOException {

        String fileUrl = s3Service.uploadFile(file);
        Goods goods = Goods.builder()
            .name(goodsDto.getName())
            .price(goodsDto.getPrice())
            .stock(goodsDto.getStock())
            .description(goodsDto.getDescription())
            .imageTitle(file.getOriginalFilename())
            .imageSize(file.getSize())
            .imageExtension(goodsDto.getImageExtension())
            .imageUrl(fileUrl)
            .build();

        goodsRepository.save(goods);

        return "register item";
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Goods> getItem(@RequestParam Long id) {
        return goodsRepository.findById(id);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Goods> getItems() {
        return goodsRepository.findAll();
    }


}
