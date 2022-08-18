import { WebPlugin } from '@capacitor/core';

import type { FileOpenerPlugin } from './definitions';

export class FileOpenerWeb extends WebPlugin implements FileOpenerPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
