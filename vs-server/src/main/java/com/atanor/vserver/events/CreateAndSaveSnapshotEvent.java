package com.atanor.vserver.events;

public class CreateAndSaveSnapshotEvent {

	private Long recordingId;

	public CreateAndSaveSnapshotEvent(final Long recordingId) {
		this.recordingId = recordingId;
	}

	public Long getRecordingId() {
		return recordingId;
	}

}
