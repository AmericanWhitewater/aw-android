package org.americanwhitewater.americanwhitewaterandroid.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cocosw.bottomsheet.BottomSheet;
import org.americanwhitewater.americanwhitewaterandroid.R;
import org.americanwhitewater.americanwhitewaterandroid.utility.AWIntent;

import org.americanwhitewater.americanwhitewaterandroid.model.api.Urls;

import butterknife.ButterKnife;
import butterknife.OnClick;

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
                    AWIntent.goToUrl(activity, Urls.DONATE_URL);
                    break;
                case R.id.join:
                    AWIntent.goToUrl(activity, Urls.JOIN_URL);
                    break;
            }
        }).build();
    }
}
