// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorCommunityFileOpener",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapacitorCommunityFileOpener",
            targets: ["FileOpenerPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "FileOpenerPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Plugin"),
        .testTarget(
            name: "FileOpenerPluginTests",
            dependencies: ["FileOpenerPlugin"],
            path: "ios/PluginTests")
    ]
)