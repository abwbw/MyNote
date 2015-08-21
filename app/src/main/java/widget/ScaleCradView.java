package widget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.example.abwbw.mynote.util.LogUtil;

/**
 * Created by abwbw on 15-8-12.
 */
public class ScaleCradView extends CardView {
    private Context mContext;
    private ScaleGestureDetector mScaleDetector;
    public ScaleCradView(Context context) {
        super(context);
        init(context);
    }



    public ScaleCradView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScaleCradView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
        mScaleDetector = new ScaleGestureDetector(mContext,new ScaleGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener{

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

        }
    }


}
