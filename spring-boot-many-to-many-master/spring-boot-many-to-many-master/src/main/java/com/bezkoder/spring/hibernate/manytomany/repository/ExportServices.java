package com.bezkoder.spring.hibernate.manytomany.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.bezkoder.spring.hibernate.manytomany.model.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional

public class ExportServices {

    @Autowired
    private EntryRepository repo;

    public List<Entry> listAll() {
        return repo.findAll(Sort.by("id").ascending());
    }



}