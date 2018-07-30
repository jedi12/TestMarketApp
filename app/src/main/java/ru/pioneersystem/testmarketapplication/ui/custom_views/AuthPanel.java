package ru.pioneersystem.testmarketapplication.ui.custom_views;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.pioneersystem.testmarketapplication.R;

public class AuthPanel extends LinearLayout {
    public static final String TAG = "AuthPanel";
    public static final int LOGIN_STATE = 0;
    public static final int IDLE_STATE = 1;
    private int mCustomState = IDLE_STATE;

    @BindView(R.id.auth_card) CardView mAuthCard;
    @BindView(R.id.show_catalog_btn) Button mShowCatalogBtn;
    @BindView(R.id.login_btn) Button mLoginBtn;
    @BindView(R.id.login_email_et) EditText mEmailEt;
    @BindView(R.id.login_password_et) EditText mPasswordEt;

    public AuthPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        showViewFromState();
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.state = mCustomState;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setCustomState(savedState.state);
    }

    public void setCustomState(int state) {
        mCustomState = state;
        showViewFromState();
    }

    private void showLoginState() {
        mAuthCard.setVisibility(VISIBLE);
        mShowCatalogBtn.setVisibility(GONE);
    }

    private void showIdleState() {
        mAuthCard.setVisibility(GONE);
        mShowCatalogBtn.setVisibility(VISIBLE);
    }

    private void showViewFromState() {
        if (mCustomState == LOGIN_STATE) {
            showLoginState();
        } else {
            showIdleState();
        }
    }

    public String getUserEmail() {
        return String.valueOf(mEmailEt.getText());
    }

    public String getUserPassword() {
        return String.valueOf(mPasswordEt.getText());
    }

    public boolean isIdle() {
        return mCustomState == IDLE_STATE;
    }

    static class SavedState extends BaseSavedState {
        private int state;

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel in) {
            super(in);
            state = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(state);
        }
    }
}
