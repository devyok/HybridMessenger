//
//  HMUtils.h
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/14.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface HMHelper : NSObject

+(NSString*) toJson :(NSDictionary*) dic;

+(NSString*) makeUUID;

+(void) enableJavaScriptConsoleLog :(id) webview;

@end
