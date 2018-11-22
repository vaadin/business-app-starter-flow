package com.vaadin.starter.responsiveapptemplate.backend;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;

import java.time.LocalDate;

public class Payment {

	private Status status;
	private String from;
	private String fromIBAN;
	private String to;
	private String toIBAN;
	private Double amount;
	private LocalDate date;

	public enum Status {
		PENDING(VaadinIcon.CLOCK, "Pending", "Payment created, not yet submitted.", LumoStyles.Badge.CONTRAST),
		SUBMITTED(VaadinIcon.QUESTION_CIRCLE, "Submitted", "Payment submitted for processing.", LumoStyles.Badge.DEFAULT),
		CONFIRMED(VaadinIcon.CHECK, "Confirmed", "Payment sent successfully.", LumoStyles.Badge.SUCCESS),
		FAILED(VaadinIcon.WARNING, "Failed", "Payment failed.", LumoStyles.Badge.ERROR);

		private VaadinIcon icon;
		private String name;
		private String desc;
		private String theme;

		Status(VaadinIcon icon, String name, String desc, String theme) {
			this.icon = icon;
			this.name = name;
			this.desc = desc;
			this.theme = theme;
		}

		public Icon getIcon() {
			Icon icon;
			switch (this) {
				case PENDING:
					icon = UIUtils.createSecondaryIcon(this.icon);
					break;
				case SUBMITTED:
					icon = UIUtils.createPrimaryIcon(this.icon);
					break;
				case CONFIRMED:
					icon = UIUtils.createSuccessIcon(this.icon);
					break;
				default:
					icon = UIUtils.createErrorIcon(this.icon);
					break;
			}
			return icon;
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

	public Payment(Status status, String from, String fromIBAN, String to, String toIBAN, Double amount, LocalDate date) {
		this.status = status;
		this.from = from;
		this.fromIBAN = fromIBAN;
		this.to = to;
		this.toIBAN = toIBAN;
		this.amount = amount;
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	public String getFrom() {
		return from;
	}

	public String getFromIBAN() {
		return fromIBAN;
	}

	public String getTo() {
		return to;
	}

	public String getToIBAN() {
		return toIBAN;
	}

	public Double getAmount() {
		return amount;
	}

	public LocalDate getDate() {
		return date;
	}
}
