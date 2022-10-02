package com.bezkoder.spring.hibernate.manytomany.repository;

import com.bezkoder.spring.hibernate.manytomany.model.Entry;
import com.bezkoder.spring.hibernate.manytomany.model.Label;
import com.bezkoder.spring.hibernate.manytomany.model.Tenant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
	
	Tenant findTenantById(long tenantId);
	
	//List<Entry> findEntrysByTenantId(long tenantId);

}