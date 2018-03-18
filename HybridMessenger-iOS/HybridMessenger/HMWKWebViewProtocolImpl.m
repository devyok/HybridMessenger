//
//  HMWKWebViewImpl.m
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/12.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import "HMWKWebViewProtocolImpl.h"
#import "HMMessage.h"
#import "NSURL+HMNSURL.h"
#import <WebKit/WebKit.h>

@interface HMWKWebViewProtocolImpl ()
@property(nonatomic) WKWebView *webview;
@property(weak,nonatomic) id<WKNavigationDelegate> userWKNavigationDelegate;
@end

@implementation HMWKWebViewProtocolImpl

-(instancetype)initWithWKWebView:(WKWebView *)wkwebview {
    if ( self = [super init] ) {
        self.webview = wkwebview;
        _userWKNavigationDelegate = self.webview.navigationDelegate;
        self.webview.navigationDelegate = self;
    }
    return self;
}

-(NSString*)executeJavaScript:(NSString *)script {
    [self.webview evaluateJavaScript:script completionHandler:nil];
    return nil;
}

-(void)setCustomUserAgent:(NSString *)newUa {
    [self.webview setCustomUserAgent:newUa];
}

-(void)dealloc {
    self.webview = nil;
    self.userWKNavigationDelegate = nil;
}

-(void)webView:(WKWebView *)webView decidePolicyForNavigationAction:(WKNavigationAction *)navigationAction decisionHandler:(void (^)(WKNavigationActionPolicy))decisionHandler {
    NSLog(@"%s",__func__);
    
    BOOL isSame = [[[navigationAction.request URL] scheme] caseInsensitiveCompare:@"HybridMessage"] == NSOrderedSame;
    
    if(isSame){
        
        NSString *value = [[navigationAction.request URL] getQueryParameter:@"sendMessage"];
        
        NSLog(@"value = %@",value);
        
        NSString *result = [value stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
        
        HMMessage *message = [HMMessage createWithJson:result];
        
        NSLog(@"native recieve message result = %@",message);
        
        [HybridMessenger dispatch:message];
        
        decisionHandler(WKNavigationActionPolicyCancel);
        
        return ;
        
    } else {
        if(_userWKNavigationDelegate){
            [self.userWKNavigationDelegate webView:webView decidePolicyForNavigationAction:navigationAction decisionHandler:decisionHandler];
            
            return ;
        }
    }
    decisionHandler(WKNavigationActionPolicyAllow);
}

@end
