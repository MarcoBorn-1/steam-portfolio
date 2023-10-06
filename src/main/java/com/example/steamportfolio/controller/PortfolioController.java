package com.example.steamportfolio.controller;

import com.example.steamportfolio.config.exceptions.DuplicateNameException;
import com.example.steamportfolio.config.exceptions.ItemNotFoundException;
import com.example.steamportfolio.entity.Portfolio;
import com.example.steamportfolio.entity.PortfolioItem;
import com.example.steamportfolio.entity.dto.PortfolioDTO;
import com.example.steamportfolio.entity.dto.PortfolioItemDTO;
import com.example.steamportfolio.entity.dto.PortfolioOverview;
import com.example.steamportfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping("/create")
    public ResponseEntity<Portfolio> createPortfolio(@RequestBody PortfolioDTO portfolioDTO) throws DuplicateNameException {
        return ResponseEntity.ok(portfolioService.createPortfolio(portfolioDTO));
    }

    @PostMapping("/{id}/add")
    public ResponseEntity<PortfolioItem> addItemToPortfolio(
            @PathVariable(name = "id") Long id,
            @RequestBody PortfolioItemDTO item)
            throws NoSuchElementException {
        return ResponseEntity.ok(portfolioService.addItemToPortfolio(id, item));
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<PortfolioOverview> getPortfolio(@PathVariable(name = "id") Long id) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(portfolioService.getPortfolio(id));
    }


}
