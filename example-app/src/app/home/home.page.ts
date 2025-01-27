import { Component } from '@angular/core';
import {
  IonHeader, IonButton, IonToolbar, IonTitle, IonContent, IonButtons,
} from '@ionic/angular/standalone';

// NATIVE
import { Camera, CameraSource, CameraResultType } from '@capacitor/camera';
import { FileOpener, FileOpenerOptions } from '@capacitor-community/file-opener';

// MODELS
import { FILE_OPENER_PLUGIN_ERRORS } from '../models/base';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  imports: [
    IonButton, IonHeader, IonToolbar, IonTitle, IonContent, IonButtons
  ]
})
export class HomePage {

  constructor() { }

  public async openFile(): Promise<void> {

    const photo = await Camera.getPhoto({
      quality: 100,
      allowEditing: false,
      resultType: CameraResultType.Uri,
      saveToGallery: true,
      correctOrientation: true,
      source: CameraSource.Prompt
    });

    if (photo.path) {
      const fileOpenerOptions: FileOpenerOptions = {
        filePath: photo.path,
        contentType: 'image/jpeg',
        openWithDefault: true
      };
      try {
        await FileOpener.open(fileOpenerOptions);
      } catch (error) {
        if ((error as { code?: string; message?: string })?.code === FILE_OPENER_PLUGIN_ERRORS.FILE_NOT_SUPPORTED) {
          console.error(`File ${photo.path} is not supported and can\'t be opened. Please make sure that the required application installed to handle this file format.`);
        } else if ((error as { code?: string; message?: string })?.code === FILE_OPENER_PLUGIN_ERRORS.FILE_NOT_FOUND) {
          console.error(`File ${photo.path} is not found.`);
        } else {
          console.error('Can\'t open the file: ' + JSON.stringify(error));
        }
      }
    }

  };

}
