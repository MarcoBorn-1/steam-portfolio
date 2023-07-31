package com.example.steamportfolio.controller;

import com.example.steamportfolio.entity.Currency;
import com.example.steamportfolio.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currency")
public class CurrencyController {
    @Autowired
    CurrencyRepository currencyRepository;

    @PostMapping("/add")
    public ResponseEntity<Currency> createCurrency(@RequestBody Currency currency) {
        Currency newCurrency = currencyRepository.save(currency);
        return ResponseEntity.ok(newCurrency);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Currency>> getAllCurrencies() {
        return ResponseEntity.ok(currencyRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Currency> getCurrency(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(currencyRepository.findById(id).orElse(null));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Object> removeCurrency(@PathVariable(name = "id") Long id) {
        currencyRepository.deleteById(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/search")
    public ResponseEntity<Currency> searchCurrency(@RequestParam String query) {
        return ResponseEntity.ok(
                currencyRepository.findCurrencyByNameIgnoreCaseOrSignIgnoreCase(query, query)
        );
    }
}
