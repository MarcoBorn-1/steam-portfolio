package com.example.steamportfolio.service;

import com.example.steamportfolio.config.exceptions.DuplicateNameException;
import com.example.steamportfolio.config.exceptions.ItemNotFoundException;
import com.example.steamportfolio.entity.Item;
import com.example.steamportfolio.entity.Portfolio;
import com.example.steamportfolio.entity.PortfolioItem;
import com.example.steamportfolio.entity.dto.PortfolioDTO;
import com.example.steamportfolio.entity.dto.PortfolioItemDTO;
import com.example.steamportfolio.repository.CurrencyRepository;
import com.example.steamportfolio.repository.ItemRepository;
import com.example.steamportfolio.repository.PortfolioItemRepository;
import com.example.steamportfolio.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PortfolioService {
    PortfolioRepository portfolioRepository;
    PortfolioItemRepository portfolioItemRepository;
    CurrencyRepository currencyRepository;
    ItemRepository itemRepository;

    @Autowired
    PortfolioService(PortfolioRepository portfolioRepository,
                     PortfolioItemRepository portfolioItemRepository,
                     CurrencyRepository currencyRepository,
                     ItemRepository itemRepository) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioItemRepository = portfolioItemRepository;
        this.currencyRepository = currencyRepository;
        this.itemRepository = itemRepository;
    }
    public Portfolio createPortfolio(PortfolioDTO portfolioDTO) throws DuplicateNameException {
        // Portfolio name has to be unique for owner
        if (portfolioRepository.findPortfolioByOwnerAndName(portfolioDTO.owner(), portfolioDTO.name()).isPresent()) {
            throw new DuplicateNameException("The portfolio name you've used is already in use.");
        }

        Portfolio portfolio = new Portfolio();
        portfolio.setName(portfolioDTO.name());
        portfolio.setOwner(portfolioDTO.owner());
        portfolio.setCurrency(currencyRepository.
                findCurrencyByNameIgnoreCaseOrSignIgnoreCase(portfolioDTO.currency(), portfolioDTO.currency()));
        portfolioRepository.save(portfolio);
        return portfolio;
    }

    public PortfolioItem addItemToPortfolio(Long portfolioID, PortfolioItemDTO item) throws NoSuchElementException {
        // Check, if item exists in database
        Optional<Item> itemOptional = itemRepository.findItemByNameIgnoreCase(item.name());
        if (itemOptional.isEmpty()) {
            throw new ItemNotFoundException();
        }

        // TODO: check, if item is already in portfolio?

        PortfolioItem portfolioItem = new PortfolioItem();
        portfolioItem.setItem(itemOptional.get());
        portfolioItem.setAmount(item.amount());
        portfolioItem.setPricePaid(item.pricePaid());
        portfolioItem.setPortfolio(portfolioRepository.getReferenceById(portfolioID));

        try {
            portfolioItemRepository.save(portfolioItem);
        }
        catch (Exception e) {
            throw new NoSuchElementException("Portfolio with ID " + portfolioID + " does not exist!");
        }

        return portfolioItem;
    }
}
