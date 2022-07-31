import { Capacitor } from '@capacitor/core'
import { DocumentScanner, ScanDocumentResponseStatus } from 'capacitor-document-scanner'

/**
 * an example showing how to use the document scanner
 */
const scanDocument = async (): Promise<void> => {
  try {
    // start the document scanner, and end after 1 photo
    const { scannedImages, status } = await DocumentScanner.scanDocument({
      maxNumDocuments: 1
    })
  
    // get the html image
    const scannedImage = document.getElementById('scannedImage') as HTMLImageElement
  
    if (status === ScanDocumentResponseStatus.Success && scannedImages?.length) {
      // set the image src to the scanned image file path
      scannedImage.src = Capacitor.convertFileSrc(scannedImages[0])

      // show the scanned image
      scannedImage.style.display = 'block'
    } else if (status === ScanDocumentResponseStatus.Cancel) {
      // user exited camera
      alert('user canceled document scan')
    }
  } catch (error) {
    // something went wrong during the document scan
    alert(error)
  }
}

scanDocument()