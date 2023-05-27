package com.websitebeaver.capacitordocumentscanner;

import androidx.activity.result.ActivityResult;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.websitebeaver.documentscanner.DocumentScanner;
import com.websitebeaver.documentscanner.constants.DocumentScannerExtra;
import java.util.ArrayList;

/**
 * This class is used to start a document scan. It accepts parameters used to customize
 * the document scan, and callback parameters.
 */
@CapacitorPlugin(name = "DocumentScanner")
public class DocumentScannerPlugin extends Plugin {

    /**
     * @property documentScanner the document scanner
     */
    DocumentScanner documentScanner;

    /**
     * start the document scanner and register callbacks
     *
     * @param call contains JS inputs and lets you return results
     */
    @PluginMethod
    public void scanDocument(PluginCall call) {
        JSObject response = new JSObject();

        // create a document scanner
        documentScanner = new DocumentScanner(
            getActivity(),
            (ArrayList<String> documentScanResults) -> {
                // document scan success
                response.put(
                    "scannedImages",
                    new JSArray(documentScanResults)
                );
                response.put("status", "success");
                call.resolve(response);
                return null;
            },
            (String errorMessage) -> {
                // document scan error
                call.reject(errorMessage);
                return null;
            },
            () -> {
                // when user cancels document scan
                response.put("status", "cancel");
                call.resolve(response);
                return null;
            },
            call.getString("responseType"),
            call.getBoolean(DocumentScannerExtra.EXTRA_LET_USER_ADJUST_CROP),
            call.getInt(DocumentScannerExtra.EXTRA_MAX_NUM_DOCUMENTS),
            call.getInt(DocumentScannerExtra.EXTRA_CROPPED_IMAGE_QUALITY)
        );

        // launch the document scanner
        startActivityForResult(
            call,
            documentScanner.createDocumentScanIntent(),
            "documentScanResult"
        );
    }

    @ActivityCallback
    private void documentScanResult(PluginCall call, ActivityResult result) {
        // trigger callbacks (success, cancel, error)
        documentScanner.handleDocumentScanIntentResult(result);
    }
}