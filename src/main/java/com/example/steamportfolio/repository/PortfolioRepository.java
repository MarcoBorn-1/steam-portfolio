package com.example.steamportfolio.repository;


import com.example.steamportfolio.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findPortfolioByOwnerAndName(String owner, String name);
}
