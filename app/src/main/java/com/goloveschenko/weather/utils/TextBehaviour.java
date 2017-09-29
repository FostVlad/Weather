package com.goloveschenko.weather.utils;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class TextBehaviour extends CoordinatorLayout.Behavior<FrameLayout> {
    private TextView location;
    private TextView details;
    private TextView weatherIcon;
    private TextView updateTime;
    private TextView temp;
    private View dividerTop;
    private RecyclerView forecastDay;
    private View dividerBottom;

    private float startIconPos;
    private boolean isInitFields;

    public TextBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FrameLayout child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FrameLayout child, View dependency) {
        if (!initFields(child)) {
            return false;
        }

        //TOOLBAR HEIGHT
        AppBarLayout appBarLayout = findViewByClass(parent, AppBarLayout.class);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewByClass(appBarLayout, CollapsingToolbarLayout.class);
        Toolbar toolbar = findViewByClass(collapsingToolbarLayout, Toolbar.class);
        CollapsingToolbarLayout.LayoutParams layoutParams = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.height = location.getHeight() + details.getHeight() + dividerTop.getHeight() + forecastDay.getHeight() + dividerBottom.getHeight() + 10; //TODO: fix padding
        toolbar.setLayoutParams(layoutParams);

        float visibility = weatherIcon.getY() / (int) startIconPos;

        weatherIcon.setY(dependency.getY() - forecastDay.getHeight() - dividerBottom.getHeight() - temp.getHeight() - weatherIcon.getHeight());
        weatherIcon.setAlpha(visibility);
        updateTime.setY(dependency.getY() - forecastDay.getHeight() - dividerBottom.getHeight() - updateTime.getHeight());
        updateTime.setAlpha(visibility);
        temp.setY(dependency.getY() - forecastDay.getHeight() - dividerBottom.getHeight() - temp.getHeight());
        temp.setAlpha(visibility);

        dividerTop.setY(dependency.getY() - forecastDay.getHeight() - dividerBottom.getHeight());
        forecastDay.setY(dependency.getY() - forecastDay.getHeight());
        dividerBottom.setY(dependency.getY());

        updateVisibility(child);

        return true;
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
        if (!isInitFields) {
            ConstraintLayout layout = findViewByClass(child, ConstraintLayout.class);
            if (layout != null && layout.getChildCount() != 0) {
                location = (TextView) layout.getChildAt(0);
                details = (TextView) layout.getChildAt(1);
                weatherIcon = (TextView) layout.getChildAt(2);
                startIconPos = weatherIcon.getY();
                updateTime = (TextView) layout.getChildAt(3);
                temp = (TextView) layout.getChildAt(4);

                dividerTop = layout.getChildAt(5);
                forecastDay = (RecyclerView) layout.getChildAt(6);
                dividerBottom = layout.getChildAt(7);

                isInitFields = true;
            } else {
                isInitFields = false;
            }
        }
        return isInitFields;
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
