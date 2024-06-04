// swift-tools-version:5.3
import PackageDescription

// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
let remoteKotlinUrl = "https://maven.pkg.github.com/vdenysov-appolica/KMM-shared-test/co/volodymyrdenysov/kmmtest/orion-kmmbridge/2.0.0/orion-kmmbridge-2.0.0.zip"
let remoteKotlinChecksum = "f7f3994004d9e427b75f7d6c1e4dacacffffc0d3650e97f56e4ece832a9a62e6"
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