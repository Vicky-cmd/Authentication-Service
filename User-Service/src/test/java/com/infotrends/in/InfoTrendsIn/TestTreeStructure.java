package com.infotrends.in.InfoTrendsIn;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.infotrends.in.InfoTrendsIn.datastructures.BinaryTree;
import com.infotrends.in.InfoTrendsIn.datastructures.Tree;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestTreeStructure {

//	@Test
	public static void main(String[] args) {
		testTree();
	}
	public static void testTree() {
		log.info("Testing Tree Structure");
		Tree<Integer> tree = new BinaryTree<>();
		tree.add(500);
		tree.add(450);
		tree.add(600);
		tree.add(475);
		tree.add(700);
		tree.add(650);
		tree.add(100);
		log.info("Inserted All elements");
		List<Integer> asList = tree.toList();
		log.info("Size -" + tree.size());
		asList.forEach(val -> log.info(val + "\n"));
		log.info("3rd Element - " + tree.get(2));
	}
}
