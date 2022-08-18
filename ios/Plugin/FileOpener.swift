import Foundation

@objc public class FileOpener: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
