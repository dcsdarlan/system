package com.izicash.system.model.entity.types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by darlan on 03/11/15.
 */
public class TimeType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.TIME};
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
        Time timeSQL = rs.getTime(names[0]);
        if (rs.wasNull()) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new Date(timeSQL.getTime()));
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.TIME);
        }
        else {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            Date date  = null;
            try {
                date = df.parse((String) value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Time timestampSQL = new Time(date.getTime());
            st.setTime(index, timestampSQL);
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
