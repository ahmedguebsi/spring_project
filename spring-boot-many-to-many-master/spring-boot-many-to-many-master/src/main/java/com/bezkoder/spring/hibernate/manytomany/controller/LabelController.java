package com.bezkoder.spring.hibernate.manytomany.controller;


import java.util.ArrayList;
import java.util.List;
import java.lang.Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.spring.hibernate.manytomany.exception.ResourceNotFoundException;
import com.bezkoder.spring.hibernate.manytomany.model.Label;
import com.bezkoder.spring.hibernate.manytomany.model.Entry;
import com.bezkoder.spring.hibernate.manytomany.repository.LabelRepository;
import com.bezkoder.spring.hibernate.manytomany.repository.EntryRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class LabelController {

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private LabelRepository labelRepository;

    @GetMapping("/label")
    public List<Label> getAllLabels() {
        List<Label> labels = new ArrayList<Label>();

        labelRepository.findAll().forEach(labels::add);
        return labels;

    }

    @PostMapping("/label")
    public Label createLabel(@RequestBody Label label) throws Exception {
        String name = label.getName();
        List<Label> labelIterable = labelRepository.findByNameContaining(name);
        if (!labelIterable.isEmpty()) {
            throw new Exception("found Label with id = " + name);
        }
        return labelRepository.saveAndFlush(label);

    }

    @GetMapping("/entry/id/{entryId}/label")
    public ResponseEntity<List<Label>> getAllLabelsByEntryId(@PathVariable(value = "entryId") Long entryId) {
        if (!entryRepository.existsById(entryId)) {
            throw new ResourceNotFoundException("Not found entry with id = " + entryId);
        }
        List<Label> labels = labelRepository.findLabelsByEntrysId(entryId);
        return new ResponseEntity<>(labels, HttpStatus.OK);

    }

    @GetMapping("/label/id/{labelId}/entry")
    public List<Entry> getAllEntrysByLabelId(@PathVariable(value = "labelId") Long labelId) {
        if (!labelRepository.existsById(labelId)) {
            throw new ResourceNotFoundException("Not found Label with id = " + labelId);

        }
        List<Entry> entrys = entryRepository.findEntrysByLabelsId(labelId);
        return entrys;
    }

    @GetMapping("/label/id/{id}")
    public Label getLabelsById(@PathVariable(value = "id") Long id) {
        return labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Label with id = "
                        +
                        id));
    }

    @GetMapping("/label/extid/{idExt}")
    public Label getLabelByExtId(@PathVariable("idExt") long idExt) {
        return labelRepository.findByIdExt(idExt)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Label with id_ext = " + idExt));

    }

    @PostMapping("/entry/id/{entryId}/label")
    public Label addLabel(@PathVariable(value = "entryId") long entryId,
            @RequestBody Label labelRequest) {
        return entryRepository.findById(entryId).map(entry -> {
            long labelId = labelRequest.getId();
            // label is existed
            if (labelId != 0L) {
                Label _label = labelRepository.findById(labelId)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Label with id = " + labelId));
                entry.addLabel(_label);
                entryRepository.save(entry);
                return _label;
            }
            // add and create new Label

           // pour ajouter les id dans la table assoc
          
            entry.addLabel(labelRequest);
            return labelRepository.save(labelRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Entry with id = " + entryId));
    }

    @PostMapping("/entry/extid/{idExt}/label")
    public Label addLabell(@PathVariable(value = "idExt") long idExt,
            @RequestBody Label labelRequest) {
        return entryRepository.findById(idExt).map(entry -> {
            long labelId = labelRequest.getId();
            // label is existed
            if (labelId != 0L) {
                Label _label = labelRepository.findById(labelId)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Label with id = " + labelId));
                entry.addLabel(_label);
                entryRepository.save(entry);
                return _label;
            }
            // add and create new Label
            // pour ajouter les id dans la table assoc
            entry.addLabel(labelRequest);
            return labelRepository.save(labelRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Entry with id externe = " + idExt));
    }

    @PutMapping("/label/id/{id}")
    public String updateLabel(@PathVariable("id") long id,
            @RequestBody Label label) {
        Label _label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LabelId " + id +
                        "notfound"));

        _label.setName(label.getName());
        _label.setIdExt(label.getIdExt());
        _label.setEntrys(label.getEntrys());
        labelRepository.save(_label);
        return "success : update by id : " + id;
    }

    @PutMapping("/label/extid/{id_ext}")
    public String updateLabelIdExt(@PathVariable("id_ext") long idExt,
            @RequestBody Label label) {
        Label _label = labelRepository.findByIdExt(idExt)
                .orElseThrow(() -> new ResourceNotFoundException("LabelIdExt " + idExt +
                        "notfound"));

        _label.setName(label.getName());
        _label.setIdExt(label.getIdExt());
        _label.setEntrys(label.getEntrys());

        labelRepository.save(_label);
        return "success : update by idext : " + idExt;

    }

    @DeleteMapping("/label/id/{id}")
    public String deleteLabelid(@PathVariable("id") long id) {
        labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found label with id = "
                        + id));

        labelRepository.deleteById(id);
        return "success: label is deleted by id :  " + id;
    }

    @DeleteMapping("/label/extid/{id_ext}")

    public String deleteLabelExt(@PathVariable("id_ext") long idExt) {
        Label label = labelRepository.findByIdExt(idExt)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Label with id = "
                        + idExt));
        labelRepository.deleteById(label.getId());

        return "label is deleted by idext :  " + idExt;
    }

}