package com.unsee.tea.biz.test;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;

import com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource;
import com.unsee.gaia.dal.services.SqlSessionPool;
import com.unsee.tea.biz.constants.TeaConstants;
import com.unsee.tea.biz.services.TeaSessionProvider;

public class TeaBaseTest {
	@Before
	public void init() throws Exception{
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.naming.java.javaURLContextFactory");
		System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

		SQLServerConnectionPoolDataSource ds = new SQLServerConnectionPoolDataSource();
		ds.setURL("jdbc:sqlserver://localhost:1433;DatabaseName=tea_trace;user=tea;password=tea;");
		ds.setUser("tea");
		ds.setPassword("tea");

		InitialContext ic = new InitialContext();
		ic.createSubcontext("java:");
		ic.createSubcontext("java:/comp");
		ic.createSubcontext("java:/comp/env");
		ic.createSubcontext("java:/comp/env/jdbc");

		ic.bind("java:/comp/env/jdbc/tea", ds);

		Properties props = new Properties();

		props.put("tea.env", "mssql-jndi");
		props.put("tea.driver", "sqlserver");

		//SqlSessionPool.getInstance().registerProvider(
		//		GAIAConstants.PROVIDER_GAIA, new GAIASessionProvider(props));
		
		SqlSessionPool.getInstance().registerProvider(
				TeaConstants.PROVIDER_BIZ, 
				new TeaSessionProvider(props));
	}
}
