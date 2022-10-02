package com.bezkoder.spring.hibernate.manytomany.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.spring.hibernate.manytomany.exception.ResourceNotFoundException;
import com.bezkoder.spring.hibernate.manytomany.model.Entry;
import com.bezkoder.spring.hibernate.manytomany.model.EntryUpdatePair;
import com.bezkoder.spring.hibernate.manytomany.model.Tenant;
import com.bezkoder.spring.hibernate.manytomany.repository.EntryRepository;
import com.bezkoder.spring.hibernate.manytomany.repository.EntryRevisionService;
import com.bezkoder.spring.hibernate.manytomany.repository.TenantRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class EntryController {
  @Autowired
  EntryRepository entryRepository;
  
  @Autowired
  TenantRepository tenantRepository;
  
  @Autowired
  private EntryRevisionService revisionService;

 
  @GetMapping("/entry/updates")
  public List<EntryUpdatePair>  getEntryUpdates( Model model) {
	  List<EntryUpdatePair> listUpdatedEntrys = revisionService.getEntryUpdates();
	  model.addAttribute("listUpdatedEntrys", listUpdatedEntrys);
	  model.addAttribute("serverTime", new Date());
      return listUpdatedEntrys ;
  }
  

  @GetMapping("/entry")
  public List<Entry> getAllEntrys(@RequestParam(required = false) String text , Model model) {

    List<Entry> entrys = new ArrayList<Entry>();

    if (text == null)
      entryRepository.findAll().forEach(entrys::add);
    else
      entryRepository.findByTextContaining(text).forEach(entrys::add);
    model.addAttribute("entrys", entrys);

    return entrys;

  }

  @PostMapping("/entry")
  public Entry createEntry(@RequestBody Entry entry) {
    return entryRepository.saveAndFlush(entry);

  }

  @GetMapping("/entry/id/{id}")
  public ResponseEntity<Entry> getEntryById(@PathVariable("id") long id) {
    Entry entry = entryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Entry with id = " + id));
    return new ResponseEntity<>(entry, HttpStatus.OK);
  }

  @GetMapping("/entry/extid/{id_ext}")
  public ResponseEntity<Entry> getEntryByExtId(@PathVariable("id_ext") long idExt) {
    Entry entry = entryRepository.findByIdExt(idExt)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Entry with id_ext = " + idExt));
    return new ResponseEntity<>(entry, HttpStatus.OK);
  }

  @PutMapping("/entry/id/{id}")
  public String updateEntry(@PathVariable("id") long id, @RequestBody Entry entry) {
    Entry _entry = entryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Entry with id = " + id));
    _entry.setText(entry.getText());
    _entry.setExtId(entry.getExtId());
    _entry.setLabels(entry.getLabels());
    entryRepository.save(_entry);
    return "success : updated by id : " + id;
  }

  @PutMapping("/entry/extid/{id_ext}")
  public String updateEntryIdExt(@PathVariable("id_ext") long idExt, @RequestBody Entry entry) {
    Entry _entry = entryRepository.findByIdExt(idExt)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Entry with idext = " + idExt));
    _entry.setText(entry.getText());
    _entry.setExtId(entry.getExtId());
    _entry.setLabels(entry.getLabels());
    entryRepository.save(_entry);
    return "success : updated by id_ext :  " + idExt;

  }

  @DeleteMapping("/entry/id/{id}")
  public String deleteEntryid(@PathVariable("id") long id) {
    entryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found entry with id = "
            + id));

    entryRepository.deleteById(id);
    return "success: entry is deleted by id :  " + id;
  }

  // ***************
  @DeleteMapping("/entry/extid/{id_ext}")

  public String deleteEntryExt(@PathVariable("id_ext") long idExt) {
    Entry entry = entryRepository.findByIdExt(idExt)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Entry with idext = "
            + idExt));
    entryRepository.deleteById(entry.getId());

    return "entry deleted by idext : " + idExt;
  }

}