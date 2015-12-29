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
                .setTitle("Delete Restaurand")
                .setMessage("Are you sure you don't like anymore this tasty Restaurand?")
                .setPositiveButton(R.string.delete_warning_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (positiveListener != null) {
                                    positiveListener.onClick(dialog, whichButton);
                                }
                            }
                        }
                )
                .setNegativeButton(R.string.delete_warning_dialog_cancel,
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
