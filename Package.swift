// swift-tools-version:5.3
import PackageDescription

// BEGIN KMMBRIDGE VARIABLES BLOCK (do not edit)
let remoteKotlinUrl = "https://maven.pkg.github.com/vdenysov-appolica/KMM-shared-test/co/volodymyrdenysov/kmmtest/orion-kmmbridge/1.6.0/orion-kmmbridge-1.6.0.zip"
let remoteKotlinChecksum = "569845c282ef501dfaf481a69b4a08c758c28c9a748457d9c4fa74cee6417b4a"
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