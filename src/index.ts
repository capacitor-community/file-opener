import { registerPlugin } from '@capacitor/core';

import type { FileOpenerPlugin } from './definitions.js';

const FileOpener = registerPlugin<FileOpenerPlugin>('FileOpener');

export * from './definitions.js';
export { FileOpener };
