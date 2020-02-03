package org.basic.db.domain;

@javax.persistence.MappedSuperclass
public abstract class AbstractIdObject implements Comparable<Integer> {
	protected Integer id;

	public AbstractIdObject() {
	}

	/**
	 * <pre>
	&#64;GeneratedValue(strategy = GenerationType.TABLE, generator="dm_user_generator")
	&#64;TableGenerator(name = "dm_user_generator", //
			table = "dm_pk_generator", pkColumnName = "pk_name", valueColumnName = "pk_id", //
			pkColumnValue = "dm_user_pk", //
			initialValue=1, allocationSize=100)
	
			
	 &#64;GeneratedValue(strategy = GenerationType.SEQUENCE,generator="dm_user_generator")  
	 &#64;SequenceGenerator(name="dm_user_generator", sequenceName="dm_user_seq")
	 * </pre>
	 */
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int compareTo(Integer o) {
		if (null == id || o == null) {
			return 0;
		}
		return id.compareTo(o);
	}
}
