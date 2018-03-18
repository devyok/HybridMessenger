//
//  HybridMessenger.h
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/12.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "HMWebViewProtocol.h"


@class HMMessage;

typedef void (^HybridMessageCallback)(HMMessage *message);

@interface HybridMessenger : NSObject

/**
 * must be call this method , otherwise the HMWebViewNotBindException will appear
 */
+(HybridMessenger*) bindWebView:(id) webview;

/**
 * dispatch this message to all callback
 */
+(BOOL) dispatch: (HMMessage*) message;

/**
 * the same URL will be executed callback
 */
+(HybridMessenger*) registerCallbck: (NSURL*) url :(HybridMessageCallback) callback;
/**
 * delete this url‘s callbacks
 */
+(HybridMessenger*) unregisterCallbck: (NSURL*) url;

/**
 * send message to h5
 */
+(void) sendHybridMessage: (HMMessage*) message;
/**
 * send message to h5
 * @param callback when the callback invoked, it is deleted
 */
+(void) sendHybridMessage: (HMMessage*) message :(HybridMessageCallback) callback;

+(NSString*) defaultCustomUserAgentString:(NSString *)oldUa;
/**
 * custom user agent by this method
 */
+(void)setCustomUserAgent:(NSString *)newUa ;

@end
