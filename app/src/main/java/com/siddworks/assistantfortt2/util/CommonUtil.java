package com.siddworks.assistantfortt2.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.UiThread;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.siddworks.assistantfortt2.R;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

	private static boolean isLoggingEnabled = false;
	private static final String TAG = "CommonUtil";

	public final static String VALIDATE_NOT_EMPTY = "validate_not_empty";
	public final static String VALIDATE_EMAIL_ADDRESS = "validate_email_address";
	public final static String VALIDATE_ERROR_INVALID_EMAIL = "Please enter a valid email address";
	public final static String VALIDATE_ERROR_MANDATORY = "Please enter ";
	public final static String VALIDATE_ERROR_MISMATCH = " do not match";

	public static String getText(EditText et) {
		if (et.getText() != null) {
			return et.getText().toString();
		}
		return null;
	}

	public static void showToast(Context mContext, String text) {
		Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
	}

	public static void clearError(EditText et) {
		et.setError(null);
	}

	public static void setError(EditText et, String error) {
		et.setError(error);
	}

	public static boolean isValid(EditText et, String validation) {
		if (validation.equals(VALIDATE_NOT_EMPTY)) {
			String value = getText(et);
			if (value != null && !TextUtils.isEmpty(value)) {
				return true;
			} else {
				et.requestFocus();
				return false;
			}
		}
		if (validation.equals(VALIDATE_EMAIL_ADDRESS)) {
			String value = getText(et);
			if (value != null && !TextUtils.isEmpty(value)) {
				if (isEmailValid(value)) {
					return true;
				} else {
					et.requestFocus();
					return false;
				}
			} else {
				et.requestFocus();
				return false;
			}
		}

		return false;
	}

	public static boolean validateAndSetErrorIfInvalid(EditText et, String validation, String error) {
		if (isValid(et, validation)) {
			clearError(et);
			return true;
		} else {
			setError(et, error);
			return false;
		}
	}

	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static void setEnabled(View et, boolean isEnabled) {
		et.setEnabled(isEnabled);
	}

	public static void setVisiblity(View view, int visibility) {
		view.setVisibility(visibility);
	}

	public static void fadeOutAndDisableView(final View view) {
		Animation fadeOut = new AlphaAnimation(1, (float) 0.5);
		fadeOut.setInterpolator(new AccelerateInterpolator());
		fadeOut.setDuration(500);

		fadeOut.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.VISIBLE);
				view.setAlpha((float) 0.5);
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationStart(Animation animation) {
			}
		});

		view.startAnimation(fadeOut);
	}

	public static void fadeOutAndHideView(final View view) {
		Animation fadeOut = new AlphaAnimation(1, 0);
		fadeOut.setInterpolator(new AccelerateInterpolator());
		fadeOut.setDuration(200);

		fadeOut.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.GONE);
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationStart(Animation animation) {
			}
		});

		view.startAnimation(fadeOut);
	}

	public static void fadeInAndShowView(final View view) {
		Animation fadeOut = new AlphaAnimation(1, (float) 0.5);
		fadeOut.setInterpolator(new AccelerateInterpolator());
		fadeOut.setDuration(200);

		fadeOut.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.VISIBLE);
				view.setAlpha(1);
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationStart(Animation animation) {
			}
		});

		view.startAnimation(fadeOut);
	}

	@UiThread
	public static void showAlertOneButton(final String requestTag, final Context mContext, final String heading, final String message) {
//		System.out.println("showAlertOneButton heading:"+heading+", message:"+message);
		if (mContext != null && !((Activity) mContext).isFinishing()) {
			new MaterialDialog.Builder(mContext)
					.title(heading)
					.content(message)
					.positiveText("Close")
					.positiveColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
					.show();
		}
	}

	public static void startActivity(Context context,
									 Class myClass) {
		Intent intent = new Intent(context, myClass);
		context.startActivity(intent);
	}

	public static String getText(Spinner spinner) {
		if(spinner != null && spinner.getSelectedItem() instanceof String)
		{
			return (String) spinner.getSelectedItem();
		}
		return null;
	}

	public static String convertMilliSecondToTime(
			long milliSeconds) {
		if (milliSeconds < 0) {
			throw new IllegalArgumentException("Duration must be greater than zero!");
		}

		long hours = TimeUnit.MILLISECONDS.toHours(milliSeconds);
		milliSeconds -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(milliSeconds);
		milliSeconds -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(milliSeconds);

		StringBuilder sb = new StringBuilder(64);
		if (hours > 0) {
			if (hours <= 9) {
				sb.append("0");
			}
			sb.append(hours);
			sb.append(":");
		}
		if (minutes <= 9) {
			sb.append("0");
		}
		sb.append(minutes);
		sb.append(":");
		if (seconds <= 9) {
			sb.append("0");
		}
		sb.append(seconds);
		return sb.toString();
	}

	public static void clearText(EditText et) {
		if (et != null) {
			et.setText("");
		}
	}

	public static int dpToPx(int dp) {
		return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}

	public static int pxToDp(int px) {
		return (int) (px / Resources.getSystem().getDisplayMetrics().density);
	}

	public static MaterialDialog.Builder getDialogBuilder(final String requestTag, final Context mContext) {
		return new MaterialDialog.Builder(mContext)
				.contentColor(Color.BLACK)
				.titleColorRes(R.color.colorPrimaryDark)
				.backgroundColorRes(R.color.white);
	}

	public static void setText(View row, int id, String artifactName) {
		if(row != null) {
			TextView title = (TextView) row.findViewById(id);
			title.setText(artifactName);
		}
	}

	public static void showSnackbar(View view, String message) {
		Snackbar snackbar = Snackbar
				.make(view, message, Snackbar.LENGTH_LONG);

		snackbar.show();
	}
}








