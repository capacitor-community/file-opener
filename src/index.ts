import { registerPlugin } from '@capacitor/core';

import type { FileOpenerPlugin } from './definitions';

const FileOpener = registerPlugin<FileOpenerPlugin>('FileOpener');

export * from './definitions';
export { FileOpener };
