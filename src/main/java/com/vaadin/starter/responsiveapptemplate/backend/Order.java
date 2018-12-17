package com.vaadin.starter.responsiveapptemplate.backend;

import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;

import java.time.LocalDate;
import java.util.Collection;

public class Order {

	private final Long id;
	private final Status status;
	private final Collection<Item> items;
	private final String customer;
	private final LocalDate date;
	private final Double value;

	public enum Status {
		PENDING("Pending", "Order received, payment pending.", LumoStyles.Badge.CONTRAST),
		OPEN("Open", "Order received, not yet billed.", LumoStyles.Badge.DEFAULT),
		SENT("Sent", "Order shipped.", LumoStyles.Badge.SUCCESS),
		FAILED("Failed", "Payment unsuccessful", LumoStyles.Badge.ERROR);

		private String name;
		private String desc;
		private String theme;

		Status(String name, String desc, String theme) {
			this.name = name;
			this.desc = desc;
			this.theme = theme;
		}

		public String getName() {
			return name;
		}

		public String getDesc() {
			return desc;
		}

		public String getTheme() {
			return theme;
		}
	}

	public Order(Long id, Status status, Collection<Item> items, String customer, LocalDate date) {
		this.id = id;
		this.status = status;
		this.items = items;
		this.customer = customer;
		this.date = date;
		this.value = items.stream().mapToDouble(Item::getPrice).sum();
	}

	public Long getId() {
		return id;
	}

	public Status getStatus() {
		return status;
	}

	public Collection<Item> getItems() {
		return items;
	}

	public int getItemCount() {
		return items.size();
	}

	public String getCustomer() {
		return customer;
	}

	public LocalDate getDate() {
		return date;
	}

	public Double getValue() {
		return this.value;
	}
}
