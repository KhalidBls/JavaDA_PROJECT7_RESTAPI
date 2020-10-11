package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class TradeController {
    // TODO: Inject Trade service
    @Autowired
    TradeRepository tradeRepository;




    @PostMapping(value = "/trade/add")
    public ResponseEntity<Void> saveTrade(@RequestBody Trade trade){
        Trade trade1 = tradeRepository.save(trade);

        if(trade1 == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("trade/{id}")
                .buildAndExpand(trade1.getTradeId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "trade/{id}")
    public Optional<Trade> getTradeById(@PathVariable int id){
        return tradeRepository.findById(id);
    }

    @PutMapping(value = "trade/update/{id}")
    public Trade updateTradeById(@PathVariable int id, @RequestBody Trade trade){
        Optional<Trade> trade1 = tradeRepository.findById(id);

        if(trade1.isPresent()){
            Trade tradeToUpdate = trade1.get();
            tradeToUpdate.setAccount(trade.getAccount());
            tradeToUpdate.setType(trade.getType());
            tradeToUpdate.setBuyQuantity(trade.getBuyQuantity());
            tradeToUpdate.setSellQuantity(trade.getSellQuantity());
            tradeToUpdate.setSellPrice(trade.getSellPrice());
            tradeToUpdate.setBuyPrice(trade.getBuyPrice());
            tradeToUpdate.setBenchmark(trade.getBenchmark());
            tradeToUpdate.setTradeDate(trade.getTradeDate());
            tradeToUpdate.setSecurity(trade.getSecurity());
            tradeToUpdate.setStatus(trade.getStatus());
            tradeToUpdate.setTrader(trade.getTrader());
            tradeToUpdate.setBook(trade.getBook());
            tradeToUpdate.setCreationName(trade.getCreationName());
            tradeToUpdate.setCreationDate(trade.getCreationDate());
            tradeToUpdate.setRevisionName(trade.getRevisionName());
            tradeToUpdate.setRevisionDate(trade.getRevisionDate());
            tradeToUpdate.setDealName(trade.getDealName());
            tradeToUpdate.setDealType(trade.getDealType());
            tradeToUpdate.setSourceListId(trade.getSourceListId());
            tradeToUpdate.setSide(trade.getSide());
            tradeRepository.save(tradeToUpdate);
            return tradeToUpdate;
        }
        return null;
    }

    @DeleteMapping(value = "trade/delete/{id}")
    public List<Trade> deleteTradeById(@PathVariable int id){
        tradeRepository.deleteById(id);
        return getAllTrade();
    }


    @GetMapping(value = "trade/list")
    public List<Trade> getAllTrade(){
        return tradeRepository.findAll();
    }

    /*
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        // TODO: find all Trade, add to model
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Trade list
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Trade by Id and to model then show to the form
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list
        return "redirect:/trade/list";
    }

     */
}
