package com.app.ocyrus.utills;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.app.ocyrus.R;


/**
 * Created by Avinash Sharma on 2/22/2018.
 * at http://www.dotsquares.com/
 */
public class RippleViewCreator extends FrameLayout {
    /**
     * The Frame rate.
     */
    private final int frameRate = 15;

    /**
     * The Speed.
     */
    private float speed = 1;
    /**
     * The Radius.
     */
    private float radius = 0;
    /**
     * The Paint.
     */
    private final Paint paint = new Paint();
    /**
     * The End radius.
     */
    private float endRadius = 0;
    /**
     * The Ripple x.
     */
    private float rippleX = 0;
    /**
     * The Ripple y.
     */
    private float rippleY = 0;
    /**
     * The Width.
     */
    private int width = 0;
    /**
     * The Height.
     */
    private int height = 0;
    /**
     * The Handler.
     */
    private final Handler handler = new Handler();
    /**
     * The Touch action.
     */
    private int touchAction;

    /**
     * Instantiates a new Ripple view creator.
     *
     * @param context the context
     */
    public RippleViewCreator(Context context) {
        this(context, null, 0);
    }

    /**
     * Instantiates a new Ripple view creator.
     *
     * @param context the context
     * @param attrs   the file_paths
     */
    public RippleViewCreator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Instantiates a new Ripple view creator.
     *
     * @param context      the context
     * @param attrs        the file_paths
     * @param defStyleAttr the def style attr
     */
    public RippleViewCreator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Add ripple to view.
     *
     * @param v the v
     */
    public static void addRippleToView(View v) {
        ViewGroup parent = (ViewGroup) v.getParent();
        int index = -1;
        if (parent != null) {
            index = parent.indexOfChild(v);
            parent.removeView(v);
        }
        RippleViewCreator rippleViewCreator = new RippleViewCreator(v.getContext());
        rippleViewCreator.setLayoutParams(v.getLayoutParams());
        if (index == -1)
            parent.addView(rippleViewCreator, index);
        else
            parent.addView(rippleViewCreator);
        rippleViewCreator.addView(v);
    }

    /**
     * Init.
     */
    private void init() {
        if (isInEditMode())
            return;

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setAntiAlias(true);
        setWillNotDraw(true);
        setDrawingCacheEnabled(true);
        setClickable(true);
    }

    /**
     * On size changed.
     *
     * @param w    the w
     * @param h    the h
     * @param oldw the oldw
     * @param oldh the oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    /**
     * Dispatch draw.
     *
     * @param canvas the canvas
     */
    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        super.dispatchDraw(canvas);

        if (radius > 0 && radius < endRadius) {
            canvas.drawCircle(rippleX, rippleY, radius, paint);
            if (touchAction == MotionEvent.ACTION_UP)
                invalidate();
        }
    }

    /**
     * On intercept touch event boolean.
     *
     * @param event the event
     * @return the boolean
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return true;
    }

    /**
     * On touch event boolean.
     *
     * @param event the event
     * @return the boolean
     */
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        rippleX = event.getX();
        rippleY = event.getY();

        touchAction = event.getAction();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {
                getParent().requestDisallowInterceptTouchEvent(false);

                radius = 1;
                endRadius = Math.max(Math.max(Math.max(width - rippleX, rippleX), rippleY), height - rippleY);
                float duration = 350;
                speed = endRadius / duration * frameRate;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (radius < endRadius) {
                            radius += speed;
                            paint.setAlpha(90 - (int) (radius / endRadius * 90));
                            handler.postDelayed(this, frameRate);
                        } else if (getChildAt(0) != null) {
                            getChildAt(0).performClick();
                        }
                    }
                }, frameRate);
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                getParent().requestDisallowInterceptTouchEvent(true);
                endRadius = Math.max(Math.max(Math.max(width - rippleX, rippleX), rippleY), height - rippleY);
                paint.setAlpha(90);
                radius = endRadius / 3;
                invalidate();
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                if (rippleX < 0 || rippleX > width || rippleY < 0 || rippleY > height) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    touchAction = MotionEvent.ACTION_CANCEL;
                    break;
                } else {
                    invalidate();
                    return true;
                }
            }
        }
        invalidate();
        return false;
    }

    /**
     * Add view.
     *
     * @param child  the child
     * @param index  the index
     * @param params the params
     */
    @Override
    public final void addView(@NonNull View child, int index, ViewGroup.LayoutParams params) {
        //limit one view
        if (getChildCount() > 0) {
            throw new IllegalStateException(this.getClass().toString() + " can only have one child.");
        }
        super.addView(child, index, params);
    }
}
