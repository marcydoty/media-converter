package com.marcilene.enums;

public enum ContentType {

	MP4("video/mp4", "mp4"),
	OGG("video/ogg", "ogg"),
	WEBM("video/webm", "webm");


	private String value;

	private String extension;

	private ContentType(String value, String extension) {
		this.setValue(value);
		this.setExtension(extension);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public static ContentType fromExtension(String extension) {
		for (ContentType contentTypeEnum : ContentType.values()) {
			if (contentTypeEnum.getExtension().equals(extension)) {
				return contentTypeEnum;
			}
		}
		return null;
	}

}
