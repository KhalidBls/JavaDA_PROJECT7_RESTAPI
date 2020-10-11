package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidListController {

    @Autowired
    BidListRepository bidListRepository;


    @PostMapping(value = "/bidList/add")
    public ResponseEntity<Void> saveBidList(@RequestBody BidList bidList){
        BidList bidList1 = bidListRepository.save(bidList);

        if(bidList1 == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("bidList/{id}")
                .buildAndExpand(bidList1.getBidListId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "bidList/{id}")
    public BidList getBidListById(@PathVariable int id){
        return bidListRepository.findByBidListId(id);
    }

    @PutMapping(value = "bidList/update/{id}")
    public BidList updateBidListById(@PathVariable int id, @RequestBody BidList bidList){
        BidList bidList1 = bidListRepository.findByBidListId(id);

        if(bidList1 != null){
            bidList1.setAccount(bidList.getAccount());
            bidList1.setType(bidList.getType());
            bidList1.setBidQuantity(bidList.getBidQuantity());
            bidListRepository.save(bidList1);
        }
        return bidList1;
    }

    @DeleteMapping(value = "bidList/delete/{id}")
    public List<BidList> deleteBidListById(@PathVariable int id){
        bidListRepository.deleteById(id);
        return getAllBidList();
    }

    @GetMapping(value = "bidList/list")
    public List<BidList> getAllBidList(){
        return bidListRepository.findAll();
    }


    // CREATION DES ENDPOINT POUR REST API A VOIR S'IL FAUT ENLEVER sa


    /*@RequestMapping("/bidList/list")
    public String home(Model model)
    {
        // TODO: call service find all bids to show to the view
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        return "redirect:/bidList/list";
    }*/


}
