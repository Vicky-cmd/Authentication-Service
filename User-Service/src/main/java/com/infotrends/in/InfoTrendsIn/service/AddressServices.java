package com.infotrends.in.InfoTrendsIn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.infotrends.in.InfoTrendsIn.dao.AddressRepository;
import com.infotrends.in.InfoTrendsIn.data.AddressDetails;
import com.infotrends.in.InfoTrendsIn.utils.AppConstants;
import com.infotrends.in.InfoTrendsIn.utils.Utilities;


@Service
public class AddressServices {

	@Autowired
	private AddressRepository addressRepo;

	@Autowired
	private SequenceGeneratorService sequenceGenSvc;
	
	@Caching(evict = {@CacheEvict(value="addr", key = "#addr.getId()", allEntries = true)}, put = {@CachePut(value = "addr", key = "#addr.getId()", unless="#result == null")})
	public AddressDetails save(AddressDetails details) {
		details.setId(Utilities.generateId(AppConstants.ADDRESSID_PREFIX, AppConstants.ADDRESSID_FORMATTOR_PARAM, sequenceGenSvc.generateSequence("Address")));
		return addressRepo.save(details);
	}
	

	@Caching(evict = {@CacheEvict(value="addr", key = "#addr.getId()", allEntries = true)}, put = {@CachePut(value = "addr", key = "#addr.getId()", unless="#result == null")})
	public AddressDetails update(AddressDetails details) {
		return addressRepo.save(details);
	}

	@Cacheable(value="addr", key = "#id", unless="#result == null")
	public Optional<AddressDetails> findByAddrId(String id) {
		return addressRepo.findById(id);
	}
	
	public List<AddressDetails> findByCustId(String id) {
		return addressRepo.findByUserId(id);
	}

	@CacheEvict(value="addr", key = "#addr.getId()", allEntries = true)
	public boolean deleteAddress(String id) {
		Optional<AddressDetails> data = addressRepo.findById(id);
		if(data.isPresent() && data.get().getId()!=null) {
			addressRepo.delete(data.get());
			return true;
		}
		return false;
	}
	
}
