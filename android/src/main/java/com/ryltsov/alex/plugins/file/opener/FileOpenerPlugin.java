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
import android.database.Cursor;
import android.content.ContentResolver;

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
        Uri fileUri = null;
        try {
            fileUri = Uri.parse(filePath);
            fileName = fileUri.getPath();
        } catch (Exception e) {
            fileName = filePath;
        }

        if (filePath.startsWith("content://")) {
            ContentResolver contentResolver = getActivity().getContentResolver();
            try (Cursor cursor = contentResolver.query(fileUri, null, null, null, null)) {
                if (cursor != null && cursor.getCount() > 0) {
                    if (contentType == null || contentType.trim().equals("")) {
                        contentType = getMimeType(fileUri);
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(fileUri, contentType);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    if (openWithDefault) {
                        getActivity().startActivity(intent);
                    } else {
                        getActivity().startActivity(Intent.createChooser(intent, "Open File in..."));
                    }
                    call.resolve();
                } else {
                    call.reject("File not found", "9");
                }
            } catch (android.content.ActivityNotFoundException exception) {
                call.reject("Activity not found: " + exception.getMessage(), "8", exception);
            } catch (Exception exception) {
                call.reject(exception.getLocalizedMessage(), "1", exception);
            }
        } else {
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
    }

    private String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    private String getMimeType(Uri uri) {
        String type = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = getActivity().getContentResolver();
            type = cr.getType(uri);
        } else {
            String extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            }
        }
        return type;
    }
}
