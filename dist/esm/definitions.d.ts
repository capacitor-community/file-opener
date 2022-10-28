/**
 * file open method options
 *
 * @since 1.0.0
 */
export interface FileOpenerOptions {
    /**
     * file path
     *
     * @since 1.0.0
     */
    filePath: string;
    /**
     * MIME type (optional)
     *
     * @since 1.0.0
     */
    contentType?: string;
    /**
     * Use the default platform chooser, if true, otherwise:
     * On Android: it will show "Open File in.." title of the chooser dialog, the system will always present the chooser dialog
     * even if the user has chosen a default one and if no activity is found to handle the file, the system will still
     * present a dialog with the specified title and an error message No application can perform this action
     * On iOS: it will presents a menu restricted to a list of apps capable of opening the current document.
     * This determination is made based on the document type and on the document types supported by the installed apps.
     * To support one or more document types, an app must register those types in its Info.plist file
     * using the CFBundleDocumentTypes key.
     *
     * (optional) default value is true
     *
     * @since 1.0.0
     */
    openWithDefault?: boolean;
    /**
     * (iOS only; iPad only) Position to anchor the chooser (ShareSheet) menu in the view (optional)
     * Please note that this is applicable only when the application runs on iPad and when
     * openWithDefault is false, otherwise this is ignored
     *
     * @since 1.0.3
     */
    chooserPosition?: {
        x: number;
        y: number;
    };
}
export interface FileOpenerPlugin {
    /**
     * Method to open a file.
     *
     * @since 1.0.0
     */
    open(options: FileOpenerOptions): Promise<void>;
}
