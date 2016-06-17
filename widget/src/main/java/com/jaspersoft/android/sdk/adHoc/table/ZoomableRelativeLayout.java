package com.jaspersoft.android.sdk.adHoc.table;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.RelativeLayout;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class ZoomableRelativeLayout extends RelativeLayout {
    private GestureDetectorCompat gestureDetectorCompat;
    private ScaleGestureDetector scaleGestureDetector;
    private ZoomListener zoomListener;
    private Zoomer zoomer;
    private float measureX;
    private float measureY;

    public ZoomableRelativeLayout(Context context) {
        super(context);
        init();
    }

    public ZoomableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZoomableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        gestureDetectorCompat = new GestureDetectorCompat(getContext(), new GestureListener());
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        gestureDetectorCompat.setIsLongpressEnabled(false);
        zoomer = new Zoomer();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return scaleGestureDetector.onTouchEvent(ev) && gestureDetectorCompat.onTouchEvent(ev);
    }

    public void setZoomListener(ZoomListener zoomListener) {
        this.zoomListener = zoomListener;
    }

    private void updateRestrictRect() {
        float scale = getChildAt(0).getScaleX();
        float width = getChildAt(0).getWidth();
        float height = getChildAt(0).getHeight();

        measureX = (width - width / scale) / 2 * scale;
        measureY = (height - height / scale) / 2 * scale + getTranslationY() / scale;
    }

    private void scroll(float distanceX, float distanceY) {
        float newX = getChildAt(0).getX() + distanceX;
        float newY = getChildAt(0).getY() + distanceY;

        newX = Math.min(Math.max(newX, -measureX), measureX);
        newY = Math.min(Math.max(newY, -measureY), measureY);

        getChildAt(0).setX(newX);
        getChildAt(0).setY(newY);
        zoomListener.onMove(newX, newY);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            // zoomer.setFocus(detector.getFocusX(), detector.getFocusY());
            // zoomer.setPivot(detector.getCurrentSpanX(), detector.getCurrentSpanY(), detector.getPreviousSpanX(), detector.getPreviousSpanY());
            zoomer.zoom(scaleFactor);
            updateRestrictRect();
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            zoomer.clear();
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            scroll(-distanceX, -distanceY);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    private class Zoomer {
        private Float prevFocusX, prevFocusY, currentFocusX, currentFocusY;

        public void setFocus(float focusX, float focusY) {
            if (currentFocusX != null && currentFocusY != null) {
                prevFocusX = currentFocusX;
                prevFocusY = currentFocusY;
            }
            currentFocusX = focusX;
            currentFocusY = focusY;
        }

        public void zoom(float scaleFactor) {
            float newScaleX = scaleFactor * getChildAt(0).getScaleX();
            float newScaleY = scaleFactor * getChildAt(0).getScaleY();
            newScaleX = Math.max(1f, Math.min(newScaleX, 15.0f));
            newScaleY = Math.max(1f, Math.min(newScaleY, 15.0f));

            getChildAt(0).setScaleX(newScaleX);
            getChildAt(0).setScaleY(newScaleY);
            zoomListener.onZoom(newScaleX);
        }

        public void setPivot(float distanceX, float distanceY, float prevDistanceX, float prevDistanceY) {
            if (prevFocusX != null && prevFocusY != null) {
                float pivotX = definePivotX(distanceX, prevDistanceX);
                float pivotY = definePivotY(distanceY, prevDistanceY);
                getChildAt(0).setPivotX(pivotX);
                getChildAt(0).setPivotY(pivotY);
            }
        }

        public void clear() {
            prevFocusX = null;
            prevFocusY = null;
            currentFocusX = null;
            currentFocusY = null;
        }

        private float definePivotX(float currentDistanceX, float prevDistanceX) {
            float halfDistanceX = currentDistanceX / 2;
            float focusDifferenceX = Math.abs(currentFocusX - prevFocusX);
            float halfDistanceDifferenceX = Math.abs(currentDistanceX - prevDistanceX) / 2;
            return currentFocusX - halfDistanceX * (focusDifferenceX / halfDistanceDifferenceX);
        }

        private float definePivotY(float currentDistanceY, float prevDistanceY) {
            float halfDistanceY = currentDistanceY / 2;
            float focusDifferenceY = Math.abs(currentFocusX - prevFocusX);
            float distanceDifferenceY = Math.abs(currentDistanceY - prevDistanceY);
            return currentFocusY + halfDistanceY * (focusDifferenceY / distanceDifferenceY);
        }
    }

    public interface ZoomListener {
        void onZoom(float zoom);

        void onMove(float x, float y);
    }
}
