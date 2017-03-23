package com.unsee.tea.biz.test;

import junit.framework.Assert;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import com.unsee.tea.biz.entities.TeaTraceEntity;
import com.unsee.tea.biz.services.TeaTraceService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeaTraceServiceTest extends TeaBaseTest{
	private static String id;
		
	@Test
	public void test1Insert() throws Exception {
		TeaTraceEntity ent = new TeaTraceEntity();
		Assert.assertEquals(true, TeaTraceService.getInstance().updateEntity(ent));
		id = ent.getId();
	}

	@Test
	public void test2GetAll() throws Exception {
		List list = TeaTraceService.getInstance().getAllObjects(null);
		Assert.assertEquals(true, list.size() > 0);
		for(Object o: list) {
		  System.out.print(o);
		}
	}
	
	@Test
	public void test3Update() throws Exception {
		TeaTraceEntity ent = (TeaTraceEntity)TeaTraceService.getInstance().getEntity(id);
		Assert.assertEquals(true, ent != null);
	}
	
	@Test
	public void test4Remove() throws Exception {
		TeaTraceEntity ent = (TeaTraceEntity)TeaTraceService.getInstance().getEntity(id);
		TeaTraceService.getInstance().removeEntity(ent);
	}
}
