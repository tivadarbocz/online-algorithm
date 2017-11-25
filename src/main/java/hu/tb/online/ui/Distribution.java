package hu.tb.online.ui;

import hu.tb.online.algorithms.random.AbstractRandom;

public class Distribution {
	private int id;
	private String name;
	private AbstractRandom random;

	public Distribution(int id, String name, AbstractRandom random) {
		this.id = id;
		this.name = name;
		this.random = random;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public AbstractRandom getRandom() {
		return random;
	}
}
