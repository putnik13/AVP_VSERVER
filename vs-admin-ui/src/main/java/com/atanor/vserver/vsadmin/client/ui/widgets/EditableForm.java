package com.atanor.vserver.vsadmin.client.ui.widgets;

import com.atanor.vserver.vsadmin.client.ui.UiUtils;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

public class EditableForm extends DynamicForm {

	public EditableForm() {
		setWidth100();
		setHeight100();
		setPadding(20);
		setNumCols(4);
		setColWidths(200, "*", 200, "*");
		setCellPadding(5);
	}

	@Override
	public void setFields(final FormItem... fields) {
		for (final FormItem item : fields) {
			addItemChangedHandler(item);
		}

		super.setFields(fields);
	}

	private static void addItemChangedHandler(final FormItem item) {
		item.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				final String newValue = (String) event.getValue();
				final FormItem item = event.getItem();
				final String originValue = item.getAttribute(UiUtils.ORIGIN_ITEM_VALUE);
				final boolean isValueChanged = originValue != null && !originValue.equals(newValue);

				item.setAttribute(UiUtils.ITEM_MODIFIED, isValueChanged);
				item.setTextBoxStyle(isValueChanged ? "textItemModified" : "textItem");
			}
		});
	}

	public static TextAreaItem createTextAreaItem(final String title) {
		final TextAreaItem item = new TextAreaItem();
		item.setTitle(title);
		item.setColSpan(4);
		item.setLength(5000);
		item.setWidth("*");
		item.setTitleStyle("formTitleVs");
		item.setTextBoxStyle("textItemReadOnly");
		item.setAttribute(UiUtils.ITEM_MODIFIED, false);
		return item;
	}

	public static TextItem createTextItem(final String title) {
		final TextItem item = new TextItem();
		item.setTitle(title);
		item.setWidth("*");
		item.setTitleStyle("formTitleVs");
		item.setTextBoxStyle("textItemReadOnly");
		item.setAttribute(UiUtils.ITEM_MODIFIED, false);
		return item;
	}

	public Boolean isModified() {
		for (final FormItem item : getFields()) {
			final Boolean isItemModified = item.getAttributeAsBoolean(UiUtils.ITEM_MODIFIED);
			if (isItemModified != null && isItemModified) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
}
