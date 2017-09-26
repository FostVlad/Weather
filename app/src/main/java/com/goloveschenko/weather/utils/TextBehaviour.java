package com.goloveschenko.weather.utils;

import android.content.Context;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class TextBehaviour extends CoordinatorLayout.Behavior<FrameLayout> {
    private Context context;

    private TextView location;
    private TextView updateTime;
    private TextView weatherIcon;
    private TextView details;
    private TextView humidity;
    private TextView pressure;
    private TextView temp;

    private View dividerTop;
    private RecyclerView forecastDay;
    private View dividerBottom;

    private int startYPosition;
    private int finalYPosition;
    private int startHeight;
    private float startToolbarPosition;

    private float startIconPos;
    private float startUpdateTimePos;
    private float startHumidityPos;
    private float startPressurePos;

    public TextBehaviour(Context context, AttributeSet attrs) {
        this.context = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FrameLayout child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FrameLayout child, View dependency) {
        //return if we have non initialized fragment
        if (!initFields(child)) {
            return false;
        }
        initProperties(child, dependency);

        //TOOLBAR HEIGHT
        AppBarLayout appBarLayout = findViewByClass(parent, AppBarLayout.class);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewByClass(appBarLayout, CollapsingToolbarLayout.class);
        Toolbar toolbar = findViewByClass(collapsingToolbarLayout, Toolbar.class);
        CollapsingToolbarLayout.LayoutParams layoutParams = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.height = location.getHeight() + details.getHeight() + temp.getHeight() + dividerTop.getHeight() + forecastDay.getHeight() + dividerBottom.getHeight();
        toolbar.setLayoutParams(layoutParams);

        final int maxScrollDistance = (int) startToolbarPosition;
        float expandedPercentageFactor = dependency.getY() / maxScrollDistance;

        float distanceYToSubtract = ((startYPosition - finalYPosition) * (1f - expandedPercentageFactor)) + child.getHeight();

        //updateTime.setAlpha(expandedPercentageFactor);
//        updateTime.setAlpha(updateTime.getY() / (int) startUpdateTimePos);
        updateTime.setAlpha(weatherIcon.getY() / (int) startIconPos);

        //HIDE ICON
        weatherIcon.setY(dependency.getY() - forecastDay.getHeight() - dividerTop.getHeight() - temp.getHeight() - pressure.getHeight() - humidity.getHeight() - details.getHeight() - weatherIcon.getHeight());
        weatherIcon.setAlpha(weatherIcon.getY() / (int) startIconPos);

        details.setY(dependency.getY() - forecastDay.getHeight() - dividerTop.getHeight() - temp.getHeight() - pressure.getHeight() - humidity.getHeight() - details.getHeight());

        humidity.setY(dependency.getY() - forecastDay.getHeight() - dividerTop.getHeight() - temp.getHeight() - pressure.getHeight() - humidity.getHeight());
//        humidity.setAlpha(expandedPercentageFactor);
//        humidity.setAlpha(humidity.getY()/ (int) startHumidityPos);

        pressure.setY(dependency.getY() - forecastDay.getHeight() - dividerTop.getHeight() - temp.getHeight() - pressure.getHeight());
//        pressure.setAlpha(expandedPercentageFactor);
//        pressure.setAlpha(pressure.getY() / (int) startPressurePos);

        temp.setY(dependency.getY() - forecastDay.getHeight() - dividerTop.getHeight() - temp.getHeight());

        dividerTop.setY(dependency.getY() - forecastDay.getHeight() - dividerTop.getHeight());
        forecastDay.setY(dependency.getY() - forecastDay.getHeight());
        dividerBottom.setY(dependency.getY());

        updateVisibility(child);

        return true;
    }

    private int getScreenHeight() {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    private void updateVisibility(FrameLayout child) {
        ConstraintLayout layout = findViewByClass(child, ConstraintLayout.class);
        List<TextView> list = findViewsByClass(layout, TextView.class);
        for (TextView textView : list) {
            if (textView.getAlpha() <= 0) {
                textView.setVisibility(View.INVISIBLE);
            } else {
                textView.setVisibility(View.VISIBLE);
            }
        }
    }

    private boolean initFields(FrameLayout child) {
        ConstraintLayout layout = findViewByClass(child, ConstraintLayout.class);
        if (layout != null && layout.getChildCount() != 0) {
            location = (TextView) layout.getChildAt(0);
            updateTime = (TextView) layout.getChildAt(1);
            weatherIcon = (TextView) layout.getChildAt(2);
            details = (TextView) layout.getChildAt(3);
            humidity = (TextView) layout.getChildAt(4);
            pressure = (TextView) layout.getChildAt(5);
            temp = (TextView) layout.getChildAt(6);

            dividerTop = (View) layout.getChildAt(7);
            forecastDay = (RecyclerView) layout.getChildAt(8);
            dividerBottom = (View) layout.getChildAt(9);

            return true;
        } else {
            return false;
        }
    }

    private void initProperties(FrameLayout child, View dependency) {
        if (startYPosition == 0) {
            startYPosition = (int) (dependency.getY());
        }

        if (finalYPosition == 0) {
            finalYPosition = (dependency.getHeight() / 2);
        }

        if (startHeight == 0) {
            startHeight = child.getHeight();
        }

        if (startToolbarPosition == 0) {
            startToolbarPosition = dependency.getY();
        }

        if (startIconPos == 0) {
            startIconPos = weatherIcon.getY();
        }

        if (startUpdateTimePos == 0) {
            startUpdateTimePos = updateTime.getY();
        }

        if (startHumidityPos == 0) {
            startHumidityPos = humidity.getY();
        }

        if (startPressurePos == 0) {
            startPressurePos = pressure.getY();
        }
    }

    private <T extends View> T findViewByClass(ViewGroup viewGroup, Class currentClass){
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (currentClass.isInstance(viewGroup.getChildAt(i))) {
                return (T) viewGroup.getChildAt(i);
            }
        }
        return null;
    }

    private <T extends View> List<T> findViewsByClass(ViewGroup viewGroup, Class currentClass){
        List<T> views = new LinkedList<>();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (currentClass.isInstance(viewGroup.getChildAt(i))) {
                views.add((T) viewGroup.getChildAt(i));
            }
        }
        return views;
    }
}
