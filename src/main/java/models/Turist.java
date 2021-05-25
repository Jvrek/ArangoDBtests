package models;

import java.io.Serializable;

public class Turist implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Double moneyToSpend;
	private boolean isFlying;

	public Turist(String name, Double moneyToSpend, boolean isFlying) {
		this.name = name;
		this.moneyToSpend = moneyToSpend;
		this.isFlying = isFlying;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getMoneyToSpend() {
		return moneyToSpend;
	}

	public void setMoneyToSpend(Double moneyToSpend) {
		this.moneyToSpend = moneyToSpend;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public void setFlying(boolean flying) {
		isFlying = flying;
	}

	@Override
	public String toString() {
		return "Turist{" +
				"name='" + name + '\'' +
				", moneyToSpend=" + moneyToSpend +
				", isFlying=" + isFlying +
				'}';
	}
}
