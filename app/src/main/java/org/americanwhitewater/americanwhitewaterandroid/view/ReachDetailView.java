package org.americanwhitewater.americanwhitewaterandroid.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import org.americanwhitewater.americanwhitewaterandroid.AWProvider;
import org.americanwhitewater.americanwhitewaterandroid.R;
import org.americanwhitewater.americanwhitewaterandroid.model.Gage;
import org.americanwhitewater.americanwhitewaterandroid.model.Reach;
import org.americanwhitewater.americanwhitewaterandroid.model.api.AWApi;
import org.americanwhitewater.americanwhitewaterandroid.model.api.Urls;
import org.americanwhitewater.americanwhitewaterandroid.utility.AWIntent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReachDetailView extends LinearLayout {
    private static final String TAG = ReachDetailView.class.getSimpleName();

    private static final int DESCRIPTION_MAX_LINES = 4;
    private static final AWApi awApi = AWProvider.Instance.awApi();

    private Reach reach;
    private ReachDetailListener listener;

    @BindView(R.id.gage_cell) GageCell gageCell;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.description_layout) RelativeLayout descriptionLayout;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.read_more) TextView readMore;
    @BindView(R.id.difficulty) TextView difficulty;
    @BindView(R.id.length) TextView length;
    @BindView(R.id.gradient) TextView gradient;

    public interface ReachDetailListener {
        void onGageSelected(Gage gage);
    }

    public ReachDetailView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.view_reach_details, this);
    }

    public ReachDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_reach_details, this);
        onFinishInflate();
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(this);

        description.setMaxLines(DESCRIPTION_MAX_LINES);
    }

    public void setListener(ReachDetailListener listener) {
        this.listener = listener;
    }

    public void showReach(Reach reach) {
        this.reach = reach;

        if (reach.getPhotoId() != null && reach.getPhotoId() != 0) {
            String photoUrl = awApi.getPhotoUrl(reach.getPhotoId());
            Picasso.with(getContext()).load(photoUrl).into(image);
            image.setVisibility(VISIBLE);
        }

        gageCell.showReach(reach);
        if (reach.getDescription() != null) {
            description.setText(Html.fromHtml(reach.getDescription()));
        }
        if (description.getLineCount() > DESCRIPTION_MAX_LINES) {
            readMore.setVisibility(VISIBLE);
            descriptionLayout.setOnClickListener(getOnDescriptionLayoutClickListener());
        } else {
            readMore.setVisibility(GONE);
        }

        difficulty.setText(String.format(getContext().getString(R.string.reach_detail_difficulty), reach.getDifficulty()));
        if (reach.getLength() != null) {
            length.setText(String.format(getContext().getString(R.string.reach_detail_length), reach.getLength()));
        } else {
            length.setText("-");
        }

        if (reach.getAvgGradient() != null) {
            gradient.setText(String.format(getContext().getString(R.string.reach_detail_gradient), reach.getAvgGradient().toString()));
        } else {
            gradient.setText("-");
        }
    }

    protected OnClickListener getOnDescriptionLayoutClickListener() {
        return v -> {
            if (description.getMaxLines() == DESCRIPTION_MAX_LINES) {
                description.setMaxLines(Integer.MAX_VALUE);
                readMore.setVisibility(GONE);
            } else {
                description.setMaxLines(DESCRIPTION_MAX_LINES);
                readMore.setVisibility(VISIBLE);
            }
        };
    }

    @OnClick(R.id.gage_cell)
    protected void onGageClick() {
        if (listener != null) {
            listener.onGageSelected(reach.getGage());
        }
    }

    @OnClick(R.id.website_link)
    protected void onWebsiteClick() {
        if (reach != null) {
            String url = String.format(Urls.REACH_DETAIL_URL, reach.getId());
            AWIntent.goToUrl(getContext(), url);
        }
    }
}
