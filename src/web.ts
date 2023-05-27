import { WebPlugin } from '@capacitor/core';

import type { 
  DocumentScannerPlugin, 
  ScanDocumentOptions, 
  ScanDocumentResponse 
} from './definitions';

export class DocumentScannerWeb
  extends WebPlugin
  implements DocumentScannerPlugin {
  async scanDocument(options?: ScanDocumentOptions): Promise<ScanDocumentResponse> {
    console.log(options)
    throw this.unimplemented('Not implemented on web.');
  }
}