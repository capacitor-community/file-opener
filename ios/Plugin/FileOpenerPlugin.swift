import Foundation
import Capacitor
/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FileOpenerPlugin)
public class FileOpenerPlugin: CAPPlugin, UIDocumentInteractionControllerDelegate {

    var documentInteractionController : UIDocumentInteractionController!
    var call: CAPPluginCall!

    @objc func open(_ call: CAPPluginCall) {
        guard self.bridge != nil else {
            call.reject("Internal error. Bridge not found!", "1")
            return
        }
        guard let filePath = call.options["filePath"] as? String else {
            call.reject("Must provide a filePath", "2")
            return
        }
        let contentType = call.getString("contentType")
        let openWithDefault = call.getBool("openWithDefault") ?? true

        self.call = call

        DispatchQueue.main.async(execute: {

            var fileURL: URL
            let decodedPath = filePath.removingPercentEncoding

            if (filePath == decodedPath) {
                let filePathReplaced = filePath.replacingOccurrences(of: "file://", with: "")
                fileURL = URL(fileURLWithPath: filePathReplaced)
            } else {
                fileURL = URL(string: filePath)!
            }

            let fileManager = FileManager.default
            if !fileManager.fileExists(atPath: fileURL.path) {
                call.reject("File does not exist", "9")
                return
            }

            var uti: String?;
            if let mime = contentType, !mime.isEmpty {
                uti = MimeTypeConverter.mimeToUti(mime)
            } else {
                if fileURL.pathExtension.isEmpty {
                    call.reject("Failed to determine the file type because extension is missing", "2")
                    return;
                }
                uti = MimeTypeConverter.fileExtensionToUti(fileURL.pathExtension)
            }

            guard let uti = uti else {
                call.reject("Failed to determine type of the file to open", "10")
                return
            }


            self.documentInteractionController = UIDocumentInteractionController.init(url: fileURL)
            self.documentInteractionController.uti = uti
            self.documentInteractionController.delegate = self
            
            var wasOpened = false;

            if (openWithDefault) {
                wasOpened = self.documentInteractionController.presentPreview(animated: true)
            } else {
                guard let view = self.bridge?.viewController?.view else {
                    call.reject("Internal error. View not found!", "1")
                    return
                }
                if UIDevice.current.userInterfaceIdiom == .pad {
                    if
                        let chooserPosition = call.options["chooserPosition"] as? JSObject,
                        let x = chooserPosition["x"] as? Float,
                        let y = chooserPosition["y"] as? Float
                    {
                        let rect = CGRect(x: 0, y: 0, width: CGFloat(x), height: CGFloat(y));
                        wasOpened = self.documentInteractionController.presentOpenInMenu(from: rect, in: view, animated: true)
                    } else {
                        let activityViewController = UIActivityViewController(activityItems: [fileURL], applicationActivities: nil)
                        activityViewController.popoverPresentationController?.permittedArrowDirections = UIPopoverArrowDirection(rawValue: 0)
                        activityViewController.popoverPresentationController?.sourceView = view
                        activityViewController.popoverPresentationController?.sourceRect = CGRect(x: view.frame.midX, y: view.frame.midY, width: 0, height: 0)
                        self.bridge?.viewController?.present(activityViewController, animated: true, completion: nil)
                        wasOpened = true
                    }
                } else {
                    let rect = CGRect(x: 0, y: 0, width: view.frame.width, height: view.frame.height);
                    wasOpened = self.documentInteractionController.presentOpenInMenu(from: rect, in: view, animated: true)
                }
            }

            if (wasOpened == false) {
                call.reject("Failed to open the file preview", "8")
                return
            } else {
                if (openWithDefault == false) {
                    self.call.resolve()
                }
            }
        })
    }
    
    public func documentInteractionControllerViewControllerForPreview(_ controller: UIDocumentInteractionController) -> UIViewController {
        var presentingViewController = bridge?.viewController;
        while (presentingViewController?.presentedViewController != nil && ((presentingViewController?.presentedViewController!.isBeingDismissed) != nil)) {
            presentingViewController = presentingViewController?.presentedViewController;
        }
        return presentingViewController!;
    }
    public func documentInteractionControllerDidEndPreview(_ controller: UIDocumentInteractionController) {
        self.call.resolve()
    }

}
