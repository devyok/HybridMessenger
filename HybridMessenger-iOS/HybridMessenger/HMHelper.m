//
//  HMUtils.m
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/14.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import "HMHelper.h"
#import <JavaScriptCore/JavaScriptCore.h>
#import <UIKit/UIWebView.h>
#import <WebKit/WKWebView.h>

@implementation HMHelper

+(NSString *)toJson:(NSDictionary *)dic {
    
    //NSJSONWritingPrettyPrinted: 这个参数会格式化json字符串，导致无法调用js的方法
    NSData *nsdata = [NSJSONSerialization dataWithJSONObject:dic options:0 error:NULL];
    
    NSString *jsonResult = [[NSString alloc] initWithData:nsdata encoding:NSUTF8StringEncoding];
    
    return jsonResult;
}

+(NSString *)makeUUID {
    return [[[NSUUID alloc] init] UUIDString];
}

+(void)enableJavaScriptConsoleLog :(id) webview{
    
    if([webview isKindOfClass:[UIWebView class]]) {
        JSContext *jscontext = [(UIWebView*)webview valueForKeyPath:@"documentView.webView.mainFrame.javaScriptContext"];
        jscontext[@"console"][@"log"] = ^(JSValue * msg) {
            NSLog(@"**Html-Info: %@", msg);
        };
        jscontext[@"console"][@"warn"] = ^(JSValue * msg) {
            NSLog(@"**Html-Warn: %@", msg);
        };
        jscontext[@"console"][@"error"] = ^(JSValue * msg) {
            NSLog(@"**Html_Error: %@", msg);
        };
    }
    
    
    
}

@end
