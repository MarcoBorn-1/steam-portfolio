package com.example.steamportfolio.repository;

import com.example.steamportfolio.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemByNameIgnoreCase(String name);
}
