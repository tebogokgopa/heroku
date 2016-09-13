package com.heroku.demo;

import javax.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@Controller
@RequestMapping("/")
public class HomeController {

    private RecordRepository repository;

    @Autowired
    public HomeController(RecordRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(ModelMap model) {
        List<Record> records = repository.findAll();
        model.addAttribute("records", records);
        model.addAttribute("insertRecord", new Record());
        return "home";
    }

    @RequestMapping(value = "/adoption/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Record> getRecord(@PathVariable("id") long id) {
        Record record = repository.findOne(id);
        if (record == null) {
            return new ResponseEntity<Record>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Record>(record, HttpStatus.OK);
    }


    //---------------------Retrieve List of Adoptions---------------------------------------------------
    @RequestMapping(value = "/adoptions/",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Record>> getOneRecord()
    {
        List<Record> record = repository.findAll();
        if(record.isEmpty())
        {
            return new ResponseEntity<List<Record>>(HttpStatus.NO_CONTENT);//OR HttpStatus.Not_Found
        }

        return new ResponseEntity<List<Record>>(record,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String insertData(ModelMap model, 
                             @ModelAttribute("insertRecord") @Valid Record record,
                             BindingResult result) {
        if (!result.hasErrors()) {
            repository.save(record);
        }
        return home(model);
    }
    
 @RequestMapping(value = "/adoption/create", method = RequestMethod.POST)
    public ResponseEntity<Void> createRecord(@RequestBody Record record, UriComponentsBuilder ucBuilder)
    {
        repository.save(record);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/record/{id}").buildAndExpand(record.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    
    
    @RequestMapping(value = "/adoption/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Record> deleteRecord(@PathVariable("id")long id)
    {
        System.out.println("Fetching & Deleting Adoption with id" + id);
        Record record = repository.findOne(id);
        if(record  == null)
        {
            System.out.println("Unable to delete. Adoption with id " + id + " not found");//comment
            return new ResponseEntity<Record>(HttpStatus.NOT_FOUND);

        }

            repository.delete(record);
            return new ResponseEntity<Record>(HttpStatus.NO_CONTENT);
        }
      
}
       
