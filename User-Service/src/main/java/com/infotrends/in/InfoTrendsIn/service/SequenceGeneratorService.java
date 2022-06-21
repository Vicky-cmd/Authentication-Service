package com.infotrends.in.InfoTrendsIn.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.infotrends.in.InfoTrendsIn.data.SeqGenerator;


@Service
public class SequenceGeneratorService {

	@Autowired
	private MongoOperations mongoOperations;
	public int generateSequence(String seqName) {
	    SeqGenerator counter = mongoOperations.findAndModify(Query.query(Criteria.where("type").is(seqName)),
	      new Update().inc("seqNo",1), FindAndModifyOptions.options().returnNew(true).upsert(true),
	      SeqGenerator.class);
	    return !Objects.isNull(counter) ? counter.getSeqNo() : 1;
	}
	
}
