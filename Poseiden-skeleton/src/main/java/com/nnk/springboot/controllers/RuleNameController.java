package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
public class RuleNameController {
    // TODO: Inject RuleName service
    @Autowired
    RuleNameRepository ruleNameRepository;


    @PostMapping(value = "/ruleName/add")
    public ResponseEntity<Void> saveRuleName(@RequestBody RuleName ruleName){
        RuleName ruleName1 = ruleNameRepository.save(ruleName);

        if(ruleName1 == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("ruleName/{id}")
                .buildAndExpand(ruleName1.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "ruleName/{id}")
    public Optional<RuleName> getRuleNameById(@PathVariable int id){
        return ruleNameRepository.findById(id);
    }

    @PutMapping(value = "ruleName/update/{id}")
    public RuleName updateRuleNameById(@PathVariable int id, @RequestBody RuleName ruleName){
        Optional<RuleName> ruleName1 = ruleNameRepository.findById(id);

        if(ruleName1.isPresent()){
            RuleName ruleNameToUpdate = ruleName1.get();
            ruleNameToUpdate.setName(ruleName.getName());
            ruleNameToUpdate.setDescription(ruleName.getDescription());
            ruleNameToUpdate.setJson(ruleName.getJson());
            ruleNameToUpdate.setTemplate(ruleName.getTemplate());
            ruleNameToUpdate.setSqlStr(ruleName.getSqlStr());
            ruleNameToUpdate.setSqlPart(ruleName.getSqlPart());
            ruleNameRepository.save(ruleNameToUpdate);
            return ruleNameToUpdate;
        }
        return null;
    }

    @DeleteMapping(value = "ruleName/delete/{id}")
    public List<RuleName> deleteRuleNameById(@PathVariable int id){
        ruleNameRepository.deleteById(id);
        return getAllRuleName();
    }


    @GetMapping(value = "ruleName/list")
    public List<RuleName> getAllRuleName(){
        return ruleNameRepository.findAll();
    }
/*
    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        // TODO: find all RuleName, add to model
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        return "redirect:/ruleName/list";
    }

     */
}
