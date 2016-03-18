package com.jaspersoft.android.sdk.service.data.report;

/**
 * @author Tom Koptel
 * @since 2.0
 */
public class ExportComponent {
    private final String mId;
    private final Type mType;

    public ExportComponent(String id, Type type) {
        mId = id;
        mType = type;
    }

    public String getId() {
        return mId;
    }

    public Type getType() {
        return mType;
    }

    public enum Type {
        FUSION_MAP("fusionMap"),
        FUSION_CHART("fusionChart"),
        FUSION_WIDGET("fusionWidget"),
        CHART("chart"),
        TABLE("table"),
        COLUMN("column"),
        GOOGLEMAP("googlemap"),
        TIBCOMAP("tibco-maps"),
        CROSSTAB("crosstab"),
        WEBFONTS("webfonts"),
        BOOKMARKS("bookmarks"),
        REPORTPARTS("reportparts"),
        HYPERLINKS("hyperlinks"),
        CUSTOM_VISUALIZATION_COMPONENT("CVComponent"),
        RAW(null);

        private String mRaw;

        Type(String raw) {
            mRaw = raw;
        }

        public String getValue() {
            return mRaw;
        }

        Type setValue(String value) {
            mRaw = value;
            return this;
        }

        public static Type parse(String rawType) {
            String type = rawType.toUpperCase();
            try {
                return Type.valueOf(type);

            } catch (IllegalArgumentException ex) {
                return Type.RAW.setValue(rawType);
            }
        }
    }
}
