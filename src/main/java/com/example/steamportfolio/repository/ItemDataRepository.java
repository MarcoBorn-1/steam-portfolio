package com.example.steamportfolio.repository;

import com.example.steamportfolio.entity.ItemData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDataRepository  extends JpaRepository<ItemData, Long> {
}
