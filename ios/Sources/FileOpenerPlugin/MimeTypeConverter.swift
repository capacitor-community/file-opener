
//
//  MimeTypeConverter.swift
//  CapacitorCommunityFileOpener
//
//  Created by Alex Ryltsov on 19.08.2022.
//

import Foundation
import MobileCoreServices

public struct MimeTypeConverter {

    public static func mimeToUti(_ mimeType: String) -> String? {
        guard let contentType = UTTypeCreatePreferredIdentifierForTag(kUTTagClassMIMEType, mimeType as CFString, nil) else { return nil }

        return contentType.takeRetainedValue() as String
    }

    public static func fileExtensionToUti(_ ext: String) -> String? {
        guard let contentType = UTTypeCreatePreferredIdentifierForTag(kUTTagClassFilenameExtension, ext as CFString, nil) else { return nil }
        return contentType.takeRetainedValue() as String
    }

}