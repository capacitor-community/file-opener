package com.ryltsov.alex.plugins.file.opener;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import androidx.core.content.FileProvider;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import org.json.JSONObject;

import java.io.File;

@CapacitorPlugin(name = "FileOpener")
public class FileOpenerPlugin extends Plugin {

    @PluginMethod()
    public void open(PluginCall call) {
        String filePath = call.getString("filePath");
        String contentType = call.getString("contentType");
        boolean openWithDefault = call.getBoolean("openWithDefault", true);

        String fileName = "";
        try {
            Uri fileUri = Uri.parse(filePath);
            fileName = fileUri.getPath();
        } catch (Exception e) {
            fileName = filePath;
        }
        File file = new File(fileName);
        if (file.exists()) {
            try {
                if (contentType == null || contentType.trim().equals("")) {
                    contentType = getMimeType(fileName);
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Context context = getActivity().getApplicationContext();
                Uri path = FileProvider.getUriForFile(context, getActivity().getPackageName() + ".file.opener.provider", file);
                intent.setDataAndType(path, contentType);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                if (openWithDefault) {
                    getActivity().startActivity(intent);
                } else {
                    getActivity().startActivity(Intent.createChooser(intent, "Open File in..."));
                }
                call.resolve();
            } catch (android.content.ActivityNotFoundException exception) {
                call.reject("Activity not found: " + exception.getMessage(), "8", exception);
            } catch (Exception exception) {
                call.reject(exception.getLocalizedMessage(), "1", exception);
            }
        } else {
            call.reject("File not found", "9");
        }
    }

    private String getMimeType(String url) {
        String mimeType = "*/*";
        int extensionIndex = url.lastIndexOf('.');
        if (extensionIndex > 0) {
            String extMimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(url.substring(extensionIndex + 1));
            if (extMimeType != null) {
                mimeType = extMimeType;
            }
        }
        return mimeType;
    }
}
