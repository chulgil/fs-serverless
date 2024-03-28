package com.barodream.backend.repository;

import com.barodream.backend.entity.GoodsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsItemRepository extends JpaRepository<GoodsItem, Long> {}
