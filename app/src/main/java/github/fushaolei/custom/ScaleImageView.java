package github.fushaolei.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class ScaleImageView extends AppCompatImageView implements View.OnTouchListener {

    public ScaleImageView(@NonNull Context context) {
        this(context, null);
    }

    public ScaleImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int pointCount = motionEvent.getPointerCount();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (pointCount == 2) {

                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (pointCount == 2) {

                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (pointCount == 2) {
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.e("=>","action pointer up");
                break;
            default:
                break;
        }
        return true;
    }


}
