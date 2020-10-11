package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CurveController {
    // TODO: Inject Curve Point service
    @Autowired
    CurvePointRepository curvePointRepository;

    @PostMapping(value = "/curvePoint/add")
    public ResponseEntity<Void> saveCurvepoint(@RequestBody CurvePoint curvePoint){
        CurvePoint curvePoint1 = curvePointRepository.save(curvePoint);

        if(curvePoint1 == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("curvePoint/{id}")
                .buildAndExpand(curvePoint1.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "curvePoint/{id}")
    public Optional<CurvePoint> getcurvePointById(@PathVariable int id){
        return curvePointRepository.findById(id);
    }

    @PutMapping(value = "curvePoint/update/{id}")
    public CurvePoint updateCurvePointById(@PathVariable int id, @RequestBody CurvePoint curvePoint){
        Optional<CurvePoint> curvePoint1 = curvePointRepository.findById(id);

        if(curvePoint1.isPresent()){
            CurvePoint curvepointToUpdate = curvePoint1.get();
            curvepointToUpdate.setCurveId(curvePoint.getCurveId());
            curvepointToUpdate.setTerm(curvePoint.getTerm());
            curvepointToUpdate.setValue(curvePoint.getValue());
            curvePointRepository.save(curvepointToUpdate);
            return curvepointToUpdate;
        }
        return null;
    }

    @DeleteMapping(value = "curvePoint/delete/{id}")
    public List<CurvePoint> deleteCurvePointById(@PathVariable int id){
        curvePointRepository.deleteById(id);
        return getAllCurvePoint();
    }


    @GetMapping(value = "bidList/list")
    public List<CurvePoint> getAllCurvePoint(){
        return curvePointRepository.findAll();
    }
/*

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        // TODO: find all Curve Point, add to model
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        return "redirect:/curvePoint/list";
    }*/
}
