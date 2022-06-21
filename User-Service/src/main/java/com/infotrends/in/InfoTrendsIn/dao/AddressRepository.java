package com.infotrends.in.InfoTrendsIn.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.infotrends.in.InfoTrendsIn.data.AddressDetails;


@Repository
public interface AddressRepository extends MongoRepository<AddressDetails, String> {

	public List<AddressDetails> findByUserId(String custId);
}
