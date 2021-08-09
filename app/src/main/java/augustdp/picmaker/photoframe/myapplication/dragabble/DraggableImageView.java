package augustdp.picmaker.photoframe.myapplication.dragabble;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;

public class DraggableImageView extends androidx.appcompat.widget.AppCompatImageView {
    private static final String TAG = "Draggable Bitmap";
    private Context context;
    private DraggableBitmap mActiveBitmap = null;
    private boolean mDrawOpacityBackground = false;
    private RectF mInnerImageBounds = null;
    private Stack<BitmapOperationMap> mOperationStack = new Stack<>();
    private List<DraggableBitmap> mOverlayBitmaps;
    private Paint mPaint = new Paint();
    private OnTouchListener touchListener = new OnTouchListener() {
        /* class com.pak.independence.profile.pic.dp.dragabble.DraggableImageView.AnonymousClass1 */
        private float mDist = 0.0f;
        private EDITMODE mEditMode = EDITMODE.NONE;
        private float[] mLastEvent;
        private PointF mMid = new PointF();
        private float mNewRotation = 0.0f;
        private float mOldDistance;
        private PointF mStart = new PointF();
        private boolean touchMoveEndChecker = false;

        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction() & 255) {
                case 0:
                    this.touchMoveEndChecker = true;
                    DraggableImageView.this.mDrawOpacityBackground = true;
                    int activeBitmap = DraggableImageView.this.getActiveBitmap(motionEvent.getX(), motionEvent.getY());
                    if (activeBitmap == -1) {
                        DraggableImageView.this.mActiveBitmap = null;
                        break;
                    } else {
                        DraggableImageView.this.mActiveBitmap = (DraggableBitmap) DraggableImageView.this.mOverlayBitmaps.get(activeBitmap);
                        this.mLastEvent = null;
                        this.mEditMode = EDITMODE.DRAG;
                        this.mStart.set(motionEvent.getX(), motionEvent.getY());
                        if (DraggableImageView.this.mActiveBitmap != null) {
                            DraggableImageView.this.mActiveBitmap.setSavedMatrix(DraggableImageView.this.mActiveBitmap.getCurrentMatrix());
                            break;
                        }
                    }
                    break;
                case 2:
                    this.touchMoveEndChecker = false;
                    DraggableImageView.this.mDrawOpacityBackground = true;
                    if (DraggableImageView.this.mActiveBitmap != null) {
                        if (this.mEditMode == EDITMODE.DRAG) {
                            DraggableImageView.this.mActiveBitmap.setCurrentMatrix(DraggableImageView.this.mActiveBitmap.getSavedMatrix());
                            DraggableImageView.this.mActiveBitmap.getCurrentMatrix().postTranslate(motionEvent.getX() - this.mStart.x, motionEvent.getY() - this.mStart.y);
                        } else if (this.mEditMode == EDITMODE.ZOOM && motionEvent.getPointerCount() == 2) {
                            float spacing = DraggableImageView.this.spacing(motionEvent);
                            DraggableImageView.this.mActiveBitmap.setCurrentMatrix(DraggableImageView.this.mActiveBitmap.getSavedMatrix());
                            if (spacing > 10.0f) {
                                float f = spacing / this.mOldDistance;
                                DraggableImageView.this.mActiveBitmap.getCurrentMatrix().postScale(f, f, this.mMid.x, this.mMid.y);
                            }
                            if (this.mLastEvent != null) {
                                this.mNewRotation = DraggableImageView.this.rotation(motionEvent);
                                float f2 = this.mNewRotation - this.mDist;
                                RectF rectF = new RectF(0.0f, 0.0f, (float) DraggableImageView.this.mActiveBitmap.mBitmap.getWidth(), (float) DraggableImageView.this.mActiveBitmap.mBitmap.getHeight());
                                DraggableImageView.this.mActiveBitmap.getCurrentMatrix().mapRect(rectF);
                                DraggableImageView.this.mActiveBitmap.getCurrentMatrix().postRotate(f2, rectF.left + (rectF.width() / 2.0f), rectF.top + (rectF.height() / 2.0f));
                            }
                        }
                    }
                case 1:
                    if (this.touchMoveEndChecker) {
                        DraggableImageView.this.mDrawOpacityBackground = false;
                        if (DraggableImageView.this.mActiveBitmap != null) {
                            DraggableImageView.this.mOperationStack.push(new BitmapOperationMap(DraggableImageView.this.mActiveBitmap, new Matrix(DraggableImageView.this.mActiveBitmap.getCurrentMatrix()), BitmapOperationMap.OPERATION.ADD));
                            DraggableImageView.this.mActiveBitmap.deActivate();
                        }
                    }
                    this.touchMoveEndChecker = true;
                    break;
                case 5:
                    this.touchMoveEndChecker = false;
                    DraggableImageView.this.mDrawOpacityBackground = true;
                    if (DraggableImageView.this.mActiveBitmap != null) {
                        this.mOldDistance = DraggableImageView.this.spacing(motionEvent);
                        if (this.mOldDistance > 10.0f) {
                            DraggableImageView.this.mActiveBitmap.setSavedMatrix(DraggableImageView.this.mActiveBitmap.getCurrentMatrix());
                            DraggableImageView.this.midPoint(this.mMid, motionEvent);
                            this.mEditMode = EDITMODE.ZOOM;
                        }
                        this.mLastEvent = new float[4];
                        this.mLastEvent[0] = motionEvent.getX(0);
                        this.mLastEvent[1] = motionEvent.getX(1);
                        this.mLastEvent[2] = motionEvent.getY(0);
                        this.mLastEvent[3] = motionEvent.getY(1);
                        this.mDist = DraggableImageView.this.rotation(motionEvent);
                        break;
                    }
                    break;
                case 6:
                    this.mEditMode = EDITMODE.NONE;
                    break;
            }
            DraggableImageView.this.invalidate();
            return true;
        }
    };

    public enum EDITMODE {
        NONE,
        DRAG,
        ZOOM,
        ROTATE
    }

    public DraggableImageView(Context context2) {
        super(context2);
        initMembers();
        setOnTouchListener(this.touchListener);
    }

    public DraggableImageView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        initMembers();
        setOnTouchListener(this.touchListener);
        this.context = context2;
    }

    private void initMembers() {
        this.mOverlayBitmaps = new ArrayList();
    }

    public void removeAll() {
        this.mOverlayBitmaps.clear();
        this.mOverlayBitmaps = new ArrayList();
        invalidate();
    }

    public int addOverlayBitmap(DraggableBitmap draggableBitmap) {
        Matrix matrix = new Matrix();
        matrix.postTranslate(this.mInnerImageBounds.left, this.mInnerImageBounds.top);
        draggableBitmap.setMarginMatrix(matrix);
        Matrix matrix2 = new Matrix();
        matrix2.postConcat(matrix);
        draggableBitmap.setCurrentMatrix(matrix2);
        this.mOperationStack.push(new BitmapOperationMap(draggableBitmap, null, BitmapOperationMap.OPERATION.NEW));
        this.mOperationStack.push(new BitmapOperationMap(draggableBitmap, draggableBitmap.getCurrentMatrix(), BitmapOperationMap.OPERATION.ADD));
        draggableBitmap.setmId(this.mOverlayBitmaps.size());
        this.mOverlayBitmaps.add(draggableBitmap);
        invalidate();
        return draggableBitmap.getmId();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getActiveBitmap(float f, float f2) {
        Matrix matrix;
        int size = this.mOverlayBitmaps.size();
        int i = -1;
        DraggableBitmap draggableBitmap = null;
        for (int i2 = 0; i2 < size; i2++) {
            DraggableBitmap draggableBitmap2 = this.mOverlayBitmaps.get(i2);
            draggableBitmap2.deActivate();
            RectF rectF = new RectF(0.0f, 0.0f, (float) draggableBitmap2.mBitmap.getWidth(), (float) draggableBitmap2.mBitmap.getHeight());
            if (draggableBitmap2.getCurrentMatrix() == null) {
                matrix = draggableBitmap2.getMarginMatrix();
            } else {
                matrix = draggableBitmap2.getCurrentMatrix();
            }
            matrix.mapRect(rectF);
            float f3 = rectF.left;
            float f4 = rectF.top;
            if (f >= f3 && f < f3 + rectF.width() && f2 >= f4 && f2 < f4 + rectF.height()) {
                i = i2;
                draggableBitmap = draggableBitmap2;
            }
        }
        if (draggableBitmap != null) {
            if (!draggableBitmap.isTouched()) {
                draggableBitmap.setTouched(true);
            }
            draggableBitmap.activate();
        }
        return i;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float spacing(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void midPoint(PointF pointF, MotionEvent motionEvent) {
        pointF.set((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float rotation(MotionEvent motionEvent) {
        return (float) Math.toDegrees(Math.atan2((double) (motionEvent.getY(0) - motionEvent.getY(1)), (double) (motionEvent.getX(0) - motionEvent.getX(1))));
    }

    public List<DraggableBitmap> getOverlayList() {
        return this.mOverlayBitmaps;
    }

    public void undo() {
        if (!this.mOperationStack.empty()) {
            BitmapOperationMap pop = this.mOperationStack.pop();
            if (!this.mOperationStack.empty()) {
                pop = this.mOperationStack.peek();
            }
            DraggableBitmap draggableBitmap = pop.getDraggableBitmap();
            Matrix operationMatrix = pop.getOperationMatrix();
            switch (pop.getOption()) {
                case NEW:
                    this.mOverlayBitmaps.remove(draggableBitmap);
                    return;
                case ADD:
                    draggableBitmap.setCurrentMatrix(operationMatrix);
                    return;
                case DELETE:
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF innerBitmapSize = getInnerBitmapSize();
        if (innerBitmapSize != null) {
            this.mInnerImageBounds = innerBitmapSize;
            canvas.clipRect(innerBitmapSize);
            Enumeration enumeration = Collections.enumeration(this.mOverlayBitmaps);
            while (enumeration.hasMoreElements()) {
                DraggableBitmap draggableBitmap = (DraggableBitmap) enumeration.nextElement();
                if (draggableBitmap.getCurrentMatrix() != null) {
                    canvas.drawBitmap(draggableBitmap.mBitmap, draggableBitmap.getCurrentMatrix(), null);
                    RectF stampBounding = getStampBounding(draggableBitmap);
                    if (this.mDrawOpacityBackground && draggableBitmap == this.mActiveBitmap) {
                        this.mPaint.setColor(0);
                        this.mPaint.setStyle(Paint.Style.FILL);
                        this.mPaint.setAlpha(20);
                        canvas.drawRect(stampBounding, this.mPaint);
                    }
                }
            }
        }
    }

    public RectF getInnerBitmapSize() {
        RectF rectF = new RectF();
        if (getDrawable() == null) {
            return null;
        }
        rectF.right = (float) getDrawable().getIntrinsicWidth();
        rectF.bottom = (float) getDrawable().getIntrinsicHeight();
        getImageMatrix().mapRect(rectF);
        return rectF;
    }

    private RectF getStampBounding(DraggableBitmap draggableBitmap) {
        if (draggableBitmap.mBitmap == null) {
            return null;
        }
        RectF rectF = new RectF(0.0f, 0.0f, (float) draggableBitmap.mBitmap.getWidth(), (float) draggableBitmap.mBitmap.getHeight());
        draggableBitmap.getCurrentMatrix().mapRect(rectF);
        return rectF;
    }

    public int deleteActiveBitmap() {
        if (this.mActiveBitmap == null) {
            return -1;
        }
        int indexOf = this.mOverlayBitmaps.indexOf(this.mActiveBitmap);
        replaceOverlayBitmap(new DraggableBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)), indexOf);
        return indexOf;
    }

    public void setActiveBitmap() {
        this.mActiveBitmap = null;
    }

    public int ActiveBitmap() {
        return this.mActiveBitmap == null ? -1 : 0;
    }

    public void replaceOverlayBitmap(DraggableBitmap draggableBitmap, int i) {
        if (i <= this.mOverlayBitmaps.size()) {
            this.mActiveBitmap = this.mOverlayBitmaps.get(i);
            draggableBitmap.setCurrentMatrix(this.mActiveBitmap.getCurrentMatrix());
            this.mOverlayBitmaps.add(i, draggableBitmap);
            this.mOverlayBitmaps.remove(this.mActiveBitmap);
            this.mActiveBitmap = this.mOverlayBitmaps.get(i);
            invalidate();
        }
    }

    public void flipActiveBitmap() {
        try {
            Matrix matrix = new Matrix();
            matrix.setScale(-1.0f, 1.0f);
            matrix.postTranslate((float) this.mActiveBitmap.mBitmap.getWidth(), 0.0f);
            Matrix currentMatrix = this.mActiveBitmap.getCurrentMatrix();
            currentMatrix.preConcat(matrix);
            this.mActiveBitmap.setCurrentMatrix(currentMatrix);
        } catch (NullPointerException unused) {
            Log.v(TAG, "active bitmap is null");
        } catch (Exception unused2) {
            Log.v(TAG, "error ocurred");
        }
    }

    public void rearrangeOverlayList() {
        int indexOf = this.mOverlayBitmaps.indexOf(this.mActiveBitmap);
        this.mOverlayBitmaps.add(this.mActiveBitmap);
        this.mOverlayBitmaps.remove(indexOf);
    }
}
