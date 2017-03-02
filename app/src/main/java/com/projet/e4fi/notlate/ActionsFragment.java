package com.projet.e4fi.notlate;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by Axel on 01/03/2017.
 */

public class ActionsFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog created = super.onCreateDialog(savedInstanceState);
        created.setTitle(R.string.actionListTitle);
        created.getLayoutInflater().inflate(R.layout.set_actions_fragment, null, false);
        return created;

    }
}
