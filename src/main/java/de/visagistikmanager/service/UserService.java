package de.visagistikmanager.service;

import java.util.List;
import java.util.Map;

import de.visagistikmanager.model.user.User;

public class UserService extends AbstractEntityService<User> {

	public User findUser() {
		return listAll().stream().findFirst().orElse(null);
	}

	@Override
	public User find(final Long id) {
		throw new IllegalArgumentException();
	}

	@Override
	public User find(final User entity) {
		throw new IllegalArgumentException();
	}

	@Override
	public Long findCountWithNamedQuery(final String namedQueryName, final Map<String, Object> params) {
		throw new IllegalArgumentException();
	}

	@Override
	public Double findCountWithNamedQuery(final String namedQueryName, final Map<String, Object> params,
			final int firstRow, final int maxRow) {
		throw new IllegalArgumentException();
	}

	@Override
	public User findSingleWithNamedQuery(final String namedQueryName, final Map<String, Object> params) {
		throw new IllegalArgumentException();
	}

	@Override
	public List<User> findWithNamedQuery(final String namedQueryName) {
		throw new IllegalArgumentException();
	}

	@Override
	public List<User> findWithNamedQuery(final String namedQueryName, final Map<String, Object> params) {
		throw new IllegalArgumentException();
	}

	@Override
	public List<User> findWithNamedQuery(final String namedQueryName, final Map<String, Object> params, final int first,
			final int pagesize, final String sortfield, final Map<String, Object> filters) {
		throw new IllegalArgumentException();
	}

	@Override
	public List<User> findWithNamedQuery(final String namedQueryName, final Map<String, Object> params,
			final int firstRow, final int maxRow) {
		throw new IllegalArgumentException();
	}
}
