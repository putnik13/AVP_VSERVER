package com.atanor.vserver.domain.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.atanor.vserver.domain.entity.VsConfig;

public class VsConfigDaoTest extends BaseDaoTest<VsConfig> {

	private static final String MEDIA_OPTIONS = ":sout=#tra1nscode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100}:std{access=file,mux=ts,dst=%s}";
	private static final String PALANTIR_URL = "192.168.1.80";
	private static final String PALANTIR_PORT = "5050";
	private static final String OUTPUT_DIR = "D:/tmp";
	private static final String PLAYER_INSTALL_PATH = "C:/Program Files/VideoLAN/VLC";

	@Test
	public void testInsertRecord() throws Exception {
		VsConfig config = new VsConfig();

		config.setName("MYCONFIG");
		config.setDefault(Boolean.TRUE);
		config.setStreamMediaOptions(MEDIA_OPTIONS);
		config.setConfMediaOptions(MEDIA_OPTIONS);
		config.setPalantirUrl(PALANTIR_URL);
		config.setPalantirPort(PALANTIR_PORT);
		config.setAddLogo(Boolean.TRUE);
		config.setRecordingsOutput(OUTPUT_DIR);
		config.setPresentationsOutput(OUTPUT_DIR);
		config.setPlayerInstallPath(PLAYER_INSTALL_PATH);

		Assert.assertNotNull(dao.insert(config));
	}

	@Test
	public void testDeleteRecord() throws Exception {
		VsConfig config = new VsConfig();

		Long id = dao.insert(config);
		config = dao.find(id);
		dao.delete(config);
	}

	@Test
	public void testSelect() throws Exception {
		VsConfig config = new VsConfig();

		config.setName("MYCONFIG");
		config.setDefault(Boolean.TRUE);
		config.setStreamMediaOptions(MEDIA_OPTIONS);
		config.setConfMediaOptions(MEDIA_OPTIONS);
		config.setPalantirUrl(PALANTIR_URL);
		config.setPalantirPort(PALANTIR_PORT);
		config.setAddLogo(Boolean.TRUE);
		config.setRecordingsOutput(OUTPUT_DIR);
		config.setPresentationsOutput(OUTPUT_DIR);
		config.setPlayerInstallPath(PLAYER_INSTALL_PATH);

		Long id = dao.insert(config);

		VsConfig configFromDB = dao.find(id);
		Assert.assertNotNull(configFromDB);
		Assert.assertEquals("MYCONFIG", configFromDB.getName());
		Assert.assertTrue(configFromDB.isDefault());
		Assert.assertEquals(MEDIA_OPTIONS, configFromDB.getStreamMediaOptions());
		Assert.assertEquals(MEDIA_OPTIONS, configFromDB.getConfMediaOptions());
		Assert.assertEquals(PALANTIR_URL, configFromDB.getPalantirUrl());
		Assert.assertEquals(PALANTIR_PORT, configFromDB.getPalantirPort());
		Assert.assertTrue(configFromDB.getAddLogo());
		Assert.assertEquals(OUTPUT_DIR, configFromDB.getRecordingsOutput());
		Assert.assertEquals(OUTPUT_DIR, configFromDB.getPresentationsOutput());
		Assert.assertEquals(PLAYER_INSTALL_PATH, configFromDB.getPlayerInstallPath());
	}

	@Test
	public void testUpdate() throws Exception {
		VsConfig config = new VsConfig();
		config.setPalantirPort(PALANTIR_PORT);

		Long id = dao.insert(config);

		VsConfig configFromDB = dao.find(id);
		Assert.assertNotNull(configFromDB);
		Assert.assertEquals(PALANTIR_PORT, configFromDB.getPalantirPort());

		configFromDB.setPalantirPort("5555");
		dao.update(configFromDB);

		configFromDB = dao.find(id);
		Assert.assertNotNull(configFromDB);
		Assert.assertEquals("5555", configFromDB.getPalantirPort());
	}

	@Test
	public void testGetAll() {
		VsConfig config1 = new VsConfig();
		VsConfig config2 = new VsConfig();
		VsConfig config3 = new VsConfig();

		dao.insert(config1);
		dao.insert(config2);
		dao.insert(config3);

		List<VsConfig> allConfigs = dao.findAll();
		Assert.assertEquals(3, allConfigs.size());
	}
}
