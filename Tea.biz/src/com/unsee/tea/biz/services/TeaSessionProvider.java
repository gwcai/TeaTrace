package com.unsee.tea.biz.services;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import com.unsee.gaia.dal.brokers.BrokerFactory;
import com.unsee.gaia.dal.session.ThreadSafetySessionProvider;

public class TeaSessionProvider extends ThreadSafetySessionProvider {

private static Logger logger = Logger.getLogger(TeaSessionProvider.class);
	
	public TeaSessionProvider(Properties prop) {
		super(prop);
	}

	@Override
	protected SqlSessionFactory buildFactory(Properties prop) {
		BrokerFactory.getInstance().loadBrokerFromConfigFile(
				"com/unsee/tea/biz/config/brokers.xml");

		String resource = "com/unsee/tea/biz/config/batis.xml";
		Reader reader = null;

		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			logger.error(e);
		}

		return new SqlSessionFactoryBuilder().build(reader, prop);
	}
}
