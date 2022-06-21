package com.infotrends.in.InfoTrendsIn.utils;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class CustomSequenceGenerator extends SequenceStyleGenerator {

	public static final String PREFIX_PARAM = "";
	public static final String NUMBER_FORMAT_PARAM = "%d";
	private String format;
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		// TODO Auto-generated method stub
		return PREFIX_PARAM + String.format(NUMBER_FORMAT_PARAM, super.generate(session, object));
	}
	@Override
	public void configure(Type arg0, Properties arg1, ServiceRegistry arg2) throws MappingException {
		// TODO Auto-generated method stub
		super.configure(arg0, arg1, arg2);
		format = ConfigurationHelper.getString(NUMBER_FORMAT_PARAM,
				arg1);
	}
	
}
