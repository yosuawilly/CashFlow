package com.cash.flow.customcomponent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager{
	
	Class<?> clazz = this.getClass().getSuperclass();
	OnPageChanged onPageChanged;
	boolean enableSwipe = false;
	
	private static final int MY_MIN_DISTANCE_FOR_FLING = 3; // dips
	private static final int MY_MIN_FLING_VELOCITY = 100; // dips
	private int myMFlingDistance;
	private int MymMinimumVelocity;
	Context context;
	
	public CustomViewPager(Context context) {
		super(context);
		this.context = context;
		postInitViewPager();
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		postInitViewPager();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent evt) {
		if(enableSwipe) return super.onTouchEvent(evt);
		return enableSwipe;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent evt) {
		if(enableSwipe) return super.onInterceptTouchEvent(evt);
		return enableSwipe;
	}
	
	public void setEnableSwipe(boolean enableSwipe) {
		this.enableSwipe = enableSwipe;
	}
	
	private void postInitViewPager() {
		Class<?> viewpager = ViewPager.class;
		try {
			final float density = context.getResources().getDisplayMetrics().density;
			Field flingDistance = viewpager.getDeclaredField("mFlingDistance");
			flingDistance.setAccessible(true);
			Field minimumVelocity = viewpager.getDeclaredField("mMinimumVelocity");
			minimumVelocity.setAccessible(true);
			
			myMFlingDistance = (int) (MY_MIN_DISTANCE_FOR_FLING * density);
			flingDistance.set(this, myMFlingDistance);
			
			MymMinimumVelocity = (int) (MY_MIN_FLING_VELOCITY * density);
			minimumVelocity.set(this, MymMinimumVelocity);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@SuppressWarnings("unchecked")
	void setCurrentItemInternal2(int item, boolean smoothScroll, boolean always, int velocity) {
		try{
			
			PagerAdapter mAdapter = (PagerAdapter) getPrivateVariable(clazz, "mAdapter");
			int mCurItem = (Integer) getPrivateVariable(clazz, "mCurItem");
			boolean mFirstLayout = (Boolean) getPrivateVariable(clazz, "mFirstLayout");
			OnPageChangeListener mOnPageChangeListener = (OnPageChangeListener) getPrivateVariable(clazz, "mOnPageChangeListener");
			OnPageChangeListener mInternalPageChangeListener = (OnPageChangeListener) getPrivateVariable(clazz, "mInternalPageChangeListener");
			ArrayList<Object> mItems = (ArrayList<Object>) getPrivateVariable(clazz, "mItems");
			int mOffscreenPageLimit = (Integer) getPrivateVariable(clazz, "mOffscreenPageLimit");
			
			if (mAdapter == null || mAdapter.getCount() <= 0) {
				executePrivateMethod(clazz, "setScrollingCacheEnabled", new Class<?>[]{Boolean.class}, Boolean.FALSE);
//	            setScrollingCacheEnabled(false);
	            return;
	        }
	        if (!always && mCurItem == item && mItems.size() != 0) {
	        	executePrivateMethod(clazz, "setScrollingCacheEnabled", new Class<?>[]{Boolean.class}, Boolean.FALSE);
//	            setScrollingCacheEnabled(false);
	            return;
	        }

	        if (item < 0) {
	            item = 0;
	        } else if (item >= mAdapter.getCount()) {
	            item = mAdapter.getCount() - 1;
	        }
	        final int pageLimit = mOffscreenPageLimit;
	        if (item > (mCurItem + pageLimit) || item < (mCurItem - pageLimit)) {
	            // We are doing a jump by more than one page.  To avoid
	            // glitches, we want to keep all current pages in the view
	            // until the scroll ends.
	            for (int i=0; i<mItems.size(); i++) {
	            	setPrivateVariable(mItems.get(0).getClass(), "scrolling", mItems.get(i), Boolean.TRUE);
//	                mItems.get(i).scrolling = true;
	            }
	        }
	        final boolean dispatchSelected = mCurItem != item;

	        if (mFirstLayout) {
	            // We don't have any idea how big we are yet and shouldn't have any pages either.
	            // Just set things up and let the pending layout handle things.
	            mCurItem = item;
	            if (dispatchSelected && mOnPageChangeListener != null) {
	                mOnPageChangeListener.onPageSelected(item);
	            }
	            if (dispatchSelected && mInternalPageChangeListener != null) {
	                mInternalPageChangeListener.onPageSelected(item);
	            }
	            requestLayout();
	        } else {
	        	executePrivateMethod(clazz, "populate", new Class<?>[]{int.class}, item);
	        	//executePrivateMethod(clazz, "scrollToItem", new Class<?>[]{int.class, boolean.class, int.class, boolean.class}, item, smoothScroll, velocity, dispatchSelected);
//	            populate(item);
	            scrollToItem2(item, smoothScroll, velocity, dispatchSelected);
	        }
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void scrollToItem2(int item, boolean smoothScroll, int velocity, boolean dispatchSelected) {
//		ArrayList<Object> items = (ArrayList<Object>) getPrivateVariable(clazz, "mItems");
//		float position = (Float) getPrivateVariable(items.get(0).getClass(), "offset", items.get(1));
//		Log.i("position", String.valueOf(position));
		try{
		OnPageChangeListener mOnPageChangeListener = (OnPageChangeListener) getPrivateVariable(clazz, "mOnPageChangeListener");
		OnPageChangeListener mInternalPageChangeListener = (OnPageChangeListener) getPrivateVariable(clazz, "mInternalPageChangeListener");
		float mFirstOffset = (Float) getPrivateVariable(clazz, "mFirstOffset");
		float mLastOffset = (Float) getPrivateVariable(clazz, "mLastOffset");
		
//		final Object curInfo = infoForPosition(item);
		final Object curInfo = executePrivateMethod(clazz, "infoForPosition", new Class<?>[]{int.class}, item);
        int destX = 0;
        if (curInfo != null) {
//            final int width = getClientWidth();
            final int width = (Integer) executePrivateMethod(clazz, "getClientWidth", new Class<?>[]{}, (Object[])null);
            float offset = (Float) getPrivateVariable(curInfo.getClass(), "offset", curInfo);
//            destX = (int) (width * Math.max(mFirstOffset, Math.min(curInfo.offset, mLastOffset)));
            destX = (int) (width * Math.max(mFirstOffset, Math.min(offset, mLastOffset)));
        }
        if (smoothScroll) {
//            smoothScrollTo(destX, 0, velocity);
            executePrivateMethod(clazz, "smoothScrollTo", new Class<?>[]{int.class,int.class,int.class}, destX, 0, velocity);
            if (dispatchSelected && mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageSelected(item);
            }
            if (dispatchSelected && mInternalPageChangeListener != null) {
                mInternalPageChangeListener.onPageSelected(item);
            }
        } else {
//            if (dispatchSelected && mOnPageChangeListener != null) {
//                mOnPageChangeListener.onPageSelected(item);
//            }
//            if (dispatchSelected && mInternalPageChangeListener != null) {
//                mInternalPageChangeListener.onPageSelected(item);
//            }
//            completeScroll(false);
            executePrivateMethod(clazz, "completeScroll", new Class<?>[]{boolean.class}, Boolean.FALSE);
            scrollTo(destX, 0);
//            pageScrolled(destX);
            executePrivateMethod(clazz, "pageScrolled", new Class<?>[]{int.class}, destX);
            
            if(onPageChanged == null) {
            	onPageChanged = new OnPageChanged(item);
            	onPageChanged.start();
            } else {
            	onPageChanged.join();
            	onPageChanged = null;
            	onPageChanged = new OnPageChanged(item);
            	onPageChanged.start();
            }
            //new OnPageChanged(item).start();
        }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public class OnPageChanged extends Thread{
		int position = -1;
		OnPageChangeListener mOnPageChangeListener;
		OnPageChangeListener mInternalPageChangeListener;
		
		public OnPageChanged(int position) {
			this.position = position;
			mOnPageChangeListener = (OnPageChangeListener) getPrivateVariable(clazz, "mOnPageChangeListener");
			mInternalPageChangeListener = (OnPageChangeListener) getPrivateVariable(clazz, "mInternalPageChangeListener");
		}
		
		public void setPosition(int position) {
			this.position = position;
		}

		@Override
		public void run() {
			if (mOnPageChangeListener != null) {
	            mOnPageChangeListener.onPageSelected(position);
	            Log.i("pageSelected", "run");
	        }
	        if (mInternalPageChangeListener != null) {
	            mInternalPageChangeListener.onPageSelected(position);
	        }
		}
		
	}
	
	@Override
	protected void onPageScrolled(int position, float offset, int offsetPixels) {
		super.onPageScrolled(position, offset, offsetPixels);
	}
	
	public Object getPrivateVariable(Class<?> clazz, String variableName, Object objToGet){
		Object object = null;
		try {
			Field field = clazz.getDeclaredField(variableName);
			field.setAccessible(true);
			object = field.get(objToGet);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public Object getPrivateVariable(Class<?> clazz, String variableName){
		Object object = null;
		try {
			Field field = clazz.getDeclaredField(variableName);
			field.setAccessible(true);
			object = field.get(this);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public void setPrivateVariable(Class<?> clazz, String variableName, Object objToSet, Object valueToSet){
		try {
			Field field = clazz.getDeclaredField(variableName);
			field.setAccessible(true);
			field.set(objToSet, valueToSet);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void setPrivateVariable(Class<?> clazz, String variableName, Object valueToSet){
		try {
			Field field = clazz.getDeclaredField(variableName);
			field.setAccessible(true);
			field.set(this, valueToSet);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public Object executePrivateMethod(Class<?> clazz,String methodName,Class<?>[] parameterTypes,Object ... args) 
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, SecurityException, NoSuchMethodException{

	    //get declared Method for execution
	    Method pvtMethod = clazz.getDeclaredMethod(methodName,parameterTypes);
	    pvtMethod.setAccessible(true);

	    //invoke loadConfiguration() method and return result Object
	    return pvtMethod.invoke(this,args);
	}

}
