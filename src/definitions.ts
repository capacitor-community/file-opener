export interface FileOpenerOptions {
  filePath: string;
  contentType: string;
  openWithDefault?: boolean;
}

export interface FileOpenerPlugin {
  open(options: FileOpenerOptions): Promise<void>;
}
