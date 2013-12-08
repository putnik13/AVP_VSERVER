package com.atanor.vserver.domain.dao;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.atanor.vserver.domain.entity.Presentation;

public class PresentationDaoTest extends BaseDaoTest<Presentation> {

	private static final String PRESENTATION_BLOB = "asfgadgsdfhgfsghfdgjdfghgfhdfghfgh";

	@Test
	public void testInsertRecord() throws Exception {
		Presentation presentation = new Presentation();

		Date createTime = new Date();
		presentation.setName("Presentation#1.pdf");
		presentation.setCreateTime(createTime);
		presentation.setPresentationBlob(PRESENTATION_BLOB);

		Assert.assertNotNull(dao.insert(presentation));
	}

	@Test
	public void testDeleteRecord() throws Exception {
		Presentation presentation = new Presentation();

		Long id = dao.insert(presentation);
		presentation = dao.find(id);
		dao.delete(presentation);
	}

	@Test
	public void testSelect() throws Exception {
		Presentation presentation = new Presentation();

		Date createTime = new Date();
		presentation.setName("Presentation#1.pdf");
		presentation.setCreateTime(createTime);
		presentation.setPresentationBlob(PRESENTATION_BLOB);

		Long id = dao.insert(presentation);

		Presentation presentationFromDB = dao.find(id);
		Assert.assertNotNull(presentationFromDB);
		Assert.assertEquals("Presentation#1.pdf", presentationFromDB.getName());
		Assert.assertEquals(createTime.getTime(), presentationFromDB.getCreateTime().getTime());
		Assert.assertEquals(PRESENTATION_BLOB, presentationFromDB.getPresentationBlob());
	}

	@Test
	public void testUpdate() throws Exception {
		Presentation presentation = new Presentation();
		presentation.setName("Presentation#1.pdf");

		Long id = dao.insert(presentation);

		Presentation presentationFromDB = dao.find(id);
		Assert.assertNotNull(presentationFromDB);
		Assert.assertEquals("Presentation#1.pdf", presentationFromDB.getName());

		presentationFromDB.setName("Presentation#2.pdf");
		dao.update(presentationFromDB);

		presentationFromDB = dao.find(id);
		Assert.assertNotNull(presentationFromDB);
		Assert.assertEquals("Presentation#2.pdf", presentationFromDB.getName());
	}

	@Test
	public void testGetAll() {
		Presentation presentation1 = new Presentation();
		Presentation presentation2 = new Presentation();
		Presentation presentation3 = new Presentation();

		dao.insert(presentation1);
		dao.insert(presentation2);
		dao.insert(presentation3);

		List<Presentation> allRecordings = dao.findAll();
		Assert.assertEquals(3, allRecordings.size());
	}
}
