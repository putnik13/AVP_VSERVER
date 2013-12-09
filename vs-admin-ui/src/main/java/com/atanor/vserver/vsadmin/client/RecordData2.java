package com.atanor.vserver.vsadmin.client;

public class RecordData2 {
	
	private static SettingsRecord[] records;

	public static SettingsRecord[] getRecords() {
		if (records == null) {
			records = getNewRecords();
		}
		return records;
	}

	public static SettingsRecord[] getNewRecords() {
		return new SettingsRecord[] {
				new SettingsRecord(
						18,
						"Input Stream Params",
						":sout=#transcode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100}:std{access=file,mux=ts,dst=%s}",
						"******", true),
				new SettingsRecord(19, "Output Stream Adress",
						"192.168.1.2:1234", null, null),
				new SettingsRecord(20, "Output Stream Type", "unicast", null,
						null),
				new SettingsRecord(21, "Path for VLC",
						"C://Program Files//VideoLAN//VLC", null, null),
				new SettingsRecord(22, "Palantir Url and Port",
						"192.168.1.80:5050 ", null, null), };
	}
}
