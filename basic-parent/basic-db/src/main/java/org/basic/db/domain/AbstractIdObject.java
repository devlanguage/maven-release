package org.basic.db.domain;

import java.io.Serializable;

@javax.persistence.MappedSuperclass
public abstract class AbstractIdObject implements Comparable<Integer>,Serializable {
	private static final long serialVersionUID = -275779834776123944L;
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
	 
	 &#64;GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	 &#64;GenericGenerator(name="native", strategy="native")
	 
	 &#64;GeneratedValue(generator = "uuidGenerator")
	 &#64;GenericGenerator(name = "uuidGenerator", strategy = "uuid") //strategy = "uuid" or "uuid2", Supported by Hibernate instead of JPA
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
