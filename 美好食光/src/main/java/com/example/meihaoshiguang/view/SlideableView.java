package com.example.meihaoshiguang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

public  class SlideableView extends FrameLayout{
	protected Scroller mScroller;
	public SlideableView(Context context) {
		super(context);
		mScroller = new Scroller(context);
	}
	

	public SlideableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
	}

	public void smoothScrollTo(int desX,int desY,int duration){
		int dx = desX - getScrollX();
		int dy = desY - getScrollY();
		mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, duration);
		invalidate();
	}
	@Override
	public void computeScroll() {
		if (!mScroller.isFinished()) {
			if (mScroller.computeScrollOffset()) {
				int oldX = getScrollX();
				int oldY = getScrollY();
				int x = mScroller.getCurrX();
				int y = mScroller.getCurrY();
				if (oldX != x || oldY != y) {
					scrollTo(x, y);
				}
				invalidate();
			} else {
				clearChildrenCache();
			} 
		} else {
			clearChildrenCache();
		} 
	}
	protected void clearChildrenCache() {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View layout = getChildAt(i);
			layout.setDrawingCacheEnabled(false);
		}
	}
}
