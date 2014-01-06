package com.atanor.vserver.pdf;

import java.util.List;

import com.atanor.vserver.common.entity.Snapshot;

public interface PdfGenerator {

	void generatePdf(String fileName, List<Snapshot> snapshots);
}
