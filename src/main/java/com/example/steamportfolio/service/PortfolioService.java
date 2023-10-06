package com.example.steamportfolio.service;

import com.example.steamportfolio.config.exceptions.DuplicateNameException;
import com.example.steamportfolio.config.exceptions.ItemNotFoundException;
import com.example.steamportfolio.entity.Item;
import com.example.steamportfolio.entity.ItemData;
import com.example.steamportfolio.entity.Portfolio;
import com.example.steamportfolio.entity.PortfolioItem;
import com.example.steamportfolio.entity.dto.*;
import com.example.steamportfolio.repository.CurrencyRepository;
import com.example.steamportfolio.repository.ItemDataRepository;
import com.example.steamportfolio.repository.PortfolioItemRepository;
import com.example.steamportfolio.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.*;

@Service
public class PortfolioService {
    PortfolioRepository portfolioRepository;
    PortfolioItemRepository portfolioItemRepository;
    CurrencyRepository currencyRepository;
    ItemService itemService;
    PriceOverviewService priceOverviewService;
    ItemDataRepository itemDataRepository;

    @Autowired
    PortfolioService(PortfolioRepository portfolioRepository,
                     PortfolioItemRepository portfolioItemRepository,
                     CurrencyRepository currencyRepository,
                     ItemService itemService,
                     PriceOverviewService priceOverviewService,
                     ItemDataRepository itemDataRepository) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioItemRepository = portfolioItemRepository;
        this.currencyRepository = currencyRepository;
        this.itemService = itemService;
        this.priceOverviewService = priceOverviewService;
        this.itemDataRepository = itemDataRepository;
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
        Optional<Item> itemOptional = itemService.itemRepository.findItemByNameIgnoreCase(item.name());
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioID);
        if (itemOptional.isEmpty()) throw new ItemNotFoundException();
        else if (portfolio.isEmpty()) {
            throw new NoSuchElementException("Portfolio with ID " + portfolioID + " does not exist!");
        }

        // TODO: check, if item is already in portfolio
        // could either combine them (with avg price), or leave as is (mostly preference, not sure yet)

        PortfolioItem portfolioItem = new PortfolioItem();
        portfolioItem.setItem(itemOptional.get());
        portfolioItem.setAmount(item.amount());
        portfolioItem.setPricePaid(item.pricePaid());
        portfolioItem.setPortfolio(portfolio.get());

        try {
            portfolioItemRepository.save(portfolioItem);
        }
        catch (Exception e) {
            throw new NoSuchElementException("Portfolio with ID " + portfolioID + " does not exist!");
        }

        return portfolioItem;
    }

    public PortfolioOverview getPortfolio(Long portfolioID) throws InterruptedException, ExecutionException {
        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioID);
        if (portfolio.isEmpty()) {
            throw new NoSuchElementException("Portfolio with ID " + portfolioID + " does not exist!");
        }
        List<Callable<PortfolioContent>> taskList = new ArrayList<>();
        List<PortfolioItem> portfolioItemList = portfolio.get().getItems();
        ExecutorService executor = Executors.newFixedThreadPool(1);

        for (PortfolioItem item : portfolioItemList) {
            Callable<PortfolioContent> callable = () -> {
                try {
                    Thread.sleep(12_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int listingAmount = itemService.getItemListingAmount(item.getItem().getName());

                try {
                    Thread.sleep(12_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                PriceOverview priceOverview = priceOverviewService.getPriceOverview(item.getPortfolio().getCurrency().getName(), 730, item.getItem().getName());
                ItemData itemData = new ItemData(priceOverview, listingAmount);
                itemDataRepository.save(itemData);
                return new PortfolioContent(
                        item.getAmount(),
                        item.getPricePaid(),
                        priceOverview.getCurrentPrice(),
                        priceOverview.getMedianPrice(),
                        priceOverview.getCurrentPrice().multiply(BigDecimal.valueOf(item.getAmount())),
                        priceOverview.getVolume(),
                        listingAmount);
            };
            taskList.add(callable);
        }

        List<Future<PortfolioContent>> contentFuture = executor.invokeAll(taskList);
        List<PortfolioContent> portfolioContentList = new ArrayList<>();
        for (Future<PortfolioContent> content: contentFuture) {
            portfolioContentList.add(content.get());
        }


        return null;
    }
}
