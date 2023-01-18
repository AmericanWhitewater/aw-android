package org.americanwhitewater.americanwhitewaterandroid.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.americanwhitewater.americanwhitewaterandroid.R;
import org.americanwhitewater.americanwhitewaterandroid.utility.AWIntent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeamView extends ScrollView {
    private static final int ABOUT_MAX_LINES = 4;
    private final TeamViewListener listener;

    @BindView(R.id.greg_layout) LinearLayout gregLayout;
    @BindView(R.id.about_greg) TextView aboutGreg;

    public interface TeamViewListener {
        void onClose();
    }

    public TeamView(Context context, TeamViewListener listener) {
        super(context);
        this.listener = listener;

        LayoutInflater.from(context).inflate(R.layout.view_team, this);
        onFinishInflate();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);
    }

    @OnClick(R.id.about_greg)
    protected void onAboutGregClicked() {
        if (aboutGreg.getMaxLines() == ABOUT_MAX_LINES) {
            aboutGreg.setMaxLines(Integer.MAX_VALUE);
        } else {
            aboutGreg.setMaxLines(ABOUT_MAX_LINES);
        }
    }

    @OnClick(R.id.close_tap_target)
    protected void onClose() {
        listener.onClose();
    }
}
