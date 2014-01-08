package com.atanor.vserver.common.json;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.piriti.json.client.JsonWriter;

import com.atanor.vserver.common.entity.Notification;
import com.atanor.vserver.common.entity.SignalMessage;
import com.atanor.vserver.common.entity.Snapshot;
import com.google.gwt.core.shared.GWT;

public class JsonConverters {

	public interface SnapshotReader extends JsonReader<Snapshot> {}
	public static final SnapshotReader SNAPSHOT_READER = GWT.create(SnapshotReader.class);

	public interface SnapshotWriter extends JsonWriter<Snapshot> {}
	public static final SnapshotWriter SNAPSHOT_WRITER = GWT.create(SnapshotWriter.class);
	
	public interface NotificationReader extends JsonReader<Notification> {}
	public static final NotificationReader NOTIFICATION_READER = GWT.create(NotificationReader.class);

	public interface NotificationWriter extends JsonWriter<Notification> {}
	public static final NotificationWriter NOTIFICATION_WRITER = GWT.create(NotificationWriter.class);
	
	public interface SignalReader extends JsonReader<SignalMessage> {}
	public static final SignalReader SIGNAL_READER = GWT.create(SignalReader.class);

	public interface SignalWriter extends JsonWriter<SignalMessage> {}
	public static final SignalWriter SIGNAL_WRITER = GWT.create(SignalWriter.class);
}
