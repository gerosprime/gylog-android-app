package com.gerosprime.gylog.ui.exercises.templatesets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.gerosprime.gylog.ui.exercises.R;

public class TemplateItemSwipeCallback extends ItemTouchHelper.SimpleCallback {

    // we want to cache these and not allocate anything repeatedly in the onChildDraw method
    private Drawable background;
    private Drawable xMark;
    private int xMarkMargin;
    private boolean initiated;

    private Context context;

    private OnSwipe listener;

    public TemplateItemSwipeCallback(Context context) {
        super(0, ItemTouchHelper.LEFT);
        this.context = context;

        init();
    }

    public void setListener(OnSwipe listener) {
        this.listener = listener;
    }

    private void init() {
        background = new ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent));
        xMark = ContextCompat.getDrawable(context, R.drawable.ic_delete_white24dp);
        xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        xMarkMargin = (int) context.getResources().getDimension(R.dimen.dimen_16dp);
        initiated = true;
    }

    // not important, we don't want drag & drop
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

        if (listener != null) {
            listener.onSwiped(viewHolder);
        }
    }

    @Override
    public void onChildDraw(Canvas canvas,
                            RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;

        // not sure why, but this method get's called for viewholder that are already swiped away
        if (viewHolder.getAdapterPosition() == -1) {
            // not interested in those
            return;
        }

        if (!initiated) {
            init();
        }

        // draw red background
        background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(),
                itemView.getRight(), itemView.getBottom());
        background.draw(canvas);

        // draw x mark
        int itemHeight = itemView.getBottom() - itemView.getTop();
        int intrinsicWidth = xMark.getIntrinsicWidth();
        int intrinsicHeight = xMark.getIntrinsicWidth();

        int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
        int xMarkRight = itemView.getRight() - xMarkMargin;
        int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
        int xMarkBottom = xMarkTop + intrinsicHeight;
        xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

        xMark.draw(canvas);

        super.onChildDraw(canvas, recyclerView, viewHolder,
                dX, dY, actionState, isCurrentlyActive);
    }

    public interface OnSwipe {
        void onSwiped(RecyclerView.ViewHolder source);
    }

}
