package de.visagistikmanager.data;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.fastnate.data.AbstractDataProvider;

import de.visagistikmanager.model.order.Notification;
import de.visagistikmanager.model.order.NotificationType;
import lombok.Getter;

public class NotifiactionImportData extends AbstractDataProvider {

	/** A list that contains all the created data. */
	@Getter
	private final List<Notification> entities = new ArrayList<>();

	/** Create the entities. */
	@Override
	public void buildEntities() throws IOException {
		for (int i = 1; i < 30; i++) {
			addNotifcation(NotificationType.values()[i % 2], LocalDate.of(2018, i % 12 + 1, i));
		}
	}

	private void addNotifcation(final NotificationType notificationType, final LocalDate date) {
		final Notification notification = new Notification();
		notification.setNotificationType(notificationType);
		notification.setDate(date);
		this.entities.add(notification);

	}

}