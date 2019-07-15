package org.third.testdata.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

/**
 * Created on Oct 27, 2014, 4:19:59 PM
 */
public class PagingHelper<E> {
	@SuppressWarnings("unchecked")
	public CurrentPage<E> fetchPage(final JdbcTemplate jt, final String sqlCountRows, final String sqlFetchRows,
			final Object args[], final int pageNo, final int pageSize, RowMapper<E> rowMapper) {
		// determine how many rows are available
		int rowCount = jt.queryForObject("select count(*) from T_people where id >= ?", new Object[] { 1 },
				Integer.class);
		rowCount = jt.queryForObject(sqlCountRows, args, Integer.class);
		// calculate the number of pages
		int pageCount = rowCount / pageSize;
		if (rowCount > pageSize * pageCount) {
			pageCount++;
		}
		// create the page object
		final CurrentPage<E> page = new CurrentPage<E>();
		page.setPageNumber(pageNo);
		page.setPagesAvailable(pageCount);
		// fetch a single page of results
		final int startRow = (pageNo - 1) * pageSize;
		jt.query(sqlFetchRows, args, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				final List pageItems = page.getPageItems();
				int currentRow = 0;
				while (rs.next() && currentRow < startRow + pageSize) {
					if (currentRow >= startRow) {
						pageItems.add(rowMapper.mapRow(rs, currentRow));
					}
					currentRow++;
				}
				return page;
			}
		});
		return page;
	}
}