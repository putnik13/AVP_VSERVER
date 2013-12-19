DROP DATABASE vserver;
CREATE DATABASE vserver DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

USE vserver;

CREATE TABLE recordings (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(32) NULL DEFAULT NULL,
	starttime DATETIME NULL DEFAULT NULL,
	endtime DATETIME NULL DEFAULT NULL,
	duration VARCHAR(32) NULL DEFAULT NULL,
	image_blob LONGTEXT NULL DEFAULT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE presentations (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(32) NULL DEFAULT NULL,
	createtime DATETIME NULL DEFAULT NULL,
	presentation_blob LONGTEXT NULL DEFAULT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE vsconfiguration (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(32) NULL DEFAULT NULL,
	is_default TINYINT(1) NULL DEFAULT NULL,
	add_logo TINYINT(1) NULL DEFAULT NULL,
    palantir_port VARCHAR(32) NULL DEFAULT NULL,
	palantir_url VARCHAR(32) NULL DEFAULT NULL,
	player_install VARCHAR(100) NULL DEFAULT NULL,
    recordings_output VARCHAR(100) NULL DEFAULT NULL,
	presentations_output VARCHAR(100) NULL DEFAULT NULL,
	recording_snapshot_output VARCHAR(100) NULL DEFAULT NULL,
	presentation_snapshot_output VARCHAR(100) NULL DEFAULT NULL,
	stream_media_resource VARCHAR(100) NULL DEFAULT NULL,
    conf_media_resource VARCHAR(100) NULL DEFAULT NULL,
	stream_media_options LONGTEXT NULL,
	conf_media_options LONGTEXT NULL,
	PRIMARY KEY (id)
);

-- preload configuration data
INSERT INTO recordings(name, starttime, endtime, duration) VALUES ('Recording1.avi', '2013-11-04 17:53:42', '2013-11-04 18:05:00', '00:12:13');
INSERT INTO recordings(name, starttime, endtime, duration) VALUES ('Recording2.avi', '2013-11-04 18:10:22', '2013-11-04 18:33:12', '00:23:44');
INSERT INTO recordings(name, starttime, endtime, duration) VALUES ('Recording3.avi', '2013-11-04 19:03:17', '2013-11-04 19:46:43', '01:32:27');
INSERT INTO recordings(name, starttime, endtime, duration) VALUES ('Recording4.avi', '2013-11-04 20:17:11', '2013-11-04 20:47:14', '03:43:15');

INSERT INTO presentations(name, createtime) VALUES ('Presentation1.pdf', '2013-11-04 17:53:42');
INSERT INTO presentations(name, createtime) VALUES ('Presentation2.pdf', '2013-12-04 18:10:22');
INSERT INTO presentations(name, createtime) VALUES ('Presentation3.pdf', '2013-12-08 21:56:17');

INSERT INTO vsconfiguration(name, is_default, add_logo, palantir_port, palantir_url, player_install, recordings_output, presentations_output, recording_snapshot_output, presentation_snapshot_output, stream_media_resource, stream_media_options)
  VALUES ('TESTCONFIG', 1, 1, '5050', '192.168.1.80', 'C:/Program Files/VideoLAN/VLC', 'D:/tmp/recordings', 'D:/tmp/presentations', 'D:/tmp/recordings/snapshots', 'D:/tmp/presentations/snapshots', 'file:///D:/temp/video/test2.mp4', ':sout=#transcode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100}:std{access=file,mux=ts,dst=%s}');
INSERT INTO vsconfiguration(name, is_default, add_logo, palantir_port, palantir_url, player_install, recordings_output, presentations_output, recording_snapshot_output, presentation_snapshot_output, stream_media_resource, stream_media_options)
  VALUES ('MYKOLACONFIG', 0, 1, '5555', '192.168.1.80', 'C:/Program Files/VideoLAN/VLC', 'D:/temp/recordings', 'D:/temp/presentations', 'D:/tmp/recordings/snapshots', 'D:/tmp/presentations/snapshots', 'file:///D:/temp/video/test2.mp4', ':sout=#transcode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100}:std{access=file,mux=ts,dst=%s}');