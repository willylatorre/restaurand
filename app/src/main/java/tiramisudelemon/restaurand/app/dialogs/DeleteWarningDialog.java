package tiramisudelemon.restaurand.app.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import tiramisudelemon.restaurand.app.R;

/**
 * Created by work on 29/12/15.
 */
public class DeleteWarningDialog extends DialogFragment {


    private DialogInterface.OnClickListener positiveListener;

    public static DeleteWarningDialog newInstance() {
        DeleteWarningDialog f = new DeleteWarningDialog();
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_delete_warning_title)
                .setMessage(R.string.dialog_delete_warning_message)
                .setPositiveButton(R.string.dialog_delete_warning_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (positiveListener != null) {
                                    positiveListener.onClick(dialog, whichButton);
                                }
                            }
                        }
                )
                .setNegativeButton(R.string.dialog_delete_warning_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                            }
                        }
                );

        return builder.create();
    }

    public DeleteWarningDialog setPositiveListener(DialogInterface.OnClickListener positiveListener) {
        this.positiveListener = positiveListener;
        return this;
    }

}
