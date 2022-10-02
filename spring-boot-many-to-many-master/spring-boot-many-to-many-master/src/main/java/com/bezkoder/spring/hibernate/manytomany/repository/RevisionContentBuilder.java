package com.bezkoder.spring.hibernate.manytomany.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.bezkoder.spring.hibernate.manytomany.model.EntryUpdatePair;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class RevisionContentBuilder {
	
	@Autowired
	private EntryRevisionService revisionService;

	
	private TemplateEngine templateEngine ;
	
	@GetMapping("/entry/updates")
	@ResponseBody
	public String getEntryUpdates(Model model) {
		
		Context context = new Context();
		List<EntryUpdatePair> listUpdatedEntrys = revisionService.getEntryUpdates();
		//model.addAttribute("listUpdatedEntrys", listUpdatedEntrys);
		context.setVariable("listUpdatedEntrys", listUpdatedEntrys);
	    return templateEngine.process("index", context) ;
	}
	  

	
	

}
