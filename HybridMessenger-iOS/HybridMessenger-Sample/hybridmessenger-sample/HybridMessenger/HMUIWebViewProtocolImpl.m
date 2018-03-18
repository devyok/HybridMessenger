//
//  DWUIWebViewImpl.m
//  hybridmessenger-sample
//
//  Created by 邓伟 on 2018/3/12.
//  Copyright © 2018年 邓伟. All rights reserved.
//

#import "HMUIWebViewProtocolImpl.h"
#import <WebKit/WebKit.h>
#import "UIWebView+HMUIWebView.h"
#import "NSURL+HMNSURL.h"
#import "HMMessage.h"
#import "HybridMessenger.h"
#import <objc/message.h>

@interface HMUIWebViewProtocolImpl ()
@property(weak,nonatomic) UIWebView *webview;
@property(weak,nonatomic) id <UIWebViewDelegate> userDelegate;
@end

@implementation HMUIWebViewProtocolImpl

-(instancetype)initWithUIWebView:(UIWebView *)uiwebview {
    if ( self = [super init] ) {
        self.webview = uiwebview;
        
        self.userDelegate = self.webview.delegate;
        
        self.webview.delegate = (id<UIWebViewDelegate>)self;
    }
    return self;
}

-(NSString*)executeJavaScript:(NSString *)script {
     return [self.webview stringByEvaluatingJavaScriptFromString:script];
}

-(void)setCustomUserAgent:(NSString *)newUa {
    [self.webview setCustomUserAgent:newUa];
}

#pragma mark - WKUIDelegate
-(BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationTypeshouldStartLoadWithRequest {
    
    BOOL isSame = [[[request URL] scheme] caseInsensitiveCompare:@"HybridMessage"] == NSOrderedSame;
    
    if(isSame){
        
        NSString *value = [[request URL] getQueryParameter:@"sendMessage"];
        
        NSLog(@"value = %@",value);
        
        NSString *result = [value stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
        
        HMMessage *message = [HMMessage createWithJson:result];
        
        NSLog(@"native recieve message result = %@",message);
        
        [HybridMessenger dispatch:message];
        
        return TRUE;
        
    } else {
        BOOL userResult = [self.userDelegate webView:self.webview shouldStartLoadWithRequest:request navigationType:navigationTypeshouldStartLoadWithRequest];
        
        return userResult;
    }
    
    return TRUE;
    
}

-(void)webViewDidFinishLoad:(UIWebView *)webView {
    NSLog(@"%s",__func__);
    
    [self.userDelegate webViewDidFinishLoad:webView];
    
}

-(void)webViewDidStartLoad:(UIWebView *)webView {
    NSLog(@"%s",__func__);
    [self.userDelegate webViewDidStartLoad:webView];
}

@end
