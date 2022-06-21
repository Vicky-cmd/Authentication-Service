package com.infotrends.in.InfoTrendsIn.data;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
