package augustdp.picmaker.photoframe.myapplication.photocommon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

public class CropImageView extends AppCompatImageView {
    private static final int PRESS_AREA_RADIUS = 36;
    private static final int PRESS_LB = 3;
    private static final int PRESS_LT = 0;
    private static final int PRESS_RB = 2;
    private static final int PRESS_RT = 1;
    private static final int REMAIN_AREA_ALPHA = 127;
    private Rect mActualArea;
    private Rect mChooseArea;
    private Paint mChooseAreaPaint;
    private boolean mFirstDrawFlag;
    private Rect mOriginalArea;
    private Bitmap mOriginalBitmap;
    private int mPressAreaFlag;
    private Rect mPressLBArea;
    private Rect mPressLTArea;
    private Rect mPressRBArea;
    private Rect mPressRTArea;
    private Paint mRemainAreaPaint;
    private Rect mRemainBottomArea;
    private Rect mRemainLeftArea;
    private Rect mRemainRightArea;
    private Rect mRemainTopArea;
    private boolean mTouchCorrectFlag;
    private int mX;
    private int mY;

    public CropImageView(Context context) {
        super(context);
        init();
    }

    public CropImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        this.mActualArea = new Rect();
        this.mChooseArea = new Rect();
        this.mPressLTArea = new Rect();
        this.mPressRTArea = new Rect();
        this.mPressRBArea = new Rect();
        this.mPressLBArea = new Rect();
        this.mRemainLeftArea = new Rect();
        this.mRemainRightArea = new Rect();
        this.mRemainTopArea = new Rect();
        this.mRemainBottomArea = new Rect();
        this.mChooseAreaPaint = new Paint();
        this.mRemainAreaPaint = new Paint();
        this.mRemainAreaPaint.setStyle(Paint.Style.FILL);
        this.mRemainAreaPaint.setAlpha(REMAIN_AREA_ALPHA);
        this.mFirstDrawFlag = true;
        this.mTouchCorrectFlag = false;
    }

    public void setBitmap(Bitmap bitmap) {
        this.mOriginalArea = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        this.mOriginalBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        setImageBitmap(this.mOriginalBitmap);
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"DrawAllocation"})
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mFirstDrawFlag) {
            this.mFirstDrawFlag = false;
            imageScale();
        }
        setRemainArea();
        canvas.drawRect(this.mRemainLeftArea, this.mRemainAreaPaint);
        canvas.drawRect(this.mRemainRightArea, this.mRemainAreaPaint);
        canvas.drawRect(this.mRemainTopArea, this.mRemainAreaPaint);
        canvas.drawRect(this.mRemainBottomArea, this.mRemainAreaPaint);
        this.mChooseAreaPaint.setColor(-1);
        this.mChooseAreaPaint.setStrokeWidth(5.0f);
        this.mChooseAreaPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(this.mChooseArea, this.mChooseAreaPaint);
        this.mChooseAreaPaint.setColor(Color.argb(153, 255, 255, 255));
        this.mChooseAreaPaint.setStrokeWidth(2.0f);
        int i = ((this.mChooseArea.right - this.mChooseArea.left) / 3) + this.mChooseArea.left;
        int i2 = this.mChooseArea.top;
        int i3 = (((this.mChooseArea.right - this.mChooseArea.left) * 2) / 3) + this.mChooseArea.left;
        int i4 = this.mChooseArea.top;
        int i5 = this.mChooseArea.right;
        int i6 = ((this.mChooseArea.bottom - this.mChooseArea.top) / 3) + this.mChooseArea.top;
        int i7 = this.mChooseArea.right;
        int i8 = (((this.mChooseArea.bottom - this.mChooseArea.top) * 2) / 3) + this.mChooseArea.top;
        int i9 = this.mChooseArea.bottom;
        int i10 = this.mChooseArea.bottom;
        int i11 = this.mChooseArea.left;
        int i12 = this.mChooseArea.left;
        float f = (float) i;
        canvas.drawLine(f, (float) i2, f, (float) i10, this.mChooseAreaPaint);
        float f2 = (float) i3;
        canvas.drawLine(f2, (float) i4, f2, (float) i9, this.mChooseAreaPaint);
        float f3 = (float) i12;
        float f4 = (float) i6;
        canvas.drawLine(f3, f4, (float) i5, f4, this.mChooseAreaPaint);
        float f5 = (float) i8;
        canvas.drawLine((float) i11, f5, (float) i7, f5, this.mChooseAreaPaint);
        this.mChooseAreaPaint.setColor(-1);
        this.mChooseAreaPaint.setStyle(Paint.Style.FILL);
        setPressArea();
        canvas.drawOval(new RectF(this.mPressLTArea), this.mChooseAreaPaint);
        canvas.drawOval(new RectF(this.mPressRTArea), this.mChooseAreaPaint);
        canvas.drawOval(new RectF(this.mPressRBArea), this.mChooseAreaPaint);
        canvas.drawOval(new RectF(this.mPressLBArea), this.mChooseAreaPaint);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.mX = (int) motionEvent.getX();
            this.mY = (int) motionEvent.getY();
            if (isInPressArea(this.mX, this.mY) || isInChooseArea(this.mX, this.mY)) {
                this.mTouchCorrectFlag = true;
                return true;
            }
        }
        if (motionEvent.getAction() != 2 || !this.mTouchCorrectFlag) {
            if (motionEvent.getAction() != 1) {
                return super.onTouchEvent(motionEvent);
            }
            this.mTouchCorrectFlag = false;
            this.mPressAreaFlag = -1;
            invalidate();
            return true;
        } else if (isMovePressArea((int) motionEvent.getX(), (int) motionEvent.getY())) {
            invalidate();
            this.mX = (int) motionEvent.getX();
            this.mY = (int) motionEvent.getY();
            return true;
        } else if (this.mChooseArea.contains(this.mActualArea)) {
            return true;
        } else {
            moveChooseArea(((int) motionEvent.getX()) - this.mX, ((int) motionEvent.getY()) - this.mY);
            invalidate();
            this.mX = (int) motionEvent.getX();
            this.mY = (int) motionEvent.getY();
            return true;
        }
    }

    private void imageScale() {
        if (this.mOriginalArea != null) {
            RectF rectF = new RectF(this.mOriginalArea);
            RectF rectF2 = new RectF();
            getImageMatrix().mapRect(rectF2, rectF);
            this.mActualArea.set((int) rectF2.left, (int) rectF2.top, (int) rectF2.right, (int) rectF2.bottom);
            this.mChooseArea.set(this.mActualArea.left + 50, this.mActualArea.top + 50, this.mActualArea.right - 50, this.mActualArea.bottom - 50);
            return;
        }
        throw new IllegalStateException("setBitmap() must called before...");
    }

    private void setRemainArea() {
        this.mRemainLeftArea.set(this.mActualArea.left, this.mActualArea.top, this.mChooseArea.left, this.mActualArea.bottom);
        this.mRemainRightArea.set(this.mChooseArea.right, this.mActualArea.top, this.mActualArea.right, this.mActualArea.bottom);
        this.mRemainTopArea.set(this.mChooseArea.left, this.mActualArea.top, this.mChooseArea.right, this.mChooseArea.top);
        this.mRemainBottomArea.set(this.mChooseArea.left, this.mChooseArea.bottom, this.mChooseArea.right, this.mActualArea.bottom);
    }

    private void setPressArea() {
        this.mPressLTArea.set(this.mChooseArea.left - 18, this.mChooseArea.top - 18, this.mChooseArea.left + 18, this.mChooseArea.top + 18);
        this.mPressRTArea.set(this.mChooseArea.right - 18, this.mChooseArea.top - 18, this.mChooseArea.right + 18, this.mChooseArea.top + 18);
        this.mPressRBArea.set(this.mChooseArea.right - 18, this.mChooseArea.bottom - 18, this.mChooseArea.right + 18, this.mChooseArea.bottom + 18);
        this.mPressLBArea.set(this.mChooseArea.left - 18, this.mChooseArea.bottom - 18, this.mChooseArea.left + 18, this.mChooseArea.bottom + 18);
    }

    public boolean isInChooseArea(int i, int i2) {
        return this.mChooseArea.contains(i, i2);
    }

    public boolean isInPressArea(int i, int i2) {
        Rect rect = new Rect(this.mPressLTArea.left - 5, this.mPressLTArea.top - 5, this.mPressLTArea.right + 5, this.mPressLTArea.bottom + 5);
        Rect rect2 = new Rect(this.mPressRTArea.left - 5, this.mPressRTArea.top - 5, this.mPressRTArea.right + 5, this.mPressRTArea.bottom + 5);
        Rect rect3 = new Rect(this.mPressRBArea.left - 5, this.mPressRBArea.top - 5, this.mPressRBArea.right + 5, this.mPressRBArea.bottom + 5);
        Rect rect4 = new Rect(this.mPressLBArea.left - 5, this.mPressLBArea.top - 5, this.mPressLBArea.right + 5, this.mPressLBArea.bottom + 5);
        if (rect.contains(i, i2)) {
            this.mPressAreaFlag = 0;
            return true;
        } else if (rect2.contains(i, i2)) {
            this.mPressAreaFlag = 1;
            return true;
        } else if (rect3.contains(i, i2)) {
            this.mPressAreaFlag = 2;
            return true;
        } else if (!rect4.contains(i, i2)) {
            return false;
        } else {
            this.mPressAreaFlag = 3;
            return true;
        }
    }

    private boolean isMovePressArea(int i, int i2) {
        switch (this.mPressAreaFlag) {
            case 0:
                pressLTArea(i - this.mX, i2 - this.mY);
                return true;
            case 1:
                pressRTArea(i - this.mX, i2 - this.mY);
                return true;
            case 2:
                pressRBArea(i - this.mX, i2 - this.mY);
                return true;
            case 3:
                pressLBArea(i - this.mX, i2 - this.mY);
                return true;
            default:
                return false;
        }
    }

    private void pressLTArea(int i, int i2) {
        int i3 = this.mChooseArea.left + i;
        int i4 = this.mChooseArea.right;
        int i5 = this.mChooseArea.top + i2;
        int i6 = this.mChooseArea.bottom;
        if (i3 < this.mActualArea.left || i3 > this.mActualArea.right - 36 || i5 < this.mActualArea.top || i5 > this.mChooseArea.bottom - 36) {
            if (i3 < this.mActualArea.left) {
                i3 = this.mActualArea.left;
            }
            if (i5 < this.mActualArea.top) {
                i5 = this.mActualArea.top;
            }
            if (i3 > this.mChooseArea.right - 36) {
                i3 = this.mChooseArea.right - 36;
            }
            if (i5 > this.mChooseArea.bottom - 36) {
                i5 = this.mChooseArea.bottom - 36;
            }
            this.mChooseArea.set(i3, i5, i4, i6);
            return;
        }
        this.mChooseArea.set(i3, i5, i4, i6);
    }

    private void pressRTArea(int i, int i2) {
        int i3 = this.mChooseArea.left;
        int i4 = this.mChooseArea.right + i;
        int i5 = this.mChooseArea.top + i2;
        int i6 = this.mChooseArea.bottom;
        if (i4 > this.mActualArea.right || i4 < this.mChooseArea.left + 36 || i5 < this.mActualArea.top || i5 > this.mChooseArea.bottom - 36) {
            if (i4 > this.mActualArea.right) {
                i4 = this.mActualArea.right;
            }
            if (i5 < this.mActualArea.top) {
                i5 = this.mActualArea.top;
            }
            if (i4 < this.mChooseArea.left + 36) {
                i4 = this.mChooseArea.left + 36;
            }
            if (i5 > this.mChooseArea.bottom - 36) {
                i5 = this.mChooseArea.bottom - 36;
            }
            this.mChooseArea.set(i3, i5, i4, i6);
            return;
        }
        this.mChooseArea.set(i3, i5, i4, i6);
    }

    private void pressRBArea(int i, int i2) {
        int i3 = this.mChooseArea.left;
        int i4 = this.mChooseArea.right + i;
        int i5 = this.mChooseArea.top;
        int i6 = this.mChooseArea.bottom + i2;
        if (i4 > this.mActualArea.right || i3 < this.mChooseArea.left + 36 || i6 > this.mActualArea.bottom || i6 < this.mChooseArea.top + 36) {
            if (i4 > this.mActualArea.right) {
                i4 = this.mActualArea.right;
            }
            if (i6 > this.mActualArea.bottom) {
                i6 = this.mActualArea.bottom;
            }
            if (i4 < this.mChooseArea.left + 36) {
                i4 = this.mChooseArea.left + 36;
            }
            if (i6 < this.mChooseArea.top + 36) {
                i6 = this.mChooseArea.top + 36;
            }
            this.mChooseArea.set(i3, i5, i4, i6);
            return;
        }
        this.mChooseArea.set(i3, i5, i4, i6);
    }

    private void pressLBArea(int i, int i2) {
        int i3 = this.mChooseArea.left + i;
        int i4 = this.mChooseArea.right;
        int i5 = this.mChooseArea.top;
        int i6 = this.mChooseArea.bottom + i2;
        if (i3 < this.mActualArea.left || i3 > this.mChooseArea.right - 36 || i6 > this.mActualArea.bottom || i6 < this.mChooseArea.top + 36) {
            if (i3 < this.mActualArea.left) {
                i3 = this.mActualArea.left;
            }
            if (i6 > this.mActualArea.bottom) {
                i6 = this.mActualArea.bottom;
            }
            if (i3 > this.mChooseArea.right - 36) {
                i3 = this.mChooseArea.right - 36;
            }
            if (i6 < this.mChooseArea.top + 36) {
                i6 = this.mChooseArea.top + 36;
            }
            this.mChooseArea.set(i3, i5, i4, i6);
            return;
        }
        this.mChooseArea.set(i3, i5, i4, i6);
    }

    public void moveChooseArea(int i, int i2) {
        int i3 = this.mChooseArea.left + i;
        int i4 = this.mChooseArea.right + i;
        int i5 = this.mChooseArea.top + i2;
        int i6 = this.mChooseArea.bottom + i2;
        if (!this.mActualArea.contains(i3, i5, i4, i6)) {
            if (i3 < this.mActualArea.left) {
                i3 = this.mActualArea.left;
                i4 = this.mChooseArea.right;
            }
            if (i4 > this.mActualArea.right) {
                int i7 = this.mActualArea.right;
                i3 = this.mChooseArea.left;
                i4 = i7;
            }
            if (i5 < this.mActualArea.top) {
                i5 = this.mActualArea.top;
                i6 = this.mChooseArea.bottom;
            }
            if (i6 > this.mActualArea.bottom) {
                i6 = this.mActualArea.bottom;
                i5 = this.mChooseArea.top;
            }
        }
        this.mChooseArea.set(i3, i5, i4, i6);
    }

    public Bitmap getCropBitmap() {
        float width = ((float) this.mOriginalBitmap.getWidth()) / ((float) (this.mActualArea.right - this.mActualArea.left));
        float height = ((float) this.mOriginalBitmap.getHeight()) / ((float) (this.mActualArea.bottom - this.mActualArea.top));
        int i = (int) (((float) (this.mChooseArea.left - this.mActualArea.left)) * width);
        int i2 = (int) (((float) i) + (((float) (this.mChooseArea.right - this.mChooseArea.left)) * width));
        int i3 = (int) (((float) (this.mChooseArea.top - this.mActualArea.top)) * height);
        return Bitmap.createBitmap(this.mOriginalBitmap, i, i3, i2 - i, ((int) (((float) i3) + (((float) (this.mChooseArea.bottom - this.mChooseArea.top)) * height))) - i3);
    }

    public void cropBitmap() {
        this.mFirstDrawFlag = true;
        this.mTouchCorrectFlag = false;
        setBitmap(getCropBitmap());
    }

    public void reset() {
        this.mFirstDrawFlag = true;
        this.mTouchCorrectFlag = false;
    }
}
