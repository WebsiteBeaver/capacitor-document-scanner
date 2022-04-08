import Foundation
import Capacitor

/**
 * This class contains functions that get called when
 * you use the DocumentScanner JavaScript functions
 */
@available(iOS 13.0, *)
@objc(DocumentScannerPlugin)
public class DocumentScannerPlugin: CAPPlugin {
    
    /** @property  documentScanner the document scanner */
    let documentScanner = DocScanner()
    
    /**
     * start the document scanner and register callbacks
     *
     * @param  call contains JS inputs and lets you return results
     */
    @objc func scanDocument(_ call: CAPPluginCall) {
        // launch the document scanner
        documentScanner.startScan(
            bridge?.viewController,
            successHandler: { (scannedDocumentImages: [String]) in
                // document scan success
                call.resolve([
                    "status": "success",
                    "scannedImages": scannedDocumentImages
                ])
            },
            errorHandler: { (errorMessage: String) in
                // document scan error
                call.reject(errorMessage)
            },
            cancelHandler: {
                // when user cancels document scan
                call.resolve([
                    "status": "cancel"
                ])
            },
            responseType: call.getString("responseType")
        )
    }
}
