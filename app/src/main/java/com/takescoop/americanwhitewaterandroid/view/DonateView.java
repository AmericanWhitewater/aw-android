package com.takescoop.americanwhitewaterandroid.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cocosw.bottomsheet.BottomSheet;
import com.takescoop.americanwhitewaterandroid.R;
import com.takescoop.americanwhitewaterandroid.utility.AWIntent;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.takescoop.americanwhitewaterandroid.model.api.Urls.DONATE_URL;
import static com.takescoop.americanwhitewaterandroid.model.api.Urls.JOIN_URL;

public class DonateView extends LinearLayout {

    public DonateView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_donate, this);
        onFinishInflate();
    }

    public DonateView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_donate, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    @OnClick(R.id.donate_button)
    protected void onDonateClick() {

        getDonateMenu((Activity) getContext()).show();
    }

    public static BottomSheet getDonateMenu(Activity activity) {
        return new BottomSheet.Builder(activity).sheet(R.menu.donate_menu).listener((dialog, menuItemId) -> {
            switch (menuItemId) {
                case R.id.donate:
                    AWIntent.goToUrl(activity, DONATE_URL);
                    break;
                case R.id.join:
                    AWIntent.goToUrl(activity, JOIN_URL);
                    break;
            }
        }).build();
    }
}
