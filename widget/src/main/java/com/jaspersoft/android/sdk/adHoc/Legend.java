package com.jaspersoft.android.sdk.adHoc;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class Legend {
    private String label;
    private int color;
    private boolean enabled;

    public Legend(String label, int color, boolean enabled) {
        this.label = label;
        this.color = color;
        this.enabled = enabled;
    }

    public String getLabel() {
        return label;
    }

    public int getColor() {
        return color;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
