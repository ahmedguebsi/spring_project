                                                                                                                                                                                                              package com.bezkoder.spring.hibernate.manytomany.controller;

import java.util.List;

import com.bezkoder.spring.hibernate.manytomany.model.Entry;
import com.bezkoder.spring.hibernate.manytomany.model.Tenant;
import com.bezkoder.spring.hibernate.manytomany.repository.TenantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TenantController {
    @Autowired
    TenantService tenantService;

    @RequestMapping(value = "/tenants", method = RequestMethod.POST)
    public Tenant createTenant(@RequestBody Tenant tenant) {
        return tenantService.createTenant(tenant);
    }

    @RequestMapping(value = "/tenants", method = RequestMethod.GET)
    public List<Tenant> readTenants() {
        return tenantService.getTenants();
    }

    @RequestMapping(value = "/tenants/{tenantId}", method = RequestMethod.PUT)
    public Tenant updatetenant(@PathVariable(value = "tenantId") Long id, @RequestBody Tenant TenantDetails) {
        return tenantService.updateTenant(id, TenantDetails);
    }

    @RequestMapping(value = "/tenants/{tenantId}", method = RequestMethod.DELETE)
    public String deleteTenants(@PathVariable(value = "tenantId") Long id) {
        tenantService.deleteTenant(id);
        return "customer deleted by id : " + id;
    }
    
    @RequestMapping(value = "/tenants/{tenantId}", method = RequestMethod.GET)
    public Tenant getTenant(@PathVariable(value = "tenantId") Long id) {
        return tenantService.getTenant(id);
    }
    
    @RequestMapping(value = "/tenants/{tenantId}/entry", method = RequestMethod.GET)
    public List<Entry> getEntryTenant(@PathVariable(value = "tenantId") Long id) {
        return tenantService.getEntryByTenant(id);
    }
    
    

}
