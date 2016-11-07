package com.escribo.common.util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.springframework.util.ObjectUtils;

/**
 * Parameterized generalized user type for enum handling It can handle any type
 * of enum as long db data type is character. We can have one more parameter and
 * get rid of this bottleneck also
 * 
 */

public class EnumStringType implements UserType, ParameterizedType {

	private Method recreateEnumMthd;

	private Method recreateStringMthd;

	@SuppressWarnings("rawtypes")
	private Class enumClass;

	/**
	 * This method uses the parameter values passed during enum mapping
	 * definition and sets corresponding properties defined
	 */
	@SuppressWarnings("unchecked")
	public void setParameterValues(Properties parameters) {
		if (parameters != null) {
			String enumMthd = parameters.getProperty("recreateEnumMthd");
			String strMthd = parameters.getProperty("recreateStringMthd");
			String className = parameters.getProperty("enumClassName");
			Class<?> returnType = null;

			try {
				enumClass = Class.forName(className);
				recreateStringMthd = enumClass.getMethod(strMthd, new Class[] {});
				returnType = recreateStringMthd.getReturnType();
				recreateEnumMthd = enumClass.getMethod(enumMthd, new Class[] { returnType });
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method maps the database mapping
	 */
	public int[] sqlTypes() {
		return new int[] { Types.CHAR };
	}

	/**
	 * This method maps the class for which user type is created
	 */
	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		return enumClass;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return ObjectUtils.nullSafeEquals(x, y);
	}

	/**
	 * Fetch the hash code
	 */
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	/**
	 * Recreate the enum from the resultset
	 */
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {

		String value = null;

		if (names != null && names.length > 0 && rs.getString(names[0]) != null) {
			value = rs.getString(names[0]);
		}

		Object returnVal = null;

		if (value == null)
			return null;
		else {
			try {
				returnVal = recreateEnumMthd.invoke(enumClass, new Object[] { value });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return returnVal;
	}

	/**
	 * Fetch the data from enum and set it in prepared statement
	 */
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
		String prepStmtVal = null;

		if (value == null) {
			st.setObject(index, null);
		} else {
			try {
				prepStmtVal = (String) recreateStringMthd.invoke(value, new Object[] {});
				st.setString(index, prepStmtVal);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Deep copy method
	 */
	@SuppressWarnings("unchecked")
	public Object deepCopy(Object value) throws HibernateException {
		if (value == null) {
			value = null;
		} else {
			try {
				String valueEnum = (String) value.getClass().getMethod("getValue").invoke(value);
				value = enumClass.getMethod("recreateEnum", String.class).invoke(enumClass, valueEnum);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return value;
	}

	public boolean isMutable() {
		return false;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		Object deepCopy = deepCopy(value);

		if (!(deepCopy instanceof Serializable))
			return (Serializable) deepCopy;

		return null;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return deepCopy(cached);
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return deepCopy(original);
	}

}
