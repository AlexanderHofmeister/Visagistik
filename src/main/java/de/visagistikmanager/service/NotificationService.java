package de.visagistikmanager.service;

import java.util.List;

import de.visagistikmanager.model.order.Notification;

public class NotificationService extends AbstractEntityService<Notification> {

	public List<Notification> findCurrentNotifications() {
		return findWithNamedQuery(Notification.FIND_CURRENT_NOTIFICATIONS);

	}

}
