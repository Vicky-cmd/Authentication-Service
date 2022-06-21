package com.infotrends.in.authenticationserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "sequence_generator")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeqGenerator {
	@Id
	private int seqNo;	
	private String type;

}
