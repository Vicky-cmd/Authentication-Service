package com.infotrends.in.authenticationserver.services;

import com.infotrends.in.authenticationserver.model.SeqGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;


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
