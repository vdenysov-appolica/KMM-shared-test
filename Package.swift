// swift-tools-version:5.3
import PackageDescription

// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
let remoteKotlinUrl = "https://maven.pkg.github.com/vdenysov-appolica/KMM-shared-test/co/volodymyrdenysov/kmmtest/orion-kmmbridge/0.1.2/orion-kmmbridge-0.1.2.zip"
let remoteKotlinChecksum = "fe2cd524c88eefe154d5888fc5b70c718ba996d139f11144310d8c3d481a22e2"
let packageName = "orion"
// END KMMBRIDGE BLOCK

let package = Package(
    name: packageName,
    platforms: [
        .iOS(.v13)
    ],
    products: [
        .library(
            name: packageName,
            targets: [packageName]
        ),
    ],
    targets: [
        .binaryTarget(
            name: packageName,
            url: remoteKotlinUrl,
            checksum: remoteKotlinChecksum
        )
        ,
    ]
)