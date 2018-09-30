package de.visagistikmanager.model.order;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import de.visagistikmanager.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NamedQuery(name = Notification.FIND_CURRENT_NOTIFICATIONS, query = "SELECT n FROM Notification n")
@NoArgsConstructor
public class Notification extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final String FIND_CURRENT_NOTIFICATIONS = "Notification.findCurrent";

	@ManyToOne
	private Order order;

	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;

	private LocalDate date;

	private boolean done;

	public Notification(final NotificationType notificationType, final LocalDate date) {
		this.notificationType = notificationType;
		this.date = date;
	}

}
