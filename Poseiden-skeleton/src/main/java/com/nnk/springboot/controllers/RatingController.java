package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class RatingController {
    // TODO: Inject Rating service
    @Autowired
    RatingRepository ratingRepository;


    @PostMapping(value = "/rating/add")
    public ResponseEntity<Void> saveRating(@RequestBody Rating rating){
        Rating rating1 = ratingRepository.save(rating);

        if(rating1 == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("rating/{id}")
                .buildAndExpand(rating1.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @GetMapping(value = "rating/{id}")
    public Optional<Rating> getRatingById(@PathVariable int id){
        return ratingRepository.findById(id);
    }

    @PutMapping(value = "rating/update/{id}")
    public Rating updateRatingById(@PathVariable int id, @RequestBody Rating rating){
        Optional<Rating> rating1 = ratingRepository.findById(id);

        if(rating1.isPresent()){
            Rating ratingToUpdate = rating1.get();
            ratingToUpdate.setMoodysRating(rating.getMoodysRating());
            ratingToUpdate.setSandPRating(rating.getSandPRating());
            ratingToUpdate.setFitchRating(rating.getFitchRating());
            ratingToUpdate.setOrderNumber(rating.getOrderNumber());
            ratingRepository.save(ratingToUpdate);
            return ratingToUpdate;
        }
        return null;
    }

    @DeleteMapping(value = "rating/delete/{id}")
    public List<Rating> deleteRatingById(@PathVariable int id){
        ratingRepository.deleteById(id);
        return getAllRating();
    }


    @GetMapping(value = "bidList/list")
    public List<Rating> getAllRating(){
        return ratingRepository.findAll();
    }
/*
    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        // TODO: find all Rating, add to model
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        return "redirect:/rating/list";
    }

 */
}
