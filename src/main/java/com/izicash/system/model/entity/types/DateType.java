package com.izicash.system.model.entity.types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by darlan on 03/11/15.
 */
public class DateType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.DATE};
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class returnedClass() {
        return Date.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y || !(x == null || y == null) && x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        java.sql.Date dateSQL = rs.getDate(names[0]);
        if (rs.wasNull()) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(new Date(dateSQL.getTime()));
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.DATE);
        }
        else {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date date  = null;
            try {
                date = df.parse((String) value);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            java.sql.Date dateSQL = new java.sql.Date(date.getTime());
            st.setDate(index, dateSQL);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }
}
