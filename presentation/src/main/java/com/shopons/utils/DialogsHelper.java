package com.shopons.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.shopons.R;

/**
 * Created by deepali on 8/3/16.
 */
public class DialogsHelper {

    public static MaterialDialog.Builder getDialogBuilder(final Activity activity, final String header,
                                                          final String content, final String okText,
                                                          final String cancelText) {

        final MaterialDialog.Builder builder = new MaterialDialog.Builder(activity)
                .autoDismiss(false)
                .cancelable(false)
                .title(header)
                .content(content)
                .positiveColor(activity.getResources().getColor(R.color.colorPrimary))
                .positiveText(okText)
                .backgroundColor(activity.getResources().getColor(R.color.white))
                .typeface(FontUtils.getFonts(activity.getBaseContext(), "Sansation_Regular.ttf"), FontUtils.getFonts(activity.getBaseContext(), "Sansation_Light.ttf"));
        ;

        if (!cancelText.equals("")) {
            builder.negativeText(cancelText);
            builder.negativeColor(activity.getResources().getColor(R.color.black));
        }

        return builder;
    }

    public static MaterialDialog.Builder getCustomViewDialog(final Activity activity, final String header,
                                                             final int layoutId, final String okText) {

        return new MaterialDialog.Builder(activity)
                .autoDismiss(false)
                .title(header)
                .customView(layoutId, true)
                .positiveColor(activity.getResources().getColor(R.color.colorPrimary))
                .positiveText(okText)
                .typeface(FontUtils.getFonts(activity.getBaseContext(),"Arcon-Regular.otf"),FontUtils.getFonts(activity.getBaseContext(),"Arcon-Rounded-Regular.otf"));

    }

    public static String getFormattedError(final String []errorString) {
        final StringBuilder builder = new StringBuilder();
        for (String error : errorString) {
            error = error.replace(":", " - ");
            builder.append(error);
            builder.append("\n");
        }
        return builder.toString();
    }

    public static String getFormattedError(final String errorString) {
        if (errorString.contains("No address associated with hostname")
                || errorString.contains("NoConnectionError")
                || errorString.contains("ConnectionException")) {
            return "Oops! Looks like you are not connected to internet!!";
        }
        if (errorString.contains("TimeoutError")
                || errorString.contains("Broken")
                || errorString.contains("Connection reset")
                || errorString.contains("Handshake")) {
            return "Oops! Timed out while connecting to server! Try Again! ";
        }
        if(errorString.contains("duplicate") && errorString.contains("phone_number")){
            return "Phone number already present!";
        }
        if(errorString.contains("duplicate") && errorString.contains("email")){
            return "Email already present!";
        }
        final StringBuilder builder = new StringBuilder();
        final String[] errors = errorString.split(";");
        for (String error : errors) {
            error = error.replace(":", " - ");
            builder.append(error);
            builder.append("\n");
        }
        return builder.toString();
    }

    public static  void showErrorDialog(final Activity activity, final Throwable throwable) {
        final MaterialDialog dialog =DialogsHelper.getDialogBuilder(activity, activity.getString(R.string.error),
                DialogsHelper.getFormattedError(throwable.getMessage()),
                activity.getString(R.string.ok), "")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                }).cancelable(false).build();
        dialog.setTypeface(dialog.getTitleView(),FontUtils.getFonts(dialog.getContext(),"Arcon-Regular.otf"));
        dialog.show();
    }


    public  static void showErrorDialog(final Activity activity, final String message) {
        final MaterialDialog dialog = DialogsHelper.getDialogBuilder(activity, activity.getString(R.string.error),
                DialogsHelper.getFormattedError(message),
                activity.getString(R.string.ok), "")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                }).cancelable(false).build();
        dialog.setTypeface(dialog.getTitleView(),FontUtils.getFonts(dialog.getContext(),"Arcon-Regular.otf"));
        dialog.show();
    }

    /**
     * Helper function which will show the Success.
     *
     * @param title
     */
    public static void showSuccessDialog(final Activity activity, final String title) {
        final MaterialDialog dialog = DialogsHelper.getDialogBuilder(activity, activity.getString(R.string.success),
                DialogsHelper.getFormattedError(title),
                activity.getString(R.string.ok), "")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                }).build();
        dialog.setTypeface(dialog.getTitleView(),FontUtils.getFonts(dialog.getContext(),"Sansation_Bold.ttf"));
        dialog.show();
    }

    public  static void showDialog(final Activity activity, final String title, final String content) {
        final MaterialDialog dialog = DialogsHelper.getDialogBuilder(activity, title,
                content,
                activity.getString(R.string.ok), "")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                }).build();
        dialog.setTypeface(dialog.getTitleView(),FontUtils.getFonts(dialog.getContext(),"Sansation_Bold.ttf"));
        dialog.show();
    }

    /**
     * Helper function to show custom dialog
     *
     * @param layoutId
     * @param title
     */
    public static  void showCustomDialog(final Activity activity, final int layoutId, final String title) {
        final MaterialDialog dialog = DialogsHelper.getCustomViewDialog(activity,
                title,
                layoutId,
                activity.getString(R.string.ok))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                })
                .build();
        dialog.show();
    }


    /**
     * Helper function to show custom dialog
     *
     * @param layoutId
     * @param title
     */
    public static  void showCustomInteractiveDialog(final Activity activity, final int layoutId, final String title,
                                                    final String contentText,
                                                    final MaterialDialog.SingleButtonCallback listener) {
        final MaterialDialog dialog = DialogsHelper.getCustomViewDialog(activity,
                title,
                layoutId,
                activity.getString(R.string.ok))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        listener.onClick(materialDialog, dialogAction);
                    }
                })
                .build();
        final TextView content = (TextView) dialog.getCustomView().findViewById(R.id.content);
        content.setText(contentText);
        dialog.show();
    }


    /**
     * Helper function to show custom dialog
     *
     * @param title
     */
    public static void showCustomInteractiveDialog(final Activity activity, final String title,
                                                   final String contentText,
                                                   final MaterialDialog.SingleButtonCallback listener) {
        final MaterialDialog dialog = DialogsHelper.getDialogBuilder(activity, title,
                contentText,
                activity.getString(R.string.ok), "")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        listener.onClick(materialDialog, dialogAction);
                    }
                }).build();
        dialog.show();
    }

    /**
     * Helper function to handle the interactive dialog
     *
     * @param okayText
     * @param cancelText
     * @param title
     * @param content
     * @param listeners
     */
    public static void showInteractiveDialog(final Activity activity, final String okayText, final String cancelText,
                                             final String title, final String content,
                                             final MaterialDialog.SingleButtonCallback... listeners) {

        final MaterialDialog.Builder builder = DialogsHelper.getDialogBuilder(activity, title,
                content,
                okayText,
                cancelText);

        if (listeners == null) {
            builder.build().show();
            return;
        }
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                listeners[0].onClick(materialDialog, dialogAction);
            }
        });
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                listeners[1].onClick(materialDialog, dialogAction);
            }
        });
        final MaterialDialog dialog = builder.build();
        dialog.show();
    }
}
