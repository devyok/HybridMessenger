//
//  UIWebView+DWUIWebView.m
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/11.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import "UIWebView+HMUIWebView.h"
#import <objc/message.h>

@implementation UIWebView (HMUIWebView)
-(void)setCustomUserAgent:(NSString *)newUserAgent {
    id webDocumentView;
    // suppress the warnings
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wundeclared-selector"
    webDocumentView = ((id(*)(id, SEL))objc_msgSend)((UIWebView*) self, @selector(_documentView));
#pragma clang diagnostic pop
    
    Ivar ivar = class_getInstanceVariable([webDocumentView class], "_webView");
    id webView = object_getIvar(webDocumentView, ivar);
    
    NSString *selString = [@"setCustom" stringByAppendingString:@"UserAgent:"];
    SEL sel = NSSelectorFromString(selString);
    [webView performSelector:sel withObject:newUserAgent];
    
}

-(NSString *)getCurrentUserAgentString {
    NSString *javascriptcmd = @"navigator.userAgent";

    return [self stringByEvaluatingJavaScriptFromString:javascriptcmd];
}

@end
