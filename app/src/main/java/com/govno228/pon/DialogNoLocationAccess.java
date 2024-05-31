    package com.govno228.pon;

    import android.app.AlertDialog;
    import android.app.Dialog;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.WindowManager;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.DialogFragment;

    public class DialogNoLocationAccess extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Нет разрешения").setMessage("Нет разрешения на доступ к местоположению");
            builder.setPositiveButton("Перейти в настройки", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + Data.packageName)); // Replace with your app's package name
                    startActivity(intent);
                    System.exit(0);
                }
            });
            builder.setNegativeButton("Выход", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            AlertDialog dialog = builder.create();
            return dialog;
        }
    }
