package com.takescoop.americanwhitewaterandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.takescoop.americanwhitewaterandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainView extends RelativeLayout {
    @BindView(R.id.tab_container) FrameLayout tabContainer;
    @BindView(R.id.main_tab_view) MainTabView mainTabView;
    @BindView(R.id.modal_container) FrameLayout modalContainer;

    public MainView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_main, this);
        onFinishInflate();
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_main, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    public FrameLayout getTabContainer() {
        return tabContainer;
    }

    public FrameLayout getModalContainer() {
        return modalContainer;
    }

    public void showModal() {
        modalContainer.setVisibility(VISIBLE);
    }

    public void hideModal() {
        modalContainer.setVisibility(GONE);
    }

    public void setTabViewVisible(boolean isVisible) {
        if (isVisible) {
            mainTabView.setVisibility(VISIBLE);
        } else {
            mainTabView.setVisibility(GONE);
        }
    }

    public void setTabListener(MainTabView.TabListener tabListener) {
        mainTabView.setTabListener(tabListener);
    }
}
