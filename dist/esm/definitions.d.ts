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
     * MIME type
     *
     * @since 1.0.0
     */
    contentType: string;
    /**
     * Use the default Android platform chooser.
     * If false, it will show "Open File in.." title of the chooser dialog, the system will always present the chooser dialog
     * even if the user has chosen a default one and if no activity is found to handle the file, the system will still
     * present a dialog with the specified title and an error message No application can perform this action
     *
     * @since 1.0.0
     */
    openWithDefault?: boolean;
}
export interface FileOpenerPlugin {
    /**
     * Method to open a file.
     *
     * @since 1.0.0
     */
    open(options: FileOpenerOptions): Promise<void>;
}
