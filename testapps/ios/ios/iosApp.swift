//
//  iosApp.swift
//  ios
//
//  Created by Kevin Galligan on 9/10/23.
//

import SwiftUI
import orion
import Atlantis

@main
struct iosApp: App {
    //    private let handle: SDKHandle
    
    private let orionManager: OrionManager = {
        let manager = OrionManager(secureStorage: orion.SecureStorage(), bundleHelper: BundleHelper())
        manager.doInit(environment: .staging, language: "en_US", userAgent: "")
        return manager
    }()
    
    init() {
        Atlantis.start()
        
        //        self.handle = StartSDKKt.startSDK(analytics: IosAnalytics())
        //        handle.appAnalytics.appStarted()
        //        print(StartSDKKt.sayHello())
    }
    
    var body: some Scene {
        WindowGroup {
            //            BreedListScreen(
            //                viewModel: BreedViewModel(repository: handle.breedRepository, breedAnalytics: handle.breedAnalytics),
            //                breedAnalytics: handle.breedAnalytics
            //            )
            Text("Test")
                .task {
//                    orionManager.setUserId(userId: "062b0e37c68638469b0d0ad0c4621c3ab155de06be2793a6d8281f3575799c68")
                    orionManager.setPage(page: .home, system: .postBooking)
                    
                    let actionObject = ActionObject(
                        id: nil,
                        name: "TEST-EVENT-NAME",
                        type: nil,
                        text: nil,
                        location: nil,
                        href: nil,
                        value: "TEST-EVENT-VALUE"
                    )
                    
                    let event = OrionEvent.AutoCapture(eventInfo: EventInfo.companion.autoCapture(), actionObject: actionObject)
                    
                    await MainActor.run {
                        orionManager.logEvent(event: event)
                    }
                }
        }
    }
}
