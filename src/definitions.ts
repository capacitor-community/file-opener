export interface FileOpenerPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
